
/* Public class ResultPanel.java
 * 
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class ResultPanel extends JPanel{
  private WikipediaGame game;
  
  public ResultPanel(WikipediaGame g){
    this.game = g;
    setLayout(new FlowLayout());
    setBackground(Color.blue);
    LinkedList<Page> path = game.getPath();
    while(path != null){
      this.add(itemPanel(path.remove()));
    }
    JButton playAgainButton = new JButton();
    this.add(playAgainButton);
  }
  
  private JPanel itemPanel(Page p){
    JPanel myItemPanel = new JPanel();
    try{
      BufferedImage myPicture = ImageIO.read(new File(p.getImageURL()));
      JLabel picLabel = new JLabel(new ImageIcon(myPicture));
      myItemPanel.add(picLabel);
    }catch(IOException ex){
      System.out.println(ex);
    }
    myItemPanel.add(new JLabel(p.toString()));
    return myItemPanel;
  }
  
  
  private class ButtonListener implements ActionListener{
    public void actionPerformed (ActionEvent event) {
      
    }
    
  }

}