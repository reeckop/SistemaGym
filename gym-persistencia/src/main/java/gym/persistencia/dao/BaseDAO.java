/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gym.persistencia.dao;

import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
import com.mongodb.client.ClientSession;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.codecs.configuration.CodecProvider;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

/**
 *
 * @author PC GAMER MASTER RACE
 */
public abstract class BaseDAO {
    private static final String CADENA_CONEXION = "mongodb://localhost:27017";

    private static final String BASE_DATOS = "fitlifegym";

    private static final CodecRegistry CODEC_REGISTRY = crearCodecs();

    private static final MongoClient CLIENTE = MongoClients.create(CADENA_CONEXION);

    private final ClientSession sesion;

    public BaseDAO(ClientSession sesion) {
        this.sesion = sesion;
    }

    public static ClientSession iniciarSesion() {
        return CLIENTE.startSession();
    }

    protected ClientSession obtenerSesion() {
        return sesion;
    }

    protected MongoDatabase obtenerBaseDatos() {
        return CLIENTE.getDatabase(BASE_DATOS).withCodecRegistry(CODEC_REGISTRY);
    }

    protected <T> MongoCollection<T> obtenerColeccion(String nombreColeccion, Class<T> tipoClase) {
        return obtenerBaseDatos().getCollection(nombreColeccion, tipoClase);
    }

    private static CodecRegistry crearCodecs() {
        CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();

        return fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));
    }

    public static void cerrarConexion() {
        CLIENTE.close();
    }

    
}
