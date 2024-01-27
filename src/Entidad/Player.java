package Entidad;

import Main.GamePanel;
import Main.MyKeyboard;
import SoundMusic.Sonido;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Player extends Entidad {

    GamePanel gp;
    MyKeyboard mk;
    boolean caminar;
    public int[][] MapaLocation;
    public int[][] MapaLocation2;
    public int Screenx = 0;
    public int Screeny = 0;
    public int rowPlayer = 0;
    public int colPlayer = 0;
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_WHITE = "\u001B[37m";
    public boolean MoverArea = false;
    public int[][] colisiones;
    public int[][] colisionesCasa;

    public Player() {

    }

    public Player(GamePanel gp, MyKeyboard mk) {

        this.gp = gp;
        this.mk = mk;
        MapaLocation = new int[50][50];
        MapaLocation2 = new int[50][50];
        this.colisiones = new int[gp.MaxWorldRow][gp.MaxWorldCol];
        this.colisionesCasa = new int[16][20];
        Screenx = gp.WIDTH / 2 - (gp.TileSet / 2);
        Screeny = gp.HEIGHT / 2 - (gp.TileSet / 2);
        getImages();
        LoadPlayerMap();
        setPosDefault();
        loadCollitions();
        loadCollitions2();
//        AreaCamara();
    }

    public void loadCollitions() {

        try {
            InputStream is = new FileInputStream("src\\Tile\\CSV\\csv file_Colisiones.csv");
            BufferedReader bf = new BufferedReader(new InputStreamReader(is));

            for (int i = 0; i < gp.MaxWorldRow; i++) {
                String linea = bf.readLine();
                String numeros[] = linea.split(",");
                for (int j = 0; j < gp.MaxWorldCol; j++) {
                    colisiones[i][j] = Integer.parseInt(numeros[j]);
                }
                System.out.println("");
            }

        } catch (FileNotFoundException e) {

        } catch (IOException ex) {
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void loadCollitions2() {

        try {
            InputStream is = new FileInputStream("src\\Tile\\CSV\\CASA CSV\\csv file_colisiones.csv");
            BufferedReader bf = new BufferedReader(new InputStreamReader(is));

            for (int i = 0; i < 16; i++) {
                String linea = bf.readLine();
                String numeros[] = linea.split(",");
                for (int j = 0; j <20; j++) {
                    colisionesCasa[i][j] = Integer.parseInt(numeros[j]);
                }

            }

        } catch (FileNotFoundException e) {

        } catch (IOException ex) {
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void LoadPlayerMap() {

        try {
            InputStream is = new FileInputStream("src\\Tile\\CSV\\csv file_Jugador.csv");
            BufferedReader bf = new BufferedReader(new InputStreamReader(is));

            for (int i = 0; i < 50; i++) {
                String linea = bf.readLine();
                String[] numeros = linea.split(",");
                for (int j = 0; j < 50; j++) {
                    int num = Integer.parseInt(numeros[j]);
//                    MapaLocation[i][j] = num;
                    MapaLocation2[i][j] = num;
                    if (num == 0) {
                        rowPlayer = i;
                        colPlayer = j;
                    }
                }
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void FoundPlayer() {
        for (int i = 0; i < MapaLocation2.length; i++) {
            for (int j = 0; j < MapaLocation2.length; j++) {
                int num = MapaLocation2[i][j];
                if (num == 0) {
                    rowPlayer = i;
                    colPlayer = j;
                }
            }
        }
    }

    public void setPosDefault() {
        worldx = gp.TileSet * 15;
        worldy = gp.TileSet * 10;
//        worldx = gp.TileSet * 25;
//        worldy = gp.TileSet * 25;

        speed = 4;
        name = "Jugador 1";
    }

    public boolean CalculateUp() {

        for (int i = 0; i < 50; i++) {
            for (int j = 0; j < 50; j++) {
                if (colisiones[i][j] == 0) {
                    int y = i * gp.TileSet + (gp.TileSet * 2);
                    int x = j * gp.TileSet + (gp.TileSet * 2);
                    if (worldy <= y && worldy >= y - gp.TileSet) {
                        if (worldx >= x - gp.TileSet && worldx <= x) {
                            for (int k = 0; k < gp.TileSet; k++) {
                                worldy++;
                                if (worldy > y) {
                                    return false;
                                }
                            }

                        }

                    }

                }
            }
        }
        return true;
    }
      public boolean CalculateUp2() {

        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 20; j++) {
                if (colisionesCasa[i][j] == 0) {
                    int y = i * gp.TileSet + (gp.TileSet * 2);
                    int x = j * gp.TileSet + (gp.TileSet * 2);
                    if (worldy <= y && worldy >= y - gp.TileSet) {
                        if (worldx >= x - gp.TileSet && worldx <= x) {
                            for (int k = 0; k < gp.TileSet; k++) {
                                worldy++;
                                if (worldy > y) {
                                    return false;
                                }
                            }

                        }

                    }

                }
            }
        }
        return true;
    }

    public boolean CalculateDown() {

        for (int i = 0; i < 50; i++) {
            for (int j = 0; j < 50; j++) {
                if (colisiones[i][j] == 0) {
                    int y = i * gp.TileSet + (gp.TileSet);
                    int x = j * gp.TileSet + (gp.TileSet * 2);
                    if (worldy >= y - gp.TileSet && worldy <= y) {
                        if (worldx >= x - gp.TileSet && worldx <= x) {
                            for (int k = 0; k < gp.TileSet; k++) {
                                worldy--;
                                if (worldy < y + 1) {
                                    return false;
                                }
                            }

                        }

                    }

                }
            }
        }
        return true;
    }
       public boolean CalculateDown2() {

        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 20; j++) {
                if (colisionesCasa[i][j] == 0) {
                    int y = i * gp.TileSet + (gp.TileSet);
                    int x = j * gp.TileSet + (gp.TileSet * 2);
                    if (worldy >= y - gp.TileSet && worldy <= y) {
                        if (worldx >= x - gp.TileSet && worldx <= x) {
                            for (int k = 0; k < gp.TileSet; k++) {
                                worldy--;
                                if (worldy < y + 1) {
                                    return false;
                                }
                            }

                        }

                    }

                }
            }
        }
        return true;
    }

    public boolean CalculateRight() {

        for (int i = 0; i < 50; i++) {
            for (int j = 0; j < 50; j++) {
                if (colisiones[i][j] == 0) {
                    int y = i * gp.TileSet + (gp.TileSet * 2);
                    int x = j * gp.TileSet + (gp.TileSet);
                    if (worldx <= x && worldx >= x - gp.TileSet) {

                        if (worldy >= y - gp.TileSet && worldy <= y) {
                            for (int k = 0; k < gp.TileSet; k++) {
                                worldx--;
                                if (worldx < x) {
                                    return false;
                                }
                            }

                        }

                    }

                }
            }
        }
        return true;
    }

     public boolean CalculateRight2() {

        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 20; j++) {
                if (colisionesCasa[i][j] == 0) {
                    int y = i * gp.TileSet + (gp.TileSet * 2);
                    int x = j * gp.TileSet + (gp.TileSet);
                    if (worldx <= x && worldx >= x - gp.TileSet) {

                        if (worldy >= y - gp.TileSet && worldy <= y) {
                            for (int k = 0; k < gp.TileSet; k++) {
                                worldx--;
                                if (worldx < x) {
                                    return false;
                                }
                            }

                        }

                    }

                }
            }
        }
        return true;
    }
    
    public boolean CalculateLeft() {

        for (int i = 0; i < 50; i++) {
            for (int j = 0; j < 50; j++) {
                if (colisiones[i][j] == 0) {
                    int y = i * gp.TileSet + (gp.TileSet * 1);
                    int x = j * gp.TileSet + (gp.TileSet);
                    if (worldx >= x && worldx <= x + gp.TileSet) {
                        if (worldy >= y - gp.TileSet && worldy <= y) {
                            for (int k = 0; k < gp.TileSet; k++) {
                                worldx++;
                                if (worldx > x + 1) {
                                    return false;
                                }
                            }

                        }

                    }

                }
            }
        }
        return true;
    }
       public boolean CalculateLeft2() {

        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 20; j++) {
                if (colisionesCasa[i][j] == 0) {
                    int y = i * gp.TileSet + (gp.TileSet * 1);
                    int x = j * gp.TileSet + (gp.TileSet);
                    if (worldx >= x && worldx <= x + gp.TileSet) {
                        if (worldy >= y - gp.TileSet && worldy <= y) {
                            for (int k = 0; k < gp.TileSet; k++) {
                                worldx++;
                                if (worldx > x + 1) {
                                    return false;
                                }
                            }

                        }

                    }

                }
            }
        }
        return true;
    }



    public void update() {
//        FoundPlayer();

        if (mk.PressUp == true) {
      if (gp.Casa == false) {
             if (CalculateUp()) {
                worldy -= speed;
                caminar = true;
            }
           }else if(gp.Casa==true){
               if (CalculateUp2()) {
                worldy -= speed;
                caminar = true;
            }
           }
           
            direccion = "down";
        } else if (mk.PressDown == true) {
              if (gp.Casa == false) {
            if (CalculateDown()) {
                worldy += speed;
                caminar = true;
            }
           }else if(gp.Casa==true){
                  if (CalculateDown2()) {
                worldy += speed;
                caminar = true;
            }
           }
          

            direccion = "up";

        } else if (mk.PressRight == true) {
           if (gp.Casa == false) {
           if (CalculateRight()) {
                worldx += speed;
                caminar = true;
            }
           }else if(gp.Casa==true){
                if (CalculateRight2()) {
                worldx += speed;
                caminar = true;
            }
           }
            
            direccion = "right";

        } else if (mk.PressLeft == true) {

            if (gp.Casa == false) {
                if (CalculateLeft()) {
                    worldx -= speed;
                    caminar = true;
                
                }
            } else if (gp.Casa == true) {
                if (CalculateLeft2()) {
                    worldx -= speed;
                    caminar = true;
                }
            }
            direccion = "left";

        } else {
            caminar = false;
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

//            gp.sonido.AdjustableSound();
    }

    public boolean Colisiones1(String direccion) {

        switch (direccion) {

            case "up":
                return CalculateUp();
            case "down":
                return CalculateDown();
            case "right":
                return CalculateRight();
            case "left":
                return CalculateUp();

        }
        return false;

    }

    public void getImages() {

        try {
            up = ImageIO.read(new FileInputStream("src\\Tile\\Player\\up.png"));
            up2 = ImageIO.read(new FileInputStream("src\\Tile\\Player\\up 2.png"));
            upStop = ImageIO.read(new FileInputStream("src\\Tile\\Player\\up stop.png"));
            down = ImageIO.read(new FileInputStream("src\\Tile\\Player\\down.png"));
            down2 = ImageIO.read(new FileInputStream("src\\Tile\\Player\\down 2.png"));
            downStop = ImageIO.read(new FileInputStream("src\\Tile\\Player\\down stop.png"));
            right = ImageIO.read(new FileInputStream("src\\Tile\\Player\\rigth.png"));
            rightStop = ImageIO.read(new FileInputStream("src\\Tile\\Player\\right stop.png"));
            right2 = ImageIO.read(new FileInputStream("src\\Tile\\Player\\right 2.png"));
            left = ImageIO.read(new FileInputStream("src\\Tile\\Player\\left.png"));
            left2 = ImageIO.read(new FileInputStream("src\\Tile\\Player\\left 2.png"));
            leftStop = ImageIO.read(new FileInputStream("src\\Tile\\Player\\left stop.png"));
            front = ImageIO.read(new FileInputStream("src\\Tile\\Player\\front.png"));

        } catch (IOException ex) {
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void draw(Graphics2D g2) {

        BufferedImage image = front;

        switch (direccion) {
            case "right":
                if (caminar) {
                    if (SpriteNum == 1) {
                        image = right;
                    }
                    if (SpriteNum == 2) {
                        image = right2;
                    }
                } else {
                    image = rightStop;
                }

                break;
            case "left":
                if (caminar) {
                    if (SpriteNum == 1) {
                        image = left;
                    }
                    if (SpriteNum == 2) {
                        image = left2;
                    }
                } else {
                    image = leftStop;
                }

                break;
            case "down":
                if (caminar) {
                    if (SpriteNum == 1) {
                        image = up;
                    }
                    if (SpriteNum == 2) {
                        image = up2;
                    }
                } else {
                    image = upStop;
                }

                break;
            case "up":
                if (caminar) {
                    if (SpriteNum == 1) {
                        image = down;
                    }
                    if (SpriteNum == 2) {
                        image = down2;
                    }
                } else {
                    image = downStop;
                }

                break;
            default:

                image = front;
        }

        g2.drawImage(image, Screenx, Screeny, gp.TileSet, gp.TileSet, null);

    }

}
