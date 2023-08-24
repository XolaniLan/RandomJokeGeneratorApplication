package za.co.mie.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import za.co.mie.model.Joke;

public class JokeDaoMySqlImpl extends JokeDaoImpl{
  private static JokeDaoMySqlImpl jokeDaoMySqlImpl = null;
  private Connection con = null;
  private PreparedStatement ps = null;
  private ResultSet rs = null;

  private JokeDaoMySqlImpl(Connection con){
    this.con = con;
    populateLocalJokeList();
  }

   // *********************************************************************************
  public static JokeDaoImpl getInstance(Connection dbCon){
    if(jokeDaoMySqlImpl == null){
      jokeDaoMySqlImpl = new JokeDaoMySqlImpl(dbCon);
    }
    return jokeDaoMySqlImpl;
  }  
  // *********************************************************************************  
  private List<Joke> retrieveJokesFromDb(){
    List<Joke> jokeList = new ArrayList<>();
    if(con != null){
      try{
        ps = con.prepareStatement("SELECT category,joke FROM jokes");
        rs = ps.executeQuery();
        while(rs.next()){
          jokeList.add(new Joke(rs.getInt("category"), rs.getString("joke")));
        }
      }catch(SQLException ex){
        System.out.println("Error!!: " + ex.getMessage());
      }finally{
        if(ps != null){
          try{
            ps.close();
          }catch(SQLException ex){
            System.out.println("Could not close: " + ex.getMessage());
          }
        }
      }
    }
    return jokeList;
  }

  // *********************************************************************************  
  private void populateLocalJokeList(){
    setAllJokes(retrieveJokesFromDb());
  }
  // *********************************************************************************  

  @Override
  public boolean addJoke(Joke joke){
    boolean retVal = false;
    if(con != null){
      try{
        ps = con.prepareStatement("INSERT INTO jokes(category,joke) values(?,?)");
        ps.setInt(1, joke.getCategory());
        ps.setString(2, joke.getJoke());
        if(ps.executeUpdate() > 0){
          retVal = true;
        }
      }catch(SQLException ex){
        System.out.println("Error!!: " + ex.getMessage());
      }finally{
        if(ps != null){
          try{
            ps.close();
          }catch(SQLException ex){
            System.out.println("Could not close: " + ex.getMessage());
          }
        }
      }
    }
    return retVal;
  }
  // *********************************************************************************  

  @Override
  public boolean deleteAJokeFromCategory(int category){
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  // *********************************************************************************  
  public static void main(String[] args){
    Connection con = null;
    try{
      Class.forName("com.mysql.cj.jdbc.Driver");
      con = DriverManager.getConnection(
        "jdbc:mysql://localhost:3306/miejoke", "stuart", "stuart1266"
      );
    }catch(ClassNotFoundException cle){
    }catch(SQLException ex){
    }
    if(con != null){
      System.out.println("Got connection");
    }
  }
}
