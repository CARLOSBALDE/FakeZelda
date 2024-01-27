package Main;

import java.awt.Graphics;
import javax.swing.*;
public class GameFrame extends JFrame {
    public static void main(String[] args) {
   
       GameFrame obj =  new GameFrame();
       GameFrame.Interna obj2 = obj.new Interna();
       
    }
    

    public  class Interna extends JFrame{
         Interna(){
//        this.setUndecorated(truAAAAAe);
        this.setTitle("FakeZelda");
        this.add(new GamePanel());
        this.setVisible(true);
        this.pack();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
//        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        
    }
    }
}
