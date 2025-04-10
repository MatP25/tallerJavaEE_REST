package tallerjakarta.apprest.adapters;

import java.time.LocalDateTime;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;
 
/*
 * Esta clase es necesaria para serializar y deserializar los objetos LocalDateTime a formato json
 * Los atributos que utilizen LocalDateTime deben incluir el annotation     
 * @XmlJavaTypeAdapter(value = LocalDateTimeAdapter.class) para indicar que debe utilizar este adapter
 * para la conversion
 * Tambien puede definirse el uso de este adaptador a nivel del package para evitar tener que agregar
 * el annotation todas las veces a cada atributo (to do...)
 */
public class LocalDateTimeAdapter extends XmlAdapter<String, LocalDateTime>{
 
    public LocalDateTime unmarshal(String value) throws Exception {
        return LocalDateTime.parse(value);
    }
 
    public String marshal(LocalDateTime date) throws Exception {
        return date.toString();
    }
}