package online.recroom.client;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;

/**
 * Created by Yeedle on 4/5/2016 10:41 PM.
 */
@ClientEndpoint
public class GameClientEndpoint
{
    private javax.websocket.Session session;


    public GameClientEndpoint(final URI uri)
    {
        connectToWebSocket(uri);
    }

    @OnOpen
    public void onOpen(final javax.websocket.Session session)
    {
        this.session = session;
    }

    @OnClose
    public void onClose(final javax.websocket.Session session, final CloseReason reason)
    {
        System.out.println(reason.getReasonPhrase());
        this.session = null;
    }

    @OnMessage
    public void onMessage(final String string){
        System.out.println("incoming msg: " + string);
        //TODO: Create a decoder to handle incoming POJOs.
    }


    /**
     * Handles outgoing messages
     * @param o outgoing message
     */
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

