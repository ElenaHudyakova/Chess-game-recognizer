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
import java.util.logging.Level;
import java.util.logging.Logger;

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
            pieces.add(new PawnPiece(new ChessPosition(file,rank2),player)); 
        
        pieces.add(new RookPiece(new ChessPosition(1, rank1), player));
        pieces.add(new KnightPiece(new ChessPosition(2, rank1), player));
        pieces.add(new BishopPiece(new ChessPosition(3, rank1), player));
        pieces.add(new QueenPiece(new ChessPosition(4, rank1), player));
        pieces.add(new KingPiece(new ChessPosition(5, rank1), player));
        pieces.add(new BishopPiece(new ChessPosition(6, rank1), player));
        pieces.add(new KnightPiece(new ChessPosition(7, rank1), player));
        pieces.add(new RookPiece(new ChessPosition(8, rank1), player));
        
    }
    
    
    
    public long [] serialize(){        
        long [] blobs = {0, 0, 0, 0};
        for (int file=1; file<=8; file++)
            for (int rank=1; rank<=8; rank++){
                ChessPosition position = new ChessPosition(file, rank);
                blobs[(file-1)/2] *= 16;
                if (getPiece(position)!=null)
                    blobs[(file-1)/2]  += getPiece(position).getSerializationCode();
                else
                    blobs[(file-1)/2]  += 14;
            }
        return blobs;
    }
    
    public View (String notationFEN){
        try{
            String [] infoBlocks = notationFEN.split(" ");
            String [] boardLines = infoBlocks[0].split("/");
            for (int rank=8; rank>=1; rank--){
                
                int file = 0;                
                for (int i=0; i<boardLines[8-rank].length(); i++){
                    String currentSimbol = Character.toString(boardLines[8-rank].charAt(i));
                    try{
                        int fileShift = Integer.parseInt(currentSimbol);
                        file += fileShift;
                    } catch (Exception e){ 
                        file += 1;
                        Class pieceClass = PGNHandler.parsePieceType(currentSimbol);
                        Piece piece = (Piece)pieceClass.newInstance();
                        piece.setPosition(new ChessPosition(file, rank));
                        if (currentSimbol.equals(currentSimbol.toUpperCase())){
                            piece.setPlayer(Piece.WHITE_PLAYER);
                        } else
                            piece.setPlayer(Piece.BLACK_PLAYER);
                        this.pieces.add(piece);
                    }                                        
                }                
            }
        } catch (Exception e){
            System.out.println(e.toString());
        }
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

    public void setMoveView(Move move, View previousView) throws InstantiationException, IllegalAccessException {
        PGNHandler.parseMove(move);
        pieces = new ArrayList<Piece>();          
                
        if ((move.isKingCastling)||(move.isQueenCastling)){
            setCastling(move, previousView);
            return;
        }

        Piece movedPiece = null;
        
        for (Piece piece:previousView.pieces){  
            if (piece.satisfyTo(move, previousView)){
                View potentialView = (View)previousView.clone();
                potentialView.pieces.remove(potentialView.getPiece(piece.getPosition()));
                potentialView.addMovedPiece((Piece)piece.clone(), move);
                if (!potentialView.isCheck(piece.getPlayer()))
                    if (movedPiece == null){
                        movedPiece = (Piece)piece.clone();                
                        movedPiece.previousMovePosition = (ChessPosition) piece.getPosition().clone(); 
                    } else{
                        throw new RuntimeException("Ambiguous move " + move.toString());
                    }  
                else
                    this.addNotMovedPiece(piece);
            } else{
                this.addNotMovedPiece(piece);                                
            }                                
        }

        addMovedPiece(movedPiece, move);

    }

    @Override
    protected Object clone() {
        try {
            View copy = (View) super.clone();
            copy.pieces = (ArrayList<Piece>)this.pieces.clone();
            return copy;
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public Piece getKing(int player){
        for (Piece piece:pieces){
            if (piece.getPlayer()==player && piece.getClass()==KingPiece.class)
                return piece;
        }     
        return null;
    }
    
    public boolean isCheck(int player){
        Piece king = getKing(player);
        for (Piece piece:pieces){
            if (player!=piece.getPlayer() && piece.canMoveTo(king.getPosition(), this, true))
                return true;
        }
        return false;
    }

    public Piece getPiece(ChessPosition position){
        for (Piece piece:pieces){
            if (piece.getPosition().equals(position))
                return piece;
        }
        return null;
    }
    
    public ArrayList<Piece> getPieces(){
        return pieces;
    }    

    private void addMovedPiece(Piece movedPiece, Move move) throws InstantiationException, IllegalAccessException {
        if (move.isPromotion){
            Piece promotionedPiece = (Piece)move.promotionPiece.newInstance();
            promotionedPiece.setPosition(movedPiece.getPosition());
            promotionedPiece.setPlayer(movedPiece.getPlayer());
            promotionedPiece.previousMovePosition = movedPiece.previousMovePosition;
            movedPiece = promotionedPiece;
        }
        
        if (movedPiece == null){
            throw new RuntimeException("Invalid move " + move.toString());
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
        
        king = getKing(move.getPlayer());
        
        if (move.getPlayer()==Piece.WHITE_PLAYER)
            rank = 1;
        else
            rank = 8;
        
        if (move.isKingCastling)
            rook = this.getPiece(new ChessPosition(8, rank));  
        else
            if (move.isQueenCastling)
                rook = this.getPiece(new ChessPosition(1, rank));
            else
                throw new RuntimeException("setCastling method calling for not castling move");
        
        king.moveTo(move, this);    
        rook.moveTo(move, this);
    }

    private void addNotMovedPiece(Piece piece) {
        Piece newPiece;
        newPiece = (Piece)piece.clone();                                
        newPiece.previousMovePosition = null;  
        pieces.add(newPiece);      
    }
}
