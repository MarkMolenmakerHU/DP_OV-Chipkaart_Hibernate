package nl.hu.dp.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Product {

    // Attributes
    @Id @Column(name = "product_nummer")
    private int product_nummer;
    private String naam;
    private String beschrijving;
    private double prijs;

    @ManyToMany(mappedBy = "producten")
    private List<OVChipkaart> ovChipkaarten = new ArrayList<>();

    // Constructors
    public Product(int product_nummer, String naam, String beschrijving, double prijs) {
        this.product_nummer = product_nummer;
        this.naam = naam;
        this.beschrijving = beschrijving;
        this.prijs = prijs;
    }
    public Product() {

    }

    // Getters and Setters
    public int getProduct_nummer() {
        return product_nummer;
    }
    public void setProduct_nummer(int product_nummer) {
        this.product_nummer = product_nummer;
    }

    public String getNaam() {
        return naam;
    }
    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getBeschrijving() {
        return beschrijving;
    }
    public void setBeschrijving(String beschrijving) {
        this.beschrijving = beschrijving;
    }

    public double getPrijs() {
        return prijs;
    }
    public void setPrijs(double prijs) {
        this.prijs = prijs;
    }

    public List<OVChipkaart> getOVChipkaarten() {
        return ovChipkaarten;
    }
    public void setOVChipkaarten(ArrayList<OVChipkaart> ovChipkaarten) {
        this.ovChipkaarten = ovChipkaarten;
    }
    public void addOVChipkaart(OVChipkaart ovChipkaart) {
        this.ovChipkaarten.add(ovChipkaart);
    }
    public void removeOVChipkaart(OVChipkaart ovChipkaart) {
        this.ovChipkaarten.remove(ovChipkaart);
    }

    // Other Methods
    public String ownString() {
        return String.format("Product %s#: %s, %s, %s", product_nummer, naam, beschrijving, prijs);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format("OVChipkaarten: %s kaart(en)", ovChipkaarten.size()));
        for (OVChipkaart ovchip : ovChipkaarten)
            stringBuilder.append("\n        ").append(ovchip.ownString());

        return String.format("%s\n    %s",
                ownString(),
                ovChipkaarten.size() > 0 ? stringBuilder.toString() : "<Geen OVChipkaarten>"
        );
    }
}
