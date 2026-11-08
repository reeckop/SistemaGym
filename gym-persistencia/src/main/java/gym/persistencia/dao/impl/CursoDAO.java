package gym.persistencia.dao.impl;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import gym.infraestructura.persistencia.MongoConnectionManager;
import gym.persistencia.dao.ICursoDAO;
import gym.persistencia.entidades.Curso;
import gym.persistencia.entidades.Horario;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;

public class CursoDAO implements ICursoDAO {

    private MongoCollection<Document> coleccion;

    public CursoDAO() {
        coleccion = MongoConnectionManager.getInstance().getCollection("cursos");
    }

    private Document cursoToDoc(Curso c) {
        List<Document> horariosDocs = new ArrayList<>();
        if (c.getHorarios() != null) {
            for (Horario h : c.getHorarios()) {
                horariosDocs.add(new Document("idHorario", h.getIdHorario())
                        .append("fecha", h.getFecha() != null ? h.getFecha().toString() : null)
                        .append("horaInicio", h.getHoraInicio() != null ? h.getHoraInicio().toString() : null)
                        .append("horaFin", h.getHoraFin() != null ? h.getHoraFin().toString() : null));
            }
        }
        return new Document("idCurso", c.getIdCurso())
                .append("nombre", c.getNombre())
                .append("disciplina", c.getDisciplina())
                .append("precio", c.getPrecio())
                .append("cupo", c.getCupo())
                .append("imagen", c.getImagen())
                .append("horarios", horariosDocs);
    }

    private Curso docToCurso(Document doc) {
        Curso c = new Curso();
        c.setIdCurso(doc.getInteger("idCurso", 0));
        c.setNombre(doc.getString("nombre"));
        c.setDisciplina(doc.getString("disciplina"));
        c.setPrecio(doc.getDouble("precio") != null ? doc.getDouble("precio") : 0.0);
        c.setCupo(doc.getInteger("cupo", 0));
        c.setImagen(doc.getString("imagen"));
        List<Document> hdocs = (List<Document>) doc.get("horarios");
        List<Horario> horarios = new ArrayList<>();
        if (hdocs != null) {
            for (Document hd : hdocs) {
                Horario h = new Horario();
                h.setIdHorario(hd.getInteger("idHorario", 0));
                String fecha = hd.getString("fecha");
                if (fecha != null) h.setFecha(LocalDate.parse(fecha));
                String hi = hd.getString("horaInicio");
                if (hi != null) h.setHoraInicio(LocalTime.parse(hi));
                String hf = hd.getString("horaFin");
                if (hf != null) h.setHoraFin(LocalTime.parse(hf));
                horarios.add(h);
            }
        }
        c.setHorarios(horarios);
        return c;
    }

    @Override
    public void guardar(Curso curso) {
        coleccion.insertOne(cursoToDoc(curso));
    }

    @Override
    public void actualizar(Curso curso) {
        coleccion.replaceOne(Filters.eq("idCurso", curso.getIdCurso()), cursoToDoc(curso));
    }

    @Override
    public void eliminar(int id) {
        coleccion.deleteOne(Filters.eq("idCurso", id));
    }

    @Override
    public List<Curso> obtenerTodos() {
        List<Curso> lista = new ArrayList<>();
        for (Document doc : coleccion.find()) {
            lista.add(docToCurso(doc));
        }
        return lista;
    }
}
