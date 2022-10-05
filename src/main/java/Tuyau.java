import java.awt.*;

public class Tuyau extends Rectangle implements Deplacable {

    public Tuyau(int hauteur, int hauteurEcran, int largeurEcran) {
        super((int)(largeurEcran  + Math.random() * 500 )  , hauteurEcran-hauteur, 100, hauteur);
    }

    @Override
    public void dessiner(Graphics2D dessin) {

        dessin.setColor(couleur);
        dessin.fillRect(x,y,largeur,hauteur);

    }

    @Override
    public void deplacer(int largeurEcran, int hauteurEcran) {
        x -= 2;
    }

    public void reinitialiser(int largeurEcran, int hauteurEcran) {
        x = largeurEcran;
    }
}
