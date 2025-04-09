package tallerjakarta.apprest.domain;

import java.time.LocalDateTime;

import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import tallerjakarta.apprest.adapters.LocalDateTimeAdapter;

@XmlRootElement
public class Tarea {
    private long id;
    private String descripcion;
    @XmlJavaTypeAdapter(value = LocalDateTimeAdapter.class)
    private LocalDateTime fechaIni;
    @XmlJavaTypeAdapter(value = LocalDateTimeAdapter.class)
    private LocalDateTime fechaFin;

   public Tarea() {}

   public Tarea(long id, String descripcion, LocalDateTime fechaIni, LocalDateTime fechaFin) {
    this.id = id;
    this.descripcion = descripcion;
    this.fechaIni = fechaIni;
    this.fechaFin = fechaFin;
   }

   public long getId() {
    return id;
   }

   public void setId(long id) {
    this.id = id;
   }

   public String getDescripcion() {
    return descripcion;
   }

   public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
   }

   public LocalDateTime getFechaIni() {
    return fechaIni;
   }

   public void setFechaIni(LocalDateTime fechaIni) {
    this.fechaIni = fechaIni;
   }

   public LocalDateTime getFechaFin() {
    return fechaFin;
   }

   public void setFechaFin(LocalDateTime fechaFin) {
    this.fechaFin = fechaFin;
   }

   @Override
   public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + (int) (id ^ (id >>> 32));
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
    Tarea other = (Tarea) obj;
    if (id != other.id)
        return false;
    return true;
   }

   @Override
   public String toString() {
    return "Tarea [id=" + id + ", descripcion=" + descripcion + ", fechaIni=" + fechaIni + ", fechaFin=" + fechaFin + "]";
   };
   
   

}
