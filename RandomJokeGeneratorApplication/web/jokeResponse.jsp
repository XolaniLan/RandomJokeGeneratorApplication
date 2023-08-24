
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="za.co.mie.model.Joke"%>
<%@page import="java.util.List"%>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Joke Results</title>
  </head>
  <body>
    <h1 align="center">Your Joke(s)</h1>
    <p>
    <% 
            List<Joke> jokeList = (List<Joke>)request.getAttribute("allJks");
            if(jokeList!=null && !jokeList.isEmpty()){
              out.print("<br><ul>");
              for(Joke j:jokeList){
                out.println("<li>"+j.getJoke()+"</li>");
              }
              out.print("<br></ul>");
            }
            
    %>
    </p>
    <hr>
    <p>
    Single Joke: ${ans}
    <p>
  </body>
</html>
