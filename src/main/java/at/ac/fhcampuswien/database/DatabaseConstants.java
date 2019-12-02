package at.ac.fhcampuswien.database;

public class DatabaseConstants {
   // JDBC driver name and database URL 
   public static final String JDBC_DRIVER = "org.h2.Driver";   
   public static final String DB_URL = "jdbc:h2:./db/game";
   //;INIT=runscript from './db/init.sql'";  
   
   // Database credentials 
   public static final String USER = "sa"; 
   public static final String PASS = "";
   
   // SQL Statements
   public static final String SQL_TABLE_SBP = "SNAKE_BODY_PART";
   // Parameters: default, red, green, blue, height, width, posX, posY
   public static final String SQL_INSERT_SBP = "INSERT INTO SNAKE_BODY_PART VALUES (default,  %s, %s, %s, %s, %s, %s, %s)";
   public static final String SQL_SELECT_BODYPARTS = "SELECT id, red, green, blue, height, width, pos_x, pos_y FROM SNAKE_BODY_PART ORDER BY id ASC";
}
