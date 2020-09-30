package nl.hu.dp.domain;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity @Table(name = "ov_chipkaart")
public class OVChipkaart {

    // Attributes
    @Id @Column(name = "kaart_nummer")
    private int kaart_nummer;
    private int klasse;
    private LocalDate geldig_tot;
    private double saldo;

    @ManyToOne
    @JoinColumn(name = "reiziger_id")
    private Reiziger reiziger;

    @ManyToMany
    @JoinTable(
            name = "ov_chipkaart_product",
            joinColumns = @JoinColumn(name = "kaart_nummer"),
            inverseJoinColumns = @JoinColumn(name = "product_nummer")
    )
    private List<Product> producten = new ArrayList<>();

    // Constructors
    public OVChipkaart(int kaart_nummer, LocalDate geldig_tot, int klasse, double saldo, Reiziger reiziger) {
        this.kaart_nummer = kaart_nummer;
        this.geldig_tot = geldig_tot;
        this.klasse = klasse;
        this.saldo = saldo;
        this.reiziger = reiziger;
        reiziger.addOVChipkaart(this);
    }
    public OVChipkaart() {

    }

    // Getters and Setters
    public int getKaart_nummer() {
        return kaart_nummer;
    }
    public void setKaart_nummer(int kaart_nummer) {
        this.kaart_nummer = kaart_nummer;
    }

    public int getKlasse() {
        return klasse;
    }
    public void setKlasse(int klasse) {
        this.klasse = klasse;
    }

    public LocalDate getGeldig_tot() {
        return geldig_tot;
    }
    public void setGeldig_tot(LocalDate geldig_tot) {
        this.geldig_tot = geldig_tot;
    }

    public double getSaldo() {
        return saldo;
    }
    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public Reiziger getReiziger() {
        return reiziger;
    }
    public void setReiziger(Reiziger reiziger) {
        this.reiziger = reiziger;
    }

    public List<Product> getProducten() {
        return producten;
    }
    public void setProducten(ArrayList<Product> producten) {
        this.producten = producten;
    }
    public void addProduct(Product product) {
        this.producten.add(product);
    }
    public void removeProduct(Product product) {
        this.producten.remove(product);
    }

    // Other Methods
    public String ownString() {
        return String.format("OVChipkaart #%s: %s %s %s", kaart_nummer, saldo, geldig_tot, klasse);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format("Producten: %s product(en)", producten.size()));
        for (Product product : producten)
            stringBuilder.append("\n        ").append(product.ownString());

        return String.format("%s\n    %s\n    %s",
                ownString(),
                reiziger != null ? reiziger.ownString() : "<Geen Reiziger>",
                producten.size() > 0 ? stringBuilder.toString() : "<Geen Producten>"
        );
    }

}
