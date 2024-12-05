package server.websocket;

import org.eclipse.jetty.websocket.api.Session;

import java.io.IOException;

public class Connection {
    private String authToken;
    private int gameID;
    private String color;
    private Session session;

    public Connection(String authToken, int gameID, String color, Session session) {
        this.authToken = authToken;
        this.gameID = gameID;
        this.color = color;
        this.session = session;
    }

    public String getAuthToken() {
        return authToken;
    }

    public int getGameID() {
        return gameID;
    }

    public String getColor() {
        return color;
    }

    public Session getSession() {
        return session;
    }

    public void send(String message) throws IOException {
        if (session != null && session.isOpen()) {
            session.getRemote().sendString(message);
        }
    }
}
