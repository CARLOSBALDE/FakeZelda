package Main;

import Entidad.Player;
import javax.swing.*;
import java.awt.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import Entidad.Player;
import GenerateTiles.Casa;
import GenerateTiles.MenuPrincipal;
import GenerateTiles.TileManager;
import GenerateTiles.TileManager;
import SoundMusic.Sonido;

public class GamePanel extends JPanel implements Runnable {

    //dimensions 
    int FPS = 60;
    final int OriginalTileSet = 16;
    final int scale = 3;
    public final int TileSet = OriginalTileSet * scale;
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int WIDTH = TileSet * maxScreenCol;
    public final int HEIGHT = TileSet * maxScreenRow;
   
    //Clases u objetos
    Thread GameThread;
    MyKeyboard teclado = new MyKeyboard();
    public Player jugador = new Player(this, teclado);
    TileManager tilemanager = new TileManager(this);
    MenuPrincipal mainmenu = new MenuPrincipal(this,teclado);
    public Sonido sonido = new Sonido(this);
    Casa casa = new Casa(this);

    //WorldMap
    public final int MaxWorldCol = 50;
    public final int MaxWorldRow = 50;
    public final int WorldWidth = TileSet * maxScreenCol;
    public final int WorldHeight = TileSet * maxScreenRow;

    ///Utilidades
    public boolean MainMenu = true;
    public boolean Casa = false;

    GamePanel() {
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.addKeyListener(teclado);
        this.setFocusable(true);
      StartGameThread();
    }

    public void StartGameThread() {
        GameThread = new Thread(this);
        GameThread.start();
    }

    @Override
    public void run() {

        double drawInterval = 1000000000 / FPS;  ///0.01666 segundos 
        double nextDrawtime = System.nanoTime() + drawInterval;
        
        while (GameThread != null) {

            update();

            repaint();
            System.out.println("Imprimiendo");
            try {
                double remainingTime = nextDrawtime - System.nanoTime();
                remainingTime = remainingTime / 1000000;

                if (remainingTime < 0) {
                    remainingTime = 0;
                }

                GameThread.sleep((long) remainingTime);

                nextDrawtime += drawInterval;

            } catch (InterruptedException ex) {
                Logger.getLogger(GamePanel.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        if (MainMenu == true) {
        mainmenu.draw(g2);
        } else if(MainMenu==false && Casa==false) {
            tilemanager.draw(g2);
            jugador.draw(g2);
        }else if (MainMenu==false && Casa==true){
            casa.draw(g2);
            jugador.draw(g2);
        }

        g2.dispose();

    }

    public void update() {
        if (MainMenu == true) {
           mainmenu.update();
        } else if(MainMenu==false && Casa==false) {
            jugador.update();
        }else if (MainMenu==false && Casa==true){
            jugador.update();
        }

    }
}
