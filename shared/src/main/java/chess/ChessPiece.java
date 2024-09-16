package chess;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {

    ChessGame.TeamColor pieceColor;
    PieceType type;
    public ChessPiece(ChessGame.TeamColor pieceColor, PieceType type) {
        this.pieceColor = pieceColor;
        this.type = type;
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return pieceColor;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return type;
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> moves = new ArrayList<>();
        if (type == PieceType.BISHOP) {
            int modifier = 1;
            while (true) { //Check diagonal right up
                ChessPosition currPosition = new ChessPosition(myPosition.getRow() + modifier,
                        myPosition.getColumn() + modifier);
                if (currPosition.getRow() >= 8 || currPosition.getColumn() >= 8 || board.getPiece(currPosition) != null) {
                    break;
                }
                ChessMove move = new ChessMove(myPosition, currPosition, null);
                moves.add(move);
                modifier++;
            }
            modifier = 1;
            while (true) { //Check diagonal right down
                ChessPosition currPosition = new ChessPosition(myPosition.getRow() - modifier,
                        myPosition.getColumn() + modifier);
                if (currPosition.getRow() < 1 || currPosition.getColumn() >= 8 || board.getPiece(currPosition) != null) {
                    break;
                }
                ChessMove move = new ChessMove(myPosition, currPosition, null);
                moves.add(move);
                modifier++;
            }
            modifier = 1;
            while (true) { //Check diagonal left up
                ChessPosition currPosition = new ChessPosition(myPosition.getRow() + modifier,
                        myPosition.getColumn() - modifier);
                if (currPosition.getRow() >= 8 || currPosition.getColumn() < 1 || board.getPiece(currPosition) != null) {
                    break;
                }
                ChessMove move = new ChessMove(myPosition, currPosition, null);
                moves.add(move);
                modifier++;
            }
            modifier = 1;
            while (true) { //Check diagonal left down
                ChessPosition currPosition = new ChessPosition(myPosition.getRow() - modifier,
                        myPosition.getColumn() - modifier);
                if (currPosition.getRow() < 1 || currPosition.getColumn() >= 8 || board.getPiece(currPosition) != null) {
                    break;
                }
                ChessMove move = new ChessMove(myPosition, currPosition, null);
                moves.add(move);
                modifier++;
            }
        }
        else if (type == PieceType.ROOK) {

        }
        return moves;
    }
}
