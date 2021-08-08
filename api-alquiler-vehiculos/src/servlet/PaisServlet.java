package servlet;

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

import entities.Pais;
import logic.PaisLogic;

@Path("/pais")
public class PaisServlet {
    private PaisLogic pl = null;
	
    public PaisServlet() {
        super();
        pl = new PaisLogic();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
	public LinkedList<Pais> getPaises() {
    	LinkedList<Pais> paises = null;
		paises = pl.getAll();
		
		return paises;
	}

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Pais newPais(Pais nuevoPais){
    	pl.newCountry(nuevoPais);
    	
    	return nuevoPais;
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Pais actualizarPais(Pais actPais, @PathParam("id") int id){
    	actPais.setIdPais(id);
    	pl.updateCountry(actPais);
    	
    	return actPais;
    }
    
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Pais eliminarPais(@PathParam("id") int id){
    	Pais delPais = new Pais();
    	delPais.setIdPais(id);
    	pl.deleteCountry(delPais);
    	
    	return delPais;
    }

}
