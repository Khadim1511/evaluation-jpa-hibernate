package ma.projet;

import ma.projet.beans.Femme;
import ma.projet.beans.Homme;
import ma.projet.beans.Mariage;
import ma.projet.service.HommeFacade;
import ma.projet.service.FemmeFacade;
import ma.projet.service.MariageFacade;

import java.time.LocalDate;

public class Main {

    public static void main(String[] args) {

        HommeFacade hf = new HommeFacade();
        FemmeFacade ff = new FemmeFacade();
        MariageFacade mf = new MariageFacade();

        // Création données test
        Homme h1 = new Homme("SAFI", "SAID");
        hf.create(h1);

        Femme f1 = new Femme("RAMI", "ALMA");
        ff.create(f1);

        Femme f2 = new Femme("ALI", "SALIMA");
        ff.create(f2);

        Femme f3 = new Femme("ALAOUI", "KHADIJA");
        ff.create(f3);

        // Mariages
        mf.creerMariage(h1, f1, LocalDate.of(1998, 3, 8));
        mf.creerMariage(h1, f2, LocalDate.of(1999, 8, 3));
        mf.creerMariage(h1, f3, LocalDate.of(2000, 4, 10));

        // Affichages demandés
        System.out.println("\n=== Affichage des mariages d'un homme ===\n");
        hf.afficherMariagesHomme(h1.getId());

        System.out.println("\n=== Affichage détaillé ===\n");
        hf.afficherEpousesDetail(h1.getId());

        // Autres fonctionnalités possibles
        System.out.println("\nHommes mariés 4 fois ou plus : " + hf.hommesMarieQuatreOuPlus().size());
    }
}
