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
	public LinkedList<Pais> getPaises() throws SQLException, IOException {
    	LinkedList<Pais> paises = null;
		paises = pl.getAll();
		
		return paises;
	}
    
    @GET
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	public Pais getPais(@PathParam("id") int id) throws SQLException, IOException {
    	Pais pais = new Pais();
    	pais.setIdPais(id);
		pais = pl.getOne(pais);
		
		return pais;
	}

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Pais nuevoPais(Pais nuevoPais) throws SQLException, IOException{
    	pl.newCountry(nuevoPais);
    	
    	return nuevoPais;
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Pais actualizarPais(Pais actPais, @PathParam("id") int id) throws SQLException, IOException{
    	actPais.setIdPais(id);
    	Pais pais = pl.updateCountry(actPais);
    	
    	return pais;
    }
    
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Pais eliminarPais(@PathParam("id") int id) throws SQLException, IOException{
    	Pais delPais = new Pais();
    	delPais.setIdPais(id);
    	Pais pais = pl.deleteCountry(delPais);
    	
    	return pais;
    }

}
