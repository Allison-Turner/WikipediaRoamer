import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class WikipediaGamePanel extends JPanel{
  private JTextField startURLInput;
  private JTextField endURLInput;
  private JTextArea pathDisplay;
  private WikipediaGame game;
  
  public WikipediaGamePanel(WikipediaGame g){
    game = g;
    add(userInputPanel(), BorderLayout.CENTER);
    add(descriptionPanel(), BorderLayout.NORTH);
    pathDisplay.setText("Your path will display here after you enter two URLS");
    add(pathDisplay, BorderLayout.SOUTH);
    
  }
  
  private JPanel userInputPanel(){
    JPanel result = new JPanel();
    result.setLayout(new FlowLayout());
    
    startURLInput = new JTextField();
    startURLInput.setSize(200, 10);
    result.add(startURLInput);
    
    JLabel startURLLabel = new JLabel("Starting URL: ");
    result.add(startURLLabel);
    
    endURLInput = new JTextField();
    endURLInput.setSize(200, 10);
    result.add(endURLInput);
    
    JLabel endURLLabel = new JLabel("Ending URL: ");
    result.add(endURLLabel);
    
    JButton getPathButton = new JButton("Get path!");
    getPathButton.addActionListener(new ButtonListener());
    result.add(getPathButton);
    
    return result;
  }
  
  private JPanel descriptionPanel(){
    JPanel result = new JPanel();
    JLabel description = new JLabel("Enter a starting URL and ending URL on Wikipedia and press Get Path!");
    result.add(description);
    return result;
  }
  
  
  private class ButtonListener() implements ActionListener{
    //in event listener: call game.setStartURL() and game.setEndURL() when button pressed
    public void actionPerformed (ActionEvent event) {
      game.setStartURL(startURLInput.getText());
      game.setEndURL(endURLInput.getText());
      game.generatePath();
      pathDisplay.setText(game.toString());
    }
    
  }

}
