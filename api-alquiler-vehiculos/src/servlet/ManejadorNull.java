package servlet;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ManejadorNull implements ExceptionMapper<NullPointerException> {

public Response toResponse(NullPointerException exception){

    return Response.status(Status.NOT_FOUND).
    entity("{\"message\": \"Sin datos\"}").
    build();
	}
}