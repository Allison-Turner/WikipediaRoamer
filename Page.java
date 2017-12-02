public abstract class Page{
  private String url;
  protected String title;
  
  public Page(String url, String title){
    this.url = url;
    this.title = title;
  }
  
  public String getURL(){
    return url;
  }
  
  public void setURL(String url){
    this.url = url;
  }
  
  public boolean equals(Page page){
    if(page.getURL().equals(this.url)){
      return true;
    }
    else{
      return false;
    }
  }
  
  public abstract void retrieveFamily();
}