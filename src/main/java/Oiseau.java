import java.awt.*;

public class Oiseau extends Carré {

    protected int vitesseVertical;


    public void sauter () {

    }

    @Override
    public void dessiner(Graphics2D dessin) {

    }

    public int getVitesseVertical() {
        return vitesseVertical;
    }

    public void setVitesseVertical(int vitesseVertical) {
        this.vitesseVertical = vitesseVertical;
    }
}
