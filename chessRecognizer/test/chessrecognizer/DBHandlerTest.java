/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chessrecognizer;

import java.util.List;
import java.util.ArrayList;
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

public class DBHandlerTest {
    
    public DBHandlerTest() {
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

    /**
     * Test of connect method, of class DBHandler.
     */
    @Test
    public void testConnect() {
        System.out.println("connect");
        DBHandler instance = new DBHandler();

        instance.externalConnect();
        instance.disconnect();
    }

    
    @Test
    public void testAddParty(){
        System.out.println("testAddParty");
        DBHandler dbHandler = new DBHandler();
        dbHandler.externalConnect();
        List<Party> parties = PGNHandler.parseParties("test_party.PGN");
        dbHandler.addParty(parties.get(0));
        dbHandler.disconnect();        
    }
}

