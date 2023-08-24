package za.co.mie.service;

import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import za.co.mie.controller.ProcessRequest;
import za.co.mie.dao.JokeDao;
import za.co.mie.dao.impl.JokeDaoJsonImpl;
import za.co.mie.model.Joke;

public class JokeServiceImpl implements JokeService, ProcessRequest{
  private JokeDao jokeDao;
  private String ourView;

  public JokeServiceImpl(JokeDao jokeDao){
    setJokeDao(jokeDao);
  }

  public void setJokeDao(JokeDao jokeDao){
    this.jokeDao = jokeDao;
  }

  @Override
  public boolean addJoke(Joke joke){
    return joke == null ? false : jokeDao.addJoke(joke);
  }

  @Override
  public Joke getARandomJokeFromCategory(int category){
    return category > 0 ? jokeDao.getARandomJokeFromCategory(category) : null;
  }

  @Override
  public List<Joke> getAllJokesFromCategory(int category){
    return category > 0 ? jokeDao.getAllJokesFromCategory(category) : null;
  }

  @Override
  public boolean deleteAJokeFromCategory(int category){
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  // ####################################################################################
  @Override
  public void processTheRequest(HttpServletRequest request, HttpServletResponse response){
    int cat = 0;
    Joke theJoke = null;
    List<Joke> theJokes = null;
    String action = request.getParameter("act"); 
    if(action != null){
      action = action.toLowerCase().trim();
      switch(action){
        case "addj":
          String joke = request.getParameter("theJoke");
          if(joke != null){
            joke = joke.trim();
          }
          cat = 0;
          try{
            cat = Integer.parseInt(request.getParameter("cat"));
          }catch(NumberFormatException nfe){
          }
          if( ! joke.isEmpty() && cat > 0){
            if(addJoke(new Joke(cat, joke))){
              request.setAttribute("ans", "Your Joke was successfully added.");
            }else{
              request.setAttribute("ans", "Your Joke was NOT added.");
            }
          }
          ourView = "jokeResponse.jsp";
          break;
        case "ranj":
          cat = 0;
          try{
            cat = Integer.parseInt(request.getParameter("cat"));
          }catch(NumberFormatException nfe){
          }
          theJoke = jokeDao.getARandomJokeFromCategory(cat);
          if(theJoke != null){
            request.setAttribute("ans", theJoke.getJoke());
          }else{
            request.setAttribute("ans", "What a Joke. None was found!");
          }
          ourView = "jokeResponse.jsp";
          break;
        case "allj":
          cat = 0;
          try{
            cat = Integer.parseInt(request.getParameter("cat"));
          }catch(NumberFormatException nfe){
          }
          theJokes = jokeDao.getAllJokesFromCategory(cat);
          if(theJokes != null &&  ! theJokes.isEmpty()){
            request.setAttribute("allJks", theJokes);
          }else{
            request.setAttribute("ans", "What a Joke. None were found!");
          }
          ourView = "jokeResponse.jsp";
          break;
        default:
          ourView = "error.jsp";
      }
    }
  }

  @Override
  public void processTheResponse(HttpServletRequest request, HttpServletResponse response){
    RequestDispatcher requestDispatcher = request.getRequestDispatcher(ourView);
    try{
      requestDispatcher.forward(request, response);
    }catch(ServletException | IOException ex){
      System.err.println("Error Dispatching View: " + ex.getMessage());
    }
  }

  // **************************** our test *****************************************************  
  public static void main(String[] args){
    //  JokeService jService = new JokeServiceImpl(JokeDaoTextImpl.getInstance("C:\\temp\\miejoketest.txt"));
    JokeService jService = new JokeServiceImpl(JokeDaoJsonImpl.getInstance("C:\\temp\\miejoketest.json"));
    System.out.println("Joke from a category:");
    System.out.println("Joke: " + jService.getARandomJokeFromCategory(1));
    System.out.println("---------------------");
    Joke j = new Joke(1, "Very Funny Anakie.");
    System.out.println("Joke added: " + jService.addJoke(j));
    System.out.println("---------------------");
    System.out.println("All Jokes from category 1:");
    System.out.println(jService.getAllJokesFromCategory(1));
  }
}
