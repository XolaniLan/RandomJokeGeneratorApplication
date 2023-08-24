package za.co.mie.dao.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import za.co.mie.model.Joke;

public class JokeDaoTextImpl extends JokeDaoImpl{
  private static JokeDaoTextImpl jokeDaoTextImpl;

// *********************************************************************************  
  private JokeDaoTextImpl(String fileName){
//    List<Joke> jokeList = new ArrayList<Joke>();
//    try(BufferedReader reader = new BufferedReader(new FileReader(fileName))){
//      String line = reader.readLine();
//      while(line != null){
//        line = line.trim();
//        if( ! line.isEmpty()){
//          String[] aJoke = line.split(",");
//          if(aJoke.length == 2){
//            try{
//              jokeList.add(new Joke(Integer.parseInt(aJoke[0]), aJoke[1]));
//            }catch(NumberFormatException nfe){
//            }
//          }
//        }
//        line = reader.readLine();
//      }
//      setAllJokes(jokeList);
//      setFileName(fileName);
//    }catch(IOException e){
//      System.err.println("Error: " + e.getMessage());
//    }
    // #######################################################################
    try{
      List<Joke> jokeList = Files.lines(Paths.get(fileName))
        .map(s -> s.trim())
        .filter(s -> !s.isEmpty())
        .map(line -> line.split(","))
        .filter(parts -> parts.length == 2)
        .map(parts -> new Joke(Integer.valueOf(parts[0]), parts[1]))
        .collect(Collectors.toList());
      setAllJokes(new ArrayList(jokeList));
      setFileName(fileName);
    }catch(IOException ex){
      System.out.println("Oh no: cannot read the jokes from a text file");
    }
  }

// *********************************************************************************
  public static JokeDaoImpl getInstance(String filename){
    if(jokeDaoTextImpl == null){
      jokeDaoTextImpl = new JokeDaoTextImpl(filename);
    }
    return jokeDaoTextImpl;
  }

// *********************************************************************************  
  @Override
  public boolean addJoke(Joke joke){
    boolean retVal = super.addJoke(joke);
    if(retVal){
      retVal = false;
      String nextJoke = "\r\n" + joke.getCategory() + "," + joke.getJoke();
      try{
        Files.write(Paths.get(getFileName()), nextJoke.getBytes(), StandardOpenOption.APPEND);
        retVal = true;
      }catch(IOException iox){
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
    JokeDaoImpl jDao = JokeDaoTextImpl.getInstance("C:\\temp\\miejoketest.txt");
    System.out.println("All Jokes:");
    System.out.println(jDao.getAllJokes());
    System.out.println("---------------------");
    System.out.println("Joke from a category:");
    System.out.println("Joke: " + jDao.getARandomJokeFromCategory(1));
    System.out.println("---------------------");
    Joke j = new Joke(1, "Very Funny again.");
    System.out.println("Joke added: "+jDao.addJoke(j));
     System.out.println("All Jokes:");
    System.out.println(jDao.getAllJokes());
    System.out.println("---------------------");
    System.out.println("All Jokes from category 1:");
    System.out.println(jDao.getAllJokesFromCategory(1));
  }
}
