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
    private final static String [] FILES = new String[]{"a", "b", "c", "d", "e", "f", "g", "h"}; 
    private static enum serializationCodes {bishop, king, };
    private static Class [] pieces = new Class[]{BishopPiece.class, KingPiece.class, 
                                                KnightPiece.class, PawnPiece.class, 
                                                QueenPiece.class, RookPiece.class};
    
    protected int file;//от 1 до 8
    protected int player;
    protected int rank;//от 1 до 8
    public ChessPosition previousMovePosition;
    
    public Piece(int file, int rank,  int color) {
        this.rank = rank;
        this.file = file;
        this.player = color;
        previousMovePosition = null;
    }
    
    public static String getFile(int file){
        return FILES[file-1];
    }

    public void moveTo(Move move, View view) {           
        if ((view.getPiece(move.fileTo, move.rankTo)!=null)&&(move.isCaptionMove))
            view.getPieces().remove(view.getPiece(move.fileTo, move.rankTo));
        file = move.fileTo;
        rank = move.rankTo;
    }
    
    @Override
    public Object clone(){
        try {
            return super.clone();
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(Piece.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public int getPlayer() {
        return player;
    }

    public int getFile() {
        return file;
    }

    public int getRank() {
        return rank;
    }

    protected boolean canMoveTo(int fileMoveTo, int rankMoveTo, View view){           
        if (view.getPiece(fileMoveTo, rankMoveTo)!=null)
            if (view.getPiece(fileMoveTo, rankMoveTo).getPlayer()==this.getPlayer())
                return false;
        if (isThereObstacle(fileMoveTo, rankMoveTo, view))
            return false; 
        return true;
    }
    
    public abstract boolean isThereObstacle(int fileMoveTo, int rankMoveTo, View view);
    public abstract String getImagePath();
    public abstract int getSerializationCode();
    
    public boolean satisfyTo(Move move, View view) {
        if (move.fileFrom!=null)
            if (move.fileFrom!=file)
                return false;           
        
        if (move.rankFrom!=null)
            if (move.rankFrom!=rank)
                return false;        
            
        return (this.getClass()==move.movingPiece)&&
               (this.getPlayer()==move.getPlayer()&&
                this.canMoveTo(move.fileTo, move.rankTo, view));        
    }

    public void setFile(int file) {
        this.file = file;
    }

    public void setPlayer(int player) {
        this.player = player;
    }

    public void setRank(int rank) {
        this.rank = rank;
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
        if (this.file != other.file) {
            return false;
        }
        if (this.player != other.player) {
            return false;
        }
        if (this.rank != other.rank) {
            return false;
        }
        return true;
    }

    
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + this.file;
        hash = 53 * hash + this.rank;
        return hash;
    }

}

