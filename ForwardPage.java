import java.util.ArrayList;

import java.net.*; // Java web package
import java.util.Scanner;
import java.io.*;

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
  
  //We have to do some webscraping here to find the children
  public void retrieveFamily(){
    //String commandURL = "https://www.mediawiki.org/w/api.php?action=query&prop=links&titles=" + title + "&format=json"; this is the one that it should be
    String url = ("https://en.wikipedia.org/w/api.php?action=query&list=backlinks&ns=0&bltitle=" 
      + this.title.replace(" ","+") + "&bllimit=" + CHILDLIMIT + "blfilterredir%3Dredirects&format=json");
      try{
        InputStream source = new URL(url).openStream();
        Scanner scan = new Scanner(source).useDelimiter("\"title\":");
        scan.next(); //skip over batch information
        while(scan.hasNext()){ 
          String childTitle = scan.next().split("\"")[1];
          if(childTitle.indexOf(":") == -1){
            children.add(new ForwardPage("https://en.wikipedia.org/wiki/"+childTitle, childTitle, this));
          }
        }
      }
      catch(IOException ex){
        System.out.println(ex);
      }
  }
  
  public String getTitle(){
    return title;
  }
  
  public ArrayList<ForwardPage> getChildren(){
    return children;
  }
  
  public void setParent(ForwardPage parent){
    this.parent = parent;
  }
  
  public ForwardPage getParent(){
    return parent;
  }
  
  public String getParentPath(ForwardPage fPage){
    
    String result = fPage.getTitle();
    
    if (fPage.getParent() == null){
      return fPage.getTitle();
    }
    
    else{
      return (getParentPath(fPage.getParent()) + ", " + result);
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
  
  public static void main(String[] args){
      ForwardPage testPage = new ForwardPage("https://en.wikipedia.org/wiki/Amsterdam", "Amsterdam");
      System.out.println(testPage);
      testPage.retrieveFamily();
      System.out.println(testPage);
    }
}