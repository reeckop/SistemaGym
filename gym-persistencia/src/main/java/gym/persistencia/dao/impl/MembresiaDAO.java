package gym.persistencia.dao.impl;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import gym.infraestructura.persistencia.MongoConnectionManager;
import gym.persistencia.dao.IMembresiaDAO;
import gym.persistencia.entidades.Beneficio;
import gym.persistencia.entidades.Membresia;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;

public class MembresiaDAO implements IMembresiaDAO {

    private MongoCollection<Document> coleccion;

    public MembresiaDAO() {
        coleccion = MongoConnectionManager.getInstance().getCollection("membresias");
    }

    private Document membresiaToDoc(Membresia m) {
        List<Document> beneficiosDocs = new ArrayList<>();
        if (m.getBeneficios() != null) {
            for (Beneficio b : m.getBeneficios()) {
                beneficiosDocs.add(new Document("idBeneficio", b.getIdBeneficio())
                        .append("nombre", b.getNombre())
                        .append("valor", b.getValor())
                        .append("unidad", b.getUnidad()));
            }
        }
        return new Document("idMembresia", m.getIdMembresia())
                .append("nombre", m.getNombre())
                .append("precio", m.getPrecio())
                .append("imagen", m.getImagen())
                .append("estado", m.getEstado())
                .append("fechaInicio", m.getFechaInicio() != null ? m.getFechaInicio().toString() : null)
                .append("fechaFin", m.getFechaFin() != null ? m.getFechaFin().toString() : null)
                .append("beneficios", beneficiosDocs);
    }

    private Membresia docToMembresia(Document doc) {
        Membresia m = new Membresia();
        m.setIdMembresia(doc.getInteger("idMembresia", 0));
        m.setNombre(doc.getString("nombre"));
        m.setPrecio(doc.getDouble("precio") != null ? doc.getDouble("precio") : 0.0);
        m.setImagen(doc.getString("imagen"));
        m.setEstado(doc.getString("estado"));
        String fi = doc.getString("fechaInicio");
        if (fi != null) m.setFechaInicio(LocalDate.parse(fi));
        String ff = doc.getString("fechaFin");
        if (ff != null) m.setFechaFin(LocalDate.parse(ff));
        List<Document> bdocs = (List<Document>) doc.get("beneficios");
        List<Beneficio> beneficios = new ArrayList<>();
        if (bdocs != null) {
            for (Document bd : bdocs) {
                Beneficio b = new Beneficio();
                b.setIdBeneficio(bd.getInteger("idBeneficio", 0));
                b.setNombre(bd.getString("nombre"));
                b.setValor(bd.getDouble("valor") != null ? bd.getDouble("valor") : 0.0);
                b.setUnidad(bd.getString("unidad"));
                beneficios.add(b);
            }
        }
        m.setBeneficios(beneficios);
        return m;
    }

    @Override
    public void guardar(Membresia membresia) {
        coleccion.insertOne(membresiaToDoc(membresia));
    }

    @Override
    public void actualizar(Membresia membresia) {
        coleccion.replaceOne(Filters.eq("idMembresia", membresia.getIdMembresia()), membresiaToDoc(membresia));
    }

    @Override
    public void eliminar(int id) {
        coleccion.deleteOne(Filters.eq("idMembresia", id));
    }

    @Override
    public List<Membresia> obtenerTodos() {
        List<Membresia> lista = new ArrayList<>();
        for (Document doc : coleccion.find()) {
            lista.add(docToMembresia(doc));
        }
        return lista;
    }
}
