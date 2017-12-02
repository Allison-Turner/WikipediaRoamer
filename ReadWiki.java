/**ReadWiki.java
  * @author Sammy Lincroft
  * Prints out all of the backlinks from a given wikipedia page
  * */

import java.net.*; // Java web package

import java.util.Scanner;
import java.io.*;


public class ReadWiki{
  public static void main(String[] args){
    /*String title = "Athens";
    String url = "https://en.wikipedia.org/w/api.php?action=query&list=backlinks&ns=0&bltitle=" + title.replace(" ","&nsfp") + "&bllimit=500blfilterredir%3Dredirects&format=json";
    
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
    }*/
    try{
      PrintWriter outFile = new PrintWriter(new File("Physics.txt"));
      printChildren("Physics",3, outFile);
      outFile.close();
    }catch(IOException ex){
      System.out.println(ex);
    }
  }
  
  public static void printChildren(String title, int i, PrintWriter outFile){
    String url = "https://en.wikipedia.org/w/api.php?action=query&list=backlinks&ns=0&bltitle=" + title.replace(" ","+") + "&bllimit=25blfilterredir%3Dredirects&format=json";
    
    if(i>0){
      try{
        InputStream source = new URL(url).openStream();
        Scanner scan = new Scanner(source).useDelimiter("\"title\":");
        scan.next(); //skip over batch information
        while(scan.hasNext()){ 
          String child = scan.next().split("\"")[1];
          if(child.indexOf(":") == -1){
            outFile.println("Child of " + title + " : " + child);
            printChildren(child, i-1, outFile);
          }
        }
      }
      catch(IOException ex){
        System.out.println(ex);
      }
    }
  }
    
}

