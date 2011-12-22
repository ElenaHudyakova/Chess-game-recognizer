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
    public BishopPiece(int file, int rank, ChessPlayer chessColor) {
        super(file, rank, chessColor);
    }
    public boolean canMoveTo(int fileMoveTo, int rankMoveTo, View view) {          
                return (((file==fileMoveTo+1)||(file==fileMoveTo-1))&&
                        ((rank==rankMoveTo+2)||(rank==rankMoveTo-2)));      
    }  
}
