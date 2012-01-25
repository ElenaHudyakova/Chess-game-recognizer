/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chessrecognizer.pieces;

import chessrecognizer.ChessPosition;
import chessrecognizer.Move;
import chessrecognizer.View;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Lenkas
 */
public abstract class Piece implements Cloneable{
    
    public final static int WHITE_PLAYER=1, BLACK_PLAYER=0;
    private static enum serializationCodes {bishop, king, };
    private static Class [] pieces = new Class[]{BishopPiece.class, KingPiece.class, 
                                                KnightPiece.class, PawnPiece.class, 
                                                QueenPiece.class, RookPiece.class};
    
    protected ChessPosition position;
    protected int player;
    public ChessPosition previousMovePosition;
    
    public Piece(ChessPosition position,  int color) {
        this.position = position;
        this.player = color;
        previousMovePosition = null;
    }
    
    public Piece (){}
       
    public void moveTo(Move move, View view) {           
        if ((view.getPiece(move.moveToPosition)!=null)&&(move.isCaptionMove)){
            view.getPieces().remove(view.getPiece(move.moveToPosition));
        } else
        if ((this.getClass()==PawnPiece.class)&&(move.isCaptionMove)&&(view.getPiece(move.moveToPosition)==null))
        {
            int rankShift;
            if (this.getPlayer()==Piece.WHITE_PLAYER)
                rankShift = -1;
            else
                rankShift = 1;
            view.getPieces().remove(view.getPiece(new ChessPosition(move.moveToPosition.getFile(), move.moveToPosition.getRank()+rankShift)));
        }
        position = move.moveToPosition;
    }
    
    public ChessPosition getPosition (){
        return position;
    }
    
    @Override
    public Object clone(){
        try {
            Piece copy = (Piece) super.clone();
            copy.position = (ChessPosition)position.clone();            
            return copy;
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(Piece.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public int getPlayer() {
        return player;
    }

    public boolean canMoveTo(ChessPosition positionMoveTo, View view, boolean isCaptionMove){           
        if (view.getPiece(positionMoveTo)!=null)
            if (view.getPiece(positionMoveTo).getPlayer()==this.getPlayer())
                return false;
        if (isThereObstacle(positionMoveTo, view))
            return false; 
        return true;
    }
    
    public abstract boolean isThereObstacle(ChessPosition positionMoveTo, View view);
    public abstract String getImagePath();
    public abstract int getSerializationCode();
    
    public boolean satisfyTo(Move move, View view) {
        if (move.moveFromPosition.getFile()!=0)
            if (move.moveFromPosition.getFile()!=position.getFile())
                return false;           
        
        if (move.moveFromPosition.getRank()!=0)
            if (move.moveFromPosition.getRank()!=position.getRank())
                return false;        
            
        return (this.getClass()==move.movingPiece)&&
               (this.getPlayer()==move.getPlayer()&&
                this.canMoveTo(move.moveToPosition, view, move.isCaptionMove));        
    }

    public void setPosition(ChessPosition position) {
        this.position = position;
    }

    public void setPlayer(int player) {
        this.player = player;
    }

    public static Piece getPieceFromSerializationCode(int serializationCode){
        try{
            Class pieceClass = pieces[serializationCode%8];
            Piece piece = (Piece)pieceClass.newInstance();
            piece.setPlayer(serializationCode/8);
            return piece;
        } catch (Exception e){
            System.out.println(e.toString());
            return null;
        }        
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Piece other = (Piece) obj;
        if (this.position != other.position && (this.position == null || !this.position.equals(other.position))) {
            return false;
        }
        if (this.player != other.player) {
            return false;
        }
        if (this.previousMovePosition != other.previousMovePosition && (this.previousMovePosition == null || !this.previousMovePosition.equals(other.previousMovePosition))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }


}

