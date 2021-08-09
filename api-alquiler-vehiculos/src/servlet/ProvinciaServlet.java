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

import entities.Provincia;
import logic.ProvinciaLogic;
import logic.token.Token;

@Path("/provincia")
public class ProvinciaServlet {
    private ProvinciaLogic pl = null;
	
    public ProvinciaServlet() {
        super();
        pl = new ProvinciaLogic();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
	public LinkedList<Provincia> getProvincias() throws SQLException, IOException {
    	LinkedList<Provincia> provincias = pl.getAll();
		
		return provincias;
	}
    
    @GET
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	public Provincia getProvincia(@PathParam("id") int id, @Context HttpHeaders httpheaders) throws SQLException, IOException {
    	String token = httpheaders.getHeaderString("token");
    	if(token != null){
    		Token.getToken(token);				
    	}else{
    		throw new NotAuthorizedException("Unauthorized");  		
    	}
    	
    	Provincia provincia = new Provincia();
    	provincia.setIdProvincia(id);
		provincia = pl.getOne(provincia);
		
		return provincia;
	}

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Provincia nuevaProvincia(Provincia nuevaProvincia, @Context HttpHeaders httpheaders) throws SQLException, IOException{
    	String token = httpheaders.getHeaderString("token");
    	if(token != null){
    		Token.getToken(token);				
    	}else{
    		throw new NotAuthorizedException("Unauthorized");  		
    	}
    	
    	pl.newProvince(nuevaProvincia);
    	
    	return nuevaProvincia;
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Provincia actualizarProvincia(Provincia actProvincia, @PathParam("id") int id, @Context HttpHeaders httpheaders) throws SQLException, IOException{
    	String token = httpheaders.getHeaderString("token");
    	if(token != null){
    		Token.getToken(token);				
    	}else{
    		throw new NotAuthorizedException("Unauthorized");  		
    	}
    	
    	actProvincia.setIdProvincia(id);
    	Provincia provincia = pl.updateProvince(actProvincia);
    	
    	return provincia;
    }
    
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Provincia eliminarProvincia(@PathParam("id") int id, @Context HttpHeaders httpheaders) throws SQLException, IOException{
    	String token = httpheaders.getHeaderString("token");
    	if(token != null){
    		Token.getToken(token);				
    	}else{
    		throw new NotAuthorizedException("Unauthorized");  		
    	}
    	
    	Provincia delProvincia = new Provincia();
    	delProvincia.setIdProvincia(id);
    	Provincia provincia = pl.deleteProvince(delProvincia);
    	
    	return provincia;
    }

}
