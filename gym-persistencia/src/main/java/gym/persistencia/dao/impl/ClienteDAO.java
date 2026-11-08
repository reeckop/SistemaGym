package gym.persistencia.dao.impl;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import gym.infraestructura.persistencia.MongoConnectionManager;
import gym.persistencia.dao.IClienteDAO;
import gym.persistencia.entidades.Cliente;
import gym.persistencia.entidades.Membresia;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;

public class ClienteDAO implements IClienteDAO {

    private MongoCollection<Document> coleccion;

    public ClienteDAO() {
        coleccion = MongoConnectionManager.getInstance().getCollection("clientes");
    }

    private Document clienteToDoc(Cliente c) {
        Document doc = new Document("idCliente", c.getIdCliente())
                .append("nombre", c.getNombre())
                .append("apellido", c.getApellido())
                .append("telefono", c.getTelefono())
                .append("fechaIngreso", c.getFechaIngreso() != null ? c.getFechaIngreso().toString() : null);
        if (c.getMembresia() != null) {
            doc.append("idMembresia", c.getMembresia().getIdMembresia());
        }
        return doc;
    }

    private Cliente docToCliente(Document doc) {
        Cliente c = new Cliente();
        c.setIdCliente(doc.getInteger("idCliente", 0));
        c.setNombre(doc.getString("nombre"));
        c.setApellido(doc.getString("apellido"));
        c.setTelefono(doc.getString("telefono"));
        String fecha = doc.getString("fechaIngreso");
        if (fecha != null) {
            c.setFechaIngreso(LocalDate.parse(fecha));
        }
        return c;
    }

    @Override
    public void guardar(Cliente cliente) {
        coleccion.insertOne(clienteToDoc(cliente));
    }

    @Override
    public void actualizar(Cliente cliente) {
        coleccion.replaceOne(Filters.eq("idCliente", cliente.getIdCliente()), clienteToDoc(cliente));
    }

    @Override
    public Cliente obtenerPorId(int id) {
        Document doc = coleccion.find(Filters.eq("idCliente", id)).first();
        if (doc == null) return null;
        return docToCliente(doc);
    }

    @Override
    public List<Cliente> obtenerTodos() {
        List<Cliente> lista = new ArrayList<>();
        for (Document doc : coleccion.find()) {
            lista.add(docToCliente(doc));
        }
        return lista;
    }
}
