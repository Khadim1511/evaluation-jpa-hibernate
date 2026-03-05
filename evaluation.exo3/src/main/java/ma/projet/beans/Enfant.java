package ma.projet.beans;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "enfants")
public class Enfant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nom;
    private LocalDate dateNaissance;

    @ManyToOne
    @JoinColumn(name = "mariage_id")
    private Mariage mariage;

    public Enfant() {
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

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public Mariage getMariage() {
        return mariage;
    }

    public void setMariage(Mariage mariage) {
        this.mariage = mariage;
    }
}
