package acme.demo;

import javax.ejb.Stateless;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.Date;

/**
 * The type SampleWebSocketEndpoint
 *
 * @author Peter Pilgrim
 */
@ServerEndpoint("/sample-websocket")
@Stateless
public class SampleWebSocketEndpoint {

    @OnMessage
    public String echoMe( String message )
    {
        return String.format("ECHO: [%s] %s", new Date(), message );
    }

    @OnOpen
    public void open( Session session ) {
        System.out.printf("%s.open( session=%s )\n",
                getClass().getSimpleName(), session );
    }

    @OnClose
    public void close( Session session ) {
        System.out.printf("%s.close( session=%s )\n",
                getClass().getSimpleName(), session );
    }

    @OnError
    public void error( Session session, Throwable error ){
        System.err.printf("%s.onError( session=%s, error=%s )\n",
                getClass().getSimpleName(), session, error);
        error.printStackTrace(System.err);
    }
}
