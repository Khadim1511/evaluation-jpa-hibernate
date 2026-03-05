package ma.projet.service;

import ma.projet.classes.Categorie;
import ma.projet.classes.Produit;
import ma.projet.dao.CategorieDao;  // Tu vas créer ce DAO après
import ma.projet.util.HibernateUtil;

import javax.persistence.EntityManager;
import java.util.List;

public class CategorieService {
    private CategorieDao dao = new CategorieDao();

    // CRUD
    public boolean create(Categorie c) { return dao.create(c); }
    public boolean update(Categorie c) { return dao.update(c); }
    public boolean delete(Categorie c) { return dao.delete(c); }
    public Categorie getById(int id) { return dao.getById(id); }
    public List<Categorie> getAll() { return dao.getAll(); }

    // Méthode spéciale : produits par catégorie (mais on l'a déjà dans ProduitService, donc optionnel)
    public List<Produit> getProduitsByCategorie(int categorieId) {
        EntityManager em = HibernateUtil.getEntityManagerFactory().createEntityManager();
        try {
            return em.createQuery("SELECT p FROM Produit p WHERE p.categorie.id = :id", Produit.class)
                    .setParameter("id", categorieId)
                    .getResultList();
        } finally {
            em.close();
        }
    }
}