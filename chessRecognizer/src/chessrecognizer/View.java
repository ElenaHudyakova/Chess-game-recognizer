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

    private void addInitialOneSidePieces(int player){
        int rank1 = 1, rank2 = 2; //white
        
        if (player==Piece.BLACK_PLAYER){
            rank1 = 8;
            rank2 = 7;
        }
        
        for (int file=1; file<=8; file++)
            pieces.add(new PawnPiece(file,rank2,player)); 
        
        pieces.add(new RookPiece(1, rank1, player));
        pieces.add(new KnightPiece(2, rank1, player));
        pieces.add(new BishopPiece(3, rank1, player));
        pieces.add(new QueenPiece(4, rank1, player));
        pieces.add(new KingPiece(5, rank1, player));
        pieces.add(new BishopPiece(6, rank1, player));
        pieces.add(new KnightPiece(7, rank1, player));
        pieces.add(new RookPiece(8, rank1, player));
        
    }
    
    
    
    public long [] serialize(){        
        long [] blobs = {0, 0, 0, 0};
        for (int file=1; file<=8; file++)
            for (int rank=1; rank<=8; rank++){
                blobs[(file-1)/2] *= 16;
                if (getPiece(file, rank)!=null)
                    blobs[(file-1)/2]  += getPiece(file, rank).getSerializationCode();
                else
                    blobs[(file-1)/2]  += 14;
            }
        return blobs;
    }

    
    public View(int blob) throws NoSuchMethodException{
        for (int file=1; file<=8; file++)
            for (int rank=1; rank<=8; rank++){
                int pieceCode = blob % 16;                
                blob /= 16;
                if (pieceCode!=14)
                    Piece.getPieceFromSerializationCode(pieceCode);
                throw new NoSuchMethodException();
            }
    }
    
    public View(){
      
    }
    
    public void setInitialView() {
        addInitialOneSidePieces(Piece.WHITE_PLAYER);
        addInitialOneSidePieces(Piece.BLACK_PLAYER);
    }

    public void setMoveView(Move move, View previousView) {
        move.parseMove();
        pieces = new ArrayList<Piece>();          
                
        if ((move.isKingCastling)||(move.isQueenCastling)){
            setCastling(move, previousView);
            return;
        }

        Piece movedPiece = null;
        
        for (Piece piece:previousView.pieces){  
            if (piece.satisfyTo(move, previousView)){
                movedPiece = (Piece)piece.clone();                
                movedPiece.previousMovePosition = new ChessPosition(piece.getFile(), piece.getRank());                
            } else{
                Piece newPiece;
                newPiece = (Piece)piece.clone();                                
                newPiece.previousMovePosition = null;  
                pieces.add(newPiece);                                      
            }                                
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
        int fileMoveTo = move.fileTo;
        int rankMoveTo = move.rankTo;
        
        if (movedPiece == null){
            System.out.println(move.toString());
            throw new RuntimeException("Invalid move");
        }
        else{
            movedPiece.moveTo(move, this);
            pieces.add(movedPiece);
        }        
    }

    private void setCastling(Move move, View previousView) {
        for (Piece piece:previousView.pieces){  
            pieces.add((Piece)piece.clone());               
        }
        
        Piece king, rook;
        int rank;
        
        if (move.getPlayer()==Piece.WHITE_PLAYER)
            rank = 1;
        else
            rank = 8;
        
        king = this.getPiece(5, rank);
        
        if (move.isKingCastling)
            rook = this.getPiece(8, rank);  
        else
            if (move.isQueenCastling)
                rook = this.getPiece(1, rank);
            else
                throw new RuntimeException("setCastling method calling for not castling move");
        
        king.moveTo(move, this);    
        rook.moveTo(move, this);
    }
}
