/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chessrecognizer;

import chessrecognizer.Figure.Type;
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
    private Figure.ChessColor player;

    public Move() {
        id = -1;
        partyID = -1;
    }

    public Move(int partyID, int moveNumber, String moveContent, Figure.ChessColor player) {
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

    public Figure.ChessColor getPlayer() {
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

    public void setPlayer(Figure.ChessColor player) {
        this.player = player;
    }

    @Override
    public String toString() {
        return "Move{" + "id=" + id + ", partyID=" + partyID + ", moveNumber=" + moveNumber + ", moveContent=" + moveContent + ", player=" + player + '}';
    }


    public Type parseFigureTypeToMove() {
        Pattern movePattern = Pattern.compile("([KRQNB])");       
        Matcher matcher = movePattern.matcher(this.moveContent);
        if (matcher.find()){
                if (matcher.group(1).equals("K"))
                    return Type.king;
                if (matcher.group(1).equals("R"))
                    return Type.rook;
                if (matcher.group(1).equals("Q"))
                    return Type.queen;
                if (matcher.group(1).equals("N"))
                    return Type.knight;
                if (matcher.group(1).equals("B"))
                    return Type.bishop;
                return Type.noType;
        }
        else
            return Type.pawn;
    }
    
    public int parseRankMoveTo(){
        Pattern movePattern = Pattern.compile(".+([1-8])");       
        Matcher matcher = movePattern.matcher(this.moveContent);
        if (matcher.find())
            return Integer.parseInt(matcher.group(1));
        else
            throw new RuntimeException("Invalid move");
    }

    public int parseFileMoveTo(){
        Pattern movePattern = Pattern.compile(".*([a-h])[1-8]");       
        Matcher matcher = movePattern.matcher(this.moveContent);
        if (matcher.find()){
                if (matcher.group(1).equals("a"))
                    return 1;
                if (matcher.group(1).equals("b"))
                    return 2;
                if (matcher.group(1).equals("c"))
                    return 3;
                if (matcher.group(1).equals("d"))
                    return 4;
                if (matcher.group(1).equals("e"))
                    return 5;
                if (matcher.group(1).equals("f"))
                    return 6;
                if (matcher.group(1).equals("g"))
                    return 7;
                if (matcher.group(1).equals("h"))
                    return 8;                
                throw new RuntimeException("Invalid move");            
        }            
        else
            throw new RuntimeException("Invalid move");
    }    
  
}
