package za.co.mie.rest.json;

import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import za.co.mie.dao.impl.JokeDaoTextImpl;
import za.co.mie.model.Joke;
import za.co.mie.service.JokeService;
import za.co.mie.service.JokeServiceImpl;

@Path("/joke")
public class JokeJsonTest{
  JokeService js = new JokeServiceImpl(JokeDaoTextImpl.getInstance("C:\\temp\\miejoketest.txt"));
  
  @GET
  @Path("/{catid}")
  @Produces(MediaType.APPLICATION_JSON)
  public Joke getARandomJokeFromCategory(@PathParam("catid") Integer catid){
    Joke joke=js.getARandomJokeFromCategory(catid);
    return joke;
  }
  
  @GET
  @Path("/all/{catid}")
  @Produces(MediaType.APPLICATION_JSON)
  public List<Joke> getAllJokesFromCategory(@PathParam("catid") Integer catid){
    return js.getAllJokesFromCategory(catid);
  }
  
  @POST
  @Path("/new")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response newJoke(Joke joke){
    if(js.addJoke(joke)){
      return Response.status(200).entity("Your joke was added").build();
    }
    return Response.status(400).entity("Bad Request - Your joke was NOT added").build();
  }
}
