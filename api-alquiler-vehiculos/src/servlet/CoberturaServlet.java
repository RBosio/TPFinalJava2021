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
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import entities.Cobertura;
import logic.CoberturaLogic;
import logic.token.Token;

@Path("/cobertura")
public class CoberturaServlet {
    private CoberturaLogic cl = null;
	
    public CoberturaServlet() {
        super();
        cl = new CoberturaLogic();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
	public LinkedList<Cobertura> getCoberturaes(@Context HttpHeaders httpheaders) throws SQLException, IOException {
    	String token = httpheaders.getHeaderString("token");
    	if(token != null){
    		Token.getToken(token);				
    	}else{
    		throw new NotAuthorizedException("Unauthorized");  		
    	}
    	
    	LinkedList<Cobertura> coberturas = null;
		coberturas = cl.getAll();
		
		return coberturas;
	}
    
    @GET
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	public Cobertura getCobertura(@PathParam("id") int id, @Context HttpHeaders httpheaders) throws SQLException, IOException {
    	String token = httpheaders.getHeaderString("token");
    	if(token != null){
    		Token.getToken(token);				
    	}else{
    		throw new NotAuthorizedException("Unauthorized");  		
    	}
    	
    	Cobertura cobertura = new Cobertura();
    	cobertura.setIdCob(id);
		cobertura = cl.getOne(cobertura);
		
		return cobertura;
	}

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Cobertura nuevoCobertura(Cobertura nuevoCobertura, @Context HttpHeaders httpheaders) throws SQLException, IOException{
    	String token = httpheaders.getHeaderString("token");
    	if(token != null){
    		Token.getToken(token);				
    	}else{
    		throw new NotAuthorizedException("Unauthorized");  		
    	}
    	
    	cl.newInsurance(nuevoCobertura);
    	
    	return nuevoCobertura;
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Cobertura actualizarCobertura(Cobertura actCobertura, @PathParam("id") int id, @Context HttpHeaders httpheaders) throws SQLException, IOException{
    	String token = httpheaders.getHeaderString("token");
    	if(token != null){
    		Token.getToken(token);				
    	}else{
    		throw new NotAuthorizedException("Unauthorized");  		
    	}
    	
    	actCobertura.setIdCob(id);
    	Cobertura cobertura = cl.updateInsurance(actCobertura);
    	
    	return cobertura;
    }
    
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Cobertura eliminarCobertura(@PathParam("id") int id, @Context HttpHeaders httpheaders) throws SQLException, IOException{
    	String token = httpheaders.getHeaderString("token");
    	if(token != null){
    		Token.getToken(token);				
    	}else{
    		throw new NotAuthorizedException("Unauthorized");  		
    	}
    	
    	Cobertura delCobertura = new Cobertura();
    	delCobertura.setIdCob(id);
    	Cobertura cobertura = cl.deleteInsurance(delCobertura);
    	
    	return cobertura;
    }

}
