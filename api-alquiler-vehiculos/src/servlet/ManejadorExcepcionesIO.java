package servlet;

import java.sql.SQLException;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ManejadorExcepcionesIO implements ExceptionMapper<SQLException> {

public Response toResponse(SQLException exception){

    return Response.status(Status.NOT_FOUND).
    entity("{\"message\": \"Ocurrió un error, intente nuevamente\"}").
    build();
	}
}