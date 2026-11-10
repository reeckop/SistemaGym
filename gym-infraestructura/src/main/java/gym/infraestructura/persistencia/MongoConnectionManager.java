package gym.infraestructura.persistencia;

import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.codecs.configuration.CodecProvider;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

public class MongoConnectionManager {
    /**
     * Cadena de conexión utilizada para conectarse a MongoDB.
     */
    public static final String CADENA_CONEXION = "mongodb://localhost:27017";

    /**
     * Nombre de la base de datos utilizada por el sistema.
     */
    public static final String BASE_DATOS = "fitlifegym";

    /**
     * Registro de codecs para trabajar con POJOs.
     */
    private static final CodecRegistry CODEC_REGISTRY = crearCodecs();

    /**
     * Única conexión compartida de MongoDB.
     */
    private static final MongoClient CLIENTE = MongoClients.create(CADENA_CONEXION);

    /**
     * Constructor privado para evitar crear instancias.
     */
    private MongoConnectionManager() {
    }

    /**
     * Obtiene la única conexión de MongoDB.
     *
     * @return Cliente de conexión MongoDB.
     */
    public static MongoClient obtenerConexion() {
        return CLIENTE;
    }

    /**
     * Obtiene la base de datos configurada.
     *
     * @return Base de datos del sistema.
     */
    public static MongoDatabase obtenerBaseDatos() {
        return CLIENTE.getDatabase(BASE_DATOS).withCodecRegistry(CODEC_REGISTRY);
    }

    /**
     * Crea el registro de codecs necesario para trabajar con POJOs.
     *
     * @return Registro de codecs configurado.
     */
    private static CodecRegistry crearCodecs() {
        CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
        return fromRegistries(getDefaultCodecRegistry(),fromProviders(pojoCodecProvider));
    }

    /**
     * Cierra la conexión cuando termina la aplicación.
     */
    public static void cerrarConexion() {
        CLIENTE.close();
    }
}
