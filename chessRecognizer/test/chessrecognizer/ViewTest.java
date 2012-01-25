/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chessrecognizer;

import chessrecognizer.pieces.KingPiece;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import chessrecognizer.pieces.PawnPiece;
import chessrecognizer.pieces.Piece;
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
public class ViewTest {
    
    public ViewTest() {
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
    public void testCreateViewOutOfFENOnInitialPosition() {
        System.out.println("testCreateViewOutOfFENOnInitialPosition");
        View instance = new View("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
        assertEquals(32, instance.getPieces().size());
        assertEquals(null, instance.getPiece(new ChessPosition(5, 4)));
        assertEquals(PawnPiece.class, instance.getPiece(new ChessPosition(5,2)).getClass());        
    }
    
    @Test
    public void testCreateViewOutOfFEN() {
        System.out.println("testCreateViewOutOfFEN");
        View instance = new View("rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR w");
        assertEquals(32, instance.getPieces().size());
        assertEquals(PawnPiece.class, instance.getPiece(new ChessPosition(5, 4)).getClass());
        assertEquals(null, instance.getPiece(new ChessPosition(5,2)));        
    }
    
    @Test
    public void testGetKingForInitialView(){
        System.out.println("testGetKingForInitialView");
        View view = new View();
        view.setInitialView();
        Piece whiteKing = view.getKing(Piece.WHITE_PLAYER); 
        Piece blackKing = view.getKing(Piece.BLACK_PLAYER);
        
        assertEquals(KingPiece.class, whiteKing.getClass());
        assertEquals(KingPiece.class, blackKing.getClass());
        assertEquals(new ChessPosition(5,1), whiteKing.getPosition());
        assertEquals(new ChessPosition(5,8), blackKing.getPosition());               
    }
    
    @Test
    public void testSearchingPartiesWithBlob() throws FileNotFoundException, UnsupportedEncodingException{
        System.out.println("testSearchingPartiesWithBlob");
        View instance = new View("rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR b");
        
        String fileName = "test.pgn";
        PartyTest.createPGN(fileName, "1. e4 b6");
        Party party = PGNHandler.parseParties(fileName).get(0);
                
        DBHandler dbHandler = new DBHandler();
        dbHandler.externalConnect();
        dbHandler.addParty(party);
        List<Party> parties = dbHandler.getPartiesInfo(instance.serialize());
        dbHandler.disconnect(); 
        
        assertEquals(true, parties.size()>=1);        
    }
    

}
