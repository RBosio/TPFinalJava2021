package servlet.handlers;

import javax.naming.AuthenticationException;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ManejadorExcepcionesAuth implements ExceptionMapper<AuthenticationException> {

public Response toResponse(AuthenticationException exception){

    return Response.status(Status.NOT_FOUND).
    entity("{\"message\": \"Email o contraseña incorrectos\"}").
    build();
	}
}