package servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.LinkedList;

import javax.activation.MimetypesFileTypeMap;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

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
    
    @GET
    @Path("/{fecha_hora_ini}/{fecha_hora_fin}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	public LinkedList<Vehiculo> getVehiculosDisponiblesPorFecha(@PathParam("fecha_hora_ini") String fechaHoraIni, @PathParam("fecha_hora_fin") String fechaHoraFin, @Context HttpHeaders httpheaders) throws SQLException, IOException {
    	String token = httpheaders.getHeaderString("token");
    	if(token != null){
    		Token.getToken(token);				
    	}else{
    		throw new NotAuthorizedException("Unauthorized");  		
    	}
    	LinkedList<Vehiculo> vehiculos = vl.getVehiclesAvailable(fechaHoraIni, fechaHoraFin);
    	
		return vehiculos;
	}

    @GET
    @Path("/images/{image}")
    @Produces("image/*")
    public Response getImage(@PathParam("image") String image) {
    	String rutaBruta = this.getClass().getResource("").getPath();
    	rutaBruta = rutaBruta.substring(0, rutaBruta.length() - 17);
    	String ruta = rutaBruta + "/source/" + image;
    	
    	File f = new File(ruta);
    	if (!f.exists()) {
    		throw new WebApplicationException(404);
    	}
    	String mt = new MimetypesFileTypeMap().getContentType(f);
    	return Response.ok(f, mt).build();
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
    
    @PUT
    @Path("/upload/{id}")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public void uploadFile(@FormDataParam("file") InputStream fileInputStream, @FormDataParam("file") FormDataContentDisposition contentDispositionHeader, @PathParam("id") int id, @Context HttpHeaders httpheaders) throws SQLException, IOException {
    	String token = httpheaders.getHeaderString("token");
    	if(token != null){
    		Token.getToken(token);				
    	}else{
    		throw new NotAuthorizedException("Unauthorized");  		
    	}
    	
    	String rutaBruta = this.getClass().getResource("").getPath();
    	rutaBruta = rutaBruta.substring(0, rutaBruta.length() - 17);
    	String ruta = rutaBruta + "/source/";
    	String nameImg = "" + contentDispositionHeader.getFileName();
    	String filePath = ruta + nameImg;
    	
    	Vehiculo veh = new Vehiculo();
    	veh.setIdVeh(id);
    	veh.setImagen(nameImg);
    	
    	saveFile(fileInputStream, filePath);
    	
    	vl.insertImage(veh);
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

    private void saveFile(InputStream uploadedInputStream, String serverLocation) {
        try {
            OutputStream outpuStream = new FileOutputStream(new File(serverLocation));
            int read = 0;
            byte[] bytes = new byte[1024];
 
            outpuStream = new FileOutputStream(new File(serverLocation));
            while ((read = uploadedInputStream.read(bytes)) != -1) {
                outpuStream.write(bytes, 0, read);
            }
            outpuStream.flush();
            outpuStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
