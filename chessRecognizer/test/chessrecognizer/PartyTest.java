/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chessrecognizer;

import java.util.logging.Logger;
import java.util.logging.Level;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
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
        String fileName = "testOneMove.pgn";
        createOnePartyNoMovesPGN(fileName);
        Party party = PGNHandler.parseParties(fileName).get(0);
        party.generateViews();
        
        assertNotNull(party.getViews());
        assertEquals(1, party.getViews().size());
    }
    
    
    @Test
    public void generateViewsForOneMove() throws FileNotFoundException {
        System.out.println("generateViewsForOneMove");
        String fileName = "testOneMove.pgn";
        PGNHandlerTest.createOnePartyPGN(fileName);
        Party party = PGNHandler.parseParties(fileName).get(0);
        party.generateViews();
        
        Figure blackPawn = new Figure(2, 7, Figure.Type.pawn, Figure.ChessColor.black, true);
        
        assertNotNull(party.getViews());
        assertEquals(3, party.getViews().size());
        assertNull(party.getViews().get(2).getFigure(6,2));
        assertEquals(blackPawn, party.getViews().get(1).getFigure(2,7));
        
    }
    
    
    
    public static void createOnePartyNoMovesPGN(String file_name) throws FileNotFoundException, UnsupportedEncodingException{
        try {
            PrintWriter writer = new PrintWriter(
                 new OutputStreamWriter(
                 new FileOutputStream(file_name), "windows-1251"));
            writer.write("[Event \"F/S Return Match\"]\n" + 
                    "[Site \"Belgrade, Serbia Yugoslavia|JUG\"]\n" +
                    "[Date \"1992.11.04\"]\n" +
                    "[Round \"29\"]\n" +                 
                    "[White \"Fischer, Robert J.\"]\n" +
                    "[Black \"Spassky, Boris V.\"]\n" +
                    "[Result \"1/2-1/2\"]\n\n");
            
            writer.close();
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(PGNHandlerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
