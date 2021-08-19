package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import entities.Alquiler;
import logic.AlquilerLogic;
import logic.token.Token;

@Path("/alquiler")
public class AlquilerServlet {
    private AlquilerLogic al = null;
	
    public AlquilerServlet() {
        super();
        al = new AlquilerLogic();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
	public LinkedList<Alquiler> getAlquileres(@Context HttpHeaders httpheaders) throws SQLException, IOException {
    	String token = httpheaders.getHeaderString("token");
    	if(token != null){
    		Token.getToken(token);				
    	}else{
    		throw new NotAuthorizedException("Unauthorized");  		
    	}
    	
    	LinkedList<Alquiler> alquileres = null;
		alquileres = al.getAll();
		
		return alquileres;
	}

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Alquiler nuevoAlquiler(Alquiler nuevoAlquiler, @Context HttpHeaders httpheaders) throws SQLException, IOException{
    	String token = httpheaders.getHeaderString("token");
    	if(token != null){
    		Token.getToken(token);				
    	}else{
    		throw new NotAuthorizedException("Unauthorized");  		
    	}
    	Alquiler alquiler = al.newRent(nuevoAlquiler);
    	
    	return alquiler;
    }
    
    @PUT
    @Path("/confirmar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Alquiler confirmarAlquiler(Alquiler confirmarAlq, @Context HttpHeaders httpheaders) throws SQLException, IOException{
    	String token = httpheaders.getHeaderString("token");
    	if(token != null){
    		Token.getToken(token);				
    	}else{
    		throw new NotAuthorizedException("Unauthorized");  		
    	}
    	Alquiler alquiler = al.confirmRent(confirmarAlq);
    	
    	return alquiler;
    }
    
    @PUT
    @Path("/devolver")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Alquiler devolucionVehiculo(Alquiler terminarAlq, @Context HttpHeaders httpheaders) throws SQLException, IOException{
    	String token = httpheaders.getHeaderString("token");
    	if(token != null){
    		Token.getToken(token);				
    	}else{
    		throw new NotAuthorizedException("Unauthorized");  		
    	}
    	
    	Alquiler alquiler = al.finishRent(terminarAlq);
    	
    	return alquiler;
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Alquiler cancelarAlq(Alquiler cancelarAlq, @Context HttpHeaders httpheaders) throws SQLException, IOException{
    	String token = httpheaders.getHeaderString("token");
    	if(token != null){
    		Token.getToken(token);				
    	}else{
    		throw new NotAuthorizedException("Unauthorized");  		
    	}
    	
    	Alquiler alquiler = al.cancelRent(cancelarAlq);
    	
    	return alquiler;
    }
}