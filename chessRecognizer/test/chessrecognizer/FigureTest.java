/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chessrecognizer;

import chessrecognizer.Piece.ChessColor;
import chessrecognizer.Piece.Type;
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
    public void testFigureToString() {
        System.out.println("testFigureToString");
        Piece instance = new Piece(1, 2, Type.pawn, ChessColor.white);
        String expResult = "Figure{pawn, 12, white, isToMove=false}";
        String result = instance.toString();
        assertEquals(expResult, result);
    }
       
    
}
