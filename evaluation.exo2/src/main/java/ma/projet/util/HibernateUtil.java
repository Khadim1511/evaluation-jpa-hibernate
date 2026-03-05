package ma.projet.util;

import ma.projet.classes.Employe;
import ma.projet.classes.EmployeTache;
import ma.projet.classes.Projet;
import ma.projet.classes.Tache;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            return new Configuration()
                    .configure() // charge hibernate.cfg.xml ou application.properties
                    .addAnnotatedClass(Projet.class)
                    .addAnnotatedClass(Employe.class)
                    .addAnnotatedClass(Tache.class)
                    .addAnnotatedClass(EmployeTache.class)
                    .buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Initialisation SessionFactory échouée : " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}