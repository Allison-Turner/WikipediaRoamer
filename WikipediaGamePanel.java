/* Public class WikipediaGamePanel.java
 * Creates a GUI for WikipediaGame.java
 * 
 * 
 */


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class WikipediaGamePanel extends JPanel{
  private JTextField startURLInput;
  private JTextField endURLInput;
  private JLabel pathDisplay;
  private WikipediaGame game;
  
  /* Constructor creates and adds two panels and a jlabel to this object
   * */
  public WikipediaGamePanel(WikipediaGame g){
    this.game = g;
    setLayout(new BorderLayout(10, 10));
    setBackground(Color.blue);
    add(userInputPanel(), BorderLayout.CENTER);
    add(descriptionPanel(), BorderLayout.NORTH);
    pathDisplay = new JLabel();
    add(makePathDisplay(), BorderLayout.SOUTH);
  }
  
  private JPanel makePathDisplay(){
    JPanel result = new JPanel();
    result.setLayout(new FlowLayout());
    pathDisplay.setText("Your path will display here after you enter two URLS");
    result.add(pathDisplay);
    return result;
  }
  
  /*Helper method creates the user input panel
   * 
   * */
  private JPanel userInputPanel(){
    JPanel result = new JPanel();
    result.setLayout(new FlowLayout());
    
    JLabel startURLLabel = new JLabel("Starting URL: ");
    result.add(startURLLabel);
    
    startURLInput = new JTextField();
    startURLInput.setPreferredSize(new Dimension(100, 20));
    result.add(startURLInput);
    
    JLabel endURLLabel = new JLabel("Ending URL: ");
    result.add(endURLLabel);
    
    endURLInput = new JTextField();
    endURLInput.setPreferredSize(new Dimension(100, 20));
    result.add(endURLInput);
    
    JButton getPathButton = new JButton("Get path!");
    getPathButton.addActionListener(new ButtonListener());
    result.add(getPathButton);
    
    return result;
  }
  
  
  /*Helper method creates a description panel, which gives the user directions
   * */
  private JPanel descriptionPanel(){
    JPanel result = new JPanel();
    JLabel description = new JLabel("Enter a starting URL and ending URL on Wikipedia and press Get Path!");
    result.add(description);
    return result;
  }
  
  /*private JTextArea resultPanel(){
    JPanel result = new JPanel();
    JLabel path = new JLabel(game.toString());
    result.add(path);
    return result;
  }*/
  
  /*Private internal class specifies what to do when the button is pressed.
   * Calls game.generatePath(), i.e. when all the behind the scenes stuff happens
   * */
  private class ButtonListener implements ActionListener{
    public void actionPerformed (ActionEvent event) {
      game.setStartURL(startURLInput.getText());
      game.setEndURL(endURLInput.getText());
      game.generatePath();
      pathDisplay.setText(game.toString());
    }
    
  }

}