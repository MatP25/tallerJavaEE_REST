package tallerjakarta.apprest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import tallerjakarta.apprest.domain.Empleado;
import tallerjakarta.apprest.domain.Tarea;

public interface IService {
    public Map<String, Empleado> getEmpleados();
    public Empleado getEmpleadoPorCedula(String cedula) throws Exception;
    public void agregarEmpleado(String nombre, String cedula) throws Exception;
    public void removerEmpleadoPorCedula(String cedula) throws Exception;
    public void agregarTarea(String cedulaEmpleado, String descTarea, LocalDateTime fechaIni, LocalDateTime fechaFin) throws Exception;
    public List<Tarea> getTareasDeEmpleadoPorCedula(String cedula) throws Exception;
}
