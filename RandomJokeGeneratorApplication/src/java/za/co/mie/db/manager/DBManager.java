package za.co.mie.db.manager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManager{
  private Connection con = null;
  private String url;
  private String database;
  private String user;
  private String password;
  private String driver;

// ***********************************************************************
  public DBManager(String url, String database, String driver, String username, String password){
    this.url = url;
    this.database = database;
    this.user = username;
    this.password = password;
    this.driver = driver;
  }

  // ***********************************************************************
  public Connection getConnection(){
    if(con == null){
      createConnection();
    }
    return con;
  }

// ***********************************************************************
  public boolean closeConnection(){
    boolean retVal = false;
    if(con != null){
      try{
        con.close();
        retVal = true;
      }catch(SQLException ex){
        System.out.println("Error closing connection: " + ex.getMessage());
      }finally{
        con = null;
      }
    }
    return retVal;
  }

// ***********************************************************************
  private boolean createConnection(){
    boolean retVal = false;
    try{
      Class.forName(driver);
    }catch(ClassNotFoundException ex){
      System.out.println("Error: " + ex.getMessage());
      return retVal;
    }
    System.out.println("Driver Loaded");
    String ourUri = url + database;
    try{
      con = DriverManager.getConnection(url + database, user, password);
      retVal = true;
    }catch(SQLException ex){
      System.out.println("Ooops Could not connect: " + ex.getMessage());
      return retVal;
    }
    return retVal;
  }
  // ***********************************************************************

  @Override
  public String toString(){
    return "DBManager{" + "con=" + con + ", url=" + url + ", database=" + database + ", user=" + user + ", password=" + password + ", driver=" + driver + '}';
  }
}
