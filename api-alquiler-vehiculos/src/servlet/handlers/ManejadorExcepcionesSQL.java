package servlet.handlers;

import java.sql.SQLException;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ManejadorExcepcionesSQL implements ExceptionMapper<SQLException> {

public Response toResponse(SQLException exception){

    return Response.status(Status.NOT_FOUND).
    entity("{\"error\": \"Ocurri? un error, intente nuevamente\", \"registroDuplicado\": \"Dato existente\"}").
    build();
	}
}