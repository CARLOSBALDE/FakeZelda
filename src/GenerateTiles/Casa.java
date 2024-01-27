package GenerateTiles;

import Main.GamePanel;
import SoundMusic.Sonido;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class Casa {

    GamePanel gp;
    int[][] sobresuelo;
    int[][] suelo;
    int[][] debajoDesuelo;
    public int SpriteCounter = 0;
    public int SpriteNum = 1;
    public Sonido sonido = new Sonido();
    ArrayList<int[][]> CapesWorld = new ArrayList<>();
    BufferedImage[] image;
    String nombres[] = {"csv file_casa bajo suelo","csv file_CASA suelo","csv file_casa sobre suelo"};
    String cancion = "";
    String backup = ".";
    boolean reproduciendo = false;
    boolean casa = true,unavez=false;
    BufferedImage Casa;
    int access = 1, TiempoQueApareceElCartel = 0;

    public Casa(GamePanel gp) {

        this.gp = gp;
        this.suelo = new int[16][20];
        this.sobresuelo = new int[16][20];
        this.debajoDesuelo = new int[16][20];
        this.CapesWorld.add(debajoDesuelo);
        this.CapesWorld.add(suelo);
        this.CapesWorld.add(sobresuelo);
        this.image = new BufferedImage[1402];
        getImages();
        loadMap();

//        loadmapBase();
    }

    public void SetPosDefautl() {
        gp.jugador.worldx = 11 * gp.TileSet;
        gp.jugador.worldy = 14 * gp.TileSet;
    }
      public void checkleave(){
       if(gp.jugador.worldx<= 11*gp.TileSet+12 && gp.jugador.worldx>=10*gp.TileSet+-12 && gp.jugador.worldy<=gp.TileSet*15+12 && gp.jugador.worldy>= gp.TileSet*15-12 ){
            System.out.println("salir de casa");
            gp.Casa=false; gp.jugador.worldx=gp.TileSet*13; gp.jugador.worldy=gp.TileSet*9;  sonido.Cerrar();  unavez= false;
             
        }
    }

    public void getImages() {
        ///pasar imagenes a una arreglos

        try {
            for (int i = 0; i < 3; i++) {
                image[i] = ImageIO.read(new FileInputStream("src\\Tile\\CASA png\\" + i + ".png"));
                
            }
            Casa = ImageIO.read(new FileInputStream("src\\Tile\\Pradera Hyrule.png"));

        } catch (IOException e) {

        }

    }

    public void loadMap() {

        for (int n = 0; n < nombres.length; n++) {

            try {
                InputStream is = new FileInputStream("src\\Tile\\CSV\\CASA CSV\\" + nombres[n] + ".csv");
                BufferedReader bf = new BufferedReader(new InputStreamReader(is));

                for (int i = 0; i < 16; i++) {
                    String linea = bf.readLine();
                    String[] numeros = linea.split(",");
                    System.out.println(i);
                    for (int j = 0; j < 20; j++) {
                        CapesWorld.get(n)[i][j] = Integer.parseInt(numeros[j]);
                    }
                }

                bf.close();

            } catch (FileNotFoundException e) {

            } catch (IOException ex) {
                Logger.getLogger(TileManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public void draw(Graphics2D g2) {

        int x = 0;
        int y = 0;
//        g2.fillRect(x, y, gp.TileSet, gp.TileSet);
//         System.out.println("x==="+x);
        for (Integer n = 0; n < nombres.length; n++) {

            for (Integer i = 0; i < 16; i++) {
                y += 48;
                for (Integer j = 0; j < 20; j++) {
                    x += 48;
                    int num = CapesWorld.get(n)[i][j];

                    if (num != -1) {

                        int screeny = y - gp.jugador.worldy + gp.jugador.Screeny;
                        int screenx = x - gp.jugador.worldx + gp.jugador.Screenx;

                        if (x + gp.TileSet > gp.jugador.worldx - gp.jugador.Screenx
                                && x - gp.TileSet < gp.jugador.worldx + gp.jugador.Screenx
                                && y + gp.TileSet > gp.jugador.worldy - gp.jugador.Screeny
                                && y - gp.TileSet < gp.jugador.worldy + gp.jugador.Screeny) {

//                            System.out.println(gp.jugador.worldy);
                            for (Integer k = 0; k < 3; k++) {

                                if (k == num) {
                                    g2.drawImage(image[k], screenx, screeny, gp.TileSet, gp.TileSet, null);
                                    break;

                                }

                            }
                        }

                    }

                }
                x = 0;
            }
            y = 0;
            x = 0;

        }

        //Dibujar carteles de localizacion actual
//        if (casa && TiempoQueApareceElCartel <= 300) {
//            g2.drawImage(Casa, 0, 0, 768, 576, null);
//        }
//
//        if (TiempoQueApareceElCartel <= 300) {
//            TiempoQueApareceElCartel++;
//        }

//        SpriteCounter++;
//        if (SpriteCounter > 10) {
//            if (SpriteNum == 1) {
//                SpriteNum = 2;
//            } else if (SpriteNum == 2) {
//                SpriteNum = 1;
//            }
//            SpriteCounter = 0;
//        }
//        CheckSoundArea();
if(gp.Casa==true && unavez==false){
    SetPosDefautl();
    unavez=true;
}

checkleave();
    }
    
  

}
