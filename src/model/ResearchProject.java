package model;

import java.util.List;
import java.util.ArrayList;

public class ResearchProject {

    private int numOfPages;
    private String topic;
    private List<Researcher> participants;
    private ArrayList<ResearchPaper> content;

    public ResearchProject(String topic, int numOfPages) {
        this.topic = topic;
        this.numOfPages = numOfPages;
        this.participants = new ArrayList<>(); 
        
    }

    public void addParticipant(Researcher researcher) {
        if (!participants.contains(researcher)) {
            participants.add(researcher);
        }
    }

    public String getTopic() {
        return topic;
    }

    public int getNumOfPages() {
        return numOfPages;
    }

    public List<Researcher> getParticipants() {
        return participants;
    }
    
    public void addPaper(ResearchPaper rp) {
    	this.content.add(rp);
    }

    @Override
    public String toString() {
        return "Research Project: " + topic + " (Pages: " + numOfPages + ")";
    }
}