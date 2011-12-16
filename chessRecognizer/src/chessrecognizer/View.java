/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chessrecognizer;

import java.util.ArrayList;

/**
 *
 * @author Lenkas
 */
public class View implements Cloneable {
    
    public ArrayList<Piece> figures = new ArrayList<Piece>();

    private void addInitialOneSideFigures(Piece.ChessColor color){
        int rank1 = 1, rank2 = 2; //white
        
        if (color==Piece.ChessColor.black){
            rank1 = 8;
            rank2 = 7;
        }
        
        for (int file=1; file<=8; file++)
            figures.add(new Piece(file,rank2,Piece.Type.pawn, color)); 
        
        figures.add(new Piece(1, rank1, Piece.Type.rook, color));
        figures.add(new Piece(2, rank1, Piece.Type.knight, color));
        figures.add(new Piece(3, rank1, Piece.Type.bishop, color));
        figures.add(new Piece(4, rank1, Piece.Type.queen, color));
        figures.add(new Piece(5, rank1, Piece.Type.king, color));
        figures.add(new Piece(6, rank1, Piece.Type.bishop, color));
        figures.add(new Piece(7, rank1, Piece.Type.knight, color));
        figures.add(new Piece(8, rank1, Piece.Type.rook, color));
    }
    
    public void setInitialView() {
        addInitialOneSideFigures(Piece.ChessColor.white);
        addInitialOneSideFigures(Piece.ChessColor.black);
    }

    public void setMoveView(Move move, View previousView) {
        figures = new ArrayList<Piece>();         
        
        Piece movedFigure = null;
        
        for (Piece figure:previousView.figures){  
            if (figure.satisfyTo(move, previousView)){
                movedFigure = (Piece)figure.clone();
                figure.setIsToMove(true);
            } else
                figures.add((Piece)figure.clone());   
        }

        addMovedFigure(movedFigure, move);                      
    }

    public Piece getFigure(int file, int rank){
        for (Piece figure:figures){
            if ((figure.getFile()==file)&&(figure.getRank()==rank))
                return figure;
        }
        return null;
    }

    private void addMovedFigure(Piece movedFigure, Move move) {
        int fileMoveTo = move.parseFileMoveTo();
        int rankMoveTo = move.parseRankMoveTo();
        
        if (movedFigure == null)
            throw new RuntimeException("Invalid move");
        else{
            movedFigure.moveTo(fileMoveTo, rankMoveTo, this);
            figures.add(movedFigure);
        }        
    }
}
