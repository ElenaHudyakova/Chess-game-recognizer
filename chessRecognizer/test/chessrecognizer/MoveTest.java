/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chessrecognizer;

import chessrecognizer.Figure.Type;
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
public class MoveTest {
    
    public MoveTest() {
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
    public void testParseFigureTypeToMovePawn() {
        System.out.println("testParseFigureTypeToMovePawn");
        Move firstMove = new Move(-1, 1, "f4", Figure.ChessColor.white);
        Type expResult = Type.pawn;
        Type result = firstMove.parseFigureTypeToMove();
        assertEquals(expResult, result);
    }
    
    @Test
    public void testParseFigureTypeToMoveKnight() {
        System.out.println("testParseFigureTypeToMoveKnight");
        Move firstMove = new Move(-1, 1, "Nf3", Figure.ChessColor.white);
        Type expResult = Type.knight;
        Type result = firstMove.parseFigureTypeToMove();
        assertEquals(expResult, result);
    }   


    @Test
    public void testParseRankMoveTo() {
        System.out.println("parseRankMoveTo");
        Move firstMove = new Move(-1, 1, "Nf3", Figure.ChessColor.white);
        int expResult = 3;
        int result = firstMove.parseRankMoveTo();
        assertEquals(expResult, result);
    }


    @Test
    public void testParseFileMoveToKnight() {
        System.out.println("testParseFileMoveToKnight");
        Move firstMove = new Move(-1, 1, "Nf3", Figure.ChessColor.white);
        int expResult = 6;
        int result = firstMove.parseFileMoveTo();
        assertEquals(expResult, result);
    }
    
    @Test
    public void testParseFileMoveToPawn() {
        System.out.println("testParseFileMoveToPawn");
        Move firstMove = new Move(-1, 1, "f4", Figure.ChessColor.white);
        int expResult = 6;
        int result = firstMove.parseFileMoveTo();
        assertEquals(expResult, result);
    }    
}

