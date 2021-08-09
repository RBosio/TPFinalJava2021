package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import entities.Localidad;
import logic.LocalidadLogic;

@Path("/localidad")
public class LocalidadServlet {
    private LocalidadLogic ll = null;
	
    public LocalidadServlet() {
        super();
        ll = new LocalidadLogic();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
	public LinkedList<Localidad> getLocalidades() throws SQLException, IOException {
    	LinkedList<Localidad> localidades = null;
		localidades = ll.getAll();
		
		return localidades;
	}
    
    @GET
    @Path("/{codPostal}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	public Localidad getLocalidad(@PathParam("codPostal") String codPostal) throws SQLException, IOException {
    	Localidad localidad = new Localidad();
    	localidad.setCodPostal(codPostal);
		localidad = ll.getOne(localidad);
		
		return localidad;
	}

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Localidad nuevaLocalidad(Localidad nuevaLocalidad) throws SQLException, IOException{
    	ll.newCity(nuevaLocalidad);
    	
    	return nuevaLocalidad;
    }

    @PUT
    @Path("/{codPostal}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Localidad actualizarLoc(Localidad actualizarLoc, @PathParam("codPostal") String codPostal) throws SQLException, IOException{
    	actualizarLoc.setCodPostal(codPostal);
    	Localidad localidad = ll.updateCity(actualizarLoc);
    	
    	return localidad;
    }
    
    @DELETE
    @Path("/{codPostal}")
    @Produces(MediaType.APPLICATION_JSON)
    public Localidad eliminarLocalidad(@PathParam("codPostal") String codPostal) throws SQLException, IOException{
    	Localidad delLoc = new Localidad();
    	delLoc.setCodPostal(codPostal);
    	Localidad localidad = ll.deleteCity(delLoc);
    	
    	return localidad;
    }

}
