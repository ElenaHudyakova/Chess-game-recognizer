/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chessrecognizer;

import chessrecognizer.pieces.Piece;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @author Elena Hudyakova
 */
public class PGNHandler {
    
    public static boolean parseTagPair(String line, Party party){
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
            return true;
        }
        return false;
    }
    
    private static void parseLine(String line, Party party){
        if (!parseTagPair(line, party))
            parseMoves(line, party);
    }
    
    public static List<Party> parseParties(String filePath){
        ArrayList<Party> parties = new ArrayList<Party>();
        try{
            BufferedReader PGNreader = new BufferedReader(new FileReader(filePath));
            String currentLine;
            Party currentParty = null;
            while ((currentLine = PGNreader.readLine())!=null){
                if (isNewParty(currentLine)){
                   parties.add(new Party()); 
                   currentParty = parties.get(parties.size()-1);
                }
                if (currentParty!=null)
                    parseLine(currentLine, currentParty);
            }
            
        } catch (Exception e){
            System.out.println(e.toString());
        }
        return parties;
    }
    
    public static void createPGNFile(List<Party> parties, String filePath){
        try{
            FileWriter stream = new FileWriter(filePath);
            BufferedWriter writer = new BufferedWriter(stream);
            for (Party party:parties){
                writer.write(PGNHandler.getAllTagPairs(party));
                writer.write("\n");
                writer.write(PGNHandler.getAllMoves(party));
                writer.write("\n");
            }
            writer.close();
        } catch (Exception e){
            System.out.println(e.toString());
        }
    }
    
    private static String getAllTagPairs(Party party){
        String result = "";
        result += "[Event \"" + party.getEvent() + "\"]\n";
        result += "[Site \"" + party.getSite() + "\"]\n";
        result += "[Date \"" + party.getDate() + "\"]\n";
        result += "[Round \"" + party.getRound() + "\"]\n";
        result += "[White \"" + party.getWhite() + "\"]\n";
        result += "[Black \"" + party.getBlack() + "\"]\n";
        result += "[Result \"" + party.getResult() + "\"]\n";        
        return result;
    }
    
    private static String getAllMoves(Party party){
        String result = "";
        
        for (Move move:party.getMoves()){
            if (move.getPlayer() == Piece.WHITE_PLAYER)
                result += move.getMoveNumber() + ".";
            result += move.getMoveContent() + " ";
            if (move.getMoveNumber()%4==0 && move.getPlayer() == Piece.BLACK_PLAYER)
                result += "\n";
        }
        result += "  " + party.getResult() + "\n";
        
        return result;
    }    

    private static boolean isNewParty(String line) {
        Pattern tagPairPattern = Pattern.compile("\\[(.+) \"(.+)\"\\]");
        Matcher matcher = tagPairPattern.matcher(line);
        if (matcher.find()&& matcher.group(1).toLowerCase().equals("event"))
            return true;
        else
            return false;
    }
    
    private static int getFirstMoveInLineNumber(String line){
        Pattern moveNumberPattern = Pattern.compile("(\\d+)\\.");
        Matcher matcher = moveNumberPattern.matcher(line);
        if (matcher.find())
            return Integer.parseInt(matcher.group(1));
        else
           throw new RuntimeException("Invalid move");
    }

    private static void parseMoves(String line, Party party) {
        String [] moves = line.split("\\d+\\. ?");
        int currentMoveInLineNumber;
        try{
            currentMoveInLineNumber = getFirstMoveInLineNumber(line);
        } catch (RuntimeException e){
            return;
        }
                
        for (String move:moves){
            Pattern movePattern = Pattern.compile("([\\w-\\+]+) ([\\w-\\+]+)");
            Matcher matcher = movePattern.matcher(move);
            if (matcher.find()){
                party.addMove(new Move(party.getId(), 
                        currentMoveInLineNumber,
                        matcher.group(1),
                        Piece.WHITE_PLAYER));
                party.addMove(new Move(party.getId(), 
                        currentMoveInLineNumber,
                        matcher.group(2),
                        Piece.BLACK_PLAYER));   
                currentMoveInLineNumber++;
            } else{//последний ход: только белый ходит
                movePattern = Pattern.compile("([\\w-]+)");
                matcher = movePattern.matcher(move);
                if (matcher.find()){
                    party.addMove(new Move(party.getId(), 
                            currentMoveInLineNumber,
                            matcher.group(1),
                            Piece.WHITE_PLAYER));
                }
            }    
        }       
    }
  
}
