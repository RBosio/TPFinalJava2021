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

import entities.Vehiculo;
import logic.VehiculoLogic;
import logic.token.Token;

@Path("/vehiculo")
public class VehiculoServlet {
    private VehiculoLogic vl = null;
	
    public VehiculoServlet() {
        super();
        vl = new VehiculoLogic();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
	public LinkedList<Vehiculo> getVehiculos(@Context HttpHeaders httpheaders) throws SQLException, IOException {
    	String token = httpheaders.getHeaderString("token");
    	if(token != null){
    		Token.getToken(token);				
    	}else{
    		throw new NotAuthorizedException("Unauthorized");  		
    	}
    	
    	LinkedList<Vehiculo> vehiculos = null;
		vehiculos = vl.getAll();
		
		return vehiculos;
	}
    
    @GET
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	public Vehiculo getVehiculo(@PathParam("id") int id, @Context HttpHeaders httpheaders) throws SQLException, IOException {
    	String token = httpheaders.getHeaderString("token");
    	if(token != null){
    		Token.getToken(token);				
    	}else{
    		throw new NotAuthorizedException("Unauthorized");  		
    	}
    	
    	Vehiculo vehiculo = new Vehiculo();
    	vehiculo.setIdVeh(id);
		vehiculo = vl.getOne(vehiculo);
		
		return vehiculo;
	}

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Vehiculo nuevoVehiculo(Vehiculo nuevoVehiculo, @Context HttpHeaders httpheaders) throws SQLException, IOException{
    	String token = httpheaders.getHeaderString("token");
    	if(token != null){
    		Token.getToken(token);				
    	}else{
    		throw new NotAuthorizedException("Unauthorized");  		
    	}
    	
    	vl.newVehicle(nuevoVehiculo);
    	
    	return nuevoVehiculo;
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Vehiculo actualizarVehiculo(Vehiculo actVehiculo, @PathParam("id") int id, @Context HttpHeaders httpheaders) throws SQLException, IOException{
    	String token = httpheaders.getHeaderString("token");
    	if(token != null){
    		Token.getToken(token);				
    	}else{
    		throw new NotAuthorizedException("Unauthorized");  		
    	}
    	
    	actVehiculo.setIdVeh(id);
    	Vehiculo vehiculo = vl.updateVehicle(actVehiculo);
    	
    	return vehiculo;
    }
    
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Vehiculo eliminarVehiculo(@PathParam("id") int id, @Context HttpHeaders httpheaders) throws SQLException, IOException{
    	String token = httpheaders.getHeaderString("token");
    	if(token != null){
    		Token.getToken(token);				
    	}else{
    		throw new NotAuthorizedException("Unauthorized");  		
    	}
    	
    	Vehiculo delVehiculo = new Vehiculo();
    	delVehiculo.setIdVeh(id);
    	Vehiculo vehiculo = vl.deleteVehicle(delVehiculo);
    	
    	return vehiculo;
    }

}
