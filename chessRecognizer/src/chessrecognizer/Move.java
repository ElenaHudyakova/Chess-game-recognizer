/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chessrecognizer;


import chessrecognizer.pieces.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Lenkas
 */
public class Move {
    private int id;
    private int partyID;
    private int moveNumber;
    private String moveContent;
    private int player;
    
    //REFACTORING?
    public boolean isCheck = false;
    public boolean isCheckmate = false;
    public boolean isCaptionMove = false;

    public boolean isKingCastling = false;
    public boolean isQueenCastling = false;
    public boolean isPromotion = false;
    public Class promotionPiece = null;
    public ChessPosition moveToPosition = new ChessPosition();
    public ChessPosition moveFromPosition = new ChessPosition();
    public Class movingPiece = null;

    public int getMoveNumber() {
        return moveNumber;
    }

    public String getMoveContent() {
        return moveContent;
    }

    public int getPartyID() {
        return partyID;
    }
    
    public Move(int partyID, int moveNumber, String moveContent, int player) {
        this.partyID = partyID;
        this.moveNumber = moveNumber;
        this.moveContent = moveContent;
        this.player = player;
    }
    
    @Override
    public String toString() {
        if (player==Piece.WHITE_PLAYER)
            return moveNumber + "     " + moveContent;
        else
            return moveNumber + "                    " + moveContent;
    } 

    public int getPlayer() {
        return player;
    }
  
}
