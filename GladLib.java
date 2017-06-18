import edu.duke.*;
import java.util.*;

public class GladLib {
    private HashMap<String, ArrayList> myMap;
    
    private ArrayList<String> usedWords;
    private HashMap<String, ArrayList> wordsConsidered;
    private int count;
    
    private Random myRandom;
    
    private static String dataSourceURL = "http://dukelearntoprogram.com/course3/data";
    private static String dataSourceDirectory = "data2";
    
    public GladLib(){
        myMap = new HashMap<String, ArrayList>();
        initializeFromSource(dataSourceDirectory);
        myRandom = new Random();
        usedWords = new ArrayList<String>();
        wordsConsidered = new HashMap<String, ArrayList>();
        count = 0;
    }
    
    public GladLib(String source){
        initializeFromSource(source);
        myRandom = new Random();
    }
    
    private void initializeFromSource(String source) {
        String[] categories = {"adjective", "noun", "color",
            "country", "name", "animal", "timeframe", "verb", "fruit"};
        for ( String category : categories ){
            ArrayList<String> list = readIt(source + "/" + category + ".txt");
            myMap.put(category, list);
        }

    }
    
    private String randomFrom(ArrayList<String> source){
        int index = myRandom.nextInt(source.size());
        return source.get(index);
    }
    
    private void wordsConsideredBuilder(String label, String word){
        if(!wordsConsidered.containsKey(label)){
             ArrayList<String> list = new ArrayList<String>();
             list.add(word);
             wordsConsidered.put(label, list);
        }
        else {
             wordsConsidered.get(label).add(word);
        }
    }
    
    private String getSubstitute(String label) {
        if (label.equals("number")){
            return ""+myRandom.nextInt(50)+5;
        }
        if (myMap.containsKey(label)) {
            String word = randomFrom(myMap.get(label));
            wordsConsideredBuilder(label, word);
            return word;
        }
        return "**UNKNOWN**";
    }
    
    private String processWord(String w){
        int first = w.indexOf("<");
        int last = w.indexOf(">",first);
        if (first == -1 || last == -1){
            return w;
        }
        String prefix = w.substring(0,first);
        String suffix = w.substring(last+1);
        String sub = getSubstitute(w.substring(first+1,last));
        while (usedWords.contains(sub)) {
          sub = getSubstitute(w.substring(first+1,last));
          count ++;
          
        }
        count ++;
        usedWords.add(sub);
        return prefix+sub+suffix;
        
    }
    
    private void printOut(String s, int lineWidth){
        int charsWritten = 0;
        for(String w : s.split("\\s+")){
            if (charsWritten + w.length() > lineWidth){
                System.out.println();
                charsWritten = 0;
            }
            System.out.print(w+" ");
            charsWritten += w.length() + 1;
        }
    }
    
    private String fromTemplate(String source){
        String story = "";
        if (source.startsWith("http")) {
            URLResource resource = new URLResource(source);
            for(String word : resource.words()){
                story = story + processWord(word) + " ";
            }
        }
        else {
            FileResource resource = new FileResource(source);
            for(String word : resource.words()){
                story = story + processWord(word) + " ";
            }
        }
        return story;
    }
    
    private ArrayList<String> readIt(String source){
        ArrayList<String> list = new ArrayList<String>();
        if (source.startsWith("http")) {
            URLResource resource = new URLResource(source);
            for(String line : resource.lines()){
                list.add(line);
            }
        }
        else {
            FileResource resource = new FileResource(source);
            for(String line : resource.lines()){
                list.add(line);
            }
        }
        return list;
    }
    
    public int totalWordsInMap(){
        int total = 0;
        for (String category : myMap.keySet()){
            total += myMap.get(category).size();
        }
        return total;
    }
    
    public int totalWordsConsidered(){
        int total = 0;
        for (String label : wordsConsidered.keySet()){
            total += wordsConsidered.get(label).size();
        }
        return total;
    }
    
    public void makeStory(){
        usedWords.clear();
        wordsConsidered.clear();
        count = 0;
        System.out.println("\n");
        String story = fromTemplate("data2/madtemplate2.txt");
        printOut(story, 60);
        System.out.println("\n");
        System.out.println("The total number of words that were replaced" +
                             " right after the story is printed: " + count);
        System.out.println("\nTotal of words considered " + totalWordsConsidered());
    }

}
