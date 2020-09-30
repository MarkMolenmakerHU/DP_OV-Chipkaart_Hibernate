package nl.hu.dp.domain;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;

@Entity
public class Reiziger {

    // Attributes
    @Id @Column(name = "reiziger_id")
    private int id;
    private String voorletters, tussenvoegsel, achternaam;
    private LocalDate geboortedatum;

    @OneToOne(
            mappedBy = "reiziger",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private Adres adres;

    @OneToMany(
            mappedBy = "reiziger",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<OVChipkaart> ovChipkaarten = new ArrayList<>();

    // Constructors
    public Reiziger() {

    }
    public Reiziger(int id, String voorletters, String tussenvoegsel, String achternaam, LocalDate geboortedatum) {
        this.id = id;
        this.voorletters = voorletters;
        this.tussenvoegsel = tussenvoegsel;
        this.achternaam = achternaam;
        this.geboortedatum = geboortedatum;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getVoorletters() {
        return voorletters;
    }
    public void setVoorletters(String voorletters) {
        this.voorletters = voorletters;
    }

    public String getTussenvoegsel() {
        return tussenvoegsel;
    }
    public void setTussenvoegsel(String tussenvoegsel) {
        this.tussenvoegsel = tussenvoegsel;
    }

    public String getAchternaam() {
        return achternaam;
    }
    public void setAchternaam(String achternaam) {
        this.achternaam = achternaam;
    }

    public LocalDate getGeboortedatum() {
        return geboortedatum;
    }
    public void setGeboortedatum(LocalDate geboortedatum) {
        this.geboortedatum = geboortedatum;
    }

    public Adres getAdres() {
        return adres;
    }
    public void setAdres(Adres adres) {
        this.adres = adres;
    }

    public List<OVChipkaart> getOVChipkaarten() {
        return ovChipkaarten;
    }
    public void setOvChipkaarten(List<OVChipkaart> ovChipkaarten) {
        this.ovChipkaarten = ovChipkaarten;
    }
    public void addOVChipkaart(OVChipkaart ovChipkaart) {
        ovChipkaarten.add(ovChipkaart);
    }
    public void removeOVChipkaart(OVChipkaart ovChipkaart) { ovChipkaarten.remove(ovChipkaart); }

    // Other Methods
    public String getNaam() {
        return voorletters + (tussenvoegsel == null ? "" : " " + tussenvoegsel) + " " + achternaam;
    }

    public String ownString() {
        return String.format("Reiziger #%s: %s (%s)", id, getNaam(), geboortedatum);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format("OVChipkaarten: %s kaart(en)", ovChipkaarten.size()));
        for (OVChipkaart ovchip : ovChipkaarten)
            stringBuilder.append("\n        ").append(ovchip.ownString());

        return String.format("%s\n    %s\n    %s",
                ownString(),
                adres != null ? adres.ownString() : "<Geen Adres>",
                ovChipkaarten.size() > 0 ? stringBuilder.toString() : "<Geen OVChipkaarten>"
        );
    }
}
