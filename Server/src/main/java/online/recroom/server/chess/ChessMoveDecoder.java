package online.recroom.server.chess;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

/**
 * Created by Yeedle on 4/11/2016 9:00 PM.
 */
public class ChessMoveDecoder implements Decoder.Text<Movement>
{
    @Override
    public Movement decode(String s) throws DecodeException
    {
        //here the JSON string gets Objectified into a IMovable
        return null;
    }

    @Override
    public boolean willDecode(String s)
    {
        //test whether incoming JSON can be objectified before passing it on to decode
        return false;
    }

    @Override
    public void init(EndpointConfig endpointConfig)
    {

    }

    @Override
    public void destroy()
    {

    }
}
