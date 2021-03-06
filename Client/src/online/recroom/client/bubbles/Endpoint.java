package online.recroom.client.bubbles;

import com.google.gson.Gson;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import online.recroom.client.Scener;
import online.recroom.messages.*;
import online.recroom.messages.bubble.messages.*;

import javax.websocket.*;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;

import static online.recroom.messages.bubble.enums.BubbleMessages.GAME_STARTED;
import static online.recroom.messages.bubble.enums.BubbleMessages.PLAYER_JOINED;

/**
 * Created by Yeedle on 5/17/2016 9:32 AM.
 */
//TODO change messagedecoder
@ClientEndpoint (decoders = {MessageDecoder.class},
        encoders = {MessageEncoder.class})
public class Endpoint
{
    private Session session;
    private Controller controller;
    private String username = "Anonymous";
    private String  WebSocketURI;

    public Endpoint(String username, Controller controller)
    {
        this.username = username.isEmpty()? "Anonymous" : username;
        WebSocketURI = "ws://localhost:8080/recroom/bubblesgame?username=" + this.username;
        this.controller = controller;
    }

    @OnOpen
    public void onOpen(final Session session)
    {
        this.session = session;
        this.session = session;
        controller.setEndpoint(this);
        controller.console("Hello, " + username + "!");
    }

    @OnMessage
    public void onMessage(Message message) throws IOException
    {

        Gson gson = new Gson();
        System.out.println(message.json);
        switch (message.type)
        {
            case GAME_STARTED:controller.handleMessage(gson.fromJson(message.json, GameStarted.class));
                break;
            case GAME_PENDING: controller.handleMessage(gson.fromJson(message.json, GamePending.class));
                break;
            case PLAYER_JOINED: controller.handleMessage(gson.fromJson(message.json, PlayerJoined.class));
                break;
            case PLAYER_LEFT: controller.handleMessage(gson.fromJson(message.json, PlayerLeft.class));
                break;
            case BUBBLE_POPPED: controller.handleMessage(gson.fromJson(message.json, BubblePoppedMessage.class));
                break;
            case GAME_OVER: controller.handleMessage(gson.fromJson(message.json, GameOver.class));
                break;
            default: controller.console("The server sent me some weird binary :/");
        }
    }

    @OnError
    public void onError(Throwable t)
    {
        controller.error(t);
    }

    @OnClose
    public void onClose(CloseReason cr) throws IOException, InterruptedException
    {
        System.out.println(cr.getCloseCode() + " " + cr.getReasonPhrase() +" " +cr.toString());
        if (cr.getReasonPhrase().equals("Game over"))
        {
            final long MILISECONDS = 7000;
            Thread.sleep(MILISECONDS);
        }
        final String PATH = "../welcome/welcome.fxml";
        FXMLLoader loader = Scener.getLoader(PATH, this.getClass());
        Parent root = loader.load();
        Platform.runLater(() ->Scener.showScene(controller.getStage(), root));
    }

    public void sendMessage(final Long id) throws IOException
    {
        session.getBasicRemote().sendText(Long.toString(id));
    }

    public void sendMessage(final online.recroom.messages.Message m) throws IOException, EncodeException
    {
        session.getBasicRemote().sendObject(m);
    }

    private void onJoinedGame(Bubble.ServerBubble[] serverBubbles, Player[] players)
    {
        Bubble[] bubbles = generateBubblesFromServerBubbles(serverBubbles);
        controller.gameJoined(bubbles, players);
    }


    private void OnGameStarted(Bubble.ServerBubble[] serverBubbles, Player[] players)
    {
        Bubble[] bubbles = generateBubblesFromServerBubbles(serverBubbles);
        controller.gameStarted(bubbles, players);
    }

    private Bubble[] generateBubblesFromServerBubbles(Bubble.ServerBubble[] serverBubbles)
    {
        Bubble[] bubbles = new Bubble[serverBubbles.length];
        for (int i = 0; i < serverBubbles.length ; i++)
            bubbles[i] = new Bubble(serverBubbles[i]);
        return bubbles;
    }

    private void OnBubblePopped(long poppedBubbleId)
    {
        controller.bubblePopped(poppedBubbleId);
    }


    private void OnGameOver(String winner, int score) throws IOException
    {
        controller.gameOver(winner, score);
        session.close(new CloseReason(CloseReason.CloseCodes.NORMAL_CLOSURE, "Game over"));
    }


    public void connect() throws URISyntaxException, IOException, DeploymentException
    {
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            container.connectToServer(this, new URI(WebSocketURI));
    }

    public void closeConnection(CloseReason.CloseCodes closereason)
    {
        try {this.session.close(new CloseReason(closereason, "User closed game"));}
        catch (IOException e) {e.printStackTrace();}
    }

    public void sendPong()
    {
        try
        {
            session.getBasicRemote().sendPong(ByteBuffer.allocate(10));
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
