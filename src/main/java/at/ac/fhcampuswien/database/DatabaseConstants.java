package at.ac.fhcampuswien.database;

public class DatabaseConstants {
   // JDBC driver name and database URL 
   public static final String JDBC_DRIVER = "org.h2.Driver";   
   static final String DB_URL = "jdbc:h2:./db/game";
   //;INIT=runscript from './db/init.sql'";  
   
   // Database credentials 
   static final String USER = "sa";
   static final String PASS = "";
   
   // SQL Statements
   public static final String SQL_TABLE_SNAKE_BODY_PARTS = "SNAKE_BODY_PART";
   // Parameters: default, red, green, blue, height, width, posX, posY
   static final String SQL_INSERT_SNAKE_BODY_PARTS = "INSERT INTO SNAKE_BODY_PART VALUES (default,  ?, ?, ?, ?, ?, ?, ?)";
   static final String SQL_SELECT_SNAKE_BODY_PARTS = "SELECT id, red, green, blue, height, width, pos_x, pos_y FROM SNAKE_BODY_PART ORDER BY id ASC";
   static final String SQL_TRUNCATE_TABLE = "TRUNCATE TABLE SNAKE_BODY_PART";

   //Error messages
   static final String SQL_ERROR_CLOSE = "Error while closing Database Connection!";
}
