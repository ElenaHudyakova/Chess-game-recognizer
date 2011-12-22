/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chessrecognizer;

import chessrecognizer.pieces.Piece;
import chessrecognizer.pieces.KnightPiece;
import chessrecognizer.pieces.KingPiece;
import chessrecognizer.pieces.QueenPiece;
import chessrecognizer.pieces.RookPiece;
import chessrecognizer.pieces.BishopPiece;
import chessrecognizer.pieces.PawnPiece;
import java.util.ArrayList;

/**
 *
 * @author Lenkas
 */
public class View implements Cloneable {
    
    public ArrayList<Piece> pieces = new ArrayList<Piece>();

    private void addInitialOneSidePieces(Piece.ChessPlayer color){
        int rank1 = 1, rank2 = 2; //white
        
        if (color==Piece.ChessPlayer.black){
            rank1 = 8;
            rank2 = 7;
        }
        
        for (int file=1; file<=8; file++)
            pieces.add(new PawnPiece(file,rank2,color)); 
        
        pieces.add(new RookPiece(1, rank1, color));
        pieces.add(new KnightPiece(2, rank1, color));
        pieces.add(new BishopPiece(3, rank1, color));
        pieces.add(new QueenPiece(4, rank1, color));
        pieces.add(new KingPiece(5, rank1, color));
        pieces.add(new BishopPiece(6, rank1, color));
        pieces.add(new KnightPiece(7, rank1, color));
        pieces.add(new RookPiece(8, rank1, color));
        
    }
    
    public void setInitialView() {
        addInitialOneSidePieces(Piece.ChessPlayer.white);
        addInitialOneSidePieces(Piece.ChessPlayer.black);
    }

    public void setMoveView(Move move, View previousView) {
        pieces = new ArrayList<Piece>();         
        
        Piece movedPiece = null;
        
        for (Piece piece:previousView.pieces){  
            if (piece.satisfyTo(move, previousView)){
                movedPiece = (Piece)piece.clone();
                piece.isToMove = true;
            } else
                pieces.add((Piece)piece.clone());   
        }

        addMovedPiece(movedPiece, move);                      
    }

    public Piece getPiece(int file, int rank){
        for (Piece figure:pieces){
            if ((figure.getFile()==file)&&(figure.getRank()==rank))
                return figure;
        }
        return null;
    }
    
    public ArrayList<Piece> getPieces(){
        return pieces;
    }    

    private void addMovedPiece(Piece movedPiece, Move move) {
        int fileMoveTo = move.parseFileMoveTo();
        int rankMoveTo = move.parseRankMoveTo();
        
        if (movedPiece == null)
            throw new RuntimeException("Invalid move");
        else{
            movedPiece.moveTo(fileMoveTo, rankMoveTo, this);
            pieces.add(movedPiece);
        }        
    }
}
