import chess.ChessGame;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import model.GameData;
import model.UserData;

import java.io.*;
import java.net.*;

public class ServerFacade {
    private final String serverUrl;

    public ServerFacade(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    public String createUser(UserData user) throws Exception {
        var path = "/user";
        var result = makeRequest("POST", path, user, LinkedTreeMap.class, null);
        return (String) result.get("authToken");
    }

    public String loginUser(UserData user) throws Exception {
        var path = "/session";
        var result = makeRequest("POST", path, user, LinkedTreeMap.class, null);
        return (String) result.get("authToken");
    }

    public void logoutUser(String authToken) throws Exception {
        var path = "/session";
        makeRequest("DELETE", path, null, Object.class, authToken);
    }

    public String listGames(String authToken) throws Exception {
        var path = "/game";
        var result = makeRequest("GET", path, null, GameData.class, authToken);
        if (result.gameName() != null) {
            return "LIST";
        } else {
            return "No games! Do 'create <NAME>' to create one!";
        }
    }

    public void createGame() throws Exception {
    }

    public void joinGame() throws Exception {
    }

    public void clear() throws Exception {
    }

    private <T> T makeRequest(String method, String path, Object request, Class<T> responseClass, String authToken) throws Exception {
        try {
            URL url = (new URI(serverUrl + path)).toURL();
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod(method);
            http.setDoOutput(true);

            if (authToken != null) {
                http.addRequestProperty("Authorization", authToken);
            }

            writeBody(request, http);
            http.connect();
            throwIfNotSuccessful(http);
            return readBody(http, responseClass);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    private static void writeBody(Object request, HttpURLConnection http) throws IOException {
        if (request != null) {
            http.addRequestProperty("Content-Type", "application/json");
            String reqData = new Gson().toJson(request);
            try (OutputStream reqBody = http.getOutputStream()) {
                reqBody.write(reqData.getBytes());
            }
        }
    }

    private void throwIfNotSuccessful(HttpURLConnection http) throws Exception {
        var status = http.getResponseCode();
        if (!isSuccessful(status)) {
            throw new Exception();
        }
    }

    private static <T> T readBody(HttpURLConnection http, Class<T> responseClass) throws IOException {
        T response = null;
        if (http.getContentLength() < 0) {
            try (InputStream respBody = http.getInputStream()) {
                InputStreamReader reader = new InputStreamReader(respBody);
                if (responseClass != null) {
                    response = new Gson().fromJson(reader, responseClass);
                }
            }
        }
        return response;
    }

    private boolean isSuccessful(int status) {
        return status / 100 == 2;
    }
}
