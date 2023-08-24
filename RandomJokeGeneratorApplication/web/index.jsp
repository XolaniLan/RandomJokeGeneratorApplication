<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Welcome to our Joke App</title>
  </head>
  <body>
    <h1 align = 'center' >Jokes</h1>
    <form method="GET" action="miejoke">   
      <p>Select Joke Genre</p>
      <p>Genre:
        <select name ="cat" size='1'>
          <option value='1'>Mom</option>
          <option value='2'>Dad</option>
          <option value='3'>Corny</option>
        </select>
      </p>      
      <br>
       <p>Select action</p>
      <p>Genre:
        <select name ="act" size='1'>
          <option value='ranj'>Any joke from a category</option>
          <option value='allj'>All jokes from a category</option>
          <option value='addj'>Add a Joke</option>
        </select>
      </p>      
      <br>
      <center>
        <input type="hidden" id="pro" name="pro" value="db">
        <input type ='submit'>        
      </center>
    </form>
  </body>
</html>
