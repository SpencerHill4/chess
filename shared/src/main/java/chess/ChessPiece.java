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
            for (int i = 0; i < 4; i++) {
                int modifier = 1;
                while (true) {
                    int rowMod = modifier;
                    int colMod = modifier;
                    if (i == 1 || i == 3) { rowMod *= -1; }
                    if (i == 2 || i == 3) { colMod *= -1; }

                    ChessPosition currPosition = new ChessPosition(myPosition.getRow() + rowMod,
                            myPosition.getColumn() + colMod);

                    if (currPosition.getRow() > 8 || currPosition.getRow() < 1) { break; }
                    if (currPosition.getColumn() > 8 || currPosition.getColumn() < 1) { break; }
                    if (board.getPiece(currPosition) != null) {
                        if (board.getPiece(currPosition).getTeamColor() == getTeamColor()) {
                            break;
                        }
                    }

                    ChessMove move = new ChessMove(myPosition, currPosition, null);
                    moves.add(move);
                    modifier++;

                    if (board.getPiece(currPosition) != null) {
                        if (board.getPiece(currPosition).getTeamColor() != getTeamColor()) {
                            break;
                        }
                    }
                }
            }
        }
        else if (type == PieceType.KING) {
            int rowMod = 1;
            for (int i = 0; i < 3; i++) {
                int colMod = 1;
                for (int j = 0; j < 3; j++) {
                    ChessPosition currPosition = new ChessPosition(myPosition.getRow() + rowMod,
                            myPosition.getColumn() + colMod);

                    if (currPosition.getRow() > 8 || currPosition.getRow() < 1) { break; }
                    if (currPosition.getColumn() <= 8 && currPosition.getColumn() >= 1
                            && currPosition != myPosition) {
                        if (board.getPiece(currPosition) != null) {
                            if (board.getPiece(currPosition).getTeamColor() == getTeamColor()) {
                                break;
                            }
                        }
                        ChessMove move = new ChessMove(myPosition, currPosition, null);
                        moves.add(move);
                    }

                    colMod--;

                    /*if (board.getPiece(currPosition) != null) {
                        if (board.getPiece(currPosition).getTeamColor() != getTeamColor()) {
                            break;
                        }
                    }*/
                }
                rowMod--;
            }
        }
        return moves;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) { return true; }
        if (object == null || getClass() != object.getClass()) { return false; }
        ChessPiece piece = (ChessPiece) object;
        return (pieceColor.equals(piece.pieceColor) && type.equals(piece.type));
    }

    @Override
    public int hashCode() {
        var typeCode = (type == null ? 9 : type.ordinal());
        var colorCode = (type == null ? 7 : pieceColor.ordinal());
        return (71 * colorCode) + typeCode;
    }

    @Override
    public String toString() {
        return String.format("%s:%s", pieceColor.toString(), type.toString());
    }
    // test
}
