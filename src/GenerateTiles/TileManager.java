package GenerateTiles;

import Main.GamePanel;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import Entidad.Player;
import SoundMusic.Sonido;
import javax.sound.sampled.LineUnavailableException;

public class TileManager<T> {

    GamePanel gp;
    Tile[] tile;
    int[][] mapTile;
    int[][] mapTileBase;
    int[][] sobresuelo;
    int[][] suelo;
    int[][] debajoDesuelo;
    int[][] Enmovimiento2;
    int[][] Enmovimiento3;
    public int SpriteCounter = 0;
    public int SpriteNum = 1;
    public Sonido sonido = new Sonido();
    ArrayList<int[][]> CapesWorld = new ArrayList<>();
    BufferedImage[] image;
    BufferedImage[] image2;
    BufferedImage HyruleField;
    BufferedImage Ordon;
    BufferedImage Ciudadela;
    BufferedImage Gerudo;
    String nombres[] = {"csv file_bajo suelo", "csv file_suelo", "csv file_Arriba del suelo", "csv file_En movimiento 2", "csv file_En movimiento 3"};
    String cancion = "";
    String backup = ".";
    boolean reproduciendo = false;
    int access = 1, TiempoQueApareceElCartel = 0;
    boolean gerudo, ordon, ciudadela, pradera;

    public TileManager(GamePanel gp) {

        this.gp = gp;
        this.tile = new Tile[50];
        this.mapTile = new int[gp.maxScreenCol][gp.maxScreenRow];
        this.mapTileBase = new int[gp.maxScreenCol][gp.maxScreenRow];
        this.suelo = new int[gp.MaxWorldRow][gp.MaxWorldCol];
        this.sobresuelo = new int[gp.MaxWorldRow][gp.MaxWorldCol];
        this.debajoDesuelo = new int[gp.MaxWorldRow][gp.MaxWorldCol];
        this.Enmovimiento2 = new int[gp.MaxWorldRow][gp.MaxWorldCol];
        this.Enmovimiento3 = new int[gp.MaxWorldRow][gp.MaxWorldCol];
        this.CapesWorld.add(debajoDesuelo);
        this.CapesWorld.add(suelo);
        this.CapesWorld.add(sobresuelo);
        this.CapesWorld.add(Enmovimiento2);
        this.CapesWorld.add(Enmovimiento3);
        this.image = new BufferedImage[1402];

        getImages();
        loadMap();

//        loadmapBase();
    }

    public void CheckHouse() {
        
        
        if(gp.jugador.worldx<= 13*gp.TileSet+12 && gp.jugador.worldx>=13*gp.TileSet+-12 && gp.jugador.worldy<=gp.TileSet*5+12 && gp.jugador.worldy>= gp.TileSet*5-12 ){
            System.out.println("entrar casa");
            gp.Casa=true; sonido.Cerrar(); 
             
        }
        
    }

    public void CheckSoundArea() {
        if (cancion != backup && access == 0) {
            reproduciendo = false;
            sonido.Parar();
        }
        backup = cancion;
        if (gp.jugador.worldx <= gp.TileSet * 30 && gp.jugador.worldy <= gp.TileSet * 16) {
            cancion = "src\\SoundMusic\\ordon-village-the-legend-of-zelda-twilight-princess.wav";
            access = 0;
            if (reproduciendo == false) {

                OrdonVillage();
                sonido.InsertarSonido(cancion);
                reproduciendo = true;
                try {
                    sonido.Reproducir();
                } catch (LineUnavailableException ex) {
                    Logger.getLogger(TileManager.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(TileManager.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        } else if (gp.jugador.worldx > gp.TileSet * 30 && gp.jugador.worldy <= gp.TileSet * 16) {
            cancion = "src\\SoundMusic\\ciudadela-mercado.wav";
            access = 0;
            if (reproduciendo == false) {
                Ciudadela();
                sonido.InsertarSonido(cancion);

                reproduciendo = true;
                try {
                    sonido.Reproducir();
                } catch (LineUnavailableException ex) {
                    Logger.getLogger(TileManager.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(TileManager.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        } else if (gp.jugador.worldy > gp.TileSet * 16 && gp.jugador.worldy <= gp.TileSet * 36) {
            cancion = "src\\SoundMusic\\hyrule-field.wav";
            access = 0;
            if (reproduciendo == false) {
                Pradera();
                sonido.InsertarSonido(cancion);

                reproduciendo = true;
                try {
                    sonido.Reproducir();
                } catch (LineUnavailableException ex) {
                    Logger.getLogger(TileManager.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(TileManager.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else if (gp.jugador.worldy > gp.TileSet * 40) {
            cancion = "src\\SoundMusic\\gerudo_valley.wav";
            access = 0;
            if (reproduciendo == false) {
                GerudoValley();
                sonido.InsertarSonido(cancion);

                reproduciendo = true;
                try {
                    sonido.Reproducir();
                } catch (LineUnavailableException ex) {
                    Logger.getLogger(TileManager.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(TileManager.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }

    public void OrdonVillage() {
        ordon = true;
        gerudo = false;
        pradera = false;
        ciudadela = false;
        TiempoQueApareceElCartel = 0;
    }

    public void GerudoValley() {
        ordon = false;
        gerudo = true;
        pradera = false;
        ciudadela = false;
        TiempoQueApareceElCartel = 0;
    }

    public void Ciudadela() {
        ordon = false;
        gerudo = false;
        pradera = false;
        ciudadela = true;
        TiempoQueApareceElCartel = 0;
    }

    public void Pradera() {
        ordon = false;
        gerudo = false;
        pradera = true;
        ciudadela = false;
        TiempoQueApareceElCartel = 0;
    }

    public void getImages() {
        ///pasar imagenes a una arreglos

        try {
            for (int i = 0; i < 1402; i++) {
                image[i] = ImageIO.read(new FileInputStream("src\\Tile\\png map\\" + i + ".png"));

            }
            HyruleField = ImageIO.read(new FileInputStream("src\\Tile\\Pradera Hyrule.png"));
            Ordon = ImageIO.read(new FileInputStream("src\\Tile\\ORDON VILLAGE.png"));
            Ciudadela = ImageIO.read(new FileInputStream("src\\Tile\\CIUDADELA.png"));
            Gerudo = ImageIO.read(new FileInputStream("src\\Tile\\GERUDO VALLEY.png"));
        } catch (IOException e) {

        }

    }

    public void loadMap() {

        for (int n = 0; n < nombres.length; n++) {

            try {
                InputStream is = new FileInputStream("src\\Tile\\CSV\\" + nombres[n] + ".csv");
                BufferedReader bf = new BufferedReader(new InputStreamReader(is));

                for (int i = 0; i < 50; i++) {
                    String linea = bf.readLine();
                    String[] numeros = linea.split(",");
                    for (int j = 0; j < 50; j++) {
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

//    public void loadmapBase() {
//        int col = 0;
//        int row = 0;
//
//        try {
//            InputStream is = new FileInputStream("src/GenerateTiles/mapBase.txt");
//            BufferedReader br = new BufferedReader(new InputStreamReader(is));
//
//            while (col < gp.maxScreenCol && row < gp.maxScreenRow) {
//                String linea = br.readLine();
//
//                String[] numeros = linea.split(" ");
//                for (int i = 0; i < numeros.length; i++) {
//                    int num = Integer.parseInt(numeros[i]);
//                    System.out.print(num);
//                    mapTileBase[col][row] = num;
//                    col++;
//                }
//
//                if (col == gp.maxScreenCol) {
//                    col = 0;
//                    row++;
//                    System.out.println("");
//                }
//            }
//            br.close();
//
//        } catch (Exception e) {
//
//        }
//
//    }
    public void draw(Graphics2D g2) {

        int x = 0;
        int y = 0;
//        g2.fillRect(x, y, gp.TileSet, gp.TileSet);
//         System.out.println("x==="+x);
        for (Integer n = 0; n < nombres.length; n++) {

            for (Integer i = 0; i < 50; i++) {
                y += 48;
                for (Integer j = 0; j < 50; j++) {
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
                            for (Integer k = 0; k < 1042; k++) {

                                if (n == 0 && num == k) {
                                    g2.drawImage(image[k], screenx, screeny, gp.TileSet, gp.TileSet, null);
                                    break;
                                }
                                if (n == 1 && num == k) {
                                    g2.drawImage(image[k], screenx, screeny, gp.TileSet, gp.TileSet, null);
                                    break;
                                }
                                if (n == 2 && num == k) {
                                    g2.drawImage(image[k], screenx, screeny, gp.TileSet, gp.TileSet, null);

                                    break;
                                }
                                if (n == 4 && SpriteNum == 1 && k == num) {
                                    g2.drawImage(image[k], screenx, screeny, gp.TileSet, gp.TileSet, null);
                                    break;
                                }

                                if (n == 3 && SpriteNum == 2 && k == num) {
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
                        if (ordon && TiempoQueApareceElCartel <= 300) {
                            g2.drawImage(Ordon, 0, 0, 768, 576, null);

                        } else if (gerudo && TiempoQueApareceElCartel <= 300) {
                            g2.drawImage(Gerudo, 0, 0, 768, 576, null);

                        } else if (ciudadela && TiempoQueApareceElCartel <= 300) {
                            g2.drawImage(Ciudadela, 0, 0, 768, 576, null);

                        } else if (pradera && TiempoQueApareceElCartel <= 300) {
                            g2.drawImage(HyruleField, 0, 0, 768, 576, null);

                        }

                        if (TiempoQueApareceElCartel <= 300) {
                            TiempoQueApareceElCartel++;
                        }
        
        SpriteCounter++;
        if (SpriteCounter > 10) {
            if (SpriteNum == 1) {
                SpriteNum = 2;
            } else if (SpriteNum == 2) {
                SpriteNum = 1;
            }
            SpriteCounter = 0;
        }
        CheckSoundArea();
        CheckHouse();
       
    }

}
