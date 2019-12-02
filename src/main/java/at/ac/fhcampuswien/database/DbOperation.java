package at.ac.fhcampuswien.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.scene.paint.Color;

public class DbOperation {

	public static void main(String[] args) {
		
	}
	
	public static void insertBodyPart(Connection conn, Color color, double height, double width, double posX, double posY) {
		
	      Statement stmt = null; 
	      try{
	         stmt = conn.createStatement();  
	         String sql = "INSERT INTO SNAKE_BODY_PART " + "VALUES (default, " + color.getRed() + ", " + color.getGreen() + ", " + color.getBlue() + ", " + height + ", " + width + ", " + posX + ", " + posY + ")"; 
	         stmt.executeUpdate(sql);
	         stmt.close();
	      } catch(SQLException se) { 
	         // Handle errors for JDBC 
	         se.printStackTrace(); 
	      } catch(Exception e) { 
	         // Handle errors for Class.forName 
	         e.printStackTrace(); 
	      } finally { 
	         // finally block used to close resources 
	         try {
	            if(stmt!=null) stmt.close();  
	         } catch(SQLException se2) { 
	         } // nothing we can do 
	      } // end try 
	}
}
