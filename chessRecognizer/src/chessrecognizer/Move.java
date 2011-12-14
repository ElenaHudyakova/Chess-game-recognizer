/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chessrecognizer;

/**
 *
 * @author Lenkas
 */
public class Move {
    private int id;
    private int partyID;
    private int moveNumber;
    private String move;
    private int player;
    
    public final static int BLACK_PLAYER = 1;
    public final static int WHITE_PLAYER = 0;

    public Move(int id, int partyID, int moveNumber, String move, int player) {
        this.id = id;
        this.partyID = partyID;
        this.moveNumber = moveNumber;
        this.move = move;
        this.player = player;
    }

    public Move(int partyID, int moveNumber, String move, int player) {
        this.partyID = partyID;
        this.moveNumber = moveNumber;
        this.move = move;
        this.player = player;
    }

    public static int getBLACK_PLAYER() {
        return BLACK_PLAYER;
    }

    public static int getWHITE_PLAYER() {
        return WHITE_PLAYER;
    }

    public int getId() {
        return id;
    }

    public String getMove() {
        return move;
    }

    public int getMoveNumber() {
        return moveNumber;
    }

    public int getPartyID() {
        return partyID;
    }

    public int getPlayer() {
        return player;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMove(String move) {
        this.move = move;
    }

    public void setMoveNumber(int moveNumber) {
        this.moveNumber = moveNumber;
    }

    public void setPartyID(int partyID) {
        this.partyID = partyID;
    }

    public void setPlayer(int player) {
        this.player = player;
    }

    @Override
    public String toString() {
        return "Move{" + "id=" + id + ", partyID=" + partyID + ", moveNumber=" + moveNumber + ", move=" + move + ", player=" + player + '}';
    }
  
}
