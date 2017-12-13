/**WikipediaGame.java
  * @author Samantha Lincroft
  * @modified date 12/2/17
  * */

import java.util.*; //get access to Hashtables

public class WikipediaGame{
  
  //set up the start and end URL's and the path 
  private String startURL;
  private String endURL;
  private ForwardPage startPage;
  private BackwardPage endPage;
  private String path;
  
  
  //Set the maximum depth to search (by gradually increasing until everything breaks)
  private final int MAXDEPTH = 3;
  
  private Hashtable<String, ForwardPage> forwardNodes;
  private Hashtable<String, BackwardPage> backwardNodes;
  
  public WikipediaGame(String start, String end){
    startURL = start;
    endURL = end;
    startPage = new ForwardPage(startURL, getTitle(startURL)); 
    endPage = new BackwardPage(endURL, getTitle(endURL));
    path = "Have yet to find a path.";
    forwardNodes = new Hashtable<String, ForwardPage>();
    backwardNodes = new Hashtable<String, BackwardPage>();
  }
  
  private String getTitle(String url){
    return url.substring(30);
  }
  
  public WikipediaGame(){
    path = "Have yet to find a path.";
    forwardNodes = new Hashtable<String, ForwardPage>();
    backwardNodes = new Hashtable<String, BackwardPage>();
  }
  
  public void setStartURL(String s){
    startURL = s;
    startPage = new ForwardPage(startURL, getTitle(startURL));
  }
  
  public void setEndURL(String e){
    endURL = e;
    endPage = new BackwardPage(endURL, getTitle(endURL));
  }
  
  public void setStartTerm(String s){
    startURL = "https://en.wikipedia.org/wiki/" + s;
    startPage = new ForwardPage(startURL, s);
  }
  
  public void setEndTerm(String e){
    endURL = "https://en.wikipedia.org/wiki/" + e;
    endPage = new BackwardPage(endURL, e);
  }
  
  public String generatePath(){ //updated version O(nCHILDLIMIT^n)
    
    path = "Path Not Found.";
    
    ForwardPage front = startPage;
    BackwardPage back = endPage;
    forwardNodes.put(front.getTitle(), front);
    backwardNodes.put(back.getTitle(), back);
    int depth = 0;
    
    while(depth<MAXDEPTH){//while we want to keep trying
      
      Enumeration<String> bIter = backwardNodes.keys();
      while(bIter.hasMoreElements()){ //go through all the forward nodes
        String title = bIter.nextElement();
        System.out.println("checking: " + title);
        path = ("checking: " + title);
        if(forwardNodes.containsKey(title)){ //if a forward node is contained in backwardNodes we've got it!
          BackwardPage b = backwardNodes.get(title);
          ForwardPage f = forwardNodes.get(title);
          System.out.println("comparing: " + f.getTitle() + " and " + b.getTitle());
          path = f.getParentPath(f);
          path += b.getChildPath(b).substring(title.length()); //substring removes duplicate "linking element"
          return path; //return the path between them
        }
      }
      
      //otherwise add all of the children of the forward pages to the forward hash table
      Enumeration<String> fIter = forwardNodes.keys();
      while(fIter.hasMoreElements()){
        ForwardPage f = forwardNodes.get(fIter.nextElement()); 
        f.retrieveFamily();
        for(ForwardPage child : f.getChildren()){
          if(!forwardNodes.containsKey(child.getTitle()))
            forwardNodes.put(child.getTitle(), child);
        }
      }
      
      //and add all of the children of the backwards pages to the backwards hash table
      bIter = backwardNodes.keys();
      while(bIter.hasMoreElements()){
        BackwardPage b = backwardNodes.get(bIter.nextElement());
        b.retrieveFamily();
        for(BackwardPage parent : b.getParents()){
          if(!backwardNodes.containsKey(parent.getTitle()))
            backwardNodes.put(parent.getTitle(), parent);
        }
      }
      
      //and tell us we're one step closer to giving up
      System.out.println((depth+1) + "/" + MAXDEPTH);
      depth++;
    }
    
    //if we gave up before finding anything... :(
    System.out.println("sorry, we could not find a path between your given nodes *sad*");
    path = "sorry, we could not find a path between your given nodes *sad*";
    return null; //return null if no path was found
  }
  
  
  public String toString(){
    return (path);
  }
  
  public static void main(String[] args){
    WikipediaGame game = new WikipediaGame("https://en.wikipedia.org/wiki/Webkinz", "https://en.wikipedia.org/wiki/Lamp");
    System.out.println(game);
    game.generatePath();
    System.out.println(game);
  }
  
}