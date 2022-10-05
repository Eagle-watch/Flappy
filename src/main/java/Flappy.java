import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class Flappy extends Canvas implements KeyListener {

    protected static int largeurEcran = 600;
    protected static int hauteurEcran = 600;

    protected boolean pause = false;

    protected Oiseau oiseau;


    protected ArrayList<Deplacable> listeDeplacable = new ArrayList<>();
    protected ArrayList<Sprite> listeSprite = new ArrayList<>();

    protected ArrayList<Tuyau> listeTuyau = new ArrayList<>();

    public Flappy() throws InterruptedException {

        JFrame fenetre = new JFrame("Flappy");
        //On récupère le panneau de la fenetre principale
        JPanel panneau = (JPanel) fenetre.getContentPane();
        //On définie la hauteur / largeur de l'écran


        panneau.setPreferredSize(new Dimension(largeurEcran, hauteurEcran));
        setBounds(0, 0, largeurEcran, hauteurEcran);
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
        this.createBufferStrategy(2);
        this.setIgnoreRepaint(true);
        this.setFocusable(false);

        this.demarrer();
    }

    public void initialiser() {

        pause = false;

        //si c'est la première initialisation
        if (oiseau == null) {
            oiseau = new Oiseau(hauteurEcran);
            listeDeplacable.add(oiseau);
//            listeDeplacable.add(nuage);

            listeSprite.add(oiseau);
//            listeSprite.add(nuage);


//           ajout tuyau

            for (int i = 0; i < 5; i++) {

                Tuyau tuyau = new Tuyau(200, hauteurEcran, largeurEcran);
                listeTuyau.add(tuyau);
                listeDeplacable.add(tuyau);
                listeSprite.add(tuyau);

            }
            //ajout nuages
            for (int i = 0; i < 50; i++) {
                Nuage nuage = new Nuage(largeurEcran, hauteurEcran);
                listeDeplacable.add(nuage);
                listeSprite.add(nuage);
            }

        } else {
            for (Deplacable deplacable : listeDeplacable) {
                deplacable.reinitialiser(largeurEcran, hauteurEcran);
            }
        }
    }

    public void demarrer() throws InterruptedException {

        long indexFrame = 0;

        initialiser();

        while (true) {

            indexFrame++;
            Graphics2D dessin = (Graphics2D) getBufferStrategy().getDrawGraphics();

            //-----------------------------
            //reset dessin
            dessin.setColor(Color.WHITE);
            dessin.fillRect(0, 0, largeurEcran, hauteurEcran);

            for (Sprite sprite : listeSprite) {
                sprite.dessiner(dessin);
            }

            if (!pause) {
                //-----si jamais l'oiseau est tombé par terre ---
                if (oiseau.getY() > hauteurEcran - oiseau.getLargeur()) {
                    System.out.println("perdu");
                    pause = true;
                } else {
                    //----sinon si le jeu continu ----
//                    oiseau.deplacer();
//                    tuyau.deplacer();

                    for (Deplacable deplacable : listeDeplacable) {
                        deplacable.deplacer(largeurEcran, hauteurEcran);
                    }
                    for (Tuyau tuyau : listeTuyau) {

                        if (Sprite.testCollision(oiseau, tuyau)) {
                            System.out.println("perdu");
                            pause = true;

                        }
                    }

                }
            } else {
                dessin.setColor(new Color(0, 0, 0, 0.1f));
                dessin.fillRect(0, 0, largeurEcran, hauteurEcran);
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
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            oiseau.sauter();
        }

        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            initialiser();
        }

        if (e.getKeyCode() == KeyEvent.VK_P) {
            //inverser un booléen
            pause = !pause;
        }
    }
}

