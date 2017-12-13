import java.net.*; // Java web package
import java.util.Scanner;
import java.io.*;

public abstract class Page{
  private String url;
  protected String title;
  protected final int CHILDLIMIT = 100;
  
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
  
  public void setTitle(String title){
    this.title = title;
  }
  
  public boolean equals(Page page){
    if(page.getURL().equals(this.url)){
      return true;
    }
    else{
      return false;
    }
  }
  
  public String getImageURL(){
    String query = ("https://en.wikipedia.org/w/api.php?action=query&titles=" + title.replace(" ", " +") + "&prop=pageimages&format=json&pithumbsize=100");
    String imageURL = "";
    try{
        InputStream source = new URL(query).openStream();
        Scanner scan = new Scanner(source).useDelimiter("\"source\":\"");
        scan.next();
        imageURL = scan.next().split("\"")[0];
        scan.close();
      }
    catch(IOException ex){
        System.out.println(ex);
      }
    return imageURL;
  }
  
  public String toString(){
    return title;
  }
  
  public abstract void retrieveFamily();
}