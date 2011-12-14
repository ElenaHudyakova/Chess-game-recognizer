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
    
    
    public void createOnePartyPGN() throws FileNotFoundException{
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
                    "[Result \"1/2-1/2\"]\n\n" +
                    "1. f4 b6 2. Nf3 Bb7\n\n");
            
            writer.close();
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(PGNHandlerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void createTwoPartiesPGN(String file_name) throws FileNotFoundException{
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
                    "[Result \"1/2-1/2\"]\n\n" +
                  "1. f4 b6 2. Nf3 Bb7 3. e3 c5 4. b3 Nc6 5. Bb5 Nf6 6. Bb2 e6 7. Bxc6 Bxc6 8. O-O " +
                  "Be7 9. Qe1 O-O 10. d3 Rc8 11. Nbd2 d5 12. Ne5 Ba8 13. Qg3 Nh5 14. Qh3 g6\n" +
                  "1/2-1/2\n\n");
            
            writer.write("[Event \"F/S Return Match\"]\n" + 
                    "[Site \"Belgrade, Serbia Yugoslavia|JUG\"]\n" +
                    "[Date \"1992.01.04\"]\n" +
                    "[Round \"29.1\"]\n" +                 
                    "[White \"Fischer, Robert J.\"]\n" +
                    "[Black \"Spassky, Boris V.\"]\n" +
                    "[Result \"0-1\"]\n\n" +
                  "1. f4 b6 2. Nf3 Bb7 3. e3 c5 4. b3 Nc6 5. Bb5 Nf6 6. Bb2 e6 7. Bxc6 Bxc6 8. O-O " +
                  "Be7 9. Qe1 O-O 10. d3 Rc8 11. Nbd2 d5\n\n");
            writer.close();
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(PGNHandlerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @Test
    public void testParseTagPairsForOneParty() throws FileNotFoundException {
        System.out.println("testParseTagPairsForOneParty");
        createOnePartyPGN();
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
        System.out.println("testParseTagPairsForTwoParties");
        String file_name = "twoParties.pgn"; 
        createTwoPartiesPGN(file_name);               
        
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
    
    @Test
    public void testParseOneMoveForOneParty() throws FileNotFoundException {
        System.out.println("testParseOneMoveForOneParty");
        createOnePartyPGN();
        String file_name = "simple.pgn";        
        
        List<Party> resultParties = PGNHandler.parseParties(file_name);
        
        assertNotNull(resultParties);
        assertNotNull(resultParties.get(0).getMoves());   
        assertEquals(1, resultParties.get(0).getMove(1).getMoveNumber());
        assertEquals(Move.WHITE_PLAYER, resultParties.get(0).getMove(1).getPlayer());
        assertEquals("f4", resultParties.get(0).getMove(1).getMoveContent());
        assertEquals(1, resultParties.get(0).getMove(2).getMoveNumber());
        assertEquals(Move.BLACK_PLAYER, resultParties.get(0).getMove(2).getPlayer());
        assertEquals("b6", resultParties.get(0).getMove(2).getMoveContent());
        assertEquals(-1, resultParties.get(0).getMove(2).getPartyID());
    }    
    
    @Test
    public void testParseTwoMovesForOneParty() throws FileNotFoundException {
        System.out.println("testParseTwoMovesForOneParty");
        createOnePartyPGN();
        String file_name = "simple.pgn";        
        
        List<Party> resultParties = PGNHandler.parseParties(file_name);
        
        assertNotNull(resultParties);
        assertNotNull(resultParties.get(0).getMoves());   
        assertEquals(4, resultParties.get(0).getMoves().size());
        assertEquals(1, resultParties.get(0).getMove(1).getMoveNumber());
        assertEquals("Nf3", resultParties.get(0).getMove(3).getMoveContent());
        assertEquals("Bb7", resultParties.get(0).getMove(4).getMoveContent());
    }
    
    @Test
    public void testParseManyMoves() throws FileNotFoundException {
        System.out.println("testParseManyMoves");
        String file_name = "manyMoves.pgn";    
        createManyMovesPGN(file_name);    
        
        List<Party> resultParties = PGNHandler.parseParties(file_name);
        
        assertNotNull(resultParties);
        assertNotNull(resultParties.get(0).getMoves());   
        assertEquals(30*2, resultParties.get(0).getMoves().size());
        assertEquals(30, resultParties.get(0).getMove(59).getMoveNumber());//последний ход
        assertEquals("Bc1", resultParties.get(0).getMove(59).getMoveContent());       
    }
    
    @Test
    public void testParseManyMovesTwoParties() throws FileNotFoundException {
        System.out.println("testParseManyMovesTwoParties");
        String file_name = "twoParties.pgn";    
        createTwoPartiesPGN(file_name);    
        
        List<Party> resultParties = PGNHandler.parseParties(file_name);
        
        assertNotNull(resultParties);
        assertEquals(2, resultParties.size());   
        assertEquals(14*2, resultParties.get(0).getMoves().size());
        assertEquals(11*2, resultParties.get(1).getMoves().size());     
    }    

    private void createManyMovesPGN(String file_name) throws FileNotFoundException {              
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
                    "[Result \"1/2-1/2\"]\n\n" +
                  "1. f4 b6 2. Nf3 Bb7 3. e3 c5 4. b3 Nc6 5. Bb5 Nf6 6. Bb2 e6 7. Bxc6 Bxc6 8. O-O " +
                  "Be7 9. Qe1 O-O 10. d3 Rc8 11. Nbd2 d5 12. Ne5 Ba8 13. Qg3 Nh5 14. Qh3 g6 15. g4 " +
                  "Nf6 16. g5 Nh5 17. Rf3 d4 18. e4 Bxg5 19. Raf1 Nxf4 20. Rxf4 Bxf4 21. Rxf4 Qg5+ " +
                  "22. Qg4 Qxe5 23. Nf3 Qg7 24. Nd2 e5 25. Rf2 Rc6 26. Nf1 Rf6 27. Rg2 Rf4 28. Qe2 " +
                  "f5 29. Ng3 fxe4 30. Bc1 e3\n\n");
            
            writer.close();
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(PGNHandlerTest.class.getName()).log(Level.SEVERE, null, ex);
        }        
        
        
    }
    
    
    
}
