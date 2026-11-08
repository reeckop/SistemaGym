package gym.infraestructura.persistencia;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import gym.infraestructura.config.AppConfig;
import org.bson.Document;

public class MongoConnectionManager {
    private static MongoConnectionManager instance;
    private MongoClient mongoClient;
    private MongoDatabase database;

    private MongoConnectionManager() {
        init();
    }

    private void init() {
        String uri = AppConfig.get("mongodb.uri", "mongodb://localhost:27017");
        String dbName = AppConfig.get("mongodb.database", "gimnasio_db");
        this.mongoClient = MongoClients.create(uri);
        this.database = mongoClient.getDatabase(dbName);
    }

    public static synchronized MongoConnectionManager getInstance() {
        if (instance == null) {
            instance = new MongoConnectionManager();
        }
        return instance;
    }

    public MongoDatabase getDatabase() {
        if (mongoClient == null) {
            init();
        }
        return database;
    }

    public MongoCollection<Document> getCollection(String collectionName) {
        return getDatabase().getCollection(collectionName);
    }

    public MongoClient getMongoClient() {
        return mongoClient;
    }

    public void close() {
        if (mongoClient != null) {
            mongoClient.close();
            mongoClient = null;
            database = null;
        }
    }
}
