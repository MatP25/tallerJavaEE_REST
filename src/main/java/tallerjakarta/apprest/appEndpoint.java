package tallerjakarta.apprest;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import tallerjakarta.apprest.domain.Empleado;
import tallerjakarta.apprest.domain.Tarea;

@ApplicationScoped
@Path("/")
public class appEndpoint {

    @Inject
    private IService service;

    @GET
    @Path("/empleados")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEmpleados() {

        return Response
            .ok(service.getEmpleados(), MediaType.APPLICATION_JSON)
            .build();
    }

    @GET
    @Path("/empleados/{cedula}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEmpleadoPorCedula(@PathParam("cedula") String cedula) {
        try {
            Empleado emp = service.getEmpleadoPorCedula(cedula);
            return Response.ok(emp).build();
        } catch (Exception e) {
            return Response
                .serverError()
                .entity("{\"error\": \"" + e.getMessage() + "\"}")
                .status(404)
                .build();
        }
    }

    @POST
    @Path("/empleados")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response agregarEmpleado(Empleado empleado) {
        
        try {
            service.agregarEmpleado(empleado.getNombre(), empleado.getCedula());
            return Response
                .status(201)
                .build();
        } catch (Exception e) {
            return Response
                .serverError()
                .entity("{\"error\": \"" + e.getMessage() + "\"}")
                .status(400)
                .build();
        }
    }

    @DELETE
    @Path("/empleados/{cedula}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response removerEmpleadoPorCedula(@PathParam("cedula") String cedula) {

        try {
            service.removerEmpleadoPorCedula(cedula);
            return Response
                .noContent()
                .build();
        } catch (Exception e) {
            return Response
                .serverError()
                .entity("{\"error\": \"" + e.getMessage() + "\"}")
                .status(500)
                .build();
        }
    }

    @POST
    @Path("/tareas")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response agregarTarea(Tarea tarea, @QueryParam("cedula-empleado") String cedula) {
        String descripcion = null;
        LocalDateTime fechaIni = null, fechaFin = null;
        try {
            descripcion = tarea.getDescripcion();
            fechaIni = tarea.getFechaIni();
            fechaFin = tarea.getFechaFin();
        } catch (Exception e) {
            return Response
            .serverError()
            .entity("{\"error\": \"Error al procesar la solicitud, verifique los datos enviados.\"}")
            .status(500)
            .build();        
        }

        try {
            service.agregarTarea(cedula, descripcion, fechaIni, fechaFin);
            return Response
                .noContent()
                .build();
        } catch (Exception e) {
            return Response
            .serverError()
            .entity("{\"error\": \"" + e.getMessage() + "\"}")
            .status(404)
            .build();          
        }
    }

    @GET
    @Path("/tareas")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTareasDeEmpleadoPorCedula(@QueryParam("cedula-empleado") String cedulaEmpleado) {
        try {
            List<Tarea> tareas = service.getTareasDeEmpleadoPorCedula(cedulaEmpleado);
            return Response
                .ok(tareas)
                .build();
        } catch (Exception e) {
            return Response
                .serverError()
                .entity("{\"error\": \"" + e.getMessage() + "\"}")
                .status(404)
                .build();
        }
    }


    
}
