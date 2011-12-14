package chessrecognizer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

class DBHandler{
	Connection connection = null;

	public boolean connect(){    
		try
	    {
	        String userName = "root";
	        String password = "123";
	        String url = "jdbc:mysql://localhost:3306/testdatabase";
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
        
        
//	public List<Detail> getAllDetails(){
//		List<Detail> details = new ArrayList<Detail>();
//		try {
//			Statement selectQuery = connection.createStatement();
//			ResultSet rs = selectQuery.executeQuery("select quantity, ID, name, price from Details");
//			while (rs.next()) {
//				details.add(new Detail(rs.getInt(1),rs.getInt(2), rs.getString(3), rs.getFloat(4)));
//			}
//			rs.close();
//			selectQuery.close();
//			return details;
//		} catch (Exception e) {
//			System.out.println(e.toString());
//			return null;
//		}
//	}
//	
//	public boolean addDetail(Detail detail){
//		try {
//			Statement insertQuery = connection.createStatement();
//			insertQuery.executeUpdate("insert into Details (quantity, name, price) values (" + 
//							detail.getQuantity() + ", '" + detail.getName() + "', " + 
//							detail.getPrice() + ");");
//			insertQuery.close();	
//			
//			Statement selectID = connection.createStatement();
//			ResultSet ID = selectID.executeQuery("select last_insert_id();");
//			ID.next();
//			detail.setId(ID.getInt(1));
//			return true	;
//		} catch (Exception e) {
//			System.out.println(e.toString());
//			return false;
//		}
//	}
//	
//	public boolean deleteDetail(int ID){
//		try{
//			Statement deleteQuery = connection.createStatement();
//			deleteQuery.executeUpdate("delete from Details where id="+ID);
//			return true;
//		} catch (Exception e) {
//			System.out.println(e.toString());
//			return false;
//		}
//	}
//	
//	public boolean editDetail(int ID, Detail detail){
//		try{
//			Statement updateQuery = connection.createStatement();
//			updateQuery.executeUpdate("update Details set name='"+ detail.getName() +
//											"', price=" + detail.getPrice() +
//											", quantity=" + detail.getQuantity() +" where id="+ID);
//			return true;
//		} catch (Exception e) {
//			System.out.println(e.toString());
//			return false;
//		}
//		
//	}
//        
//        public Detail getDetail(int ID){
//            try {
//                Statement selectQuery = connection.createStatement();
//                ResultSet rs = selectQuery.executeQuery("select quantity, ID, name, price from Details where id="+ID);
//                if (rs.next())
//                    return new Detail(rs.getInt(1),rs.getInt(2), rs.getString(3), rs.getFloat(4));
//                else return null;
//	    } catch (Exception e) {
//			System.out.println(e.toString());
//			return null;
//            }            
//        }
	
}
