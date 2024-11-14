import chess.*;

public class Main {
    public static void main(String[] args) {
        var piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        System.out.println("♕ 240 Chess client.Client: " + piece);

        var serverUrl = "http://localhost:8080";
        new Repl(serverUrl).run();
    }
}