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
public class PawnPiece extends Piece {

    public PawnPiece(int file, int rank, ChessPlayer chessColor) {
        super(file, rank, chessColor);
    }
    
    public boolean canMoveTo(int fileMoveTo, int rankMoveTo, View view) {    
        if (view.getPiece(fileMoveTo, rankMoveTo)!=null)
            return false;
        if (player==ChessPlayer.white){
            if (rank==2) //пешка еще не двигалась
                return ((file==fileMoveTo)&&((rank==rankMoveTo-1)||(rank==rankMoveTo-2)));
            else
                return ((file==fileMoveTo)&&(rank==rankMoveTo-1));
        } else     
            if (rank==7) //пешка еще не двигалась
                return ((file==fileMoveTo)&&((rank==rankMoveTo+1)||(rank==rankMoveTo+2)));
            else
                return ((file==fileMoveTo)&&(rank==rankMoveTo+1));

    }  
    
}
