package tallerjakarta.apprest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import tallerjakarta.apprest.domain.Empleado;
import tallerjakarta.apprest.domain.Tarea;

@ApplicationScoped
public class appService implements IService {

    Map<String, Empleado> empleados;

    @PostConstruct
    public void init() {
        this.empleados = new HashMap<String, Empleado>();

        Tarea t1 = new Tarea((int)(Math.random()*10001), "Implementar tests unitarios", LocalDateTime.now(), LocalDateTime.now().plusDays(7));
        Tarea t2 = new Tarea((int)(Math.random()*10001), "Diseño de landing page", LocalDateTime.now(), LocalDateTime.now().plusDays(4));
        Tarea t3 = new Tarea((int)(Math.random()*10001), "Implementar login", LocalDateTime.now(), LocalDateTime.now().plusDays(15));

        List<Tarea> tareasE1 = new ArrayList<Tarea>();
        List<Tarea> tareasE2 = new ArrayList<Tarea>();

        tareasE1.add(t1);
        tareasE1.add(t2);
        tareasE2.add(t3);

        Empleado e1 = new Empleado((int)(Math.random()*10001), "Esteban Quito", "12345678", tareasE1);
        Empleado e2 = new Empleado((int)(Math.random()*10001), "Elvis Cocho", "87654321", tareasE2);
        Empleado e3 = new Empleado((int)(Math.random()*10001), "Armando Casas", "12121212", new ArrayList<Tarea>());

        this.empleados.put(e1.getCedula(), e1);
        this.empleados.put(e2.getCedula(), e2);
        this.empleados.put(e3.getCedula(), e3);
    }

    @Override
    public Map<String,Empleado> getEmpleados() {
        return this.empleados;
    }

    @Override
    public Empleado getEmpleadoPorCedula(String cedula) throws Exception {
        Empleado emp = this.empleados.get(cedula);

        if (emp == null) {
            throw new Exception("No se encontró un empleado con la cédula [" + cedula + "].");
        }

        return emp;
    }

    @Override
    public void agregarEmpleado(String nombre, String cedula) throws Exception {
        Empleado emp = this.empleados.get(cedula);

        if (emp != null) {
            throw new Exception("Ya existe un empleado con la cédula [" + cedula + "].");
        }
        
        Empleado nuevoEmpleado = new Empleado(
            (int)(Math.random()*10001), 
            nombre, 
            cedula, 
            new ArrayList<Tarea>()
        );

        this.empleados.put(cedula, nuevoEmpleado);
    }

    @Override
    public void removerEmpleadoPorCedula(String cedula) throws Exception {

        Empleado emp = this.empleados.get(cedula);

        if (emp == null) {
            throw new Exception("No existe un empleado con la cédula [" + cedula + "].");
        }
        if (!emp.getTareas().isEmpty()) {
            throw new Exception("No es posible eliminar un empleado con tareas asignadas.");
        }

        this.empleados.remove(cedula);
    }

    @Override
    public void agregarTarea(String cedulaEmpleado, String descTarea, LocalDateTime fechaIni, LocalDateTime fechaFin) throws Exception {
        Empleado emp = this.empleados.get(cedulaEmpleado);
        if (emp == null) {
            throw new Exception("No existe un empleado con la cédula [" + cedulaEmpleado + "].");
        }

        Tarea nuevaTarea = new Tarea(
            (int)(Math.random()*10001),
            descTarea,
            fechaIni,
            fechaFin
        );

        emp.agregarTarea(nuevaTarea);
    }

    @Override
    public List<Tarea> getTareasDeEmpleadoPorCedula(String cedula) throws Exception {
        Empleado emp = this.empleados.get(cedula);
        if (emp == null) {
            throw new Exception("No existe un empleado con la cédula [" + cedula + "].");
        }

        return emp.getTareas();
    }
}