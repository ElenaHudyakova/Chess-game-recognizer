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
public class KingPiece extends Piece {
    public KingPiece(ChessPosition position, int chessColor) {
        super(position, chessColor);
    } 
    
    public KingPiece(){     
    }
        
    public int getSerializationCode(){
        return 1+this.player*8;
    }
    
    @Override
    public void moveTo(Move move, View view) {           
        if (move.isKingCastling){
            this.position.setFile(7);
            return;
        }
        if (move.isQueenCastling){
            this.position.setFile(3);
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
    public boolean canMoveTo(ChessPosition positionMoveTo, View view, boolean isCaptionMove) {            
        if (!super.canMoveTo(positionMoveTo,view, isCaptionMove))
            return false; 
        return (((position.getFile()-positionMoveTo.getFile()<=1)&&(position.getFile()-positionMoveTo.getFile()>=-1))&&
                        ((position.getRank()-positionMoveTo.getRank()<=1)&&(position.getRank()-positionMoveTo.getRank()>=-1)));      
    }

    @Override
    public boolean isThereObstacle(ChessPosition positionMoveTo, View view) {
        return false;
    }
}
