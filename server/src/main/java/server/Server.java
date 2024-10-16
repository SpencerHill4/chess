package server;

import com.google.gson.Gson;
import model.AuthData;
import model.UserData;
import service.LoginService;
import service.LogoutService;
import service.RegisterService;
import spark.*;
import exception.ResponseException;

public class Server {
    private RegisterService registerService;
    private LoginService loginService;
    private LogoutService logoutService;

    public Server() {
    }

    public Server(RegisterService registerService, LoginService loginService, LogoutService logoutService) {
        this.registerService = registerService;
        this.loginService = loginService;
        this.logoutService = logoutService;
    }

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.
        Spark.post("/user", this::register);
        Spark.post("/session", this::login);
        Spark.delete("/session", this::logout);
        Spark.exception(ResponseException.class, this::exceptionHandler);

        //This line initializes the server and can be removed once you have a functioning endpoint 
        Spark.init();

        Spark.awaitInitialization();
        return Spark.port();
    }

    private void exceptionHandler(ResponseException ex, Request req, Response res) {
        res.status(ex.StatusCode());
    }

    private Object register(Request request, Response response) throws ResponseException {
        var user = new Gson().fromJson(request.body(), UserData.class);
        var existingUser = registerService.getUser(user.getUsername());

        if (existingUser == null) {
            registerService.createUser(user);

            AuthData authData = new AuthData(user.getUsername());
            registerService.createAuth(authData);

            return new Gson().toJson(authData);
        } else {
            throw new ResponseException(403, "Error: already taken");
        }
    }

    private Object login(Request request, Response response) throws ResponseException {
        var user = new Gson().fromJson(request.body(), UserData.class);

        if (loginService.getUser(user.getUsername()) != null) {
            AuthData authData = new AuthData(user.getUsername());
            loginService.createAuth(authData);
            return new Gson().toJson(authData);
        } else {
            throw new ResponseException(0, "");
        }
    }

    private Object logout(Request request, Response response) throws ResponseException {
        var object = new Gson().fromJson(request.body(), Object.class);
        var authToken = object.toString();

        if (logoutService.getAuth(authToken) != null) {
            return "";
        } else {
            throw new ResponseException(0, "");
        }
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
