/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chessrecognizer.pieces;

import chessrecognizer.ChessPosition;
import chessrecognizer.Move;
import chessrecognizer.View;

/**
 *
 * @author Lenkas
 */
public class RookPiece extends Piece {
    public RookPiece(ChessPosition position, int chessColor) {
        super(position, chessColor);
    }
    
    public RookPiece(){     
    }
       
        
    public int getSerializationCode(){
        return 5+this.player*8;
    }
    
    @Override
    public void moveTo(Move move, View view) {           
        if (move.isKingCastling){
            this.position.setFile(6);            
            return;
        }
        if (move.isQueenCastling){
            this.position.setFile(4);
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
    public boolean isThereObstacle(ChessPosition positionMoveTo, View view){
        if (position.getFile()==positionMoveTo.getFile()){
            if (position.getRank()<positionMoveTo.getRank()){                
                for (int rankIterator=position.getRank()+1; rankIterator<=positionMoveTo.getRank()-1; rankIterator++)
                    if (view.getPiece(new ChessPosition(position.getFile(), rankIterator))!=null)
                        return true; 
                return false;
            } else{                                    
                for (int rankIterator=position.getRank()-1; rankIterator>=positionMoveTo.getRank()+1; rankIterator--)
                    if (view.getPiece(new ChessPosition(position.getFile(), rankIterator))!=null)
                        return true;                                            
                return false;
            }                
        } 
        if (position.getRank()==positionMoveTo.getRank()){
            if (position.getFile()<positionMoveTo.getFile()){
                for (int fileIterator=position.getFile()+1; fileIterator<positionMoveTo.getFile(); fileIterator++)
                    if (view.getPiece(new ChessPosition(fileIterator, position.getRank()))!=null)
                        return true;   
                return false;
            } else{
                for (int fileIterator=position.getFile()-1; fileIterator>positionMoveTo.getFile(); fileIterator--)
                    if (view.getPiece(new ChessPosition(fileIterator, position.getRank()))!=null)
                        return true;   
                return false;
            }             
        }
        return true;
    }
    
    @Override
    public boolean canMoveTo(ChessPosition positionMoveTo, View view, boolean isCaptionMove) {           
        if (!super.canMoveTo(positionMoveTo, view, isCaptionMove))
            return false;
        return ((position.getFile()==positionMoveTo.getFile())||(position.getRank()==positionMoveTo.getRank()));      
    }  
}
