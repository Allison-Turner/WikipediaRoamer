import java.util.ArrayList;

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