/*@author Ellie Czepiel(secondary), Allison Turner(primary), and Sammy Lincroft(secondary)
 * Last Modified: 14 December 2017
 * Purpose: This is a child of the Page class, specifying that this is a Page that is only concerned with what Pages link to this Page. It also can determine whether it shares a child with a ForwardPage
 */
//Importing necessary libraries: input/output, web package, utility
import java.util.ArrayList;
import java.net.*;
import java.util.*;
import java.io.*;

public class BackwardPage extends Page{
  //Defining the class variables
  private ArrayList<BackwardPage> parents;
  private BackwardPage child;
  private boolean retrievedFamily;
  
  /*
   *Constructor for all pages between the end page and the connecting page.
   * @param String url is the url of the web page that this object represents
   * @param String title is the title of the web page
   * @param BackwardPage child is the page that is linked to from this page
   */
  public BackwardPage(String url, String title, BackwardPage child){
    super(url, title);
    this.child = child;
    parents = new ArrayList<BackwardPage>();
    retrievedFamily = false;
  }
  
  /*
   *Constructor for the end page. child is set to null because we have no value for this and don't care about it.
   * @param String url is the url of the web page that this object represents
   * @param String title is the title of the web page
   */
  public BackwardPage(String url, String title){
    super(url, title);
    this.child = null;
    parents = new ArrayList<BackwardPage>();
    retrievedFamily = false;
  }
  
  /*
   *Populates the ArrayList<BackwardPage> parents with all of the pages that this page links to by sending a query to Wikipedia and parsing the results 
   */
  public void retrieveFamily(){
    //Only perform this operation if it hasn't been already
    if(!retrievedFamily){
      //Defining the query, substituting in the page title and the child limit in order to receive relevant results
      String url = ("https://en.wikipedia.org/w/api.php?action=query&list=backlinks&ns=0&bltitle=" 
                      + this.title.replace(" ","+") + "&bllimit=" + CHILDLIMIT + "blfilterredir%3Dredirects&format=json");
      try{
        //Open an internet connection with the query URL
        InputStream source = new URL(url).openStream();
        //Only search for page titles and URLs, there's a lot of extraneous information in this query return that we don't need
        Scanner scan = new Scanner(source).useDelimiter("\"title\":");
        //Skip over the batch information
        scan.next();
        //While there are more parents to parse
        while(scan.hasNext()){ 
          //Get the next parent
          String parentTitle = scan.next().split("\"")[1];
          //If this child is not a link to a talk or some other dead end type page, add it to parents
          if(parentTitle.indexOf(":") == -1 && parentTitle.indexOf("\\") == -1){
            parents.add(new BackwardPage("https://en.wikipedia.org/wiki/"+parentTitle, parentTitle, this));
          }
        }
      }
      //Print any exception
      catch(IOException ex){
        System.out.println(ex);
      }
      //Set retrievedFamily to true so we know that this operation has been performed
      retrievedFamily = true;
    }
  }
  
  /*
   * Determines whether this page has a child that is identical to a parent of the parameter BackwardPage
   * @param ForwardPage fPage is the page whose children (equivalent to a BackwardPage's parents) will be examined
   * @return boolean value for whether there is a common value in the lists of children and parents
   */  
  public boolean familySharesMember(ForwardPage fPage){
    //Retrieve the ForwardPage's children
    ArrayList<ForwardPage> children = fPage.getChildren();
    //Start off assuming that there is no common Page
    boolean shared = false;
    
    //For each BackwardPage parent, compare it to every ForwardPage child. If they are equal, there is a common page, and the shared var is changed
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
  
  /*Returns the page's parents
   *@return ArrayList<ForwardPage> parents is the list of the pages that link to this page
   */
  public ArrayList<BackwardPage> getParents(){
    return parents;
  }
  
  /*
   *Resets what this page links to, or at least the one page that is relevant that is linked to
   * @param BackwardPage child is the new child
   */
  public void setChild(BackwardPage child){
    this.child = child;
  }
  
  /*Returns the page that this page links to
   * @return the child of this page
   */
  public BackwardPage getChild(){
    return child;
  }
  
  /*
   *Returns a string representation of the path from the parameter page all of the way back to the end page
   * @param BackwardPage bPage is the page to start the path with, and will work forwards from there by going through children
   * @return String representation of the path
   */
 /* public String getChildPath(BackwardPage bPage){
  * //Start by adding the parameter page to the string
    String result = bPage.getTitle();
    
    //If there's no child page, return the parameter page title
    if(bPage.getChild() == null){
      return result;
    }
    
    //Else recurse forwards through the children
    else{
      return (result + ", " + getChildPath(bPage.getChild()));
    }
  }*/
  
   /*
   * Returns a LinkedList of all of the pages from the parameter page to the end page
   * @param BackwardPage bPage is the page to begin working forwards from through child pages
   * @return LinkedList<Page> the path from the parameter page to the end page in a linked list
   */
  public LinkedList<Page> getChildPath(BackwardPage bPage){
    //Initialize the result list
    LinkedList<Page> result = new LinkedList<Page>();
    
    //If there is no child, simply return just the parameter page
    if(bPage.getChild() == null){
      result.add(bPage);
      return result;
    }
    //Otherwise, recurse forwards through the children, adding each page to the list, and returning the result
    result = getChildPath(bPage.getChild());
    
    result.addFirst(bPage);
    
    return result;
  }
    
  /* Returns a String of the page and its parents
   * @return String representation of the BackwardPage and its parents
   */
    /*public String toString(){
    String result = "This Page's URL: " + this.getURL();
    result += ("This Page's Title: " + title);
    
    if(child != null){
      result += "\nThis Page's Child's URL: "+ child.getURL();
    }
    
    if(parents.size() > 0){
      result += "\nThis Page's Parent's\n";
      for(int i = 0; i < parents.size(); i++){
        result += parents.get(i).getTitle() + "\n";
      }
    }
    
    return result;
  }*/
    
  /*
   *Tests the functionality of this object 
   */
    public static void main(String[] args){
      BackwardPage testPage = new BackwardPage("https://en.wikipedia.org/wiki/Amsterdam", "Amsterdam");
      System.out.println(testPage);
      testPage.retrieveFamily();
      System.out.println(testPage);
    }
}
