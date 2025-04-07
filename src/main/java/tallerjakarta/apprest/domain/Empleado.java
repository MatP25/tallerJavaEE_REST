package tallerjakarta.apprest.domain;

import java.util.List;

public class Empleado {
    private long id;
    private String nombre;
    private String cedula;
    private List<Tarea> tareas;

    public Empleado(){}

    public Empleado(long id, String nombre, String cedula, List<Tarea> tareas) {
        this.id = id;
        this.nombre = nombre;
        this.cedula = cedula;
        this.tareas = tareas;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public List<Tarea> getTareas() {
        return tareas;
    }

    public void setTareas(List<Tarea> tareas) {
        this.tareas = tareas;
    };
    
    public void agregarTarea(Tarea tarea) {
        this.tareas.add(tarea);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((cedula == null) ? 0 : cedula.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Empleado other = (Empleado) obj;
        if (cedula == null) {
            if (other.cedula != null)
                return false;
        } else if (!cedula.equals(other.cedula))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Empleado [id=" + id + ", nombre=" + nombre + ", cedula=" + cedula + ", tareas=" + tareas + "]";
    }
    
    
}
