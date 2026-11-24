package gym.servicios.catalogos;

import gym.servicios.dto.BeneficioDTO;
import gym.servicios.dto.MembresiaDTO;
import gym.servicios.enums.EstadoMembresia;
import java.util.List;

/**
 *  Catálogo base para que siempre existan las opciones Bronce, Plata y Oro.
 *
 * @author PC GAMER MASTER RACE
 */
public final class MembresiasPredeterminadas {
    private static final List<MembresiaDTO> MEMBRESIAS = List.of(
            crear("000000000000000000000001", "Bronce", 350, "/gym/membresias/bronce.png", List.of(
                    new BeneficioDTO("100000000000000000000001", "Acceso al gimnasio", 1, "mes"),
                    new BeneficioDTO("100000000000000000000002", "Rutina inicial", 1, "incluida"))),
            crear("000000000000000000000002", "Plata", 500, "/gym/membresias/plata.png", List.of(
                    new BeneficioDTO("200000000000000000000001", "Acceso al gimnasio", 1, "mes"),
                    new BeneficioDTO("200000000000000000000002", "Clases grupales", 4, "sesiones"),
                    new BeneficioDTO("200000000000000000000003", "Invitaciones", 1, "al mes"))),
            crear("000000000000000000000003", "Oro", 650, "/gym/membresias/oro.png", List.of(
                    new BeneficioDTO("300000000000000000000001", "Acceso al gimnasio", 1, "mes"),
                    new BeneficioDTO("300000000000000000000002", "Clases grupales", 30, "sesiones"),
                    new BeneficioDTO("300000000000000000000003", "Invitaciones", 2, "al mes"),
                    new BeneficioDTO("300000000000000000000004", "Descuento en tienda", 10, "%")))
    );

    private MembresiasPredeterminadas() {
    }

    public static List<MembresiaDTO> obtenerTodas() {
        return MEMBRESIAS;
    }

    private static MembresiaDTO crear(String id, String nombre, double precio, String imagen,
            List<BeneficioDTO> beneficios) {
        return new MembresiaDTO(id, nombre, precio, imagen, EstadoMembresia.ACTIVA, null, null, beneficios);
    }
}
