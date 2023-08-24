package za.co.mie.dao.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import za.co.mie.model.Joke;

public class JokeDaoJsonImpl extends JokeDaoImpl{
  private static JokeDaoJsonImpl jokeDaoJsonImpl = null;
  private ObjectMapper objectMapper;

  // *********************************************************************************
  private JokeDaoJsonImpl(String fileName){
    super();
    objectMapper = new ObjectMapper();
    try{
      List<Joke> jokes = Arrays.asList(
        objectMapper.readValue(Paths.get(fileName).toFile(), Joke[].class));
      setAllJokes(new ArrayList(jokes));
      setFileName(fileName);
    }catch(IOException ex){
      System.err.println("ERROR: " + ex.getMessage());
    }
  }

  // *********************************************************************************
  public static JokeDaoImpl getInstance(String fileName){
    if(jokeDaoJsonImpl == null){
      jokeDaoJsonImpl = new JokeDaoJsonImpl(fileName);
    }
    return jokeDaoJsonImpl;
  }

  // *********************************************************************************  
  @Override
  public boolean addJoke(Joke joke){
    boolean retVal = false;
    super.addJoke(joke);
    try{
      objectMapper.writeValue(new File(getFileName()), getAllJokes());
      retVal = true;
    }catch(IOException iox){
      System.err.println("ERROR: " + iox.getMessage());
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
    JokeDaoImpl jDao = JokeDaoJsonImpl.getInstance("C:\\temp\\miejoketest.json");
    System.out.println("All Jokes:");
    System.out.println(jDao.getAllJokes());
    System.out.println("---------------------");
    System.out.println("Joke from a category:");
    System.out.println("Joke: " + jDao.getARandomJokeFromCategory(1));
    System.out.println("---------------------");
//    Joke j = new Joke(1, "Very Funny.");
//    System.out.println("Joke added: "+jDao.addJoke(j));
//     System.out.println("All Jokes:");
//    System.out.println(jDao.getAllJokes());
    System.out.println("---------------------");
    System.out.println("All Jokes from category 1:");
    System.out.println(jDao.getAllJokesFromCategory(1));
  }
}
