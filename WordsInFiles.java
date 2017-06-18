
/**
 * Write a description of class WordsInFiles here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
import java.util.*;
import edu.duke.*;
import java.io.*;

public class WordsInFiles
{
    private HashMap<String, ArrayList> wordMap;
    
    public WordsInFiles() {
        wordMap = new HashMap<String, ArrayList>();
    }
    
    private void addWordsFromFile(File f){
        
        FileResource fr = new FileResource(f);
        
        for (String word : fr.words()){
            
            if(!wordMap.containsKey(word)){
                
                ArrayList<String> fileList = new ArrayList<String>();
                wordMap.put(word, fileList);
            
            }
                
            String fileName = f.getName();
            if(!wordMap.get(word).contains(fileName)){
                wordMap.get(word).add(fileName);
            }
            
        }
    }
    
    public void buildWordFileMap() {
        wordMap.clear();
        DirectoryResource dr = new DirectoryResource();
        
        for (File file : dr.selectedFiles()){
            addWordsFromFile(file);
        }
    }
   
    public int maxNumber(){
        int maxNum = 0;
        
        for (String word : wordMap.keySet()){
            
            if (maxNum < wordMap.get(word).size()){
                maxNum = wordMap.get(word).size();
            }
        }
        return maxNum;
    }
    
    public void printFilesIn(String word){
        
       if(wordMap.containsKey(word)){
           ArrayList<String> fileList = wordMap.get(word);
           for( int k = 0; k < fileList.size(); k++){
               System.out.println(fileList.get(k));
            }
        }
    }
    
    public void tester(){
        ArrayList<String> wordsWithMax = new ArrayList<String>();
        buildWordFileMap();

        for (String word: wordMap.keySet()){
            if(wordMap.get(word).size() == maxNumber()) {
               wordsWithMax.add(word);
            }
        }
        
        System.out.print("The greatest number of files a word appears in is " + maxNumber() +
               ", and there are " + wordsWithMax.size() + " such words: ");
         
        for (int k = 0; k < wordsWithMax.size(); k++){
            System.out.print("\"" + wordsWithMax.get(k) + "\"");
            if (k < 1 && k < wordsWithMax.size()-1){
                System.out.print(" and ");
            } else {
                System.out.print(". \n");
            }
        }
        
        for (int k = 0; k < wordsWithMax.size(); k++){
            System.out.println("\"" + wordsWithMax.get(k) + "\" appears in the files: ");
            printFilesIn(wordsWithMax.get(k));
        }
        
        int occurrence = 5;
        int count = 0;
        for (String word : wordMap.keySet()){
            if(wordMap.get(word).size() == occurrence){
                count ++;
            }
        }
        System.out.println(count);
    }
    
    public void examTest(){
        
        buildWordFileMap();
        int occurrence = 4;
        int count = 0;
        for (String word : wordMap.keySet()){
            if(wordMap.get(word).size() == occurrence){
                count ++;
            }
        }
        System.out.println(count);
        printFilesIn("sad");
        System.out.println("");
        printFilesIn("red");
    }
}
