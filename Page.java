/*
 *@author Allison Turner, Sammy Lincroft, and Ellie Czepiel
 * Last Modified: 13 December 2017
 * Purpose: This is the general form of a page within the bounds of the Wikipedia game algorithm. It only requires a page title and URL, and defines the maximum number of children that will be retrieved via webscraping
 */
//Importing the Java web package, Scanner library, and file input output library
import java.net.*;
import java.util.Scanner;
import java.io.*;

public abstract class Page{
  //Defining the class variables and initializing the maximum number of children
  private String url;
  protected String title;
  protected final int CHILDLIMIT = 30;
  
  /* Constructor, only comes as a two parameter constructor because a page without a title and url is useless
   *@param String url is the URL of the web page
   * @param String title is the title of the web page and summarizes what it's about
   */
  public Page(String url, String title){
    this.url = url;
    this.title = title;
  }
  
  /*Returns the URL of the web page
   *@return url is the URL of the web page that this Page object represents 
   */
  public String getURL(){
    return url;
  }
  
  /* Resets the URL of the page
   *@param String url is the new URL of the page 
   */
  public void setURL(String url){
    this.url = url;
  }
  
  /* Resets the title of the page
   *@param String title is the new title of the Page
   */
  public void setTitle(String title){
    this.title = title;
  }
  
  /*
   *Returns the title of the page
   * @return title of page
   */
  public String getTitle(){
    return title;
  }
  
  /*
   * Evaluates whether this page and the parameter page represent the same web page
   * @param Page page is the page to compare this one to
   *@return whether the URLs of the two pages are equivalent 
   */
  public boolean equals(Page page){
    if(page.getURL().equals(this.url)){
      return true;
    }
    else{
      return false;
    }
  }
  
  /*
   * Retrieves the URL of the page's first image
   *@return String URL of the page's first image 
   */
  public String getImageURL(){
    String query = ("https://en.wikipedia.org/w/api.php?action=query&titles=" + title.replace(" ", " +") + "&prop=pageimages&format=json&pithumbsize=100");
    String imageURL = "";
    try{
        InputStream source = new URL(query).openStream();
        Scanner scan = new Scanner(source).useDelimiter("\"source\":\"");
        scan.next();
        if (scan.hasNext()) imageURL = scan.next().split("\"")[0];
        scan.close();
      }
    catch(IOException ex){
        System.out.println(ex);
      }
    return imageURL;
  }
  
  /* String representation of the page
   *@return the title of the page 
   */
  public String toString(){
    return title;
  }
  
  /*
   *Children must implement a retrieveFamily() method 
   */
  public abstract void retrieveFamily();
}
