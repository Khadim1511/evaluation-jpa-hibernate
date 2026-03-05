package ma.projet.service;

import ma.projet.beans.Homme;
import ma.projet.beans.Mariage;
import ma.projet.dao.HommeDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;

import java.util.List;

public class HommeFacade extends AbstractFacade<Homme, Integer> {

    public HommeFacade() {
        super(new HommeDao());
    }

    public void afficherMariagesHomme(int hommeId) {
        Homme h = findById(hommeId);
        if (h == null) {
            System.out.println("Homme introuvable");
            return;
        }

        System.out.println("Nom : " + h.getNom() + " " + h.getPrenom());
        System.out.println("Mariages en cours :");

        int i = 1;
        for (Mariage m : h.getMariages()) {
            if (m.getDateFin() == null) {
                System.out.println(i + ". Femme : " + m.getFemme().getNom() + " " + m.getFemme().getPrenom());
                System.out.println("   Date début : " + m.getDateDebut());
                System.out.println("   Nb enfants : " + m.getEnfants().size());
                i++;
            }
        }
    }

    public void afficherEpousesDetail(int hommeId) {
        Homme h = findById(hommeId);
        if (h == null)
            return;

        System.out.println(h.getNom() + " " + h.getPrenom());
        System.out.println("Mariages :");

        int num = 1;
        for (Mariage m : h.getMariages()) {
            String etat = m.getDateFin() == null ? "En cours" : "Terminé " + m.getDateFin();
            System.out.println(num + ". Femme : " + m.getFemme().getNom() + " " + m.getFemme().getPrenom());
            System.out.println("   Début : " + m.getDateDebut() + "   " + etat);
            System.out.println("   Nb Enfants : " + m.getEnfants().size());
            num++;
        }
    }

    public List<Homme> hommesMarieQuatreOuPlus() {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            return s.createQuery("FROM Homme h WHERE size(h.mariages) >= 4", Homme.class).list();
        }
    }
}
