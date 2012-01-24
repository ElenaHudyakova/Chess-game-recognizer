/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chessrecognizer.pieces;

import chessrecognizer.View;

/**
 *
 * @author Lenkas
 */
public class BishopPiece extends Piece {
    public BishopPiece(int file, int rank, int chessColor) {
        super(file, rank, chessColor);
    }
    
    public int getSerializationCode(){
        return 0+this.player*8;
    }
    
    public boolean isThereObstacle(int fileMoveTo, int rankMoveTo, View view){
        int fileDirection, rankDirection;
        
        if (Math.abs(file-fileMoveTo)!=Math.abs(rank-rankMoveTo))
            return true;
        
        if (fileMoveTo>file)
            fileDirection = 1;
        else
            fileDirection = -1;
        
        if (rankMoveTo>rank)
            rankDirection = 1;
        else
            rankDirection = -1;
        
        for (int iterator=1; iterator<Math.abs(rankMoveTo-rank); iterator++)
             if (view.getPiece(file+(fileDirection)*iterator, rank+(rankDirection)*iterator)!=null)
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
    public boolean canMoveTo(int fileMoveTo, int rankMoveTo, View view) {          
        if (!super.canMoveTo(fileMoveTo, rankMoveTo, view))
            return false;         
        return (Math.abs(file-fileMoveTo)==Math.abs(rank-rankMoveTo));      
    }  
}
