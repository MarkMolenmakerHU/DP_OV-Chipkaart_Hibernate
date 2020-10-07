package nl.hu.dp.dao.hibernate;

import nl.hu.dp.dao.AdresDAO;
import nl.hu.dp.dao.ReizigerDAO;
import nl.hu.dp.domain.Adres;
import nl.hu.dp.domain.Reiziger;
import org.hibernate.Session;

import java.util.List;

public class AdresDAOHibernate implements AdresDAO {

    private final Session session;
    private final ReizigerDAO rdao;

    public AdresDAOHibernate(Session session, ReizigerDAO rdao) {
        this.session = session;
        this.rdao = rdao;

    }

    @Override
    public boolean save(Adres adres) {
        rdao.save(adres.getReiziger());

        if (findById(adres.getId()) == null) {
            session.beginTransaction();
            session.save(adres);
            session.getTransaction().commit();
        }

        return false;
    }

    @Override
    public boolean update(Adres adres) {
        rdao.update(adres.getReiziger());
        return false;
    }

    @Override
    public boolean delete(Adres adres) {
        rdao.delete(adres.getReiziger());
        return false;
    }

    @Override
    public Adres findById(int id) {
        session.beginTransaction();
        Adres adres = session.load(Adres.class, id);
        session.getTransaction().commit();
        return adres;
    }

    @Override
    public Adres findByReiziger(Reiziger reiziger) {
        session.beginTransaction();
        Adres adres = (Adres) session.createQuery("SELECT a FROM Adres a WHERE a.reiziger = :reiziger").setParameter("reiziger", reiziger).getResultList().get(0);
        session.getTransaction().commit();
        return adres;
    }

    @Override
    public List<Adres> findAll() {
        session.beginTransaction();
        List<Adres> adressen = session.createQuery("SELECT a FROM Adres a").getResultList();
        session.getTransaction().commit();
        return adressen;
    }
}
