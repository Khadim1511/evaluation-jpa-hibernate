package ma.projet.service;

import ma.projet.beans.Femme;
import ma.projet.beans.Homme;
import ma.projet.beans.Mariage;
import ma.projet.dao.MariageDao;

import java.time.LocalDate;

public class MariageFacade extends AbstractFacade<Mariage, Integer> {

    public MariageFacade() {
        super(new MariageDao());
    }

    public boolean creerMariage(Homme h, Femme f, LocalDate dateDebut) {
        Mariage m = new Mariage(h, f, dateDebut);
        return create(m);
    }
}
