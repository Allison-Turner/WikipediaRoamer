import java.util.ArrayList;

import java.net.*; // Java web package
import java.util.Scanner;
import java.io.*;

public class BackwardPage extends Page{
  private ArrayList<BackwardPage> parents;
  private BackwardPage child;
  
  public BackwardPage(String url, String title, BackwardPage child){
    super(url, title);
    this.child = child;
    parents = new ArrayList<BackwardPage>();
  }
  
  public BackwardPage(String url, String title){
    super(url, title);
    this.child = null;
    parents = new ArrayList<BackwardPage>();
  }
  
  //Webscraping wooo
  public void retrieveFamily(){
    String url = ("https://en.wikipedia.org/w/api.php?action=query&list=backlinks&ns=0&bltitle=" 
      + this.title.replace(" ","+") + "&bllimit=" + CHILDLIMIT + "blfilterredir%3Dredirects&format=json");
      try{
        InputStream source = new URL(url).openStream();
        Scanner scan = new Scanner(source).useDelimiter("\"title\":");
        scan.next(); //skip over batch information
        while(scan.hasNext()){ 
          String parentTitle = scan.next().split("\"")[1];
          if(parentTitle.indexOf(":") == -1){
            parents.add(new BackwardPage("https://en.wikipedia.org/wiki/"+parentTitle, parentTitle, this));
          }
        }
      }
      catch(IOException ex){
        System.out.println(ex);
      }
  }
  
  public boolean familySharesMember(ForwardPage fPage){//Argue about design choices later
    ArrayList<ForwardPage> children = fPage.getChildren();
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
  
  public String getTitle(){
    return title;
  }
  
  public ArrayList<BackwardPage> getParents(){
    return parents;
  }
  
  public void setChild(BackwardPage child){
    this.child = child;
  }
  
  public BackwardPage getChild(){
    return child;
  }
  
  public String getChildPath(BackwardPage bPage){
    String result = bPage.getTitle();
    
    if(bPage.getChild() == null){
      return result;
    }
    
    else{
      return (result + ", " + getChildPath(bPage.getChild()));
    }
  }
    
    public String toString(){
    String result = "This Page's URL: " + this.getURL();
    result += ("This Page's Title: " + title);
    
    if(child != null){
      result += "\nThis Page's Child's URL: "+ child.getURL();
    }
    
    if(parents.size() > 0){
      result += "\nThis Page's Parent's URLs:\n";
      for(int i = 0; i < parents.size(); i++){
        result += parents.get(i).getURL() + "\n";
      }
    }
    
    return result;
  }
    
    public static void main(String[] args){
      BackwardPage testPage = new BackwardPage("https://en.wikipedia.org/wiki/Amsterdam", "Amsterdam");
      System.out.println(testPage);
      testPage.retrieveFamily();
      System.out.println(testPage);
    }
}