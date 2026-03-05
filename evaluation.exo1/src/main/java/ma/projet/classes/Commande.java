package ma.projet.classes;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "commande")
public class Commande {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private Date date;

    @OneToMany(mappedBy = "commande")
    private List<LigneCommande> lignes;

    public Commande() { }

    public Commande(Date date) {
        this.date = date;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }
    public List<LigneCommande> getLignes() { return lignes; }
    public void setLignes(List<LigneCommande> lignes) { this.lignes = lignes; }

    @Override
    public String toString() {
        return "Commande [id=" + id + ", date=" + (date != null ? date.toString() : "non définie") + ", lignes=" + (lignes != null ? lignes.size() : 0) + "]";
    }
}