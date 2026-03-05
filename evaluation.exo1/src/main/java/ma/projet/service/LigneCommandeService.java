package ma.projet.service;

import ma.projet.classes.LigneCommande;
import ma.projet.dao.LigneCommandeDao;  // Tu vas créer ce DAO après
import ma.projet.util.HibernateUtil;

import javax.persistence.EntityManager;
import java.util.List;

public class LigneCommandeService {
    private LigneCommandeDao dao = new LigneCommandeDao();

    // CRUD
    public boolean create(LigneCommande lc) { return dao.create(lc); }
    public boolean update(LigneCommande lc) { return dao.update(lc); }
    public boolean delete(LigneCommande lc) { return dao.delete(lc); }
    public LigneCommande getById(int id) { return dao.getById(id); }
    public List<LigneCommande> getAll() { return dao.getAll(); }

    // Méthode spéciale : lignes d'une commande
    public List<LigneCommande> getLignesByCommande(int commandeId) {
        EntityManager em = HibernateUtil.getEntityManagerFactory().createEntityManager();
        try {
            return em.createQuery("SELECT lc FROM LigneCommande lc WHERE lc.commande.id = :id", LigneCommande.class)
                    .setParameter("id", commandeId)
                    .getResultList();
        } finally {
            em.close();
        }
    }
}