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
  
  private Hashtable<ForwardPage, ForwardPage> forwardNodes;
  private Hashtable<BackwardPage, BackwardPage> backwardNodes;
  
  public WikipediaGame(String start, String end){
    startURL = start;
    endURL = end;
    startPage = new ForwardPage(startURL, getTitle(startURL)); 
    endPage = new BackwardPage(endURL, getTitle(endURL));
    path = "Have yet to find a path.";
    forwardNodes = new Hashtable<ForwardPage, ForwardPage>();
    backwardNodes = new Hashtable<BackwardPage, BackwardPage>();
  }
  
  private String getTitle(String url){
    return url.substring(30);
  }
  
  public WikipediaGame(){
    path = "Have yet to find a path.";
    forwardNodes = new Hashtable<ForwardPage, ForwardPage>();
    backwardNodes = new Hashtable<BackwardPage, BackwardPage>();
  }
  
  public void setStartURL(String s){
    startURL = s;
    startPage = new ForwardPage(startURL, getTitle(startURL));
  }
  
  public void setEndURL(String e){
    endURL = e;
    endPage = new BackwardPage(endURL, getTitle(endURL));
  }
  
  public String generatePath(){
    
    path = "Path Not Found.";
    
    ForwardPage front = startPage;
    BackwardPage back = endPage;
    forwardNodes.put(front, front);
    backwardNodes.put(back, back);
    int depth = 0;
    
    while(depth<MAXDEPTH){//while we want to keep trying
      
      Enumeration<ForwardPage> fIter = forwardNodes.keys();
      while(fIter.hasMoreElements()){
        ForwardPage f = fIter.nextElement();
        Enumeration<BackwardPage> bIter = backwardNodes.keys();
        while(bIter.hasMoreElements()){
          BackwardPage b = bIter.nextElement();
          //System.out.println("comparing: " + f.getTitle() + " and " + b.getTitle());
          if(f.equals(b)){
            System.out.println("comparing: " + f.getTitle() + " and " + b.getTitle());
            path = f.getParentPath(f);
            path += f.toString();
            path += b.getChildPath(b);
            return path; //if there's a match return the path between them
          }
        }
      }
      
      //otherwise add all of the children of the forward pages to the forward hash table
      fIter = forwardNodes.keys();
      while(fIter.hasMoreElements()){
        ForwardPage f = fIter.nextElement(); 
        f.retrieveFamily();
        for(ForwardPage child : f.getChildren()){
          forwardNodes.put(child, child);
        }
      }
      
      //and add all of the children of the backwards pages to the backwards hash table
      Enumeration<BackwardPage> bIter = backwardNodes.keys();
      while(bIter.hasMoreElements()){
        BackwardPage b = bIter.nextElement();
        b.retrieveFamily();
        for(BackwardPage parent : b.getParents()){
          backwardNodes.put(parent, parent);
        }
      }
      
      //and tell us we're one step closer to giving up
      depth++;
    }
    
    System.out.println("sorry, we could not find a path between your given nodes");
    return null; //return null if no path was found
  }
  
  public String toString(){
    return (path);
  }
  
  public static void main(String[] args){
    WikipediaGame game = new WikipediaGame("https://en.wikipedia.org/wiki/Webkinz", "https://en.wikipedia.org/wiki/Athens");
    System.out.println(game);
    game.generatePath();
    System.out.println(game);
  }
  
}