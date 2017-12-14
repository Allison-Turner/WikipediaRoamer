/*@author Ellie Czepiel, Allison Turner, and Sammy Lincroft
 * Last Modified: 14 December 2017
 * Purpose: This is a child of the Page class, specifying that this is a Page that is only concerned with what is linked to from this Page. It also can determine whether it shares a child with a BackwardPage
 */
//Importing necessary libraries: input/output, web package, utility
import java.util.ArrayList;
import java.net.*;
import java.util.*;
import java.io.*;

public class ForwardPage extends Page{
  //Defining the class variables
  private ArrayList<ForwardPage> children;
  private ForwardPage parent;
  private boolean retrievedFamily;
  
  /*
   *Constructor for all pages between the start page and the connecting page.
   * @param String url is the url of the web page that this object represents
   * @param String title is the title of the web page
   * @param ForwardPage parent is the page that links to this page.
   */
  public ForwardPage(String url, String title, ForwardPage parent){
    super(url, title);
    children = new ArrayList<ForwardPage>();
    this.parent = parent;
    retrievedFamily = false;
  }
  
  /*
   *Constructor for the start page. parent is set to null because we have no value for this and don't care about it.
   * @param String url is the url of the web page that this object represents
   * @param String title is the title of the web page
   */
  public ForwardPage(String url, String title){
    super(url, title);
    children = new ArrayList<ForwardPage>();
    this.parent = null;
    retrievedFamily = false;
  }
  
  /*
   *Populates the ArrayList<ForwardPage> children with all of the pages that this page links to by sending a query to Wikipedia and parsing the results 
   */
  public void retrieveFamily(){
    //Only perform this operation if it hasn't been already
    if(!retrievedFamily){
      //Defining the query, substituting in the page title and the child limit in order to receive relevant results
    //String commandURL = "https://www.mediawiki.org/w/api.php?action=query&prop=links&titles=" + title + "&format=json"; this is the one that it should be
    String url = ("https://en.wikipedia.org/w/api.php?action=query&titles=" + this.title.replace(" ","+") + "&prop=links&pllimit=" + CHILDLIMIT + "&format=json");
      try{
        //Open an internet connection with the query URL
        InputStream source = new URL(url).openStream();
        //Only search for page titles and URLs, there's a lot of extraneous information in this query return that we don't need
        Scanner scan = new Scanner(source).useDelimiter("\"title\":");
        //Skip over the batch information
        scan.next();
        //While there are more children to parse
        while(scan.hasNext()){ 
          //Get the next child
          String childTitle = scan.next().split("\"")[1];
          //If this child is not a link to a talk or some other dead end type page, add it to children
          if(childTitle.indexOf(":") == -1 && childTitle.indexOf("\\") == -1){
            children.add(new ForwardPage("https://en.wikipedia.org/wiki/"+childTitle, childTitle, this));
          }
        }
      }
      //Print any exceptions
      catch(IOException ex){
        System.out.println(ex);
      }
      //Set retrievedFamily to true so we know that this operation has been performed
      retrievedFamily = true;
    }
  }
  
  /*Returns the page's children
   *@return ArrayList<ForwardPage> children is the list of the pages that this page links to 
   */
  public ArrayList<ForwardPage> getChildren(){
    return children;
  }
  
  /*
   *Reset the page's parent
   * @param ForwardPage parent is the new parent page
   */
  public void setParent(ForwardPage parent){
    this.parent = parent;
  }
  
  /*
   *Retrieve the Page object that represents the page's parent
   * @return ForwardPage parent, the page's parent
   */
  public ForwardPage getParent(){
    return parent;
  }
  
  /*
   *Returns a string representation of the path from the parameter page all of the way back to the start page
   * @param ForwardPage fPage is the page to start the path with, and will work backwards from there by going through parents
   * @return String representation of the path
   */
  /*public String getParentPath(ForwardPage fPage){
    
    //Start by adding the parameter page to the String
    String result = fPage.getTitle();
    
    //If there's no parent page, return the parameter page title
    if (fPage.getParent() == null){
      return fPage.getTitle();
    }
    
    //Else recurse backwards through the parents
    else{
      return (getParentPath(fPage.getParent()) + ", " + result);
    }
  }*/
  
  /*
   * Returns a LinkedList of all of the pages from the start page to the parameter page
   * @param ForwardPage fPage is the page to begin working backwards from through parent pages
   * @return LinkedList<Page> the path from the start page to the parameter page in a linked list
   */
  public LinkedList<Page> getParentPath(ForwardPage fPage){
    //Initialize the result list
    LinkedList<Page> result = new LinkedList<Page>();
    
    //If there is no parent, simply return just the parameter page
    if(fPage.getParent() == null){
      result.add(fPage);
      return result;
    }
    //Otherwise, recurse back through the parents, adding each page to the list, and returning the result
    result = getParentPath(fPage.getParent());
    
    result.add(fPage);
    
    return result;
  }
  
  
  /*
   * Determines whether this page has a child that is identical to a parent of the parameter BackwardPage
   * @param BackwardPage bPage is the page whose parents (equivalent to a ForwardPage's children) will be examined
   * @return boolean value for whether there is a common value in the lists of children and parents
   */
  public boolean familySharesMember(BackwardPage bPage){
    //Retrieve the BackwardPage's parents
    ArrayList<BackwardPage> parents = bPage.getParents();
    //Start off assuming that there is no common Page
    boolean shared = false;
    
    //For each FOrwardPage child, compare it to every BackwardPage parent. If they are equal, there is a common page, and the shared var is changed
    for(int i = 0; i < parents.size(); i++){
      for(int j = 0; j < children.size(); j++){
        if(parents.get(i).equals(children.get(j))){
          shared = true;
        }
      }
    }
    //Return whether there is a common page
    return shared;
  }
  
  /* Returns a String of the page and its children
   * @return String representation of the ForwardPage and its children
   */
  /*public String toString(){
    String result = "This Page's URL: " + this.getURL();
    
    if(parent != null){
      result += "\nThis Page's Parent's URL: "+ parent.getURL();
    }
    
    if(children.size() > 0){
      result += "\nThis Page's Children\n";
      for(int i = 0; i < children.size(); i++){
        result += children.get(i).getTitle() + "\n";
      }
    }
    
    return result;
  }*/
  
  /*
   *Tests the functionality of this object 
   */
  public static void main(String[] args){
      ForwardPage testPage = new ForwardPage("https://en.wikipedia.org/wiki/Amsterdam", "Amsterdam");
      System.out.println(testPage);
      testPage.retrieveFamily();
      System.out.println(testPage);
      System.out.println(testPage.getImageURL());
    }
}
