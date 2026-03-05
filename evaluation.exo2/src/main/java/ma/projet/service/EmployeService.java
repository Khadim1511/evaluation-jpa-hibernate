package ma.projet.service;

import ma.projet.classes.Employe;
import ma.projet.classes.EmployeTache;
import ma.projet.classes.Tache;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.Date;
import java.util.List;

public class EmployeService {

    public List<Tache> getTachesRealiseesParEmploye(Employe e) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<EmployeTache> q = session.createQuery(
                    "FROM EmployeTache et WHERE et.employe = :emp", EmployeTache.class);
            q.setParameter("emp", e);
            List<Tache> list = q.getResultList().stream()
                    .map(EmployeTache::getTache)
                    .toList();
            return list;
        }
    }

    // Bonus : tâches réalisées entre deux dates
    public List<Tache> getTachesRealiseesEntre(Employe e, Date debut, Date fin) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<EmployeTache> q = session.createQuery(
                    "FROM EmployeTache et WHERE et.employe = :emp " +
                            "AND et.date BETWEEN :debut AND :fin", EmployeTache.class);
            q.setParameter("emp", e);
            q.setParameter("debut", debut);
            q.setParameter("fin", fin);
            return q.getResultList().stream()
                    .map(EmployeTache::getTache)
                    .toList();
        }
    }
}