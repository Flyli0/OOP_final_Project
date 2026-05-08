package model;

import java.util.List;
import java.util.ArrayList;

public class ResearchProject {

    private int numOfPages;
    private String topic;
    private List<ResearcherDecorator> participants;
    private ArrayList<ResearchPaper> content;
    private int citations;

    public ResearchProject(String topic, int numOfPages) {
        this.topic = topic;
        this.numOfPages = numOfPages;
        this.participants = new ArrayList<>(); 
        this.content = new ArrayList<ResearchPaper>();
    }

    public void addParticipant(ResearcherDecorator researcher) {
        if (!participants.contains(researcher)) {
            participants.add(researcher);
        }
    }

    public String getTopic() {
        return topic;
    }
    
    public void citate() {
    	this.citations+=1;
    }
    
    public int getCitations() {
    	return this.citations;
    }
    
    public int getNumOfPages() {
        return numOfPages;
    }
    
    public List<ResearchPaper> getContent(){
    	return this.content;
    }
    
    public List<User> getParticipants() {
    	List<User> authors =
    		    participants.stream()
    		        .map(r -> (User) r)
    		        .toList();
    	return authors;
    }
    
    public void addPaper(ResearchPaper rp) {
    	this.content.add(rp);
    }

    @Override
    public String toString() {
        return "Research Project: " + topic + " (Pages: " + numOfPages + ")";
    }
}