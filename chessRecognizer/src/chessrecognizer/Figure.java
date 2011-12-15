/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chessrecognizer;

/**
 *
 * @author Lenkas
 */
public class Figure implements Cloneable{


    public enum ChessColor{white, black};
    public enum Type{king, queen, rook, bishop, knight, pawn, noType};
    private Type type;
    private int file;//от 1 до 8
    private ChessColor color;
    private int rank;//от 1 до 8
    private boolean isToMove;//будет ли ходить
    
    public Figure(int file, int rank,  Type type, ChessColor color) {
        this.rank = rank;
        this.file = file;
        this.type = type;
        this.color = color;
        this.isToMove = false;
    }
    
    public Figure(int file, int rank,  Type type, ChessColor color, boolean isToMove) {
        this.rank = rank;
        this.file = file;
        this.type = type;
        this.color = color;
        this.isToMove = isToMove;
    }
    public void moveTo(int fileMoveTo, int rankMoveTo, View view) {
        file = fileMoveTo;
        rank = rankMoveTo;        
    }
    
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public ChessColor getColor() {
        return color;
    }

    public int getFile() {
        return file;
    }

    public boolean isToMove() {
        return isToMove;
    }

    public int getRank() {
        return rank;
    }

    public Type getType() {
        return type;
    }


    public void setIsToMove(boolean isToMove) {
        this.isToMove = isToMove;
    }
    
    public boolean canMoveTo(int fileMoveTo, int rankMoveTo, View view) {
        if (type==Type.pawn){            
            if (color==ChessColor.white){
                if (rank==2) //пешка еще не двигалась
                    return ((file==fileMoveTo)&&((rank==rankMoveTo-1)||(rank==rankMoveTo-2)));
                else
                    return ((file==fileMoveTo)&&(rank==rankMoveTo-1));
            }      
            if (color==ChessColor.black){
                if (rank==7) //пешка еще не двигалась
                    return ((file==fileMoveTo)&&((rank==rankMoveTo+1)||(rank==rankMoveTo+2)));
                else
                    return ((file==fileMoveTo)&&(rank==rankMoveTo+1));
            }               
        }
        return false;
    }

    @Override
    public String toString() {
        return "Figure{" + type + ", "+ file + "" + rank +", " + color + ", isToMove=" + isToMove + '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Figure other = (Figure) obj;
        if (this.type != other.type) {
            return false;
        }
        if (this.file != other.file) {
            return false;
        }
        if (this.color != other.color) {
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

}
