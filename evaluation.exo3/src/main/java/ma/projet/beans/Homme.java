package ma.projet.beans;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "hommes")
public class Homme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nom;
    private String prenom;

    @OneToMany(mappedBy = "homme", cascade = CascadeType.ALL)
    private List<Mariage> mariages = new ArrayList<>();

    public Homme() {
    }

    public Homme(String nom, String prenom) {
        this.nom = nom;
        this.prenom = prenom;
    }

    public void addMariage(Mariage m) {
        mariages.add(m);
        m.setHomme(this);
    }

    // getters + setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public List<Mariage> getMariages() {
        return mariages;
    }
}
