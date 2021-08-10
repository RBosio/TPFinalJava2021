package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;

import javax.naming.AuthenticationException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import entities.Persona;
import logic.PersonaLogic;
import logic.token.Token;

@Path("/persona")
public class PersonaServlet {
    private PersonaLogic pl = null;
	
    public PersonaServlet() {
        super();
        pl = new PersonaLogic();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
	public LinkedList<Persona> getPersonas(@Context HttpHeaders httpheaders) throws SQLException, IOException {
    	String token = httpheaders.getHeaderString("token");
    	if(token != null){
    		Token.getToken(token);				
    	}else{
    		throw new NotAuthorizedException("Unauthorized");  		
    	}
    	
    	LinkedList<Persona> personas = null;
		personas = pl.getAll();
		
		return personas;
	}
    
    @GET
    @Path("/{dni}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	public Persona getPersona(@PathParam("dni") String dni, @Context HttpHeaders httpheaders) throws SQLException, IOException {
    	String token = httpheaders.getHeaderString("token");
    	if(token != null){
    		Token.getToken(token);				
    	}else{
    		throw new NotAuthorizedException("Unauthorized");  		
    	}
    	
    	Persona persona = new Persona();
    	persona.setDni(dni);
		persona = pl.getOne(persona);
		
		return persona;
	}

    @POST
    @Path("/signup")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Persona nuevaPersona(Persona nuevaPersona) throws SQLException, IOException{
    	pl.newUser(nuevaPersona);
    	
    	return nuevaPersona;
    }
    
    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Persona login(Persona nuevaPersona) throws SQLException, IOException, AuthenticationException{
    	Persona personaLogueada = pl.login(nuevaPersona);
    	
    	return personaLogueada;
    }

    @PUT
    @Path("/{dni}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Persona actualizarUser(Persona actualizarUser, @PathParam("dni") String dni, @Context HttpHeaders httpheaders) throws SQLException, IOException{
    	String token = httpheaders.getHeaderString("token");
    	if(token != null){
    		Token.getToken(token);				
    	}else{
    		throw new NotAuthorizedException("Unauthorized");  		
    	}
    	
    	actualizarUser.setDni(dni);
    	Persona persona = pl.updateUser(actualizarUser);
    	
    	return persona;
    }
    
    @DELETE
    @Path("/{dni}")
    @Produces(MediaType.APPLICATION_JSON)
    public Persona eliminarPersona(@PathParam("dni") String dni, @Context HttpHeaders httpheaders) throws SQLException, IOException{
    	String token = httpheaders.getHeaderString("token");
    	if(token != null){
    		Token.getToken(token);				
    	}else{
    		throw new NotAuthorizedException("Unauthorized");  		
    	}
    	
    	Persona delUser = new Persona();
    	delUser.setDni(dni);
    	Persona persona = pl.deleteUser(delUser);
    	
    	return persona;
    }

}
