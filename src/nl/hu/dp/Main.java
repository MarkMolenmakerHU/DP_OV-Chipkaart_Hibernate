package nl.hu.dp;

import nl.hu.dp.dao.AdresDAO;
import nl.hu.dp.dao.OVChipkaartDAO;
import nl.hu.dp.dao.ProductDAO;
import nl.hu.dp.dao.ReizigerDAO;
import nl.hu.dp.dao.hibernate.AdresDAOHibernate;
import nl.hu.dp.dao.hibernate.OVChipkaartDAOHibernate;
import nl.hu.dp.dao.hibernate.ProductDAOHibernate;
import nl.hu.dp.dao.hibernate.ReizigerDAOHibernate;
import nl.hu.dp.domain.Adres;
import nl.hu.dp.domain.OVChipkaart;
import nl.hu.dp.domain.Product;
import nl.hu.dp.domain.Reiziger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

/**
 * Testklasse - deze klasse test alle andere klassen in deze package.
 *
 * System.out.println() is alleen in deze klasse toegestaan (behalve voor exceptions).
 *
 * @author tijmen.muller@hu.nl
 */
public class Main {
    // Creëer een factory voor Hibernate sessions.
    private static final SessionFactory factory;

    static {
        try {
            // Create a Hibernate session factory
            factory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    /**
     * Retouneer een Hibernate session.
     *
     * @return Hibernate session
     * @throws HibernateException
     */
    private static Session getSession() throws HibernateException {
        return factory.openSession();
    }

    public static void main(String[] args) throws SQLException {
        //testReizgerDAOHibernate();
        //testAdresDAOHibernate();
        //testOVChipkaartDAOHibernate();
        testProductDAOHibernate();
    }

    /**
     * P6. Haal alle (geannoteerde) entiteiten uit de database.
     */
    private static void testFetchAll() {
        Session session = getSession();
        try {
            Metamodel metamodel = session.getSessionFactory().getMetamodel();
            for (EntityType<?> entityType : metamodel.getEntities()) {
                Query query = session.createQuery("from " + entityType.getName());

                System.out.println("[Test] Alle objecten van type " + entityType.getName() + " uit database:");
                for (Object o : query.list()) {
                    System.out.println("  " + o);
                }
                System.out.println();
            }
        } finally {
            session.close();
        }
    }

    /**
     * P7. Test ReizigerDAOHibernate class.
     */
    private static void testReizgerDAOHibernate() {
        // Nieuwe DAO
        ReizigerDAO rdao = new ReizigerDAOHibernate(getSession());

        // Aantal nu in DB
        int size = rdao.findAll().size();
        System.out.println("[TEST findAll()] " + size + " Reizigers in de Database VOOR save.");

        // Maak nieuwe Reizger
        Reiziger bob = new Reiziger();
        bob.setVoorletters("B");
        bob.setAchternaam("Blokhout");
        bob.setGeboortedatum(LocalDate.now());
        bob.setId(6);
        bob.setTussenvoegsel("van");

        // Save
        rdao.save(bob);

        // Aantal na de save in db
        size = rdao.findAll().size();
        System.out.println("[TEST save()] " + size + " Reizigers in de Database NA save.");

        // Find saved
        System.out.println("[TEST findById()] " + rdao.findById(bob.getId()));

        // Update achternaam
        bob.setAchternaam("UPDATE");
        rdao.update(bob);

        // Find door Gbdatum na update
        System.out.println("[TEST update()/findByGbdatum()] " + rdao.findByGbdatum("2020-10-06")); //@TODO Bij aftekenen verander dit in datum van vandaag!

        // Delete
        rdao.delete(bob);

        // Aantal na de delete in db
        size = rdao.findAll().size();
        System.out.println("[TEST delete()] " + size + " Reizigers in de Database NA delete.");

        // 2 Regels skippen
        System.out.println("\n");
    }

    /**
     * P7. Test AdresDAOHibernate class.
     */
    private static void testAdresDAOHibernate() {
        // Nieuwe DAO
        ReizigerDAO rdao = new ReizigerDAOHibernate(getSession());
        AdresDAO adao = new AdresDAOHibernate(getSession(), rdao);

        // Aantal nu in DB
        int size = adao.findAll().size();
        System.out.println("[TEST findAll()] " + size + " Adressen in de Database VOOR save.");

        // Maak nieuw Adres
        Adres adres = new Adres();
        adres.setId(10);
        adres.setStraat("Straatweg");
        adres.setHuisnummer("100");
        adres.setPostcode("1000AA");
        adres.setWoonplaats("Amsterdam");

        // Nieuwe Reiziger
        Reiziger reiziger = new Reiziger();
        reiziger.setVoorletters("X");
        reiziger.setAchternaam("Xander");
        reiziger.setId(20);
        reiziger.setGeboortedatum(LocalDate.now());
        reiziger.setAdres(adres);

        adres.setReiziger(reiziger);

        // Save
        adao.save(adres);

        // Aantal na de save in db
        size = adao.findAll().size();
        System.out.println("[TEST save()] " + size + " Adressen in de Database NA save.");

        // Find saved
        System.out.println("[TEST findById()] " + adao.findById(adres.getId()));

        // Update adres
        adres.setWoonplaats("Mexico");
        adao.update(adres);

        // Find door reizger na update
        System.out.println("[TEST update()/findByReizger()] " + adao.findByReiziger(reiziger));

        // Delete
        adao.delete(adres);

        // Aantal na de delete in db
        size = adao.findAll().size();
        System.out.println("[TEST delete()] " + size + " Adressen in de Database NA delete.");

        // 2 Regels skippen
        System.out.println("\n");
    }

    /**
     * P7. Test OVChipkaartDAOHibernate class.
     */
    private static void testOVChipkaartDAOHibernate() {
        // Nieuwe DAO
        ReizigerDAO rdao = new ReizigerDAOHibernate(getSession());
        OVChipkaartDAO odao = new OVChipkaartDAOHibernate(getSession(), rdao);

        // Aantal nu in DB
        int size = odao.findAll().size();
        System.out.println("[TEST findAll()] " + size + " OVChipkaarten in de Database VOOR save.");

        // Maak nieuwe OVChipkaart
        OVChipkaart ovChipkaart = new OVChipkaart();
        ovChipkaart.setKaart_nummer(123456);
        ovChipkaart.setSaldo(100);
        ovChipkaart.setGeldig_tot(LocalDate.now().plusDays(100));
        ovChipkaart.setKlasse(1);

        // Reiziger
        Reiziger reiziger = new Reiziger();
        reiziger.setVoorletters("O");
        reiziger.setTussenvoegsel("OV");
        reiziger.setAchternaam("Kaart");
        reiziger.setId(99);
        reiziger.setGeboortedatum(LocalDate.now());
        reiziger.addOVChipkaart(ovChipkaart);

        ovChipkaart.setReiziger(reiziger);

        // Save
        odao.save(ovChipkaart);

        // Aantal na de save in db
        size = odao.findAll().size();
        System.out.println("[TEST save()] " + size + " OVChipkaarten in de Database NA save.");

        // Find saved
        System.out.println("[TEST findById()] " + odao.findByKaartNummer(ovChipkaart.getKaart_nummer()));

        // Update OVChipkaart
        ovChipkaart.setSaldo(1);
        odao.update(ovChipkaart);

        // Find door reizger na update
        System.out.println("[TEST update()/findByReizger()] " + odao.findByReiziger(reiziger));

        // Delete
        odao.delete(ovChipkaart);

        // Aantal na de delete in db
        size = odao.findAll().size();
        System.out.println("[TEST delete()] " + size + " OVChipkaarten in de Database NA delete.");

        // 2 Regels skippen
        System.out.println("\n");
    }

    /**
     * P7. Test ProductDAOHibernate class.
     */
    private static void testProductDAOHibernate() {
        // Nieuwe DAO
        ProductDAO pdao = new ProductDAOHibernate(getSession());

        // Aantal nu in DB
        int size = pdao.findAll().size();
        System.out.println("[TEST findAll()] " + size + " Producten in de Database VOOR save.");

        // Maak nieuw Product
        Product product = new Product();
        product.setProduct_nummer(666);
        product.setNaam("Speciaal Product");
        product.setBeschrijving("Product voor iedereen met ovkaarten");
        product.setPrijs(6.66);

        // Save
        pdao.save(product);

        // Aantal na de save in db
        size = pdao.findAll().size();
        System.out.println("[TEST save()] " + size + " Producten in de Database NA save.");

        // Find saved
        System.out.println("[TEST findById()] " + pdao.findById(product.getProduct_nummer()));

        // Update Product
        OVChipkaart ovChipkaart = new OVChipkaart();
        ovChipkaart.setKaart_nummer(666666);
        ovChipkaart.setSaldo(4.20);
        ovChipkaart.setGeldig_tot(LocalDate.now().plusDays(20));
        ovChipkaart.setKlasse(3);
        ovChipkaart.addProduct(product);

        product.addOVChipkaart(ovChipkaart);
        pdao.update(product);

        // Find door OVChipkaart na update
        System.out.println("[TEST update()/findByOVChipkaart()] " + pdao.findByOVChipkaart(ovChipkaart));

        // Delete
        pdao.delete(product);

        // Aantal na de delete in db
        size = pdao.findAll().size();
        System.out.println("[TEST delete()] " + size + " Producten in de Database NA delete.");

        // 2 Regels skippen
        System.out.println("\n");
    }
}