package nl.hu.dp.dao.hibernate;

import nl.hu.dp.dao.ReizigerDAO;
import nl.hu.dp.domain.Reiziger;
import org.hibernate.Session;

import java.time.LocalDate;
import java.util.List;

public class ReizigerDAOHibernate implements ReizigerDAO {

    private final Session session;

    public ReizigerDAOHibernate(Session session) {
        this.session = session;
    }

    @Override
    public boolean save(Reiziger reiziger) {
        session.beginTransaction();
        session.save(reiziger);
        session.getTransaction().commit();
        return false;
    }

    @Override
    public boolean update(Reiziger reiziger) {
        session.beginTransaction();
        session.update(reiziger);
        session.getTransaction().commit();
        return false;
    }

    @Override
    public boolean delete(Reiziger reiziger) {
        session.beginTransaction();
        session.delete(reiziger);
        session.getTransaction().commit();
        return false;
    }

    @Override
    public Reiziger findById(int id) {
        session.beginTransaction();
        Reiziger reiziger = session.load(Reiziger.class, id);
        session.getTransaction().commit();
        return reiziger;
    }

    @Override
    public List<Reiziger> findByGbdatum(String datum) {
        session.beginTransaction();
        List<Reiziger> reizigers = session.createQuery("SELECT r FROM Reiziger r WHERE r.geboortedatum = :datum").setParameter("datum", LocalDate.parse(datum)).getResultList();
        session.getTransaction().commit();
        return reizigers;
    }

    @Override
    public List<Reiziger> findAll() {
        session.beginTransaction();
        List<Reiziger> reizigers = session.createQuery("SELECT r FROM Reiziger r").getResultList();
        session.getTransaction().commit();
        return reizigers;
    }

}
