package nl.hu.dp.dao;

import nl.hu.dp.domain.Adres;
import nl.hu.dp.domain.OVChipkaart;
import nl.hu.dp.domain.Product;
import nl.hu.dp.domain.Reiziger;

import java.sql.SQLException;
import java.util.List;

public interface ProductDAO {

    boolean save(Product product);
    boolean update(Product product);
    boolean delete(Product product);

    Product findById(int id);
    List<Product> findByOVChipkaart(OVChipkaart ovChipkaart);
    List<Product> findAll();

}
