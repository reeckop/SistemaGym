package gym.infraestructura;

import gym.infraestructura.config.AppConfig;
import gym.infraestructura.notificaciones.ApiNotificacionesClient;
import gym.infraestructura.persistencia.MongoConnectionManager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InfraestructuraTest {

    @Test
    void testConfiguracionCargada() {
        String dbName = AppConfig.get("mongodb.database", "default_db");
        assertEquals("gimnasio_db", dbName);
    }

    @Test
    void testInstanciaMongoConnectionManager() {
        MongoConnectionManager manager = MongoConnectionManager.getInstance();
        assertNotNull(manager);
        assertNotNull(manager.getMongoClient());
        assertNotNull(manager.getDatabase());
        assertEquals("gimnasio_db", manager.getDatabase().getName());
    }

    @Test
    void testEnvioNotificacionFallback() {
        ApiNotificacionesClient client = new ApiNotificacionesClient();
        boolean resultado = client.enviarNotificacion("RECORDATORIO", "6441234567", "Tu membresia vence pronto");
        assertTrue(resultado);
    }
}
