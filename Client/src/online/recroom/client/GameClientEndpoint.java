package online.recroom.client;

import javax.websocket.*;
import javax.websocket.ClientEndpoint;
import java.io.IOException;
import java.net.URI;
import java.util.logging.Logger;

/**
 * Created by Yeedle on 4/5/2016 10:41 PM.
 */
@ClientEndpoint
public class GameClientEndpoint
{
    private Session session;
    private static final Logger LOGGER = Logger.getLogger(RecRoom.class.getName());

    public GameClientEndpoint(final URI uri)
    {
        connectToWebSocket(uri);
    }

    @OnOpen
    public void onOpen(final Session session)
    {
        this.session = session;
    }

    @OnClose
    public void onClose(final Session session, final CloseReason reason)
    {
        System.out.println(reason.getReasonPhrase());
        this.session = null;
    }

    @OnMessage
    public void onMessage(final String string){
        //TODO: Create a decoder to handle incoming POJOs.
    }

    public void sendMessage(final String o)
    {
        try
        {
            session.getBasicRemote().sendText(o);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private void connectToWebSocket(final URI uri)
    {
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        try
        {
            container.connectToServer(this, uri);
        } catch (Exception e)
        {
            e.printStackTrace();
        }

    }
}

