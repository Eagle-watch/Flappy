import java.awt.*;

public class Tuyau extends Rectangle implements Deplassable {

    public Tuyau(int hauteur, int hauteurEcran, int largeurEcran) {
        super(largeurEcran   - 100  , hauteurEcran-hauteur, 100, hauteur);
    }

    @Override
    public void dessiner(Graphics2D dessin) {

        dessin.setColor(couleur);
        dessin.fillRect(x,y,largeur,hauteur);

    }

    @Override
    public void deplacer() {

        x -= 1;

    }

    public void reinitialiser (int largeurEcran) {

        x = largeurEcran;

    }
}
