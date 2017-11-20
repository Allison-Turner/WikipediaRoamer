public class WikipediaGame{
  
  private String startURL;
  private String endURL;
  private forwardPage startPage;
  private backwardPage endPage;
  
  private final int MAXDEPTH = 6;
  
  private Hashtable<ForwardPage, ForwardPage> forwardNodes;
  private Hashtable<BackwardPage, BackwardPage> backwardsNodes;
  
  public WikipediaGame(startURL, endURL){
    startURL = startURL;
    endURL = endURL;
    startPage = new forwardPage(startURL);
    endPage = new backwardPage(endURL);
  }
  
  public WikipediaGame(){
  }
  
  public void setStartURL(String s){
    startURL = s;
    startPage = new forwardPage(startURL);
  }
  
  public void setEndURL(String e){
    endURL = e;
    endPage = new backwardPage(endURL);
  }
  
  public ArrayList<Page> generatePath(){
    
    ArrayList<Page> path = new ArrayList<Page>();
    
    forwardPage front = startPage;
    backwardPage back = endPage;
    forwardNodes.add(front);
    backwardNodes.add(back);
    depth = 1;
    
    while(depth<MAXDEPTH){//while we want to keep trying
      
      for(Page f : forwardNodes){
        for(Page b : backwardNodes){ 
          if(f.equals(b)){ //compare every element of the forward and backward hash tables
            path.put(f.getParentPath());
            path.put(f);
            path.put(b.getChildrenPath());
            return path; //if there's a match return the path between them
          }
        }
      }
      
      //otherwise add all of the children of the forward pages to the forward hash table
      for(Page f : forwardNodes){ 
        f.retrieveFamily();
        for(Page child : f.getChildren()){
          forwardNodes.put(child);
        }
      }
      
      //and add all of the children of the backwards pages to the backwards hash table
      for(Page b : backwardNodes){
        b.retrieveFamily();
        for(Page parent : b.getParents()){
          backwardNodes.put(parent);
        }
      }
      
      //and tell us we're one step closer to giving up
      depth++;
    }
    
    System.out.println("sorry, we could not find a path between your given nodes");
    return null; //return null if no path was found
  }
  
  public String toString(){
    return ("the current state of the game");
  }
  
}