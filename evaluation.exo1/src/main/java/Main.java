package ma.projet;

import ma.projet.classes.Categorie;
import ma.projet.classes.Commande;
import ma.projet.classes.LigneCommande;
import ma.projet.classes.Produit;
import ma.projet.service.ProduitService;
import ma.projet.util.HibernateUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        EntityManager em = HibernateUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        ProduitService ps = new ProduitService();

        try {
            tx.begin();

            // 1. Créer catégorie
            Categorie cat = new Categorie("INF", "Informatique");
            em.persist(cat);

            // 2. Créer produits
            Produit p1 = new Produit("ES12", 120.0f, cat);
            Produit p2 = new Produit("ZR85", 90.0f, cat);
            em.persist(p1);
            em.persist(p2);

            // 3. Créer commande
            Commande cmd = new Commande(new Date());
            em.persist(cmd);

            // 4. Ajouter lignes de commande
            LigneCommande lc1 = new LigneCommande(5, p1, cmd);
            LigneCommande lc2 = new LigneCommande(3, p2, cmd);
            em.persist(lc1);
            em.persist(lc2);

            tx.commit();

            // 5. Tests
            System.out.println("Tous les produits :");
            ps.getAll().forEach(p -> System.out.println(p.getReference() + " - " + p.getPrix() + " DH"));

            System.out.println("\nProduits > 100 DH :");
            ps.getProduitsPrixSup100().forEach(p -> System.out.println(p.getReference()));

            System.out.println("\nProduits de la commande " + cmd.getId() + " :");
            ps.getProduitsByCommande(cmd.getId()).forEach(p -> System.out.println(p.getReference()));

            // Bonus : produits entre deux dates (exemple : aujourd'hui - 1 jour à aujourd'hui + 1 jour)
            Date debut = new Date(System.currentTimeMillis() - 86400000); // -1 jour
            Date fin = new Date(System.currentTimeMillis() + 86400000);   // +1 jour
            System.out.println("\nProduits commandés entre " + debut + " et " + fin + " :");
            ps.getProduitsBetweenDates(debut, fin).forEach(p -> System.out.println(p.getReference()));

        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
            HibernateUtil.shutdown();
        }
    }
}