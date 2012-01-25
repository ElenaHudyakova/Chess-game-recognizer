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
public class RookPiece extends Piece {
    public RookPiece(int file, int rank, int chessColor) {
        super(file, rank, chessColor);
    }
    
    public RookPiece(){     
    }
       
        
    public int getSerializationCode(){
        return 5+this.player*8;
    }
    
    @Override
    public void moveTo(Move move, View view) {           
        if (move.isKingCastling){
            this.file = 6;            
            return;
        }
        if (move.isQueenCastling){
            this.file = 4;
            return;
        }

        super.moveTo(move, view);
    }
    
    
    public String getImagePath(){
        if (player==Piece.BLACK_PLAYER)
            return "images/rook_black.png";
        else
            return "images/rook_white.png";
    }
    
    //NEED REFACTORING!!!
    public boolean isThereObstacle(int fileMoveTo, int rankMoveTo, View view){
        if (file==fileMoveTo){
            if (rank<rankMoveTo){                
                for (int rankIterator=rank+1; rankIterator<rankMoveTo-1; rankIterator++)
                    if (view.getPiece(file, rankIterator)!=null)
                        return true; 
                return false;
            } else{                                    
                for (int rankIterator=rank-1; rankIterator>rankMoveTo+1; rankIterator--)
                    if (view.getPiece(file, rankIterator)!=null)
                        return true;                                            
                return false;
            }                
        } 
        if (rank==rankMoveTo){
            if (file<fileMoveTo){
                for (int fileIterator=file+1; fileIterator<fileMoveTo; fileIterator++)
                    if (view.getPiece(fileIterator, rank)!=null)
                        return true;   
                return false;
            } else{
                for (int fileIterator=file-1; fileIterator>fileMoveTo; fileIterator--)
                    if (view.getPiece(fileIterator, rank)!=null)
                        return true;   
                return false;
            }             
        }
        return true;
    }
    
    @Override
    public boolean canMoveTo(int fileMoveTo, int rankMoveTo, View view) {           
        if (!super.canMoveTo(fileMoveTo, rankMoveTo, view))
            return false;
        return ((file==fileMoveTo)||(rank==rankMoveTo));      
    }  
}
