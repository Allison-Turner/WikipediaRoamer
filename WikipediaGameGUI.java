/*@author Ellie Czepiel, Sammy Lincroft, and Allison Turner
 * Last Modified: 14 December 2017
 * Purpose: This class creates a visually pleasing user interface with which to play the Wikipedia Game. It uses several different JPanels and switches between these using CardLayout
 */
//Importing all necessary libraties
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.*;
import java.net.*;

public class WikipediaGameGUI{
  //Initializing all class variables. Some of these class variables usually are stored in separate JPanel child classes, but because the CardLayout requires access to the event listeners of certain buttons, the panels have to be created in this
  //class and each panel's elements must be accessible to multiple methods
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
  private static Component results;
  private static final Color backgroundColor = new Color(155, 130, 189);
  private static final Color textColor = new Color(240, 240, 240);
  private static final Font headerFont = new Font("Sans Serif", Font.BOLD, 48);
  private static final Font textFont = new Font("Sans Serif", Font.BOLD, 18);
  
  /*
   *Builds the JFrame and all JPanels that will be displayed in the JFrame, then makes it visible 
   */
  public static void main(String[] args){
    //Creating the new window
    JFrame frame = new JFrame("Wikipedia Game");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    //Creating the overarching JPanel to contain the CardLayout, the layout itself, and instantiating a new Wikipedia game
    cards = new CardLayout();
    innerFrame = new JPanel(cards);
    newGame = new WikipediaGame();
    
    //Create a new menu panel and add it to the list of panels that CardLayout knows about
    Component menu = makeMenuPanel();
    cards.addLayoutComponent(menu, "Menu Panel");
    
    //Create a new instruction panel and add it to the list of panels that CardLayout knows about
    Component instructions = makeInstructionPanel();
    cards.addLayoutComponent(instructions, "Instruction Panel");
    
    //Create a new interaction panel and add it to the list of panels that CardLayout knows about
    Component interactions = makeInteractionPanel();
    cards.addLayoutComponent(interactions, "Interaction Panel");
    
    //Create a new result panel and add it to the list of panels that CardLayout knows about
    results = makeResultPanel();
    cards.addLayoutComponent(results, "Results Panel");
    
    //Making sure the overarching JPanel knows about the JPanels that CardLayout will be switching between
    innerFrame.add(menu);
    innerFrame.add(instructions);
    innerFrame.add(interactions);
    innerFrame.add(results);
    
    //Finish up the JFrame and then make it visible and usable
    Container c = frame.getContentPane();
    Dimension d = new Dimension(1000,800);
    c.setPreferredSize(d);
    c.add(innerFrame);
    frame.pack();
    frame.setPreferredSize(frame.getPreferredSize());
    frame.setVisible(true);
  }
  
  /*Private method to create the main menu JPanel
   *@return the completed menu JPanel 
   */
  private static Component makeMenuPanel(){
    //Create an empty JPanel and set the layout style to FlowLayout and make it blue
    JPanel result = new JPanel(new BorderLayout());
    result.setBackground(backgroundColor);
    
    JPanel title = new JPanel(new GridLayout(3, 1));
    
    JLabel name = new JLabel();
    name.setText("The Wikipedia Game");
    name.setForeground(textColor);
    name.setFont(headerFont);
    name.setHorizontalAlignment(SwingConstants.CENTER);
    title.add(name);
    JLabel authors = new JLabel();
    authors.setText("Authors: Sammy Lincroft, Allison Turner, Ellie Czepiel");
    authors.setForeground(textColor);
    authors.setFont(textFont);
    authors.setHorizontalAlignment(SwingConstants.CENTER);
    title.add(authors);
    title.setBackground(backgroundColor);
    result.add(title, BorderLayout.PAGE_START);
    JPanel imagePanel = new JPanel(new FlowLayout());
    imagePanel.setBackground(backgroundColor);
    
    try{
      BufferedImage image = ImageIO.read(new URL("https://upload.wikimedia.org/wikipedia/foundation/thumb/7/70/Wikipedia-puzzleglobe-V2.svg/1200px-Wikipedia-puzzleglobe-V2.svg.png"));
      JLabel imageLabel = new JLabel(new ImageIcon(image));
      imagePanel.add(imageLabel);
    }
    catch (Exception e){
      System.out.println(e);
    }
    
    JPanel result2 = new JPanel(new FlowLayout());
    
    //Create the button to switch to the instruction JPanel and create its event listener
    seeInstructions = new JButton("See Instructions");
    ButtonListener listener = new ButtonListener();
    seeInstructions.addActionListener(listener); 
    result2.add(seeInstructions);
    title.add(result2);
    
    //Create the button to switch to the game play JPanel and create its event listener
    playGame = new JButton("Play Game");
    ButtonListener listener2 = new ButtonListener();
    playGame.addActionListener(listener2);
    result2.add(playGame);
    result2.setBackground(backgroundColor);
    
    
    result.add(imagePanel, BorderLayout.CENTER);
    
    //Return the menu JPanel
    return result;
  }
  
  /*Private method to create the instruction JPanel
   * @return the completed instruction JPanel
   */
  private static Component makeInstructionPanel(){
    //Create an empty JPanel
    JPanel result = new JPanel(new GridLayout(6, 1));
    result.setBackground(backgroundColor);
    
    //Adding the section to explain what the game is
    JLabel whatIsGameLabel = new JLabel("What Is The Wikipedia Game?");
    whatIsGameLabel.setForeground(textColor);
    whatIsGameLabel.setFont(headerFont);
    whatIsGameLabel.setHorizontalAlignment(SwingConstants.CENTER);
    JTextArea whatIsGameText = new JTextArea(whatIsGameText());
    whatIsGameText.setLineWrap(true);
    whatIsGameText.setForeground(textColor);
    whatIsGameText.setBackground(backgroundColor);
    whatIsGameText.setFont(textFont);
    whatIsGameText.setEditable(false);
    result.add(whatIsGameLabel);
    result.add(whatIsGameText);
    
    //Adding the section to explain how to play
    JLabel instructionLabel = new JLabel("How to Play");
    instructionLabel.setForeground(textColor);
    instructionLabel.setFont(headerFont);
    instructionLabel.setHorizontalAlignment(SwingConstants.CENTER);
    JTextArea instructionText = new JTextArea(instructionText());
    instructionText.setLineWrap(true);
    instructionText.setForeground(textColor);
    instructionText.setBackground(backgroundColor);
    instructionText.setFont(textFont);
    instructionText.setEditable(false);
    result.add(instructionLabel);
    result.add(instructionText);
    
    //Add the button that will return the user to the menu panel and create its event listener
    seeMainMenu = new JButton("Back to Main Menu");
    seeMainMenu.addActionListener(new ButtonListener());
    JPanel result2 = new JPanel(new FlowLayout());
    result2.add(seeMainMenu);
    result2.setBackground(backgroundColor);
    result.add(result2);
    
    
    //Return the instruction JPanel
    return result;
  }
  
  /*Creates the string required by the whatIsGameText, because this text is rather long and would crowd the JPanel creation method
   *@return the text required to explain what the game is 
   */
  private static String whatIsGameText(){
    return ("This is a game that sprang from the sheer interconnectedness of Wikipedia. Pages link to dozens of other pages and are in turn linked to from dozens of other pages. " +
              "You are given a start page title and an end page title, and must navigate from the former to the latter by only clicking links on that page and the subsequent pages. The fewer clicks, the better.");
  }
  
  /*Creates the string required by the instructionText, because this text is rather long and would crowd the JPanel creation method
   *@return the text required to explain the instructions
   */
  private static String instructionText(){
    return ("Enter the start page's title and the end page's title into their respective text fields. Click generate path, and the computer will begin to navigate through Wikipedia to find a path from one" +
              "to the other. You can try to beat the computer, either in time-to-completion or in number of pages clicked, but really, we don't think that's likely.");
  }
  
  /*Private method to create the JPanel where the user plays the game
   * @return the interaction panel
   */
  private static Component makeInteractionPanel(){
    //Create an empty JPanel and set layout manager to BorderLayout
    JPanel result = new JPanel();
    result.setLayout(new GridLayout(2, 1));
    result.setBackground(backgroundColor);
    
    JPanel interactionPanel = new JPanel(new FlowLayout());
    interactionPanel.setBackground(backgroundColor);
    
    //Add subpanels containing elements for gameplay and for description
    interactionPanel.add(descriptionPanel());
    interactionPanel.add(userInputPanel());
    result.add(interactionPanel);
    
    JPanel imagePanel = new JPanel(new FlowLayout());
    imagePanel.setBackground(backgroundColor);
    
    try{
      BufferedImage image = ImageIO.read(new URL("https://upload.wikimedia.org/wikipedia/foundation/thumb/7/70/Wikipedia-puzzleglobe-V2.svg/1200px-Wikipedia-puzzleglobe-V2.svg.png"));
      JLabel imageLabel = new JLabel(new ImageIcon(image));
      imagePanel.add(imageLabel);
    }
    catch (Exception e){
      System.out.println(e);
    }
    
    result.add(imagePanel);
    //Return interactions panel
    return result;
  }
  
  
  /*Helper method creates the user input panel
   * @return JPanel containg textfields, labels, and a button 
   */
  private static JPanel userInputPanel(){
    //Create new JPanel and set to flow layout
    JPanel result = new JPanel();
    result.setLayout(new GridLayout(3, 2));
    result.setBackground(backgroundColor);
    
    //Start URL label and field added
    JLabel startURLLabel = new JLabel("Starting Title: ");
    startURLLabel.setForeground(textColor);
    startURLLabel.setFont(textFont);
    result.add(startURLLabel);
    
    startURLInput = new JTextField();
    startURLInput.setPreferredSize(new Dimension(200, 20));
    result.add(startURLInput);
    
    //End URL label and field added
    JLabel endURLLabel = new JLabel("Ending Title: ");
    endURLLabel.setForeground(textColor);
    endURLLabel.setFont(textFont);
    result.add(endURLLabel);
    
    endURLInput = new JTextField();
    endURLInput.setPreferredSize(new Dimension(200, 20));
    result.add(endURLInput);
    
    //empty JLabel makes the button align on the bottom right instead of the bottom left
    result.add(new JLabel(""));
    //Button to generate link path created, event listener created, button added
    getPathButton = new JButton("Get Path!");
    getPathButton.addActionListener(new ButtonListener()); 
    result.add(getPathButton);
    
    //Jpanel returned
    return result;
  }
  
  
  /*Helper method creates a description panel, which gives the user directions
   * */
  private static JPanel descriptionPanel(){
    JPanel result = new JPanel();
    result.setBackground(backgroundColor);
    JLabel description = new JLabel("Enter the titles of a starting page and an ending page and press Get Path!");
    description.setForeground(textColor);
    description.setFont(new Font("Sans Serif", Font.BOLD, 22));
    description.setHorizontalAlignment(SwingConstants.CENTER);
    result.add(description);
    return result;
  }
  
  /*Private method to create the panel to display the title of and first image on each page in the path
   * @return the result JPanel
   */
  private static Component makeResultPanel(){
    //Create new JPanel
    JPanel result = new JPanel();
    result.setBackground(backgroundColor);
      LinkedList<Page> path = newGame.getPath();
      result.setLayout(new BorderLayout());
      JPanel errorContainer = new JPanel(new FlowLayout());
      errorContainer.setBackground(backgroundColor);
      JLabel error = new JLabel("Sorry we could not find your path. *sad*");
      error.setForeground(textColor);
      error.setFont(textFont);
      error.setHorizontalAlignment(SwingConstants.CENTER);
      errorContainer.add(error);
      
      JLabel errPicLabel = new JLabel();
      try{
        BufferedImage errorImage = ImageIO.read(new URL("http://protechitjobs.com/wp-content/uploads/2015/12/error-404-road-not-found.jpg"));
        errPicLabel = new JLabel(new ImageIcon(errorImage));
        errorContainer.add(errPicLabel);
      }catch(IOException ex){
        System.out.println(ex);
      }
      
      JPanel result2 = new JPanel(new FlowLayout());
      result2.setBackground(backgroundColor);
      //Generate an itemPanel for each page in the path
      while(path != null && !path.isEmpty()){
        JPanel item = itemPanel(path.remove());
        error.setText("Here's the path we found between your pages (reads left to right):");
        errPicLabel.setVisible(false);
        result2.add(item);
      }
      result2.setPreferredSize(result2.getPreferredSize());
      
      //display a representation in words of the process the program took to find this path:
      JPanel centerPanel = new JPanel(new GridLayout(2, 1));
      JTextArea description = new JTextArea();
      description.setLineWrap(true);
      description.setForeground(textColor);
      description.setBackground(backgroundColor);
      description.setFont(textFont);
      description.setEditable(false);
      description.setText(newGame.getProcess());
      centerPanel.setBackground(backgroundColor);
      centerPanel.add(result2);
      centerPanel.add(description);
      
      
      
    //Add a play again button to take you back to the interactions panel
    playAgainButton = new JButton("Play Again");
    playAgainButton.addActionListener(new ButtonListener());
    JPanel result3 = new JPanel(new FlowLayout());
    result3.add(playAgainButton);
    result3.setBackground(backgroundColor);
    result.add(result3, BorderLayout.PAGE_END);
    result.add(centerPanel, BorderLayout.CENTER);
    result.add(errorContainer, BorderLayout.PAGE_START);
    
    //Return the result JPanel
    return result;
  }
  
  /* helper method creates and returns a panel consisting of an image and the title of a given wikipedia page
   * @param p the given wikipedia page
   * @return the JPanel containing the image and the title
   */
   private static JPanel itemPanel(Page p){
    System.out.println("making an item panel out of " + p);
    JPanel myItemPanel = new JPanel();
    myItemPanel.setBackground(backgroundColor);
    myItemPanel.setLayout(new FlowLayout());
    try{
      BufferedImage myPicture = ImageIO.read(new URL(p.getImageURL()));
      JLabel picLabel = new JLabel(new ImageIcon(myPicture));
      myItemPanel.add(picLabel);
    }catch(IOException ex){
      try{
        BufferedImage myPicture = ImageIO.read(new URL("https://a1.memecaptain.com/src_thumbs/85584.jpg"));
        JLabel picLabel = new JLabel(new ImageIcon(myPicture));
        myItemPanel.add(picLabel);
      }catch(IOException ex2){
        System.out.println(ex2);
      }
    }
     myItemPanel.add(new JLabel(p.toString().replace("+"," ")));
    myItemPanel.setPreferredSize(new Dimension(150, 150));
    myItemPanel.addMouseListener(new ItemPanelListener(p.getURL()));
    return myItemPanel;
  }
  
  private static class ItemPanelListener extends MouseAdapter implements MouseListener{
    private String URL;
    public ItemPanelListener(String url){
      super();
      URL = url;
    }
    
    public void mouseClicked(MouseEvent event){
      try{
        java.awt.Desktop.getDesktop().browse(new java.net.URI(URL));
      }catch(IOException ex){
        System.out.println(ex);
      }catch(URISyntaxException ex2){
        System.out.println(ex2);
      }
    }
  }
  
   /*Private internal class specifies actions for all the buttons in the GUI
    */
  private static class ButtonListener implements ActionListener{
    public void actionPerformed (ActionEvent event) {
      
      if (event.getSource() == seeInstructions){
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
        results = makeResultPanel();
        cards.addLayoutComponent(results, "Results Panel");
        innerFrame.add(results);
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
