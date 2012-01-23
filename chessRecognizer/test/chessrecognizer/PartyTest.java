/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chessrecognizer;

import chessrecognizer.pieces.*;
import chessrecognizer.pieces.PawnPiece;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
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
public class PartyTest {
    
    public PartyTest() {
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
    public void generateViewsForNoMove() throws FileNotFoundException, UnsupportedEncodingException {
        System.out.println("generateViewsForOneMove");
        String fileName = "test.pgn";
        createPGN(fileName, "");
        Party party = PGNHandler.parseParties(fileName).get(0);
        party.generateViews();
        
        assertNotNull(party.getViews());
        assertEquals(1, party.getViews().size());
    }
    
    
    @Test
    public void generateViewsForOneMove() throws FileNotFoundException, UnsupportedEncodingException {
        System.out.println("generateViewsForOneMove");
        String fileName = "test.pgn";
        createPGN(fileName, "1. f4 b6");
        Party party = PGNHandler.parseParties(fileName).get(0);
        party.generateViews();
        
        Piece blackPawn = new PawnPiece(2, 7, Piece.BLACK_PLAYER);
        blackPawn.previousMovePosition = new ChessPosition(2, 7);
                
        assertNotNull(party.getViews());
        assertEquals(3, party.getViews().size());
        assertNull(party.getViews().get(2).getPiece(6,2));
        assertEquals(blackPawn, party.getViews().get(1).getPiece(2,7));        
    }
    
    @Test
    public void generateViewsForKnightMoves() throws FileNotFoundException, UnsupportedEncodingException  {
        System.out.println("generateViewsForKnightMoves");
        String fileName = "testMove.pgn";
        createPGN(fileName, "1. Na3 Nf6 2. Nc4 Ng4");
        Party party = PGNHandler.parseParties(fileName).get(0);
        party.generateViews();
        
        Piece whiteKnight = new KnightPiece(3, 4, Piece.WHITE_PLAYER);
                
        assertNotNull(party.getViews());
        assertEquals(5, party.getViews().size());
        assertNull(party.getViews().get(4).getPiece(7,8));
        assertEquals(whiteKnight, party.getViews().get(4).getPiece(3,4));      
    }    
    
    @Test
    public void generateViewsForNotPawnKillingMoves() throws FileNotFoundException, UnsupportedEncodingException  {
        System.out.println("generateViewsForStandardKillingMoves");
        String fileName = "testMove.pgn";
        createPGN(fileName, "1. Nc3 d5 2. Nxd5 Qxd5");
        Party party = PGNHandler.parseParties(fileName).get(0);
        party.generateViews();
        
        Piece blackQueen = new QueenPiece(4, 5, Piece.BLACK_PLAYER);

                
        assertNotNull(party.getViews());
        assertEquals(5, party.getViews().size());
        assertEquals(32-2, party.getViews().get(4).getPieces().size());        
        assertEquals(blackQueen, party.getViews().get(4).getPiece(4,5));      
    }
    
    @Test
    public void generateViewsForPawnKillingMoves() throws FileNotFoundException, UnsupportedEncodingException  {
        System.out.println("generateViewsForPawnKillingMoves");
        String fileName = "testMove.pgn";
        createPGN(fileName, "1. a4 b5 2. axb5 Na6 ");
        Party party = PGNHandler.parseParties(fileName).get(0);
        party.generateViews();        
                
        assertNotNull(party.getViews());
        assertEquals(5, party.getViews().size());
        assertEquals(32-1, party.getViews().get(4).getPieces().size());        
    }
    
    @Test
    public void generateViewsForBishopMoves() throws FileNotFoundException, UnsupportedEncodingException  {
        System.out.println("generateViewsForBishopMoves");
        String fileName = "testMove.pgn";
        createPGN(fileName, "1. d3 d6 2. Bg5 Be6");
        Party party = PGNHandler.parseParties(fileName).get(0);
        party.generateViews();
                
        assertNotNull(party.getViews());
        assertEquals(5, party.getViews().size());
        assertNull(party.getViews().get(4).getPiece(3,8));  
    }   
    
    @Test
    public void generateViewsForQueenMoves() throws FileNotFoundException, UnsupportedEncodingException  {
        System.out.println("generateViewsForQueenMoves");
        String fileName = "testMove.pgn";
        createPGN(fileName, "1. e3 d5 2. Qg4 Qd6");
        Party party = PGNHandler.parseParties(fileName).get(0);
        party.generateViews();
        
        assertNotNull(party.getViews());
        assertEquals(5, party.getViews().size());
        assertNull(party.getViews().get(4).getPiece(4,1));  
    }  
    
    @Test
    public void generateViewsForRookMove() throws FileNotFoundException, UnsupportedEncodingException  {
        System.out.println("generateViewsForRookMove");
        String fileName = "testOneMove.pgn";
        createPGN(fileName, "1. a4 h6 2. Ra3 Rh7");
        Party party = PGNHandler.parseParties(fileName).get(0);
        party.generateViews();
        
        Piece whiteRook = new RookPiece(1, 1, Piece.WHITE_PLAYER);
        whiteRook.previousMovePosition = new ChessPosition(1, 1);
                
        assertNotNull(party.getViews());
        assertEquals(5, party.getViews().size());
        assertNull(party.getViews().get(4).getPiece(8,8));
        assertEquals(whiteRook, party.getViews().get(2).getPiece(1,1));
        
    }    

    @Test
    public void generateViewsForSomeBadParty() throws FileNotFoundException, UnsupportedEncodingException  {
        System.out.println("generateViewsForRookMove");
        String fileName = "testOneMove.pgn";
        createPGN(fileName, "1.d4 Nf6 2.c4 c5 3.d5 e6 4.Nc3 exd5 " + 
                "5.cxd5 d6 6.e4 g6 7.f4 Bg7 8.Bb5+ Nfd7 " +  
                "9.a4 Qh4+ 10.g3 Qe7 11.Nf3 O-O 12.O-O Na6 " +
                "13.e5 Nb4 14.Ne4 dxe5 15.d6 Qd8 16.fxe5 Nc6 " +
                "17.Bg5 Qb6 18.Nf6+ Kh8 19.Nd5 Qa5 20.Nd2 Ndxe5 " +
                "21.Nb3 Bg4 22.Qc1 Nf3+ 23.Rxf3 Bxf3 24.Nxa5 Bxd5 " +
                "25.Nxc6 bxc6 26.Qxc5  1-0");
        Party party = PGNHandler.parseParties(fileName).get(0);
        party.generateViews();
        
        assertEquals(52, party.getViews().size());
    }    

    
    @Test
    public void generateViewsForKingMove() throws FileNotFoundException, UnsupportedEncodingException  {
        System.out.println("generateViewsForKingMove");
        String fileName = "testOneMove.pgn";
        createPGN(fileName, "1. e4 f6 2. Ke2 Kf7");
        Party party = PGNHandler.parseParties(fileName).get(0);
        party.generateViews();
                
        assertNotNull(party.getViews());
        assertEquals(5, party.getViews().size());
        assertNull(party.getViews().get(4).getPiece(5,1));        
    }    
    
    @Test
    public void generateViewsForKnightMove() throws FileNotFoundException, UnsupportedEncodingException  {
        System.out.println("generateViewsForKnightMove");
        String fileName = "testOneMove.pgn";
        createPGN(fileName, "1. Na3 Nh6");
        Party party = PGNHandler.parseParties(fileName).get(0);
        party.generateViews();
        
        assertNotNull(party.getViews());
        assertEquals(3, party.getViews().size());
        assertNull(party.getViews().get(2).getPiece(2,1));        
    }
    
    @Test
    public void generateViewsForKingsideCastling() throws FileNotFoundException, UnsupportedEncodingException  {
        System.out.println("generateViewsForKingsideCastling");
        String fileName = "testOneMove.pgn";
        createPGN(fileName, "1. e4 e5 2. Nf3 Nc6 3. Bb5 a6 4. Ba4 Nf6 5. O-O Be7 6. Re1 b5 7. Bb3 d6 ");
        Party party = PGNHandler.parseParties(fileName).get(0);
        party.generateViews();
        
        assertNotNull(party.getViews());
        assertEquals(1+7*2, party.getViews().size());
   
    }  
    
    @Test
    public void generateViewsForMoveWithInitialCoordinate() throws FileNotFoundException, UnsupportedEncodingException  {
        System.out.println("generateViewsForMoveWithInitialCoordinate");
        String fileName = "testOneMove.pgn";
        createPGN(fileName, "1. e4 e5 2. Nf3 Nc6 3. Bb5 a6 4. Ba4 Nf6 "+
                "5. O-O Be7 6. Re1 b5 7. Bb3 d6 8. c3 O-O 9. h3 Nb8  10. d4 Nbd7 11. c4 c6");
        Party party = PGNHandler.parseParties(fileName).get(0);
        party.generateViews();
        
        assertNotNull(party.getViews());
        assertEquals(1+11*2, party.getViews().size());   
    }    

   
    public static void createPGN(String file_name, String moves) throws FileNotFoundException, UnsupportedEncodingException{
        try {
            PrintWriter writer = new PrintWriter(
                 new OutputStreamWriter(
                 new FileOutputStream(file_name), "windows-1251"));
            writer.write("[Event \"F/S Return Match\"]\n\n" + moves);
            writer.close();
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(PGNHandlerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
