package ma.projet.beans;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "mariages")
public class Mariage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "homme_id", nullable = false)
    private Homme homme;

    @ManyToOne
    @JoinColumn(name = "femme_id", nullable = false)
    private Femme femme;

    @Column(name = "date_debut")
    private LocalDate dateDebut;

    @Column(name = "date_fin")
    private LocalDate dateFin;

    @OneToMany(mappedBy = "mariage", cascade = CascadeType.ALL)
    private List<Enfant> enfants = new ArrayList<>();

    public Mariage() {
    }

    public Mariage(Homme h, Femme f, LocalDate debut) {
        this.homme = h;
        this.femme = f;
        this.dateDebut = debut;
    }

    // getters + setters
    public int getId() {
        return id;
    }

    public Homme getHomme() {
        return homme;
    }

    public void setHomme(Homme homme) {
        this.homme = homme;
    }

    public Femme getFemme() {
        return femme;
    }

    public void setFemme(Femme femme) {
        this.femme = femme;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    public List<Enfant> getEnfants() {
        return enfants;
    }
}
