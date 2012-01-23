/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chessrecognizer;

import java.util.ArrayList;
//import java.util.Date;
import java.sql.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Lenkas
 */
public class Party {   
    private int id;
    private String event;
    private String site;
    private String round;
    private String white;
    private String black;
    private String date;
    private String result;
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
    
//    public String getStingDate(){
//        return date.getYear()+"."+date.getMonth()+"."+date.getDay();
//    }

    
    public void addMove(Move move){
        moves.add(move);
    }
    
    public Party() {
        this.id = -1;
    }

    public Party(String event, String site, String round, String white, String black, String date, String result) {
        this.event = event;
        this.site = site;
        this.round = round;
        this.white = white;
        this.black = black;
        this.date = date;
        this.result = result;
    }

    public Party(int id, String event, String site, String round, String white, String black, String date, String result) {
        this.id = id;
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

    public String getDate() {
        return date;
    }
    
    public String getEvent() {
        return event;
    }

    public int getId() {
        return id;
    }

    public String getResult() {
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

    public void setDate(String date) {
        this.date = date;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setResult(String result) throws RuntimeException{
        if (result.equals("1/2-1/2")||result.equals("1-0")||result.equals("0-1")||result.equals("*")){                    
            this.result = result;
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
        return "ID " + id + ", " + event + ", раунд " + round + ", " + date;
    }

//    public void setDate(String date) throws RuntimeException{
//        Pattern datePattern = Pattern.compile("(\\d+)\\.(\\d+)\\.(\\d+)");
//        Matcher matcher = datePattern.matcher(date);
//        if (matcher.find())
//            setDate(new Date(Integer.parseInt(matcher.group(1)), 
//                             Integer.parseInt(matcher.group(3)), 
//                             Integer.parseInt(matcher.group(2))));
//        else
//            date = null;
//            //throw new RuntimeException("Invalid date");
//    }
//    
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
