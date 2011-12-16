/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chessrecognizer;

import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Lenkas
 */
public class Party {   
    public enum PartyResult{WHITE_WIN, BLACK_WIN, DRAW, NO_RESULT};

    private int id;
    private String event;
    private String site;
    private String round;
    private String white;
    private String black;
    private Date date;
    private PartyResult result;
    private ArrayList<Move> moves = new ArrayList<Move>();//нумерация с 1
    private ArrayList<View> views = new ArrayList<View>();//нумерация с 0

    public ArrayList<Move> getMoves() {
        return moves;
    }
    
    public Move getMove(int moveSequenceNumber) {
        return moves.get(moveSequenceNumber-1);
    }    

    public ArrayList<View> getViews() {
        return views;
    }
    
    public View getView(int viewSequenceNumber) {
        return views.get(viewSequenceNumber);
    }    

    
    public void addMove(Move move){
        moves.add(move);
    }
    
    public Party() {
        this.id = -1;
    }

    public Party(String event, String site, String round, String white, String black, Date date, PartyResult result) {
        this.event = event;
        this.site = site;
        this.round = round;
        this.white = white;
        this.black = black;
        this.date = date;
        this.result = result;
    }

    
    public String getBlack() {
        return black;
    }

    public Date getDate() {
        return date;
    }

    public String getEvent() {
        return event;
    }

    public int getId() {
        return id;
    }

    public PartyResult getResult() {
        return result;
    }

    public String getRound() {
        return round;
    }

    public String getSite() {
        return site;
    }

    public String getWhite() {
        return white;
    }

    public void setBlack(String black) {
        this.black = black;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setResult(PartyResult result) {
        if ((result==PartyResult.WHITE_WIN)||(result==PartyResult.BLACK_WIN)||(result==PartyResult.DRAW))
            this.result = result;
        else    
            throw new RuntimeException("Invalid result");
    }

    public void setResult(String partyResult) throws RuntimeException{
        if (partyResult.equals("1/2-1/2")){                    
            setResult(PartyResult.DRAW);
            return;
        }
        if (partyResult.equals("1-0")){
            setResult(PartyResult.WHITE_WIN);
            return;
        }                      
        if (partyResult.equals("0-1")){
            setResult(PartyResult.BLACK_WIN);
            return;
        }                          
        if (partyResult.equals("*")){
            setResult(PartyResult.NO_RESULT); 
            return;
        }                
         
        throw new RuntimeException("Invalid party result");
    }
    
    public void setRound(String round) {
        this.round = round;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public void setWhite(String white) {
        this.white = white;
    }

    
    @Override
    public String toString() {
        return "Party{" + "id=" + id + ", event=" + event + ", site=" + site + ", round=" + round + ", white=" + white + ", black=" + black + ", date=" + date + ", result=" + result + '}';
    }

    void setDate(String date) throws RuntimeException{
        Pattern datePattern = Pattern.compile("(\\d+)\\.(\\d+)\\.(\\d+)");
        Matcher matcher = datePattern.matcher(date);
        if (matcher.find())
            setDate(new Date(Integer.parseInt(matcher.group(1)), 
                             Integer.parseInt(matcher.group(3)), 
                             Integer.parseInt(matcher.group(2))));
        else
            throw new RuntimeException("Invalid date");
    }
    
    public void generateViews(){
        View initialView = new View();
        initialView.setInitialView();
        views.add(initialView);
        
        for (Move move:moves){
            View view = new View();
            view.setMoveView(move, views.get(views.size()-1));
            views.add(view);
        }
        
    }
    
}
