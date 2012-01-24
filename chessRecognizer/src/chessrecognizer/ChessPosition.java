/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chessrecognizer;

/**
 *
 * @author Lenkas
 */
public class ChessPosition {
    private int file, rank;

    public ChessPosition(int file, int rank) {
        this.file = file;
        this.rank = rank;
    }

    public ChessPosition() {
        this.file = 1;
        this.rank = 1;
    }

    public int getFile() {
        return file;
    }

    public int getRank() {
        return rank;
    }

    public void setFile(int file) {
        if (file<=8 || file>=1)
            this.file = file;
    }

    public void setRank(int rank) {
        if (rank<=8 || rank>=1)
            this.rank = rank;
    }
    
}
