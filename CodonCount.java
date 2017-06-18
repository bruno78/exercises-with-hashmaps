
/**
 * Write a description of class CodonCount here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
import java.util.*;
import edu.duke.*;

public class CodonCount
{
    private HashMap<String, Integer> dnaMap;

    public CodonCount()
    {
        dnaMap = new HashMap<String, Integer>();
    }
    
    public void buildCodonMap(int start, String dna){
        dnaMap.clear();
        int end = start + 3;
        
        while(end <= dna.length()) {
            
            String codon = dna.substring(start, end);
            if (!dnaMap.containsKey(codon)){
                dnaMap.put(codon, 1);
            }
            else {
                dnaMap.put(codon, dnaMap.get(codon)+1);
            }
            
            start += 3;
            end += 3;
        }
    }
    
    public String getMostCommonCodon(){
        int occurrence = 0;
        String mostCommonCodon = "";
        
        for (String codon : dnaMap.keySet()){
            if (dnaMap.get(codon) > occurrence) {
                occurrence = dnaMap.get(codon);
                mostCommonCodon = codon;
            }
        }
        
        return mostCommonCodon;
    }
    
    public void printCodonCounts(int start, int end){
        
        for (String codon : dnaMap.keySet()) {
            if (dnaMap.get(codon) >= start && dnaMap.get(codon) <= end){
               System.out.println(codon + " " + dnaMap.get(codon));
            }
        }
    }
    
    public void tester(){
        
        FileResource fr = new FileResource();
        for (String dna : fr.lines()){
            dna = dna.toUpperCase();
            
            for (int k=0; k < 3; k++){
                buildCodonMap(k, dna);
                System.out.println("Reading frame starting with " + k +
                        " results in " + dnaMap.size() + " unique codons\n" +
                        "and most common codon is " + getMostCommonCodon() +
                        " with count " + dnaMap.get(getMostCommonCodon()));
                
                System.out.println("Counts of codons between 1 and 5 inclusive are: ");
                System.out.println("");
                printCodonCounts(1, 5);
                System.out.println("");
            }
        }
    }
}
