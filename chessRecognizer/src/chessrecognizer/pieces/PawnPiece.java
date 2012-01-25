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
public class PawnPiece extends Piece {

    public PawnPiece(ChessPosition position, int chessColor) {
        super(position, chessColor);
    }
        
    public PawnPiece(){}
    
    public int getSerializationCode(){
        return 3+this.player*8;
    }
    
    public boolean isThereObstacle(ChessPosition positionMoveTo, View view){
        if (((player==Piece.WHITE_PLAYER)&&(position.getRank()==positionMoveTo.getRank()-1))||
            ((player==Piece.BLACK_PLAYER)&&(position.getRank()==positionMoveTo.getRank()+1)))
            return false;
        if (view.getPiece(new ChessPosition(position.getFile(), 
                                            (position.getRank()+positionMoveTo.getRank())/2))==null)
            return false;
        return true;
    }
    
    @Override
    public boolean canMoveTo(ChessPosition positionMoveTo, View view, boolean isCaptionMove) {  
        int rankShift;
        if (this.getPlayer()==Piece.WHITE_PLAYER)
            rankShift = -1;
        else
            rankShift = 1;
        
        if (this.isNoCaptionMove(positionMoveTo)){
            if ((view.getPiece(positionMoveTo)==null)&&(!isThereObstacle(positionMoveTo, view)))
                return true;
        }        
        if (this.isCaptionMove(positionMoveTo) && isCaptionMove){
            if (view.getPiece(positionMoveTo)!=null){
                if (view.getPiece(positionMoveTo).getPlayer()!=this.getPlayer())
                    return true;
            }else            
                if (view.getPiece(new ChessPosition(positionMoveTo.getFile(), positionMoveTo.getRank()+rankShift))!=null && 
                        view.getPiece(new ChessPosition(positionMoveTo.getFile(), positionMoveTo.getRank()+rankShift)).getPlayer()!=this.getPlayer())
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

    private boolean isNoCaptionMove(ChessPosition positionMoveTo) {
        if (player==Piece.WHITE_PLAYER){
            if (position.getRank()==2) //пешка еще не двигалась
                return ((position.getFile()==positionMoveTo.getFile())&&((position.getRank()==positionMoveTo.getRank()-1)||(position.getRank()==positionMoveTo.getRank()-2)));
            else
                return ((position.getFile()==positionMoveTo.getFile())&&(position.getRank()==positionMoveTo.getRank()-1));
        } else     
            if (position.getRank()==7) //пешка еще не двигалась
                return ((position.getFile()==positionMoveTo.getFile())&&((position.getRank()==positionMoveTo.getRank()+1)||(position.getRank()==positionMoveTo.getRank()+2)));
            else
                return ((position.getFile()==positionMoveTo.getFile())&&(position.getRank()==positionMoveTo.getRank()+1));
    }
    
    private boolean isCaptionMove(ChessPosition positionMoveTo) {
        if (((position.getFile()==positionMoveTo.getFile()+1)||(position.getFile()==positionMoveTo.getFile()-1))
           &&(((position.getRank()==positionMoveTo.getRank()-1)&&(getPlayer()==Piece.WHITE_PLAYER))
                ||((position.getRank()==positionMoveTo.getRank()+1)&&(getPlayer()==Piece.BLACK_PLAYER))))
            return true;
        else
            return false;
    }
    
}
