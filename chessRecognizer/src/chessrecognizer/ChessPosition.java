/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chessrecognizer;

/**
 *
 * @author Lenkas
 */
public class ChessPosition  implements Cloneable {
    private int file, rank;

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ChessPosition other = (ChessPosition) obj;
        if (this.file != other.file) {
            return false;
        }
        if (this.rank != other.rank) {
            return false;
        }
        return true;
    }

    @Override
    public Object clone()  {
        return new ChessPosition(file, rank);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        return hash;
    }

    public ChessPosition(int file, int rank) {
        this.file = file;
        this.rank = rank;
    }

    public ChessPosition() {
        this.file = 0;
        this.rank = 0;
    }

    public int getFile() {
        return file;
    }

    public int getRank() {
        return rank;
    }

    public void setFile(int file) {
        if (file<=8 || file>=0)
            this.file = file;
    }

    public void setRank(int rank) {
        if (rank<=8 || rank>=0)
            this.rank = rank;
    }
    
}
