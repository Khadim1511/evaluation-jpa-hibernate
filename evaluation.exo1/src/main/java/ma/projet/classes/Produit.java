package ma.projet.classes;

import javax.persistence.*;

@Entity
@Table(name = "produit")
@NamedQuery(name = "Produit.findByPrixSup", query = "SELECT p FROM Produit p WHERE p.prix > :prix")
public class Produit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String reference;
    private float prix;

    @ManyToOne
    @JoinColumn(name = "categorie_id")
    private Categorie categorie;

    public Produit() { }  // Vide, obligatoire

    public Produit(String reference, float prix, Categorie categorie) {
        this.reference = reference;
        this.prix = prix;
        this.categorie = categorie;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getReference() { return reference; }
    public void setReference(String reference) { this.reference = reference; }
    public float getPrix() { return prix; }
    public void setPrix(float prix) { this.prix = prix; }
    public Categorie getCategorie() { return categorie; }
    public void setCategorie(Categorie categorie) { this.categorie = categorie; }

    @Override
    public String toString() {
        return "Produit [reference=" + reference + ", prix=" + prix + " DH, categorie=" + (categorie != null ? categorie.getLibelle() : "aucune") + "]";
    }
}