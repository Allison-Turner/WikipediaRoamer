/**WikipediaGame.java
  * @author Samantha Lincroft, Ellie Czepiel, Allison Turner
  * @modified date 12/14/17
  * Purpose: Plays the Wikipedia game. This is the overarching class that governs the creation of the link path
  */
//Import necessary library
import java.util.*;

public class WikipediaGame{
  
  //set up the start and end URL's and the path 
  private String startURL;
  private String endURL;
  private ForwardPage startPage;
  private BackwardPage endPage;
  private LinkedList<Page> path;
  
  
  //Set the maximum depth to search (by gradually increasing until everything breaks)
  private final int MAXDEPTH = 3;
  
 //Initializing hashtables of current pages being examined
  private Hashtable<String, ForwardPage> forwardNodes;
  private Hashtable<String, BackwardPage> backwardNodes;
  
 /*Constructor
 *@param String start is the title of the starting page 
 *@param String end is the title of the ending page
 */
  public WikipediaGame(String start, String end){
    startURL = start;
    endURL = end;
    startPage = new ForwardPage(startURL, getTitle(startURL)); 
    endPage = new BackwardPage(endURL, getTitle(endURL));
    forwardNodes = new Hashtable<String, ForwardPage>();
    backwardNodes = new Hashtable<String, BackwardPage>();
  }
  
 /*Parses the String url to isolate only the page title
 *@param URL is the page's web URL
 @return the portion of the URL that contains the title
 */
  private String getTitle(String url){
    return url.substring(30);
  }
  
 /*No parameter constructor, only initializes the node hashtables. All other variables must be defined with other set methods before
 *the game can be played
 */
  public WikipediaGame(){
    forwardNodes = new Hashtable<String, ForwardPage>();
    backwardNodes = new Hashtable<String, BackwardPage>();
  }
  
 /* Resets the start page of the game
 *@param String s is the URL of the new start page
 */
  public void setStartURL(String s){
    startURL = s;
    startPage = new ForwardPage(startURL, getTitle(startURL));
  }
  
 /*Resets the end page of the game
 *@param String e is the URL of the end page
 */
  public void setEndURL(String e){
    endURL = e;
    endPage = new BackwardPage(endURL, getTitle(endURL));
  }
  
 /*Resets the start page using the entered approximated title
 *@param String s is the term entered by the user believed to be the title of a page. This means the user doesn't need exact URLs to
 *generate a path
 */
  public void setStartTerm(String s){
    String newS = s.replace(" ", "+");
    startURL = "https://en.wikipedia.org/wiki/" + newS;
    startPage = new ForwardPage(startURL, newS);
  }
  
 /*Resets the end page using the entered approximated title
 *@param String e is the term entered by the user believed to be the title of a page. This means the user doesn't need exact URLs to
 *generate a path
 */
  public void setEndTerm(String e){
    String newE = e.replace(" ", "+");
    endURL = "https://en.wikipedia.org/wiki/" + newE;
    endPage = new BackwardPage(endURL, newE);
  }
  
 /*This method explores the children of each page until a common page is found in the middle. This is the driving method for beating
 *game
 *@return LinkedList<Page> is the list of pages connecting from the start page to the end page 
 */
  public LinkedList<Page> generatePath(){ //updated version O(nCHILDLIMIT^n)
    
    //path = "Path Not Found.";
    path = new LinkedList<Page>();
    
    ForwardPage front = startPage;
    BackwardPage back = endPage;
    forwardNodes.put(front.getTitle(), front);
    backwardNodes.put(back.getTitle(), back);
    int depth = 0;
    
   //While we have not exceeded our maximum depth
    while(depth<MAXDEPTH){
      
      Enumeration<String> bIter = backwardNodes.keys();
     //go through all the forward nodes
      while(bIter.hasMoreElements()){ 
        String title = bIter.nextElement();
        System.out.println("checking: " + title);
       //if a forward node is contained in backwardNodes we've got it!
        if(forwardNodes.containsKey(title)){ 
          BackwardPage b = backwardNodes.get(title);
          ForwardPage f = forwardNodes.get(title);
         //Print the found common link
          System.out.println("comparing: " + f.getTitle() + " and " + b.getTitle());
         //Create the path
          path = f.getParentPath(f);
          path.removeLast();
          path.addAll(b.getChildPath(b));
          
         //Return the path
          return path;
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
      
      //and tell us we're one step closer to giving up, i.e. increment the traversed depth variable
      System.out.println((depth+1) + "/" + MAXDEPTH);
      depth++;
    }
    
    //if we gave up before finding anything... :(
    System.out.println("sorry, we could not find a path between your given nodes *sad*");
    return null; //return null if no path was found
  }
  
 /*Returns the link path in linked list form
 *@return LinkedList<Page> is the list of pages from the start page to the end page
 */
  public LinkedList<Page> getPath(){
    return path;
  }
  
 /*Gets a string representation of the path
 *@return String representation of the linked list of the path, since that's the only relevant result of the game
 */
  public String toString(){
    if(path == null){
      return("sorry we don't have a path yet");
    }
    return (path.toString());
  }
  
 /*
 *Tests the functionality of the game object
 */
  public static void main(String[] args){
    WikipediaGame game = new WikipediaGame("https://en.wikipedia.org/wiki/Webkinz", "https://en.wikipedia.org/wiki/Dolphin");
    System.out.println(game);
    System.out.println(game.generatePath());
    System.out.println(game);
  }
  
}