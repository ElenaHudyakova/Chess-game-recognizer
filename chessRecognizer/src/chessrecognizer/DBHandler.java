package chessrecognizer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DBHandler{
    Connection connection = null;

    public void connect(String userName, String password, String url){
        try
        {
            Class.forName ("com.mysql.jdbc.Driver").newInstance ();
            connection = DriverManager.getConnection(url, userName, password);
            System.out.println ("Database connection established");
        }
        catch (Exception e)
        {
            System.err.println(e.toString());
            System.err.println ("Cannot connect to database server");
        }           
    }

    public void externalConnect(){    
        String userName = "chess";
        String password = "lenusik5";
        String url = "jdbc:mysql://62.76.47.251:3306/chess";
        connect(userName, password, url);
    }

    public void localConnect(){    
        String userName = "root";
        String password = "123";
        String url = "jdbc:mysql://localhost:3306/chess";
        connect(userName, password, url);
    }

    public void disconnect(){
        try{
            if (connection != null)
            {
                connection.close ();
                System.out.println ("Database connection terminated");	     
            }
        }
        catch (Exception e) { 
             System.err.println(e.toString());
        }
    }


    public boolean addParty(Party party){
        try {
                party.generateViews();
                
                Statement insertPartyQuery = connection.createStatement();
                insertPartyQuery.executeUpdate("insert into parties (event, site, date, round, white, black, result) values ('" + 
                                                party.getEvent().replace("'", "''") + "', '" + party.getSite().replace("'", "''") + "', '" +
                                                party.getDate() +  "', '" + party.getRound() + "', '" +
                                                party.getWhite().replace("'", "''") + "', '" + 
                                                party.getBlack().replace("'", "''") + "', '" + party.getResult()+ "');");
                insertPartyQuery.close();	

                Statement selectID = connection.createStatement();
                ResultSet ID = selectID.executeQuery("select last_insert_id();");
                ID.next();
                party.setId(ID.getInt(1));

                for (int i=0; i<party.getMoves().size(); i++){
                    Move move = party.getMoves().get(i);
                    long [] blobs = party.getViews().get(i).serialize();
                    Statement insertMovesQuery = connection.createStatement();
                    insertMovesQuery.executeUpdate("insert into moves (party_id, move_number, move_content, "
                            + "player, view_blob1, view_blob2, view_blob3, view_blob4 ) values (" + 
                                                    party.getId() + ", " + move.getMoveNumber() + ", '" +
                                                    move.getMoveContent() +  "', " + move.getPlayer() + ", " +  
                                                    blobs[0] + ", " + blobs[1] + ", " + blobs[2] + ", "+ blobs[3] + ");");
                    insertMovesQuery.close();                            
                }

                return true;
        } catch (Exception e) {
                System.out.println(e.toString());
                return false;
        }
    }

    public ArrayList<Party> getPartiesInfo(String condition) {
        try {
            ArrayList<Party> parties = new ArrayList<Party>();
            Statement selectQuery = connection.createStatement();
            ResultSet rs = selectQuery.executeQuery("select parties.id, event, site, round, white, black, date, result  from parties "+ condition);
            while (rs.next()){
                parties.add(new Party(rs.getInt(1),rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),rs.getString(6), rs.getString(7), rs.getString(8)));
            }
            return parties;
        } catch (Exception e) {
                System.out.println(e.toString());
                return null;
        }           
    }

    public Party getFullParty(int id) {
        try {
            Party party = null; 
            Statement selectQuery = connection.createStatement();
            ResultSet rs = selectQuery.executeQuery("select id, event, site, round, white, black, date, result  from parties WHERE id = "+id);
            if (rs.next()){
                party = new Party(rs.getInt(1),rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),rs.getString(6), rs.getString(7), rs.getString(8));
            }
            
            selectQuery = connection.createStatement();
            rs = selectQuery.executeQuery("select move_number, move_content, player  from moves where party_id="+ party.getId());
            while (rs.next()){
                party.addMove(new Move(party.getId(), rs.getInt(1), rs.getString(2), rs.getInt(3)));
            }
            return party;
        } catch (Exception e) {
                System.out.println(e.toString());
                return null;
        }  
    }

    public List<Party> getPartiesInfo(long[] blob) {
        return getPartiesInfo(", moves WHERE moves.party_id=parties.id and "
                + "moves.view_blob1=" + blob[0] + " and moves.view_blob2=" + blob[1] +
                " and moves.view_blob3=" + blob[2] + " and moves.view_blob4=" + blob[3]);
    }
	
}
