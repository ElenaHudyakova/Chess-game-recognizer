/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chessrecognizer.pieces;

import chessrecognizer.Move;
import chessrecognizer.View;
import chessrecognizer.pieces.PawnPiece;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lenkas
 */
public abstract class Piece implements Cloneable{
    
    public enum ChessPlayer{white, black};

    protected int file;//от 1 до 8
    protected ChessPlayer player;
    protected int rank;//от 1 до 8
    public boolean isToMove;//будет ли ходить

    
    public Piece(int file, int rank,  ChessPlayer color) {
        this.rank = rank;
        this.file = file;
        this.player = color;
        this.isToMove = false;
    }

    public void moveTo(int fileMoveTo, int rankMoveTo, View view) {
        file = fileMoveTo;
        rank = rankMoveTo;        
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

    public ChessPlayer getPlayer() {
        return player;
    }

    public int getFile() {
        return file;
    }

    public int getRank() {
        return rank;
    }

    public abstract boolean canMoveTo(int fileMoveTo, int rankMoveTo, View view);
    
    public boolean satisfyTo(Move move, View view) {
        Class pieceTypeToMove = move.parsePieceTypeToMove();
        int fileMoveTo = move.parseFileMoveTo();
        int rankMoveTo = move.parseRankMoveTo();
        if (this instanceof PawnPiece) {
            fileMoveTo++; 
        }
    
        return (this.getClass()==pieceTypeToMove)&&
               (this.getPlayer()==move.getPlayer()&&
                this.canMoveTo(fileMoveTo, rankMoveTo, view));        
    }

    @Override
    public String toString() {
        return "Piece{" + getClass().toString() + ", "+ file + " " + rank +", " + player + ", isToMove=" + isToMove + '}';
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
        if (this.isToMove != other.isToMove) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + this.file;
        hash = 53 * hash + (this.player != null ? this.player.hashCode() : 0);
        hash = 53 * hash + this.rank;
        hash = 53 * hash + (this.isToMove ? 1 : 0);
        return hash;
    }

}

