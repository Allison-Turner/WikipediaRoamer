import java.util.ArrayList;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;
import java.io.IOException;

public class ForwardPage extends Page{
  private ArrayList<ForwardPage> children;
  private ForwardPage parent;
  
  public ForwardPage(String url, String title, ForwardPage parent){
    super(url, title);
    children = new ArrayList<ForwardPage>();
    this.parent = parent;
  }
  
  public ForwardPage(String url, String title){
    super(url, title);
    children = new ArrayList<ForwardPage>();
    this.parent = null;
  }
  
  public ArrayList<ForwardPage> getChildren(){
    return children;
  }
  
  public void retrieveFamily(){
    String commandURL = "https://www.mediawiki.org/w/api.php?action=query&prop=links&titles=" + title + "&format=json";
    try{
        InputStream source = new URL(commandURL).openStream();
        Scanner scan = new Scanner(source).useDelimiter("\"title\":");
        scan.next(); //skip over batch information
        while(scan.hasNext()){ 
          String child = scan.next().split("\"")[1];
          if(child.indexOf(":") == -1){

            String[] wordsInTitle = child.split(" ");
            String title = "";
            for(int i = 0; i < wordsInTitle.length - 1; i++){
              title += wordsInTitle + "_";
            }
            title += wordsInTitle[wordsInTitle.length - 1];
              
            children.add(new ForwardPage(("https://en.wikipedia.org/wiki/" + title), child, this));
          }
        }
      }
      catch(IOException ex){
        System.out.println(ex);
      }
    }
  
  public void setParent(ForwardPage parent){
    this.parent = parent;
  }
  
  public ForwardPage getParent(){
    return parent;
  }
  
  public String getParentPath(ForwardPage fPage){
    String result = fPage.getURL();
    
    if(parent == null){
      return result;
    }
    
    else{
      return (result + "\n" + getParentPath(fPage.getParent()));
    }
  }
  
  public boolean familySharesMember(BackwardPage bPage){
    ArrayList<BackwardPage> parents = bPage.getParents();
    boolean shared = false;
    
    for(int i = 0; i < parents.size(); i++){
      for(int j = 0; j < children.size(); j++){
        if(parents.get(i).equals(children.get(j))){
          shared = true;
        }
      }
    }
    return shared;
  }
  
  public String toString(){
    String result = "This Page's URL: " + this.getURL();
    
    if(parent != null){
      result += "\nThis Page's Parent's URL: "+ parent.getURL();
    }
    
    if(children.size() > 0){
      result += "\nThis Page's Children's URLs:\n";
      for(int i = 0; i < children.size(); i++){
        result += children.get(i).getURL() + "\n";
      }
    }
    
    return result;
  }
}
