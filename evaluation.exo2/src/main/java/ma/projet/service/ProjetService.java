package ma.projet.service;

import ma.projet.classes.Employe;
import ma.projet.classes.Projet;
import ma.projet.classes.Tache;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class ProjetService {

    public List<Projet> getProjetsGerésParEmploye(Employe e) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Projet> q = session.createQuery(
                    "SELECT DISTINCT t.projet FROM Tache t " +
                            "JOIN t.employeTaches et WHERE et.employe = :emp", Projet.class);
            q.setParameter("emp", e);
            return q.getResultList();
        }
    }

    public List<Tache> getTachesPlanifieesPourProjet(Projet p) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "FROM Tache t WHERE t.projet = :p", Tache.class)
                    .setParameter("p", p)
                    .getResultList();
        }
    }

    // Tâches réalisées (celles qui ont une ligne dans EmployeTache)
    public List<Tache> getTachesRealiseesPourProjet(Projet p) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "SELECT et.tache FROM EmployeTache et WHERE et.tache.projet = :p", Tache.class)
                    .setParameter("p", p)
                    .getResultList();
        }
    }

    // Tâches dont le prix > 1000 DH
    public List<Tache> getTachesPrixSuperieur1000() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "FROM Tache t WHERE t.prix > 1000", Tache.class)
                    .getResultList();
        }
    }
}