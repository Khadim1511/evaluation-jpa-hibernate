package ma.projet.service;

import ma.projet.classes.Categorie;
import ma.projet.classes.Commande;
import ma.projet.classes.Produit;
import ma.projet.dao.ProduitDao;
import ma.projet.util.HibernateUtil;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.List;

public class ProduitService {
    private ProduitDao dao = new ProduitDao();

    // CRUD basique (on utilise le DAO)
    public boolean create(Produit p) { return dao.create(p); }
    public boolean update(Produit p) { return dao.update(p); }
    public boolean delete(Produit p) { return dao.delete(p); }
    public Produit getById(int id) { return dao.getById(id); }
    public List<Produit> getAll() { return dao.getAll(); }

    // Méthode 1 : produits par catégorie
    public List<Produit> getProduitsByCategorie(int categorieId) {
        EntityManager em = HibernateUtil.getEntityManagerFactory().createEntityManager();
        try {
            TypedQuery<Produit> query = em.createQuery(
                    "SELECT p FROM Produit p WHERE p.categorie.id = :id", Produit.class);
            query.setParameter("id", categorieId);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    // Méthode 2 : produits commandés entre deux dates
    public List<Produit> getProduitsBetweenDates(Date debut, Date fin) {
        EntityManager em = HibernateUtil.getEntityManagerFactory().createEntityManager();
        try {
            TypedQuery<Produit> query = em.createQuery(
                    "SELECT DISTINCT lc.produit FROM LigneCommande lc " +
                            "WHERE lc.commande.date BETWEEN :debut AND :fin", Produit.class);
            query.setParameter("debut", debut);
            query.setParameter("fin", fin);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    // Méthode 3 : produits d'une commande donnée
    public List<Produit> getProduitsByCommande(int commandeId) {
        EntityManager em = HibernateUtil.getEntityManagerFactory().createEntityManager();
        try {
            TypedQuery<Produit> query = em.createQuery(
                    "SELECT lc.produit FROM LigneCommande lc WHERE lc.commande.id = :id", Produit.class);
            query.setParameter("id", commandeId);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    // Méthode 4 : produits prix > 100 DH avec requête nommée
    public List<Produit> getProduitsPrixSup100() {
        EntityManager em = HibernateUtil.getEntityManagerFactory().createEntityManager();
        try {
            TypedQuery<Produit> query = em.createNamedQuery("Produit.findByPrixSup", Produit.class);
            query.setParameter("prix", 100.0f);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
}