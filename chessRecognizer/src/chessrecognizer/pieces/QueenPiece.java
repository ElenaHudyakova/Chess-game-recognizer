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
public class QueenPiece extends Piece {
    public QueenPiece(ChessPosition position, int chessColor) {
        super(position, chessColor);
    }
    
    public QueenPiece(){}  
    
    
    public int getSerializationCode(){
       return 4+this.player*8;
    }
    
    public boolean isThereObstacle(ChessPosition positionMoveTo, View view){
        Piece rook = new RookPiece(position, player);
        Piece bishop = new BishopPiece(position, player);
        return (rook.isThereObstacle(positionMoveTo, view) &&
                bishop.isThereObstacle(positionMoveTo, view));
    }
    
    
    public String getImagePath(){
        if (player==Piece.BLACK_PLAYER)
            return "images/queen_black.png";
        else
            return "images/queen_white.png";
    }
    
    @Override
    public boolean canMoveTo(ChessPosition positionMoveTo, View view, boolean isCaptionMove) {           
        if (!super.canMoveTo(positionMoveTo, view, isCaptionMove))
            return false; 
        Piece rook = new RookPiece(position, player);
        Piece bishop = new BishopPiece(position, player);
        return (rook.canMoveTo(positionMoveTo, view, isCaptionMove)||
                bishop.canMoveTo(positionMoveTo, view, isCaptionMove));      
    }  
}
