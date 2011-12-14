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
    private String moveContent;
    private int player;
    
    public final static int BLACK_PLAYER = 1;
    public final static int WHITE_PLAYER = 0;

    public Move() {
        id = -1;
        partyID = -1;
    }

    public Move(int partyID, int moveNumber, String moveContent, int player) {
        this.partyID = partyID;
        this.moveNumber = moveNumber;
        this.moveContent = moveContent;
        this.player = player;
    }
    
    

    public int getId() {
        return id;
    }

    public String getMoveContent() {
        return moveContent;
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

    public void setMoveContent(String moveContent) {
        this.moveContent = moveContent;
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
        return "Move{" + "id=" + id + ", partyID=" + partyID + ", moveNumber=" + moveNumber + ", moveContent=" + moveContent + ", player=" + player + '}';
    }
  
}
