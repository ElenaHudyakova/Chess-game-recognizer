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
    public Integer fileFrom = null;
    public Integer fileTo = null;
    public Integer rankFrom = null;
    public Integer rankTo = null;
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

    public void parseMove(){
        if ("O-O".equals(moveContent)){
            isKingCastling = true;
            return;
        }
        if ("O-O-O".equals(moveContent)){
            isQueenCastling = true;
            return;
        }           
        
        Pattern movePattern = Pattern.compile("([KRQNB]?)([a-h]?)([1-8]?)(x?)([a-h])([1-8])(=?)([KRQNB]?)([\\+#]?)");       
        
        Matcher matcher = movePattern.matcher(this.moveContent);
        if (matcher.find()){
            isCheck = ("+".equals(matcher.group(9)));
            isCheckmate = ("#".equals(matcher.group(9)));
            isCaptionMove = ("x".equals(matcher.group(4)));
            if (!"".equals(matcher.group(7))){
                isPromotion = true;
                promotionPiece = parsePieceType(matcher.group(8));
            }
            if (!"".equals(matcher.group(1))){
                movingPiece = parsePieceType(matcher.group(1));
            } else
                movingPiece = PawnPiece.class;
                        
            if (!"".equals(matcher.group(2))){
                
                fileFrom = parseFile(matcher.group(2));
            }
            if (!"".equals(matcher.group(3))){
                rankFrom = parseRank(matcher.group(3));
            }            
            fileTo = parseFile(matcher.group(5));
            rankTo = parseRank(matcher.group(6));
        } else{
            System.out.print(this.moveContent + " " + this.moveNumber);
            throw new RuntimeException("Invalid move");
        }
            
        
    }
    
    private Class parsePieceType(String type) {
        if (type.equals("K"))
            return KingPiece.class;
        if (type.equals("R"))
            return RookPiece.class;
        if (type.equals("Q"))
            return QueenPiece.class;
        if (type.equals("N"))
            return KnightPiece.class;
        if (type.equals("B"))
            return BishopPiece.class;
        throw new RuntimeException("Invalid move");
    }
    
    private int parseRank(String rank){
        int intRank = Integer.parseInt(rank);
        if ((intRank<=8)&&(intRank>=1))
            return Integer.parseInt(rank);
        else
            throw new RuntimeException("Invalid move");
    }

    private int parseFile(String file){        
        if (file.equals("a"))
            return 1;
        if (file.equals("b"))
            return 2;
        if (file.equals("c"))
            return 3;
        if (file.equals("d"))
            return 4;
        if (file.equals("e"))
            return 5;
        if (file.equals("f"))
            return 6;
        if (file.equals("g"))
            return 7;
        if (file.equals("h"))
            return 8;                
        throw new RuntimeException("Invalid move");            
    }

    public int getPlayer() {
        return player;
    }
  
}
