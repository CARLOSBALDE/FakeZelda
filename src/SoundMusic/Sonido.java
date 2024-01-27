package SoundMusic;

import Main.GamePanel;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sonido {

    //Globales
    File f = new File("src\\SoundMusic\\cascada.wav");
    AudioInputStream audioStream ;
    Clip clip;
    String ruta = "";
    public int[][] audios;
    public String[] audiosRutas = {"src\\SoundMusic\\cascada.wav"};
    GamePanel gp;
    int row = 0;
    int col = 0;
    int cont = 0;
    public boolean ejecutando = false;
    public Sonido (){
        
    }
    
    public Sonido(GamePanel gp) {
        audios = new int[50][50];
        this.gp = gp;
        try {
            audioStream = AudioSystem.getAudioInputStream(f);
             clip = AudioSystem.getClip();
//        loadSounds();
        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(Sonido.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Sonido.class.getName()).log(Level.SEVERE, null, ex);
        } catch (LineUnavailableException ex) {
            Logger.getLogger(Sonido.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void loadSounds() {

        try {
            InputStream is = new FileInputStream("src\\Tile\\CSV\\csv file_Sounds.csv");
            BufferedReader bf = new BufferedReader(new InputStreamReader(is));

            for (int i = 0; i < 50; i++) {
                String linea = bf.readLine();
                String[] numeros = linea.split(",");
                for (int j = 0; j < 50; j++) {
                    if (Integer.parseInt(numeros[j]) == 0) {
                        audios[i][j] = 0;
                        row = i;
                        col = j;
                        break;
                    }
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Sonido.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Sonido.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void Reproducir() throws LineUnavailableException, IOException {

        clip.open(audioStream);
        clip.start();
        ejecutando = true;
    }
   

    public void Parar() {
        clip.stop();
        ejecutando = false;
    }
    public void Cerrar(){
        clip.close();
    }
    
    public boolean EstaReproduciendose(){
        return clip.isActive();
    }

    public void Reinciar() {
        clip.stop(); 
        clip.start();
        ejecutando = true;
    }

    public void InsertarSonido(String sonido) {
        this.ruta = sonido;

        f = new File(this.ruta);
        try {
            audioStream = AudioSystem.getAudioInputStream(f);
            clip = AudioSystem.getClip();
        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(Sonido.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Sonido.class.getName()).log(Level.SEVERE, null, ex);
        } catch (LineUnavailableException ex) {
            Logger.getLogger(Sonido.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void AdjustableSound() {
        int limite = 10,distanciaDelSonido=row; 
        if (gp.jugador.worldy <= (row + limite) * gp.TileSet) {
                  for (int i = 0; i < (row + limite); i++) {
                       distanciaDelSonido+=gp.TileSet;  
                      if(gp.jugador.worldy <=distanciaDelSonido ){
                         break; 
                      }
                      
            }
                  
            try {
                if (cont == 0) {
                    Reproducir(); cont++;
                }   
                if(cont==1){
                  float vol = (float) distanciaDelSonido/1000;
                
                    vol = (float) 1.0f - vol;
                    
                    DecimalFormat df = new DecimalFormat(".0");
                    vol =  Float.parseFloat(df.format(vol)) ;
                    CheckVolumen(vol);
                }
            } catch (LineUnavailableException ex) {
                Logger.getLogger(Sonido.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Sonido.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            Parar(); cont =0;
            System.out.println("parar");
        }

    }
    
    public void CheckVolumen(float volumen){
        FloatControl ganancia  = (FloatControl)   clip.getControl(FloatControl.Type.MASTER_GAIN);
       ganancia.setValue(20f*(float)Math.log10(volumen));
        Reinciar();
    }
}
