package chessrecognizer;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class DBHandler{
	Connection connection = null;

	public boolean connect(){    
		try
	    {
	        String userName = "chess";
	        String password = "lenusik5";
	        String url = "jdbc:mysql://62.76.47.251:3306/chess";
	        Class.forName ("com.mysql.jdbc.Driver").newInstance ();
	        connection = DriverManager.getConnection(url, userName, password);
	        System.out.println ("Database connection established");
	        return true;
	    }
	    catch (Exception e)
	    {
	    	System.err.println(e.toString());
	        System.err.println ("Cannot connect to database server");
	        return false;
	    }
	}	
	public boolean disconnect(){
		try{
			if (connection != null)
	        {
	                connection.close ();
	                System.out.println ("Database connection terminated");	     
	        }
	        return true;
		}
		catch (Exception e) { 
			return false;
		}
	}
        
        

    public boolean addParty(Party party){
            try {
                    Statement insertPartyQuery = connection.createStatement();
                    System.out.println(party.getDate());
                    insertPartyQuery.executeUpdate("insert into parties (event, site, date, round, white, black, result) values ('" + 
                                                    party.getEvent() + "', '" + party.getSite() + "', '" +
                                                    party.getDate() +  "', '" + party.getRound() + "', '" +
                                                    party.getWhite() + "', '" + party.getBlack()+ "', '" + party.getResult()+ "');");
                    insertPartyQuery.close();	

                    Statement selectID = connection.createStatement();
                    ResultSet ID = selectID.executeQuery("select last_insert_id();");
                    ID.next();
                    party.setId(ID.getInt(1));

                    for (Move move:party.getMoves()){
                        Statement insertMovesQuery = connection.createStatement();
                        insertMovesQuery.executeUpdate("insert into moves (party_id, move_number, move_content, player) values (" + 
                                                        party.getId() + ", " + move.getMoveNumber() + ", '" +
                                                        move.getMoveContent() +  "', " + move.getPlayer() + ");");
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
            ResultSet rs = selectQuery.executeQuery("select id, event, site, round, white, black, date, result  from parties "+ condition);
            while (rs.next()){
                parties.add(new Party(rs.getInt(1),rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),rs.getString(6), rs.getString(7), rs.getString(8)));
            }
            return parties;
        } catch (Exception e) {
                System.out.println(e.toString());
                return null;
        }           
    }

    public Party getFullPartyInfo(int id) {
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
	
}
