/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chessrecognizer.pieces;

import chessrecognizer.Move;
import chessrecognizer.View;

/**
 *
 * @author Lenkas
 */
public class KingPiece extends Piece {
    public KingPiece(int file, int rank, int chessColor) {
        super(file, rank, chessColor);
    } 
    
        
    public int getSerializationCode(){
        return 1+this.player*8;
    }
    
    @Override
    public void moveTo(Move move, View view) {           
        if (move.isKingCastling){
            this.file = 7;
            return;
        }
        if (move.isQueenCastling){
            this.file = 3;
            return;
        }

        super.moveTo(move, view);
    }
    
    public boolean isThereObstacle(int fileMoveTo, int rankMoveTo, View view){
        return false;
    }
    
    
    public String getImagePath(){
        if (player==Piece.BLACK_PLAYER)
            return "images/king_black.png";
        else
            return "images/king_white.png";
    }
    
    @Override
    public boolean canMoveTo(int fileMoveTo, int rankMoveTo, View view) {            
        if (!super.canMoveTo(fileMoveTo, rankMoveTo, view))
            return false; 
        return (((file-fileMoveTo<=1)&&(file-fileMoveTo>=-1))&&
                        ((rank-rankMoveTo<=1)&&(rank-rankMoveTo>=-1)));      
    }  
}
