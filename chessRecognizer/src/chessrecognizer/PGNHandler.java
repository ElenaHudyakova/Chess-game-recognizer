/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chessrecognizer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @author Elena Hudyakova
 */
public class PGNHandler {
    
    private static void parseTagPair(String line, Party party){
        Pattern tagPairPattern = Pattern.compile("\\[(.+) \"(.+)\"\\]");
        Matcher matcher = tagPairPattern.matcher(line);
        boolean matchFound = matcher.find();
        if (matchFound){
            if (matcher.group(1).toLowerCase().equals("event"))
                party.setEvent(matcher.group(2));
            if (matcher.group(1).toLowerCase().equals("site"))
                party.setSite(matcher.group(2));
            if (matcher.group(1).toLowerCase().equals("date"))
                party.setDate(matcher.group(2));                                          
            if (matcher.group(1).toLowerCase().equals("white"))
                party.setWhite(matcher.group(2));
            if (matcher.group(1).toLowerCase().equals("black"))
                party.setBlack(matcher.group(2));
            if (matcher.group(1).toLowerCase().equals("round"))
                party.setRound(matcher.group(2));
            if (matcher.group(1).toLowerCase().equals("result"))
                party.setResult(matcher.group(2));                       
        }
    }
    
    private static void parseLine(String line, Party party){
        parseTagPair(line, party);
    }
    
    public static List<Party> parseParties(String file_name){
        ArrayList<Party> parties = new ArrayList<Party>();
        try{
            BufferedReader PGNreader = new BufferedReader(new FileReader(file_name));
            String currentLine;
            while ((currentLine = PGNreader.readLine())!=null){
                if (isNewParty(currentLine)){
                   parties.add(new Party()); 
                }
                Party currentParty = parties.get(parties.size()-1);
                parseLine(currentLine, currentParty);
            }
            
        } catch (Exception e){
            System.out.println(e.toString());
        }
        return parties;
    }
    
    public static void createPGNFile(List<Party> parties){
        
    }

    private static boolean isNewParty(String line) {
        Pattern tagPairPattern = Pattern.compile("\\[(.+) \"(.+)\"\\]");
        Matcher matcher = tagPairPattern.matcher(line);
        if (matcher.find()&& matcher.group(1).toLowerCase().equals("event"))
            return true;
        else
            return false;
    }
    
}
