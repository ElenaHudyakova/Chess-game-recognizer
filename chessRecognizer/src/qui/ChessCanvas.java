/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package qui;

import chessrecognizer.ChessPosition;
import chessrecognizer.PGNHandler;
import chessrecognizer.View;
import chessrecognizer.pieces.Piece;
import java.awt.*;import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


public class ChessCanvas extends java.awt.Canvas
{
  private final int width=40,height=40,dimension=8, xShift=20, bigYShift=60, bigXShift = 35, yShift=25;
  private final Color colors[] =  {Color.white,Color.gray};
  
  private View view;
  private ChessPosition movedPiecePreviousPosition = null;
  
  public void setView(View view){
      this.view = view;
  }
  
  public ChessCanvas(){
      super();
      view = new View();
      view.setInitialView();
  }
  
  @Override
  public void paint(Graphics g) 
  {
    int xPos, yPos, numTile;  
    ChessPosition paintedPosition = new ChessPosition();
    Graphics2D g2D = (Graphics2D) g; 
    g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    movedPiecePreviousPosition = null;
    
    numTile=dimension*dimension; 
    
    for (int tileCount=0;tileCount<numTile;tileCount++)
    {
          paintedPosition.setRank(8-tileCount/dimension); //y
          paintedPosition.setFile(tileCount%dimension+1);//x
          
          drawBoarderLetters(g2D, paintedPosition);          
          drawTile(g2D, paintedPosition);
          drawMovingPiecePosition(g2D, view.getPiece(paintedPosition));
    }    
    markPreviousPosition(g2D);    
  }
  
  private void fillRect(Graphics2D g2D,int x1,int y1,int x2,int y2)
  {
      Rectangle2D.Float r1=new Rectangle2D.Float(x1,y1,x2,y2);
      g2D.fill(r1);
      g2D.drawRect(x1, y1, x2, y2);
  }
  
  private void drawMovingPiecePosition(Graphics2D g2D, Piece piece){
      Image img=null;

      if (piece!=null)
        try {
            int xPos=width*(piece.getPosition().getFile()-1);  
            int yPos=height*(8-piece.getPosition().getRank());
            img=ImageIO.read(new File(piece.getImagePath()));
            if (piece.previousMovePosition!=null){
                g2D.setColor(Color.yellow);
                fillRect(g2D, xPos+xShift, yPos, width, height);
                movedPiecePreviousPosition = piece.previousMovePosition;
            }
            g2D.drawImage(img,xPos+xShift,yPos,this);
        }
        catch(IOException e)
        {
            System.out.println(e.toString());
        }      
  }
  
  private void markPreviousPosition(Graphics2D g2D){
    int xPos, yPos;
    if (movedPiecePreviousPosition!=null){
        xPos=width*(movedPiecePreviousPosition.getFile()-1);  
        yPos=height*(8-movedPiecePreviousPosition.getRank());
        g2D.setColor(Color.yellow);
        fillRect(g2D, xPos+20, yPos, width, height);
    }      
  }

    private void drawBoarderLetters(Graphics2D g2D, ChessPosition paintedPosition) {
      int xPos=width*(paintedPosition.getFile()-1);  
      int yPos=height*(dimension-paintedPosition.getRank());

      g2D.setPaint(Color.black);
      if (paintedPosition.getFile()==1)
          g2D.drawString(Integer.toString(paintedPosition.getRank()), xPos, yPos+yShift);
      if (paintedPosition.getRank()==1)
          g2D.drawString(PGNHandler.getFile(paintedPosition.getFile()), xPos+bigXShift, yPos+bigYShift);          

    }

    private void drawTile(Graphics2D g2D, ChessPosition paintedPosition) {
        int xPos=width*(paintedPosition.getFile()-1);  
        int yPos=height*(dimension-paintedPosition.getRank());
        g2D.setPaint(colors[(paintedPosition.getFile()+paintedPosition.getRank()+1)%2]);	  
        fillRect(g2D, xPos+xShift, yPos, width, height);
    }

  
  
}