/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chessrecognizer;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lenkas
 */
public class View implements Cloneable {
    public ArrayList<Figure> figures = new ArrayList<Figure>();

    private void addInitialOneSideFigures(Figure.ChessColor color){
        int rank1 = 1, rank2 = 2; //white
        if (color==Figure.ChessColor.black){
            rank1 = 8;
            rank2 = 7;
        }
        
        for (int file=1; file<=8; file++)
            figures.add(new Figure(file,rank2,Figure.Type.pawn, color)); 
        
        figures.add(new Figure(1, rank1, Figure.Type.rook, color));
        figures.add(new Figure(2, rank1, Figure.Type.knight, color));
        figures.add(new Figure(3, rank1, Figure.Type.bishop, color));
        figures.add(new Figure(4, rank1, Figure.Type.queen, color));
        figures.add(new Figure(5, rank1, Figure.Type.king, color));
        figures.add(new Figure(6, rank1, Figure.Type.bishop, color));
        figures.add(new Figure(7, rank1, Figure.Type.knight, color));
        figures.add(new Figure(8, rank1, Figure.Type.rook, color));
    }
    
    public void setInitialView() {
        addInitialOneSideFigures(Figure.ChessColor.white);
        addInitialOneSideFigures(Figure.ChessColor.black);
    }

    public void setMoveView(Move move, View previousView) throws CloneNotSupportedException {
        figures = new ArrayList<Figure>(); 
        
        Figure.Type figureTypeToMove = move.parseFigureTypeToMove();
        int fileMoveTo = move.parseFileMoveTo();
        int rankMoveTo = move.parseRankMoveTo();
        Figure movedFigure = null;
        
        System.out.println(fileMoveTo + "" + rankMoveTo);
        
        for (Figure figure:previousView.figures){  
            
            if ((figure.getType()==figureTypeToMove)&&(figure.getColor()==move.getPlayer())){
                if (figure.canMoveTo(fileMoveTo, rankMoveTo, previousView)){
                    System.out.println("!");
                    movedFigure = (Figure)figure.clone();
                    figure.setIsToMove(true);
                } else
                    figures.add((Figure)figure.clone());   
            } else
                figures.add((Figure)figure.clone()); 
        }

        if (movedFigure == null)
            throw new RuntimeException("Invalid move");
        else{
            movedFigure.moveTo(fileMoveTo, rankMoveTo, this);
            figures.add(movedFigure);
        }                              
    }

    public Figure getFigure(int file, int rank){
        for (Figure figure:figures){
            if ((figure.getFile()==file)&&(figure.getRank()==rank))
                return figure;
        }
        return null;
    }
}
