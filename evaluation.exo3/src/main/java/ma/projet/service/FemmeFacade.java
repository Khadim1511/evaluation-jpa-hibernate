package ma.projet.service;

import ma.projet.beans.Femme;
import ma.projet.dao.FemmeDao;

public class FemmeFacade extends AbstractFacade<Femme, Integer> {

    public FemmeFacade() {
        super(new FemmeDao());
    }
}
