package za.co.mie.dao;

import java.util.List;
import za.co.mie.model.Joke;


public interface JokeDao{
  public boolean addJoke(Joke joke);
  public Joke getARandomJokeFromCategory(int category);
  public List<Joke> getAllJokesFromCategory(int category);
  public boolean deleteAJokeFromCategory(int category);
  //public boolean editAJokeFromCategory(int category);
}
