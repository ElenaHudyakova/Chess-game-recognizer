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

public class KnightPiece extends Piece {
    
    public KnightPiece(ChessPosition position, int chessColor) {
        super(position, chessColor);
    }
       
    public KnightPiece(){}
    
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
    public boolean canMoveTo(ChessPosition positionMoveTo, View view, boolean isCaptionMove) {
        if (!super.canMoveTo(positionMoveTo, view, isCaptionMove))
            return false;
        return ((((position.getFile()==positionMoveTo.getFile()+1)||(position.getFile()==positionMoveTo.getFile()-1))&&
                        ((position.getRank()==positionMoveTo.getRank()+2)||(position.getRank()==positionMoveTo.getRank()-2)))||
                (((position.getFile()==positionMoveTo.getFile()+2)||(position.getFile()==positionMoveTo.getFile()-2))&&
                        ((position.getRank()==positionMoveTo.getRank()+1)||(position.getRank()==positionMoveTo.getRank()-1))));      
    }

    @Override
    public boolean isThereObstacle(ChessPosition positionMoveTo, View view) {
        return false;
    }
}
