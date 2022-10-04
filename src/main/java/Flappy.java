import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class Flappy extends Canvas implements KeyListener {

        protected int largeurEcran = 600;
        protected int hauteurEcran = 600;

        protected boolean pause = false;

        protected Oiseau oiseau;
        protected Tuyau tuyau;

        protected ArrayList<Deplassable> listeDeplassable = new ArrayList<>();
        protected ArrayList<Sprite> listeDeSprite = new ArrayList<>();

        public Flappy() throws InterruptedException {
            JFrame fenetre = new JFrame("Flappy");
            //On récupère le panneau de la fenetre principale
            JPanel panneau = (JPanel) fenetre.getContentPane();
            //On définie la hauteur / largeur de l'écran
            panneau.setPreferredSize(new Dimension(largeurEcran, hauteurEcran));
            setBounds(0, 0, largeurEcran,hauteurEcran);
            //On ajoute cette classe (qui hérite de Canvas) comme composant du panneau principal
            panneau.add(this);

            fenetre.pack();
            fenetre.setResizable(false);
            fenetre.setLocationRelativeTo(null);
            fenetre.setVisible(true);
            fenetre.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            fenetre.requestFocus();
            fenetre.addKeyListener(this);

            //On indique que le raffraichissement de l'ecran doit être fait manuellement.
            createBufferStrategy(2);
            setIgnoreRepaint(true);
            this.setFocusable(false);

            demarrer();
        }

    public void initialiser() {

//    Si 1ere initialisation
        if (oiseau== null) {
            oiseau = new Oiseau(hauteurEcran);
            pause = false;
            tuyau = new Tuyau(200, hauteurEcran, largeurEcran);
            listeDeSprite.add(tuyau);
            listeDeSprite.add(oiseau);
            listeDeplassable.add(tuyau);
            listeDeplassable.add(oiseau);
        } else {

            oiseau.reintialiser(hauteurEcran);
            tuyau.reinitialiser(largeurEcran);

        }

    }
        public void demarrer() throws InterruptedException {

            long indexFrame = 0;

            initialiser();
            listeDeplassable.add(tuyau);
            listeDeplassable.add(oiseau);


            while(true) {
                indexFrame ++;
                Graphics2D dessin = (Graphics2D) getBufferStrategy().getDrawGraphics();

                //-----------------------------
                //reset dessin
                dessin.setColor(Color.WHITE);
                dessin.fillRect(0,0,largeurEcran,hauteurEcran);

                for (Sprite sprite : listeDeSprite){

                    sprite.dessiner(dessin);

                }

                oiseau.dessiner(dessin);
                tuyau.dessiner(dessin);
                //LE CODE ICI <----
                if (!pause) {

                    // si jamais l'oiseau est au sol
                    if (oiseau.getY() > hauteurEcran - oiseau.getLargeur()) {

                        System.out.println("Tu as Perdu");
                        pause= true;

                    } else {
                        // sinon si le jeu continue
                        for (Deplassable deplassable : listeDeplassable) {

                            deplassable.deplacer();

                        }

                    }
                } else {

                    dessin.setColor(new Color(0,0,0,0.2f));
                    dessin.fillRect(0,0,largeurEcran,hauteurEcran);

                }

                //-----------------------------
                dessin.dispose();
                getBufferStrategy().show();
                Thread.sleep(1000 / 60);
            }
        }

        public static void main(String[] args) throws InterruptedException {
            new Flappy();
        }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

            if (e.getKeyCode()== KeyEvent.VK_SPACE){
        oiseau.setVitesseVertical(2);
    }
            if (e.getKeyCode() == KeyEvent.VK_ENTER){
                initialiser();
            }

            if (e.getKeyCode() == KeyEvent.VK_P){
            //inverser un booleen
                pause = !pause;

            }

        }
}

