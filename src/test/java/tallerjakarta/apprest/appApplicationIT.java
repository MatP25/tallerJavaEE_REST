package tallerjakarta.apprest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import jakarta.inject.Inject;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * Run integration tests against the server and the deployed application.
 */
// @RunAsClient
// @ExtendWith(ArquillianExtension.class)
public class appApplicationIT {

    @Inject
    IService service;
    private final String baseUrl = "http://localhost:8080/api";

    @Test
    @DisplayName("Test: GET para obtener la lista de empleados con sus tareas")
    public void getEmpleados() {
        System.out.println("Obteniendo la lista de empleados con sus tareas...");
        Client apiClient = ClientBuilder.newClient();
        URI uri = URI.create(baseUrl + "/empleados");

        Response response = apiClient
            .target(uri)
            .request(MediaType.APPLICATION_JSON)
            .get();
        /*
        String responseBody = response.readEntity(String.class);
        Jsonb jsonb = JsonbBuilder.create();
        System.out.println("Empleados: ");
        Map<String,Empleado> empleados = jsonb
            .fromJson(
                responseBody, 
                new HashMap<String,Empleado>(){}.getClass().getGenericSuperclass()
                );
        System.out.println(empleados);
        */
        int responseCode = response.getStatus();
        String responseContentType = response.getHeaderString("Content-Type");
        boolean correctType = responseContentType.equals("application/json");
        boolean correctCode = responseCode == 200;

        assert(correctType && correctCode);
    }

    @ParameterizedTest
    @DisplayName("Test: GET para obtener los datos de un empleado existente por su cedula")
    @ValueSource(strings = {"12345678", "87654321"}) //cedulas de empleados que se que existen
    public void getEmpleadoPorCedula(String cedula) {
        System.out.println("Obteniendo los datos del empleado con cedula " + cedula + "...");
        Client apiClient = ClientBuilder.newClient();
        URI uri = URI.create(baseUrl + "/empleados/" + cedula);

        Response response = apiClient
            .target(uri)
            .request(MediaType.APPLICATION_JSON)
            .get();
        
        int responseCode = response.getStatus();
   
        assertEquals(responseCode, 200);
    }

    @ParameterizedTest
    @ValueSource(strings = {"123", "543"}) //cedulas de empleados que se que NO existen
    @DisplayName("Test: GET para intentar obtener los datos de un empleado con una cedula inexistente")
    public void getEmpleadoPorCedulaInexistente(String cedula) {
        System.out.println("Obteniendo los datos del empleado con cedula " + cedula + "...");
        Client apiClient = ClientBuilder.newClient();
        URI uri = URI.create(baseUrl + "/empleados/" + cedula);

        Response response = apiClient
            .target(uri)
            .request(MediaType.APPLICATION_JSON)
            .get();
        
        int responseCode = response.getStatus();
   
        assertEquals(responseCode, 404);
    }

    @Test
    @DisplayName("Test: POST Crear un nuevo empleado con su nombre y cédula")
    public void agregarEmpleado() {
        System.out.println("Creando un nuevo empleado con datos validos...");
        URI uri = URI.create(baseUrl + "/empleados");
        int randomVal = (int)(Math.random()*101);
        String jsonBody = "{\"nombre\": \"Aquiles Caigo\", \"cedula\": \"" + randomVal + "\"}";
        Client apiClient = ClientBuilder.newClient();

        Response response = apiClient
            .target(uri)
            .request(MediaType.APPLICATION_JSON)
            .post(Entity.json(jsonBody));

        int responseCode = response.getStatus();

        assertEquals(responseCode, 201);
    }

    @Test
    @DisplayName("Test: POST Intentar crear empleado con una cédula ya registrada.")
    public void agregarEmpleadoYaExistente() {
        System.out.println("Creando un nuevo empleado con una cédula ya registrada...");
        URI uri = URI.create(baseUrl + "/empleados");
        String jsonBody = "{\"nombre\": \"Aquiles Caigo\", \"cedula\": \"12345678\"}";
        Client apiClient = ClientBuilder.newClient();

        Response response = apiClient
            .target(uri)
            .request(MediaType.APPLICATION_JSON)
            .post(Entity.json(jsonBody));

        int responseCode = response.getStatus();

        assertEquals(responseCode, 400);
    }

    @ParameterizedTest
    @ValueSource(strings = {"12121212"})
    @DisplayName("Test: DELETE Remover un empleado existente.")
    public void removerEmpleadoPorCedula(String cedula) {
        System.out.println("Removiendo el empleado con la cedula " + cedula + "...");
        Client apiClient = ClientBuilder.newClient();
        URI uri = URI.create(baseUrl + "/empleados/" + cedula);

        Response response = apiClient
            .target(uri)
            .request(MediaType.APPLICATION_JSON)
            .delete();
        
        int responseCode = response.getStatus();
   
        assertEquals(responseCode, 204);
    }

    @ParameterizedTest
    @ValueSource(strings = {"123", "456"})
    @DisplayName("Test: DELETE Intentar remover un empleado con una cédula inexistente.")
    public void removerEmpleadoConCedulaInexistente(String cedula) {
        System.out.println("Removiendo el empleado con la cedula no registrada " + cedula + "...");
        Client apiClient = ClientBuilder.newClient();
        URI uri = URI.create(baseUrl + "/empleados/" + cedula);

        Response response = apiClient
            .target(uri)
            .request(MediaType.APPLICATION_JSON)
            .delete();
        
        int responseCode = response.getStatus();
   
        assertEquals(responseCode, 500);
    }

    @ParameterizedTest
    @ValueSource(strings = {"12345678", "87654321"})
    @DisplayName("Test: DELETE Intentar remover un empleado con tareas asignadas.")
    public void removerEmpleadoConTareas(String cedula) {
        System.out.println("Intentando remover un empleado con tareas asignadas...");
        Client apiClient = ClientBuilder.newClient();
        URI uri = URI.create(baseUrl + "/empleados/" + cedula);

        Response response = apiClient
            .target(uri)
            .request(MediaType.APPLICATION_JSON)
            .delete();
        
        int responseCode = response.getStatus();
   
        assertEquals(responseCode, 500);
    }

    @Test
    @DisplayName("Test: POST Agregar nueva tarea a un empleado.")
    public void agregarNuevaTarea() {
        System.out.println("Agregando una nueva tarea a un empleado existente...");
        URI uri = URI.create(baseUrl + "/tareas");
        String jsonBody = "{\"cedula\": \"12345678\",\"descripcion\": \"nueva tarea\",\"fechaIni\": \"2025-04-04T22:07:33.342090995\",\"fechaFin\": \"2025-04-11T22:08:37.636882532\"}";
        Client apiClient = ClientBuilder.newClient();

        Response response = apiClient
            .target(uri)
            .request(MediaType.APPLICATION_JSON)
            .post(Entity.json(jsonBody));

        int responseCode = response.getStatus();
        assertEquals(responseCode, 204);
    }

    @Test
    @DisplayName("Test: POST Intentando agregar una nueva tarea con un formato de fecha incorrecto.")
    public void agregarNuevaTareaDatosInvalidos() {
        System.out.println("Intentando agregar una nueva tarea con un formato de fecha incorrecto...");
        URI uri = URI.create(baseUrl + "/tareas");
        String jsonBody = "{\"cedula\": \"12345678\",\"descripcion\": \"nueva tarea\",\"fechaIni\": \"2025-04-04\",\"fechaFin\": \"2025-04-11\"}";
        Client apiClient = ClientBuilder.newClient();

        Response response = apiClient
            .target(uri)
            .request(MediaType.APPLICATION_JSON)
            .post(Entity.json(jsonBody));

        int responseCode = response.getStatus();
        assertEquals(responseCode, 500);
    }

    @Test
    @DisplayName("Test: POST Intentar agregar una nueva tarea a un empleado inexistente.")
    public void agregarNuevaTareaAEmpleadoInexistente() {
        System.out.println("Intentando agregar una nueva tarea a un empleado inexistente...");
        URI uri = URI.create(baseUrl + "/tareas");
        String jsonBody = "{\"cedula\": \"123\",\"descripcion\": \"nueva tarea\",\"fechaIni\": \"2025-04-04T22:07:33.342090995\",\"fechaFin\": \"2025-04-11T22:08:37.636882532\"}";
        Client apiClient = ClientBuilder.newClient();

        Response response = apiClient
            .target(uri)
            .request(MediaType.APPLICATION_JSON)
            .post(Entity.json(jsonBody));

        int responseCode = response.getStatus();
        assertEquals(responseCode, 404);
    }

    @ParameterizedTest
    @ValueSource(strings = {"12345678", "87654321"})
    @DisplayName("Test: GET Obtener las tareas de un empleado existente.")
    public void getTareasDeEmpleadoPorCedula(String cedula) {
        System.out.println("Obteniendo las tareas del empleado con cedula " + cedula + "...");
        URI uri = URI.create(baseUrl + "/tareas?cedula-empleado=" + cedula);
        Client apiClient = ClientBuilder.newClient();

        Response response = apiClient
            .target(uri)
            .request(MediaType.APPLICATION_JSON)
            .get();

        int responseCode = response.getStatus();
        String responseContentType = response.getHeaderString("Content-Type");
        boolean correctType = responseContentType.equals("application/json");
        boolean correctCode = responseCode == 200;

        assert(correctType && correctCode);
    }

    @ParameterizedTest
    @ValueSource(strings = {"123", "999"})
    @DisplayName("Test: GET Intentar obtener las tareas de un empleado no registrado.")
    public void getTareasDeEmpleadoPorCedulaInexistente(String cedula) {
        System.out.println("Intentando obtener las tareas del empleado con cedula " + cedula + "...");
        URI uri = URI.create(baseUrl + "/tareas?cedula-empleado=" + cedula);
        Client apiClient = ClientBuilder.newClient();

        Response response = apiClient
            .target(uri)
            .request(MediaType.APPLICATION_JSON)
            .get();

        int responseCode = response.getStatus();
        String responseContentType = response.getHeaderString("Content-Type");
        boolean correctType = responseContentType.equals("application/json");
        boolean correctCode = responseCode == 404;

        assert(correctType && correctCode);
    }
}
