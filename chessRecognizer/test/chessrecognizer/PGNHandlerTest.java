/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chessrecognizer;

import java.util.Date;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.List;
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
public class PGNHandlerTest {
    
    public PGNHandlerTest() {
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
    
    
    public void createSimplePGN() throws FileNotFoundException{
        String file_name = "simple.pgn";
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
                    "[Result \"1/2-1/2\"]");
            writer.close();
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(PGNHandlerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void createTwoPartiesPGN() throws FileNotFoundException{
        String file_name = "twoParties.pgn";
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
            
            writer.write("[Event \"F/S Return Match\"]\n" + 
                    "[Site \"Belgrade, Serbia Yugoslavia|JUG\"]\n" +
                    "[Date \"1992.01.04\"]\n" +
                    "[Round \"29.1\"]\n" +                 
                    "[White \"Fischer, Robert J.\"]\n" +
                    "[Black \"Spassky, Boris V.\"]\n" +
                    "[Result \"0-1\"]\n");
            writer.close();
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(PGNHandlerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @Test
    public void testParseTagPairsForOneParty() throws FileNotFoundException {
        System.out.println("parseOneParty");
        createSimplePGN();
        String file_name = "simple.pgn";        
        
        List<Party> resultParties = PGNHandler.parseParties(file_name);
        
        assertNotNull(resultParties);
        assertEquals(1, resultParties.size());        
        assertEquals("F/S Return Match", resultParties.get(0).getEvent());
        assertEquals("Belgrade, Serbia Yugoslavia|JUG", resultParties.get(0).getSite());
        assertEquals(new Date(1992, 4, 11), resultParties.get(0).getDate());
        assertEquals("29", resultParties.get(0).getRound());
        assertEquals("Fischer, Robert J.", resultParties.get(0).getWhite());
        assertEquals("Spassky, Boris V.", resultParties.get(0).getBlack());
        assertEquals(Party.DRAW, resultParties.get(0).getResult());

    }

    @Test
    public void testParseTagPairsForTwoParties() throws FileNotFoundException {
        System.out.println("parseTwoParties");
        createTwoPartiesPGN();
        String file_name = "twoParties.pgn";        
        
        List<Party> resultParties = PGNHandler.parseParties(file_name);
        
        assertNotNull(resultParties);
        assertEquals(2, resultParties.size());        
        assertEquals("F/S Return Match", resultParties.get(1).getEvent());
        assertEquals("Belgrade, Serbia Yugoslavia|JUG", resultParties.get(1).getSite());
        assertEquals(new Date(1992, 4, 1), resultParties.get(1).getDate());
        assertEquals("29.1", resultParties.get(1).getRound());
        assertEquals("Fischer, Robert J.", resultParties.get(1).getWhite());
        assertEquals("Spassky, Boris V.", resultParties.get(1).getBlack());
        assertEquals(Party.BLACK_WIN, resultParties.get(1).getResult());

    }    
    
}
