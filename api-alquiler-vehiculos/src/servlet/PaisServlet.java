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

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;

import entities.Pais;
import logic.PaisLogic;
import logic.token.Token;

@Path("/pais")
public class PaisServlet {
    private PaisLogic pl = null;
	
    public PaisServlet() {
        super();
        pl = new PaisLogic();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
	public LinkedList<Pais> getPaises() throws SQLException, IOException {
    	LinkedList<Pais> paises = null;
		paises = pl.getAll();
		
		return paises;
	}
    
    @GET
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	public Pais getPais(@PathParam("id") int id, @Context HttpHeaders httpheaders) throws SQLException, IOException {
    	String token = httpheaders.getHeaderString("token");
    	if(token != null){
    		Token.getToken(token);				
    	}else{
    		throw new NotAuthorizedException("Unauthorized");  		
    	}
    	
    	Pais pais = new Pais();
    	pais.setIdPais(id);
		pais = pl.getOne(pais);
		
		return pais;
	}

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Pais nuevoPais(Pais nuevoPais, @Context HttpHeaders httpheaders) throws SQLException, IOException{
    	String token = httpheaders.getHeaderString("token");
    	if(token != null){
    		Token.getToken(token);				
    	}else{
    		throw new NotAuthorizedException("Unauthorized");  		
    	}
    	
    	pl.newCountry(nuevoPais);
    	
    	return nuevoPais;
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Pais actualizarPais(Pais actPais, @PathParam("id") int id, @Context HttpHeaders httpheaders) throws SQLException, IOException{
    	String token = httpheaders.getHeaderString("token");
    	if(token != null){
    		Token.getToken(token);				
    	}else{
    		throw new NotAuthorizedException("Unauthorized");  		
    	}
    	
    	actPais.setIdPais(id);
    	Pais pais = pl.updateCountry(actPais);
    	
    	return pais;
    }
    
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Pais eliminarPais(@PathParam("id") int id, @Context HttpHeaders httpheaders) throws SQLException, IOException{
    	String token = httpheaders.getHeaderString("token");
    	if(token != null){
    		Token.getToken(token);				
    	}else{
    		throw new NotAuthorizedException("Unauthorized");  		
    	}
    	
    	Pais delPais = new Pais();
    	delPais.setIdPais(id);
    	Pais pais = pl.deleteCountry(delPais);
    	
    	return pais;
    }

}
