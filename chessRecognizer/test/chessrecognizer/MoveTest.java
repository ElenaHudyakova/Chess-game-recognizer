/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chessrecognizer;


import chessrecognizer.pieces.*;
import chessrecognizer.pieces.QueenPiece;
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
    public void testParseSimplePawnMove() {
        System.out.println("testParseSimplePawnMove");
        Move testMove = new Move(-1, 1, "a4", Piece.WHITE_PLAYER);
        PGNHandler.parseMove(testMove);
        assertEquals(new ChessPosition(0, 0), testMove.moveFromPosition);
        assertEquals(new ChessPosition(1,4), testMove.moveToPosition);     
        assertEquals(PawnPiece.class, testMove.movingPiece);    
        assertEquals(false, testMove.isCheck);
        assertEquals(false, testMove.isCheckmate);        
    }
    
    @Test
    public void testParseCaptionPawnMove() {
        System.out.println("testParseCaptionPawnMove");
        Move testMove = new Move(-1, 1, "bxa7", Piece.WHITE_PLAYER);
        PGNHandler.parseMove(testMove);
        assertEquals(new ChessPosition(2, 0), testMove.moveFromPosition);
        assertEquals(new ChessPosition(1,7), testMove.moveToPosition);         
        assertEquals(PawnPiece.class, testMove.movingPiece);      
    }
    
    @Test
    public void testParsePromotionMove() {
        System.out.println("testParsePromotionMove");
        Move testMove = new Move(-1, 1, "e8=Q", Piece.WHITE_PLAYER);
        PGNHandler.parseMove(testMove);
        assertEquals(new ChessPosition(0, 0), testMove.moveFromPosition);
        assertEquals(new ChessPosition(5,8), testMove.moveToPosition);      
        assertEquals(PawnPiece.class, testMove.movingPiece);      
        assertEquals(QueenPiece.class, testMove.promotionPiece); 
    }    
    
    @Test
    public void testParseKingMove() {
        System.out.println("testParseKingMove");
        Move testMove = new Move(-1, 1, "Ke8", Piece.WHITE_PLAYER);
        PGNHandler.parseMove(testMove);
        assertEquals(new ChessPosition(0, 0), testMove.moveFromPosition);
        assertEquals(new ChessPosition(5,8), testMove.moveToPosition);         
        assertEquals(KingPiece.class, testMove.movingPiece);      
        assertEquals(false, testMove.isCaptionMove); 
    }      

    @Test
    public void testParseBishopCaptionMove() {
        System.out.println("testParseBishopMove");
        Move testMove = new Move(-1, 1, "B1xa8", Piece.WHITE_PLAYER);
        PGNHandler.parseMove(testMove);
        assertEquals(new ChessPosition(0, 1), testMove.moveFromPosition);
        assertEquals(new ChessPosition(1,8), testMove.moveToPosition);                
        assertEquals(BishopPiece.class, testMove.movingPiece);      
        assertEquals(true, testMove.isCaptionMove); 
    }  
    
    @Test
    public void testParseCheckMove() {
        System.out.println("testParseCheckMove");
        Move testMove = new Move(-1, 1, "e6+", Piece.WHITE_PLAYER);
        PGNHandler.parseMove(testMove);
  
        assertEquals(false, testMove.isCaptionMove); 
        assertEquals(true, testMove.isCheck); 
        assertEquals(false, testMove.isCheckmate);         
    }      
    
    @Test
    public void testParseCheckmateMove() {
        System.out.println("testParseCheckmateMove");
        Move testMove = new Move(-1, 1, "e6#", Piece.WHITE_PLAYER);
        PGNHandler.parseMove(testMove);
  
        assertEquals(false, testMove.isCaptionMove); 
        assertEquals(false, testMove.isCheck); 
        assertEquals(true, testMove.isCheckmate);         
    }   
    
    @Test
    public void testParseKingCastling() {
        System.out.println("testParseKingCastling");
        Move testMove = new Move(-1, 1, "O-O", Piece.WHITE_PLAYER);
        PGNHandler.parseMove(testMove);
  
        assertEquals(true, testMove.isKingCastling);   
        assertEquals(false, testMove.isQueenCastling);  
    } 
    
    @Test
    public void testParseQueenCastling() {
        System.out.println("testParseQueenCastling");
        Move testMove = new Move(-1, 1, "O-O-O", Piece.WHITE_PLAYER);
        PGNHandler.parseMove(testMove);
  
        assertEquals(true, testMove.isQueenCastling); 
        assertEquals(false, testMove.isKingCastling); 
    } 
}

