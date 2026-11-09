package gym.infraestructura.persistencia;

import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import gym.infraestructura.config.AppConfig;
import org.bson.Document;
import org.bson.codecs.configuration.CodecProvider;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

public class MongoConnectionManager {
    /**
     * Cadena de conexión utilizada
     * para conectarse a MongoDB.
     */
    public static final String CADENA_CONEXION = "mongodb://localhost:27017";
    
    /**
     * Nombre de la base de datos
     * utilizada por el sistema.
     */
    public static final String BASE_DATOS = "fitlifegym";

    /**
     * Crea una conexión con MongoDB.
     * 
     * @return Cliente de conexión MongoDB.
     */
    public static MongoClient crearConexion() {
        MongoClient client = MongoClients.create(CADENA_CONEXION);
        return client;
    }

    /**
     * Obtiene el registro de codecs
     * necesario para trabajar con POJOs.
     * 
     * @return Registro de codecs configurado.
     */
    public static CodecRegistry obtenerCodecs() {
        
        // Esto nos genera un Proveedor de codecs
        CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
        
        // Generar un registro de codecs(default + pojo)
        CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));
        
        return pojoCodecRegistry;
    }
}
