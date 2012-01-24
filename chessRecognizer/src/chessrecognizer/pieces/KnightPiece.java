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

public class KnightPiece extends Piece {
    
    public KnightPiece(int file, int rank, int chessColor) {
        super(file, rank, chessColor);
    }
       
        
    public int getSerializationCode(){
        return 2+this.player*8;
    }
    
    public boolean isThereObstacle(int fileMoveTo, int rankMoveTo, View view){
        return false;
    }
    
    
    public String getImagePath(){
        if (player==Piece.BLACK_PLAYER)
            return "images/knight_black.png";
        else
            return "images/knight_white.png";
    }
    
    @Override
    public boolean canMoveTo(int fileMoveTo, int rankMoveTo, View view) {
        if (!super.canMoveTo(fileMoveTo, rankMoveTo, view))
            return false;
        return ((((file==fileMoveTo+1)||(file==fileMoveTo-1))&&
                        ((rank==rankMoveTo+2)||(rank==rankMoveTo-2)))||
                (((file==fileMoveTo+2)||(file==fileMoveTo-2))&&
                        ((rank==rankMoveTo+1)||(rank==rankMoveTo-1))));      
    }  
}
