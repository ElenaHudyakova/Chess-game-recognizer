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

    public PawnPiece(int file, int rank, int chessColor) {
        super(file, rank, chessColor);
    }
        
    public int getSerializationCode(){
        return 3+this.player*8;
    }
    
    public boolean isThereObstacle(int fileMoveTo, int rankMoveTo, View view){
        if (((player==Piece.WHITE_PLAYER)&&(rank==rankMoveTo-1))||
            ((player==Piece.BLACK_PLAYER)&&(rank==rankMoveTo+1)))
            return false;
        if (view.getPiece(file, (rank+rankMoveTo)/2)==null)
            return false;
        return true;
    }
    
    @Override
    public boolean canMoveTo(int fileMoveTo, int rankMoveTo, View view) {    
        if (this.isSimpleMove(fileMoveTo, rankMoveTo)){
            if ((view.getPiece(fileMoveTo, rankMoveTo)==null)&&(!isThereObstacle(fileMoveTo, rankMoveTo, view)))
                return true;
        }        
        if (this.isCaptionMove(fileMoveTo, rankMoveTo)){
            if (view.getPiece(fileMoveTo, rankMoveTo)!=null)
                if (view.getPiece(fileMoveTo, rankMoveTo).getPlayer()!=this.getPlayer())
                    return true;
        }
        return false;      
    }
    
    
    public String getImagePath(){
        if (player==Piece.BLACK_PLAYER)
            return "images/pawn_black.png";
        else
            return "images/pawn_white.png";
    }

    private boolean isSimpleMove(int fileMoveTo, int rankMoveTo) {
        if (player==Piece.WHITE_PLAYER){
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
    
    private boolean isCaptionMove(int fileMoveTo, int rankMoveTo) {
        if (((file==fileMoveTo+1)||(file==fileMoveTo-1))
           &&(((rank==rankMoveTo-1)&&(getPlayer()==Piece.WHITE_PLAYER))
                ||((rank==rankMoveTo+1)&&(getPlayer()==Piece.BLACK_PLAYER))))
            return true;
        else
            return false;
    }
    
}
