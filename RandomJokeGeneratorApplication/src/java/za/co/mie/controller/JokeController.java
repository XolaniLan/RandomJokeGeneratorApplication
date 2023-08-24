package za.co.mie.controller;

import java.io.IOException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import za.co.mie.dao.impl.JokeDaoJsonImpl;
import za.co.mie.dao.impl.JokeDaoMySqlImpl;
import za.co.mie.dao.impl.JokeDaoTextImpl;
import za.co.mie.db.manager.DBManager;
import za.co.mie.service.JokeServiceImpl;

@WebServlet(name = "JokeController", urlPatterns = {"/miejoke"})
public class JokeController extends HttpServlet{
  protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException{
    String process = request.getParameter("pro");
    if(process != null){
      System.out.println("Controller has received a request");
      ProcessRequest pr = RequestActionFactory.createProcess(process, request);
      if(pr != null){
        pr.processTheRequest(request, response);
        pr.processTheResponse(request, response);
      }
    }
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException{
    processRequest(request, response);
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException{
    processRequest(request, response);
  }
}

abstract class RequestActionFactory{
  public static ProcessRequest createProcess(String theAction, HttpServletRequest request){
    switch(theAction){
      case "txt":
        return new JokeServiceImpl(JokeDaoTextImpl.getInstance("C:\\temp\\miejoketest.txt"));
      case "jsn":
        return new JokeServiceImpl(JokeDaoJsonImpl.getInstance("C:\\temp\\miejoketest.json"));
      case "db":
        DBManager dbManager = null;
        ServletContext sc = request.getServletContext();
        if(sc != null){
          dbManager = (DBManager) sc.getAttribute("dbman");
        }
        if(dbManager == null){
          return null;
        }
        return new JokeServiceImpl(JokeDaoMySqlImpl.getInstance(dbManager.getConnection()));
      default:
        return null;
    }
  }
}
