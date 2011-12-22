/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chessrecognizer;

import chessrecognizer.pieces.Piece;
import chessrecognizer.pieces.PawnPiece;
import chessrecognizer.pieces.Piece.ChessPlayer;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Lenkas
 */
public class FigureTest {
    
    public FigureTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    
    @Test
    public void testPieceToString() {
        System.out.println("testFigureToString");
        Piece instance = new PawnPiece(1, 2, ChessPlayer.white);
        String expResult = "Piece{class chessrecognizer.PawnPiece, 12, white, isToMove=false}";
        String result = instance.toString();
        assertEquals(expResult, result);
    }
       
    
}
