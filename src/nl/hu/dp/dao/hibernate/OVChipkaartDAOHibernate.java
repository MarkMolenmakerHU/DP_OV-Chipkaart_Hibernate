package nl.hu.dp.dao.hibernate;

import nl.hu.dp.dao.OVChipkaartDAO;
import nl.hu.dp.dao.ReizigerDAO;
import nl.hu.dp.domain.Adres;
import nl.hu.dp.domain.OVChipkaart;
import nl.hu.dp.domain.Reiziger;
import org.hibernate.Session;

import java.util.List;

public class OVChipkaartDAOHibernate implements OVChipkaartDAO {

    private final Session session;
    private final ReizigerDAO rdao;

    public OVChipkaartDAOHibernate(Session session, ReizigerDAO rdao) {
        this.session = session;
        this.rdao = rdao;
    }

    @Override
    public boolean save(OVChipkaart ovChipkaart) {
        rdao.save(ovChipkaart.getReiziger());

        if (findByKaartNummer(ovChipkaart.getKaart_nummer()) == null) {
            session.beginTransaction();
            session.save(ovChipkaart);
            session.getTransaction().commit();
        }

        return false;
    }

    @Override
    public boolean update(OVChipkaart ovChipkaart) {
        rdao.update(ovChipkaart.getReiziger());
        return false;
    }

    @Override
    public boolean delete(OVChipkaart ovChipkaart) {
        rdao.delete(ovChipkaart.getReiziger());
        return false;
    }

    @Override
    public OVChipkaart findByKaartNummer(int kaart_nummer) {
        session.beginTransaction();
        OVChipkaart ovkaart = session.load(OVChipkaart.class, kaart_nummer);
        session.getTransaction().commit();
        return ovkaart;
    }

    @Override
    public List<OVChipkaart> findByReiziger(Reiziger reiziger) {
        session.beginTransaction();
        List<OVChipkaart> ovkaarten = session.createQuery("SELECT o FROM OVChipkaart o WHERE o.reiziger = :reiziger").setParameter("reiziger", reiziger).getResultList();
        session.getTransaction().commit();
        return ovkaarten;
    }

    @Override
    public List<OVChipkaart> findAll() {
        session.beginTransaction();
        List<OVChipkaart> ovkaarten = session.createQuery("SELECT o FROM OVChipkaart o").getResultList();
        session.getTransaction().commit();
        return ovkaarten;
    }
}
