package ma.projet.test;

import ma.projet.classes.*;
import ma.projet.service.*;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TestProjetMain {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public static void main(String[] args) {
        // On s'assure que la SessionFactory est initialisée
        HibernateUtil.getSessionFactory();

        try {
            creerDonneesTest();
            afficherTests();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            HibernateUtil.getSessionFactory().close();
        }
    }

    private static void creerDonneesTest() throws ParseException {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();

            // ------------------ Employés ------------------
            Employe e1 = new Employe("Alaoui", "Mohamed");
            Employe e2 = new Employe("Bennani", "Sara");
            Employe e3 = new Employe("El Amrani", "Youssef");

            session.persist(e1);
            session.persist(e2);
            session.persist(e3);

            // ------------------ Projets ------------------
            Projet p1 = new Projet("Gestion de stock", sdf.parse("01/01/2013"));
            Projet p2 = new Projet("Site e-commerce", sdf.parse("15/03/2013"));
            Projet p3 = new Projet("Application mobile", sdf.parse("10/05/2013"));
            Projet p4 = new Projet("Système de réservation", sdf.parse("20/06/2013"));

            session.persist(p1);
            session.persist(p2);
            session.persist(p3);
            session.persist(p4);

            // ------------------ Tâches ------------------
            Tache t1 = new Tache("Analyse", sdf.parse("01/02/2013"), sdf.parse("20/02/2013"), 1200);
            Tache t2 = new Tache("Conception", sdf.parse("21/02/2013"), sdf.parse("15/03/2013"), 1800);
            Tache t3 = new Tache("Développement", sdf.parse("16/03/2013"), sdf.parse("25/04/2013"), 4500);
            Tache t4 = new Tache("Tests", sdf.parse("26/04/2013"), sdf.parse("10/05/2013"), 900);
            Tache t5 = new Tache("Déploiement", sdf.parse("11/05/2013"), sdf.parse("20/05/2013"), 600);

            p1.addTache(t1);
            p1.addTache(t2);
            p1.addTache(t3);
            p1.addTache(t4);
            p1.addTache(t5);

            session.persist(t1);
            session.persist(t2);
            session.persist(t3);
            session.persist(t4);
            session.persist(t5);

            // ------------------ Affectations (EmployeTache) ------------------
            // Mohamed a fait plusieurs tâches sur le projet 1
            session.persist(new EmployeTache(e1, t1, sdf.parse("18/02/2013")));
            session.persist(new EmployeTache(e1, t2, sdf.parse("10/03/2013")));
            session.persist(new EmployeTache(e1, t3, sdf.parse("20/04/2013")));

            // Sara a participé au développement et tests
            session.persist(new EmployeTache(e2, t3, sdf.parse("22/04/2013")));
            session.persist(new EmployeTache(e2, t4, sdf.parse("05/05/2013")));

            // Youssef a fait l'analyse et le déploiement
            session.persist(new EmployeTache(e3, t1, sdf.parse("15/02/2013")));
            session.persist(new EmployeTache(e3, t5, sdf.parse("18/05/2013")));

            tx.commit();
            System.out.println("Données de test créées avec succès !");

        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    private static void afficherTests() throws ParseException {
        EmployeService employeService = new EmployeService();
        ProjetService projetService = new ProjetService();

        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            // On recharge le projet 1 (celui qui correspond à l'exemple de l'énoncé)
            Projet projet = session.get(Projet.class, 1); // ← change l'ID si besoin après création

            if (projet == null) {
                System.out.println("Projet non trouvé !");
                return;
            }

            System.out.println("\n==================================================");
            System.out.println(" AFFICHAGE DEMANDÉ DANS L'ÉNONCÉ ");
            System.out.println("==================================================");
            System.out.println("Projet : " + projet.getId() +
                    "   Nom : " + projet.getNom() +
                    "   Date début : " + sdf.format(projet.getDateDebut()));

            System.out.println("Liste des tâches réalisées :");
            List<Tache> tachesRealisees = projetService.getTachesRealiseesPourProjet(projet);

            for (Tache t : tachesRealisees) {
                System.out.printf("%2d   %-15s   %s   %s%n",
                        t.getId(),
                        t.getNom(),
                        t.getDateDebut() != null ? sdf.format(t.getDateDebut()) : "null",
                        t.getDateFin() != null ? sdf.format(t.getDateFin()) : "null");
            }

            // ------------------------------------------------------
            // 1. Tâches réalisées par un employé (ex: Mohamed)
            Employe mohamed = session.get(Employe.class, 1); // ← adapte l'ID
            if (mohamed != null) {
                System.out.println("\nTâches réalisées par : " + mohamed.getNom() + " " + mohamed.getPrenom());
                List<Tache> tachesEmp = employeService.getTachesRealiseesParEmploye(mohamed);
                for (Tache t : tachesEmp) {
                    System.out.println(" - " + t.getNom() + " (" + t.getPrix() + " DH)");
                }
            }

            // ------------------------------------------------------
            // 2. Projets gérés par un employé (celui qui a au moins une tâche)
            System.out.println("\nProjets gérés par Mohamed :");
            List<Projet> projetsEmp = projetService.getProjetsGerésParEmploye(mohamed);
            for (Projet p : projetsEmp) {
                System.out.println(" - " + p.getNom());
            }

            // ------------------------------------------------------
            // 3. Tâches dont le prix > 1000 DH
            System.out.println("\nTâches avec prix > 1000 DH :");
            List<Tache> tachesChères = projetService.getTachesPrixSuperieur1000();
            for (Tache t : tachesChères) {
                System.out.println(" - " + t.getNom() + " → " + t.getPrix() + " DH (Projet: " + t.getProjet().getNom() + ")");
            }

            // ------------------------------------------------------
            // 4. Tâches réalisées entre deux dates (bonus)
            Date debut = sdf.parse("01/03/2013");
            Date fin   = sdf.parse("30/04/2013");

            System.out.println("\nTâches réalisées par Mohamed entre " +
                    sdf.format(debut) + " et " + sdf.format(fin) + " :");
            List<Tache> entreDates = employeService.getTachesRealiseesEntre(mohamed, debut, fin);
            for (Tache t : entreDates) {
                System.out.println(" - " + t.getNom());
            }

        } finally {
            session.close();
        }
    }
}