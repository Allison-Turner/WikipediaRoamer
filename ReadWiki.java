/**ReadWiki.java
  * @author Sammy Lincroft
  * Prints out all of the backlinks from a given wikipedia page
  * */

import java.net.*; // Java web package

import java.util.Scanner;
import java.io.*;


public class ReadWiki{
  public static void main(String[] args){
    String title = "Amsterdam";
    String url = "https://en.wikipedia.org/w/api.php?action=query&list=backlinks&bltitle=" + title + "&bllimit=500blfilterredir%3Dredirects&format=json";
    
    try{
      InputStream source = new URL(url).openStream();
      Scanner scan = new Scanner(source).useDelimiter("\"title\":");
      scan.next(); //skip over batch information
      while(scan.hasNext()){
        System.out.println(scan.next().split("\"")[1]);
      }
    }
    catch(IOException ex){
      System.out.println(ex);
    }
  }
}