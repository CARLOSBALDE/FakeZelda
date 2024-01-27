package GenerateTiles;

import Entidad.Entidad;
import Main.GameFrame;
import Main.GamePanel;
import Main.MyKeyboard;
import SoundMusic.Sonido;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class MenuPrincipal {

    MyKeyboard teclado = new MyKeyboard();
    GamePanel gp;
    MyKeyboard mk;
    int row;
    int col;
    BufferedImage menuleave;
    BufferedImage menuhowtoplay;
    BufferedImage menustart;
    int option = 1;
    public int SpriteCounter = 0;
    public int SpriteNum = 1;
    public Sonido sonido = new Sonido();
    public Sonido sonido2 = new Sonido();
    boolean howtoplay = false;

    public MenuPrincipal(GamePanel gp, MyKeyboard mk) {
        this.gp = gp;
        this.mk = mk;
        try {
            menuleave = ImageIO.read(new FileInputStream("src\\Tile\\MENU LEAVE.png"));
            menuhowtoplay = ImageIO.read(new FileInputStream("src\\Tile\\menu how to play.png"));
            menustart = ImageIO.read(new FileInputStream("src\\Tile\\menu start.png"));
        } catch (IOException ex) {
            Logger.getLogger(MenuPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
        sonido.InsertarSonido("src\\SoundMusic\\the-legend-of-zelda-twilight-princess-hd-ost-file-select.wav");
        sonido2.InsertarSonido("src\\SoundMusic\\interaccion.wav");
        try {
            sonido.Reproducir();
        } catch (LineUnavailableException ex) {
            Logger.getLogger(MenuPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MenuPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
        sonido.CheckVolumen(0.2f);
    }

    public void draw(Graphics2D g2) {

        if (option == 1) {
            g2.drawImage(menustart, 0, 0, null);
        } else if (option == 2) {
            g2.drawImage(menuhowtoplay, 0, 0, null);
            if (howtoplay) {

            } else {

            }

        } else if (option == 3) {
            g2.drawImage(menuleave, 0, 0, null);

        }

    }

    public void update() {

        if (SpriteCounter == 0) {
            if (mk.PressUp == true) {
                SpriteCounter = 10;
                option--;

                if (sonido2.EstaReproduciendose()) {
                    sonido2.Cerrar();

                    sonido2.InsertarSonido("src\\SoundMusic\\interaccion2.wav");

                    try {
                        sonido2.Reproducir();
                    } catch (LineUnavailableException ex) {
                        Logger.getLogger(MenuPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(MenuPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {

                    sonido2.InsertarSonido("src\\SoundMusic\\interaccion2.wav");

                    try {
                        sonido2.Reproducir();
                    } catch (LineUnavailableException ex) {
                        Logger.getLogger(MenuPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(MenuPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            } else if (mk.PressDown == true) {
                SpriteCounter = 10;
                option++;
                if (sonido2.EstaReproduciendose()) {
                    sonido2.Cerrar();
                    sonido2.InsertarSonido("src\\SoundMusic\\interaccion2.wav");
                    try {
                        sonido2.Reproducir();
                    } catch (LineUnavailableException ex) {
                        Logger.getLogger(MenuPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(MenuPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    sonido2.InsertarSonido("src\\SoundMusic\\interaccion2.wav");
                    try {
                        sonido2.Reproducir();
                    } catch (LineUnavailableException ex) {
                        Logger.getLogger(MenuPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(MenuPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            } else if (mk.PressEnter == true) {
                if (option == 1) {
                    gp.MainMenu = false;
                    sonido.Parar();
                } else if (option == 3) {
                    System.exit(0);
                } else if (option == 2) {
                    howtoplay = true;
                }

            }

            if (option > 3) {
                option = 3;
            }
            if (option < 1) {
                option = 1;
            }

        }

        SpriteCounter--;
        if (SpriteCounter <= 0) {
            SpriteCounter = 0;
        }

    }

}
