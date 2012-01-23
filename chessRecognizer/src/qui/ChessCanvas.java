/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package qui;

import chessrecognizer.ChessPosition;
import chessrecognizer.View;
import chessrecognizer.pieces.Piece;
import java.awt.*;import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


public class ChessCanvas extends java.awt.Canvas
{
  private final int width=40,height=40,dimension=8;
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
  
  public void paint(Graphics g) 
  {
    int file, rank, xPos, yPos, numTile;
    
    Graphics2D g2D = (Graphics2D) g; 
    g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    movedPiecePreviousPosition = null;
    
    numTile=dimension*dimension;
    
    for (int tileCount=0;tileCount<numTile;tileCount++)
    {
          rank = 8-tileCount/dimension; //y
          file = tileCount%dimension+1;//x
	  xPos=width*(file-1);  
          yPos=height*(8-rank);
          
          g2D.setPaint(Color.black);
          if (file==1)
              g.drawString(Integer.toString(rank), xPos, yPos+25);
          
          g2D.setPaint(colors[(file+rank)%2]);
	  
          fillRect(g2D, xPos+20, yPos, width, height);

          Image img=null;
          if (view.getPiece(file, rank)!=null)
            try {
                img=ImageIO.read(new File(view.getPiece(file, rank).getImagePath()));
                if (view.getPiece(file, rank).previousMovePosition!=null){
                    g2D.setColor(Color.yellow);
                    fillRect(g2D, xPos+20, yPos, width, height);
                    movedPiecePreviousPosition = view.getPiece(file, rank).previousMovePosition;
                }
                g.drawImage(img,xPos+20,yPos,this);
            }
            catch(IOException e)
            {
                System.out.println(e.toString());
            }
          
          g2D.setPaint(Color.black);
          if (rank==1)
              g.drawString(Piece.getFile(file), xPos+35, yPos+60);          
    }
    
    if (movedPiecePreviousPosition!=null){
        xPos=width*(movedPiecePreviousPosition.getFile()-1);  
        yPos=height*(8-movedPiecePreviousPosition.getRank());
        g2D.setColor(Color.yellow);
        fillRect(g2D, xPos+20, yPos, width, height);
    }
    
  }
  private void fillRect(Graphics2D g2D,int x1,int y1,int x2,int y2)
  {
      Rectangle2D.Float r1=new Rectangle2D.Float(x1,y1,x2,y2);
      g2D.fill(r1);
      g2D.drawRect(x1, y1, x2, y2);
  }

}