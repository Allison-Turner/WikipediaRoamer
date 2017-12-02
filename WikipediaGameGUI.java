import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class WikipediaGameGUI{
  
  public static void main(String[] args){
    JFrame frame = new JFrame("Grad School Comparison");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    
    
    WikipediaGamePanel newGame = new WikipediaGamePanel(new WikipediaGame());
    
    frame.getContentPane().add(newGame);
    frame.pack();
    frame.setVisible(true);
  }
}