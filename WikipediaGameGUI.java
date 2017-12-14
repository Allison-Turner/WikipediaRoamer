import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.*;


public class WikipediaGameGUI{
  private static CardLayout cards;
  private static JPanel innerFrame;
  private static JButton seeInstructions;
  private static JButton seeMainMenu;
  private static JButton playGame;
  private static JButton getPathButton;
  private static JButton playAgainButton;
  private static JTextField startURLInput;
  private static JTextField endURLInput;
  private static WikipediaGame newGame;
  
  
  
  public static void main(String[] args){
    JFrame frame = new JFrame("Wikipedia Game");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    
    cards = new CardLayout();
    innerFrame = new JPanel(cards);
    newGame = new WikipediaGame();
    
    Component menu = makeMenuPanel();
    cards.addLayoutComponent(menu, "Menu Panel");
    Component instructions = makeInstructionPanel();
    cards.addLayoutComponent(instructions, "Instruction Panel");
    Component interactions = makeInteractionPanel();
    cards.addLayoutComponent(interactions, "Interaction Panel");
    Component results = makeResultPanel();
    cards.addLayoutComponent(results, "Results Panel");
    innerFrame.add(menu);
    innerFrame.add(instructions);
    innerFrame.add(interactions);
    innerFrame.add(results);
    //cards.first(innerFrame);
    
    
    //WikipediaGamePanel newGame = new WikipediaGamePanel(new WikipediaGame());
    
    frame.getContentPane().add(innerFrame);
    frame.pack();
    frame.setPreferredSize(frame.getPreferredSize());
    frame.setVisible(true);
  }
  
  private static Component makeMenuPanel(){
    JPanel result = new JPanel(new FlowLayout());
    //setLayout(new FlowLayout());
    result.setBackground(Color.blue);
    
    seeInstructions = new JButton("See Instructions");
    ButtonListener listener = new ButtonListener();
    seeInstructions.addActionListener(listener); 
    result.add(seeInstructions);
    
    playGame = new JButton("Play Game");
    ButtonListener listener2 = new ButtonListener();
    playGame.addActionListener(listener2);
    result.add(playGame);
    return result;
  }
  
  private static Component makeInstructionPanel(){
    JPanel result = new JPanel();
    JLabel whatIsGameLabel = new JLabel("What Is The Wikipedia Game?");
    JTextArea whatIsGameText = new JTextArea(whatIsGameText());
    result.add(whatIsGameLabel);
    result.add(whatIsGameText);
    
    JLabel instructionLabel = new JLabel("How to Play");
    JTextArea instructionText = new JTextArea(instructionText());
    result.add(instructionLabel);
    result.add(instructionText);
    
    seeMainMenu = new JButton("Back to Main Menu");
    seeMainMenu.addActionListener(new ButtonListener());
    result.add(seeMainMenu);
    return result;
  }
  
  private static String whatIsGameText(){
    return ("This is a game that sprang from the sheer interconnectedness of Wikipedia. Pages link to dozens of other pages and are in turn linked to from dozens of other pages. " +
              "You are given a start page URL and an end page URL, and must navigate from the former to the latter by only clicking links on that page and the subsequent pages. The fewer clicks, the better.");
  }
  
  private static String instructionText(){
    return ("Enter the start URL and the end URL into their respective text fields. Click generate path, and the computer will begin to navigate through Wikipedia to find a path from one" +
              "to the other. You can try to beat the computer, eitherin time-to-completion or in number of pages clicked, but really, we don't think that's likely.");
  }
  
  private static Component makeInteractionPanel(){
    JPanel result = new JPanel();
    result.setLayout(new BorderLayout(10, 10));
    result.setBackground(Color.blue);
    result.add(userInputPanel(), BorderLayout.CENTER);
    result.add(descriptionPanel(), BorderLayout.NORTH);
    return result;
  }
  
  
  /*Helper method creates the user input panel
   * 
   * */
  private static JPanel userInputPanel(){
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
    
    getPathButton = new JButton("Get Path!");
    getPathButton.addActionListener(new ButtonListener()); 
    result.add(getPathButton);
    
    return result;
  }
  
  
  /*Helper method creates a description panel, which gives the user directions
   * */
  private static JPanel descriptionPanel(){
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
  
  private static Component makeResultPanel(){
    JPanel result = new JPanel();
    result.setLayout(new FlowLayout());
    result.setBackground(Color.blue);
    
    LinkedList<Page> path = newGame.getPath();
    while(path != null){
      result.add(itemPanel(path.remove()));
    }
    
    playAgainButton = new JButton("Play Again");
    playAgainButton.addActionListener(new ButtonListener());
    result.add(playAgainButton);
    return result;
  }
  
   private static JPanel itemPanel(Page p){
    JPanel myItemPanel = new JPanel();
    myItemPanel.setLayout(new FlowLayout());
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
  
  private static class ButtonListener implements ActionListener{
    public void actionPerformed (ActionEvent event) {
      
      if (event.getSource() == seeInstructions){
        System.out.println("See instructions clicked!!!");
        cards.show(innerFrame, "Instruction Panel");
      }
      
      else if (event.getSource() == playGame){
        cards.show(innerFrame, "Interaction Panel");
      }
      
      else if(event.getSource() == seeMainMenu){
        cards.show(innerFrame, "Menu Panel");
      }
      
      else if(event.getSource() == getPathButton){
        newGame.setEndTerm(endURLInput.getText());
        newGame.setStartTerm(startURLInput.getText());
        newGame.generatePath();
        System.out.println(newGame);
        cards.show(innerFrame, "Results Panel");
      }
      
      else if(event.getSource() == playAgainButton){
        newGame = new WikipediaGame();
        startURLInput.setText("");
        endURLInput.setText("");
        cards.show(innerFrame, "Interaction Panel");
      }
    }
    
  }
}
