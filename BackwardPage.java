import java.util.ArrayList;

public class BackwardPage extends Page{
  private ArrayList<BackwardPage> parents;
  private BackwardPage child;
  
  public BackwardPage(String url, BackwardPage child){
    super(url);
    this.child = child;
    parents = new ArrayList<BackwardPage>();
  }
  
  public BackwardPage(String url){
    super(url);
    this.child = null;
    parents = new ArrayList<BackwardPage>();
  }
  
  //Webscraping wooo
  public void retrieveFamily(){
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
    String result = this.getURL();
    
    if(child == null){
      return result;
    }
    
    else{
      return (result + "\n" + getChildPath(this.getChild()));
    }
  }
    
    public String toString(){
    String result = "This Page's URL: " + this.getURL();
    
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
}
