import java.util.ArrayList;

public class ForwardPage extends Page{
  private ArrayList<ForwardPage> children;
  private ForwardPage parent;
  
  public ForwardPage(String url, ForwardPage parent){
    super(url);
    children = new ArrayList<Page>();
    this.parent = parent;
  }
  
  public ForwardPage(String url){
    super(url);
    children = new ArrayList<Page>();
    this.parent = null;
  }
  
  //We have to do some webscraping here to find the children
  public void retrieveFamily(){
  }
  
  public ArrayList<ForwardPage> getChildren(){
    return children;
  }
  
  public void setParent(){
  }
  
  public ForwardPage getParent(){
    return parent;
  }
  
  public ArrayList<ForwardPage> getParentPath(){
    ArrayList<ForwardPage> pages = new ArrayList<ForwardPage>();
    
    pages.add(this);
    
    if(parent != null){
      return pages;
    }
    else{
      pages.add(parent);
      ArrayList<ForwardPage> grandparents = parent.getParentPath();
      for(ForwardPage page : grandparents){
        pages.add(page);
      }
    }
  }
  
  public boolean familySharesMember(BackwardPage bPage){
    ArrayList<BackwardPage> parents = bPage.getParents();
    boolean sharedpage = false;
    
    for(int i = 0; i < parents.size(); i++){
      for(int j = 0; j < children.size(); j++){
        if(parents.get(i).equals(children.get(j))){
          sharedPage = true;
        }
      }
    }
    return sharedPage;
  }
  
  public String toString(){
    String result = "This Page's URL: " url;
    
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