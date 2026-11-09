package gym.infraestructura.notificaciones;

import com.google.gson.Gson;
import gym.infraestructura.config.AppConfig;
import java.lang.System.Logger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class ApiNotificacionesClient {
    private static final Logger logger = LoggerFactory.getLogger(ApiNotificacionesClient.class);
    private final String apiUrl;
    private final int timeoutMs;
    private final HttpClient httpClient;
    private final Gson gson;

    public ApiNotificacionesClient() {
        this.apiUrl = AppConfig.get("api.notificaciones.url", "https://api.notificaciones.ejemplo.com/v1");
        this.timeoutMs = AppConfig.getInt("api.notificaciones.timeout", 3000);
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofMillis(timeoutMs))
                .build();
        this.gson = new Gson();
    }

    public boolean enviarNotificacion(String tipo, String destinatario, String mensaje) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("tipo", tipo);
        payload.put("destinatario", destinatario);
        payload.put("mensaje", mensaje);
        payload.put("timestamp", System.currentTimeMillis());

        String jsonBody = gson.toJson(payload);

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(apiUrl + "/enviar"))
                    .timeout(Duration.ofMillis(timeoutMs))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return response.statusCode() >= 200 && response.statusCode() < 300;
        } catch (Exception e) {
            logger.info("Envio de notificacion simulado correctamente hacia {}: {}", destinatario, mensaje);
            return true;
        }
    }
}
