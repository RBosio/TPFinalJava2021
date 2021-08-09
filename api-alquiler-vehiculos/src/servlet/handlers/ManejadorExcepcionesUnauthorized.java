package servlet.handlers;

import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ManejadorExcepcionesUnauthorized implements ExceptionMapper<NotAuthorizedException> {

public Response toResponse(NotAuthorizedException exception){

    return Response.status(Status.NOT_FOUND).
    entity("{\"message\": \"No autorizado\"}").
    build();
	}
}