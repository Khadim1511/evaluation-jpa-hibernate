// EmployeTache.java  ← table d'association + attributs (date, prix si besoin)
package ma.projet.classes;


import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "employe_tache")
public class EmployeTache {

    @EmbeddedId
    private EmployeTachePK id;

    @ManyToOne
    @MapsId("employeId")
    @JoinColumn(name = "employe_id")
    private Employe employe;

    @ManyToOne
    @MapsId("tacheId")
    @JoinColumn(name = "tache_id")
    private Tache tache;

    @Temporal(TemporalType.DATE)
    private Date date;           // date réelle de réalisation

    // Constructeurs
    public EmployeTache() {}

    public EmployeTache(Employe e, Tache t, Date date) {
        this.employe = e;
        this.tache = t;
        this.date = date;
        this.id = new EmployeTachePK(e.getId(), t.getId());
    }

    // Getters & Setters
    public Employe getEmploye() { return employe; }
    public void setEmploye(Employe employe) { this.employe = employe; }
    public Tache getTache() { return tache; }
    public void setTache(Tache tache) { this.tache = tache; }
    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }
}