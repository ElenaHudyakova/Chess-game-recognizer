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
public class QueenPiece extends Piece {
    public QueenPiece(int file, int rank, int chessColor) {
        super(file, rank, chessColor);
    }
    
    public QueenPiece(){}  
    
    
    public int getSerializationCode(){
       return 4+this.player*8;
    }
    
    public boolean isThereObstacle(int fileMoveTo, int rankMoveTo, View view){
        Piece rook = new RookPiece(file, rank, player);
        Piece bishop = new BishopPiece(file, rank, player);
        return (rook.isThereObstacle(fileMoveTo, rankMoveTo, view)&&bishop.isThereObstacle(fileMoveTo, rankMoveTo, view));
    }
    
    
    public String getImagePath(){
        if (player==Piece.BLACK_PLAYER)
            return "images/queen_black.png";
        else
            return "images/queen_white.png";
    }
    
    @Override
    public boolean canMoveTo(int fileMoveTo, int rankMoveTo, View view) {           
        if (!super.canMoveTo(fileMoveTo, rankMoveTo, view))
            return false; 
        Piece rook = new RookPiece(file, rank, player);
        Piece bishop = new BishopPiece(file, rank, player);
        return (rook.canMoveTo(fileMoveTo, rankMoveTo, view)||bishop.canMoveTo(fileMoveTo, rankMoveTo, view));      
    }  
}
