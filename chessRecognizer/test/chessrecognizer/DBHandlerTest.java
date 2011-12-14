/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chessrecognizer;

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
        boolean expResult = false;
        boolean result = instance.connect();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    /**
     * Test of disconnect method, of class DBHandler.
     */
    @Test
    public void testDisconnect() {
        System.out.println("disconnect");
        DBHandler instance = new DBHandler();
        boolean expResult = false;
        boolean result = instance.disconnect();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }
}

