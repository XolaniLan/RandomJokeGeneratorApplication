package za.co.mie.dao.impl;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import za.co.mie.dao.JokeDao;
import za.co.mie.model.Joke;

public abstract class JokeDaoImpl implements JokeDao{
  private List<Joke> allJokes;
  private String fileName;

  public JokeDaoImpl(){}

  @Override
  public List<Joke> getAllJokesFromCategory(int category){
    return allJokes.stream()
                   .filter(jk->category==jk.getCategory())
                   .collect(Collectors.toList());
  }

  @Override
   public Joke getARandomJokeFromCategory(int category){
     List<Joke> theList = getAllJokesFromCategory(category);
     if(theList.isEmpty()){
       return new Joke(category,"No Jokes found");
     }
     Random random = new Random();
     return theList.get(random.nextInt(theList.size()));
   }
  
  public void setAllJokes(List<Joke> allJokes){
    this.allJokes = allJokes;
  }

  public String getFileName(){
    return fileName;
  }

  public void setFileName(String fileName){
    this.fileName = fileName;
  }
  
   public boolean addJoke(Joke joke){
     return allJokes.add(joke);
  }
  
   public List<Joke> getAllJokes(){
     return allJokes;
   }
}
