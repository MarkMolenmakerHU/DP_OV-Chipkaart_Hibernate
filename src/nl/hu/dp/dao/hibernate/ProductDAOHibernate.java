package nl.hu.dp.dao.hibernate;

import nl.hu.dp.dao.ProductDAO;
import nl.hu.dp.domain.Adres;
import nl.hu.dp.domain.OVChipkaart;
import nl.hu.dp.domain.Product;
import org.hibernate.Session;

import java.util.List;

public class ProductDAOHibernate implements ProductDAO {

    private final Session session;
    public ProductDAOHibernate(Session session) {
        this.session = session;
    }

    @Override
    public boolean save(Product product) {
        session.beginTransaction();
        session.save(product);
        session.getTransaction().commit();
        return false;
    }

    @Override
    public boolean update(Product product) {
        session.beginTransaction();
        session.update(product);
        session.getTransaction().commit();
        return false;
    }

    @Override
    public boolean delete(Product product) {
        session.beginTransaction();
        session.delete(product);
        session.getTransaction().commit();
        return false;
    }

    @Override
    public Product findById(int id) {
        session.beginTransaction();
        Product product = session.load(Product.class, id);
        session.getTransaction().commit();
        return product;
    }

    @Override
    public List<Product> findByOVChipkaart(OVChipkaart ovChipkaart) {
        session.beginTransaction();
        List<Product> producten = session.createQuery("SELECT p FROM OVChipkaart o JOIN o.producten p WHERE o.kaart_nummer = :kaart_nummer").setParameter("kaart_nummer", ovChipkaart.getKaart_nummer()).getResultList();
        session.getTransaction().commit();
        return producten;
    }

    @Override
    public List<Product> findAll() {
        session.beginTransaction();
        List<Product> producten = session.createQuery("SELECT p FROM Product p").getResultList();
        session.getTransaction().commit();
        return producten;
    }
}
