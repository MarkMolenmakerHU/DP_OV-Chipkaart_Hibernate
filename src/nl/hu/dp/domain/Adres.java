package nl.hu.dp.domain;

import javax.persistence.*;

@Entity
public class Adres {

    // Attributes
    @Id @Column(name = "adres_id")
    private int id;
    private String postcode, huisnummer, straat, woonplaats;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reiziger_id")
    private Reiziger reiziger;

    // Constructors
    public Adres(int id, String postcode, String huisnummer, String straat, String woonplaats, Reiziger reiziger) {
        this.id = id;
        this.postcode = postcode;
        this.huisnummer = huisnummer;
        this.straat = straat;
        this.woonplaats = woonplaats;
        this.reiziger = reiziger;
        reiziger.setAdres(this);
    }
    public Adres() {

    }

    // Getters and Setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getHuisnummer() {
        return huisnummer;
    }
    public void setHuisnummer(String huisnummer) {
        this.huisnummer = huisnummer;
    }

    public String getPostcode() {
        return postcode;
    }
    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getStraat() {
        return straat;
    }
    public void setStraat(String straat) {
        this.straat = straat;
    }

    public String getWoonplaats() {
        return woonplaats;
    }
    public void setWoonplaats(String woonplaats) {
        this.woonplaats = woonplaats;
    }

    public Reiziger getReiziger() {
        return reiziger;
    }
    public void setReiziger(Reiziger reiziger) {
        this.reiziger = reiziger;
    }

    // Other Methods
    public String ownString() {
        return String.format("Adres #%s: %s %s %s %s", id, straat, huisnummer, postcode, woonplaats);
    }

    @Override
    public String toString() {
        return String.format("%s\n    %s", ownString(), reiziger != null ? reiziger.ownString() : "<Geen Reiziger>");
    }
}
