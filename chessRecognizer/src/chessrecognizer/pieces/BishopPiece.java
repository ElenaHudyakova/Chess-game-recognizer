/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chessrecognizer.pieces;

import chessrecognizer.ChessPosition;
import chessrecognizer.View;

/**
 *
 * @author Lenkas
 */
public class BishopPiece extends Piece {
    public BishopPiece(ChessPosition position, int chessColor) {
        super(position, chessColor);
    }
    
    public BishopPiece(){     
    }
        
    public int getSerializationCode(){
        return 0+this.player*8;
    }
    
    @Override
    public boolean isThereObstacle(ChessPosition positionMoveTo, View view){
        int fileDirection, rankDirection;
        
        if (Math.abs(position.getFile()-positionMoveTo.getFile())!=
                Math.abs(position.getRank()-positionMoveTo.getRank()))
            return true;
        
        if (positionMoveTo.getFile()>position.getFile())
            fileDirection = 1;
        else
            fileDirection = -1;
        
        if (positionMoveTo.getRank()>position.getRank())
            rankDirection = 1;
        else
            rankDirection = -1;
        
        for (int iterator=1; iterator<Math.abs(position.getRank()-positionMoveTo.getRank()); iterator++)
             if (view.getPiece(new ChessPosition(position.getFile()+(fileDirection)*iterator, 
                     position.getRank()+(rankDirection)*iterator))!=null)
                return true; 
        return false;           
    }
    
    public String getImagePath(){
        if (player==Piece.BLACK_PLAYER)
            return "images/bishop_black.png";
        else
            return "images/bishop_white.png";
    }
    
    @Override
    public boolean canMoveTo(ChessPosition positionMoveTo, View view, boolean isCaptionMove) {          
        if (!super.canMoveTo(positionMoveTo, view, isCaptionMove))
            return false;         
        return (Math.abs(position.getFile()-positionMoveTo.getFile())==
                Math.abs(position.getRank()-positionMoveTo.getRank()));      
    }  
}
