package model;

import java.util.List;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class News implements Serializable{

    private String id;
    private String header;
    private String topic; 
    private String content;
    private List<User> authors;
    private boolean isPinned;
    private List<Comment> comments;
    private static int idCounter = 0;
    private boolean is_research;
    private int citations;
    

    public News(String header, String topic, String content, User author) {
    	this.authors = new ArrayList<User>();
    	int cur_id = ++idCounter;
    	String current_date = new Date().toLocaleString();
    	this.id = current_date + "NEW" + cur_id;
        this.header = header;
        this.topic = topic;
        this.content = content;
        this.authors.add(author);
        
        this.comments = new ArrayList<>();
        this.is_research = false;
    }
    
    public News(String topic, ResearchProject content) {
    	this.authors = new ArrayList<User>();
    	int cur_id = ++idCounter;
    	String current_date = new Date().toLocaleString();
    	this.id = current_date + "NEW" + cur_id;
    	int counter = 1;
    	List<ResearchPaper> pages = content.getContent();
    	for(ResearchPaper page : pages) {
    		this.content += page.getContent();
    		this.content += "\n----------------------------------" + counter + "----------------------------------";
    		counter+=1;
    	}
    	this.topic = "Research: " + topic;
    	this.authors = content.getParticipants();
    	this.is_research = true;
    	this.citations = content.getCitations();
    	this.isPinned = topic.equalsIgnoreCase("Research");
    }

    public String getId() {
        return id;
    }

    public String getHeader() {
        return header;
    }

    public String getTopic() {
        return topic;
    }

    public String getContent() {
        return content;
    }

    public List<User> getAuthors() {
        return authors;
    }

    public boolean isPinned() {
        return isPinned;
    }

    public void setPinned(boolean pinned) {
        isPinned = pinned;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void addComment(Comment comment) {
        this.comments.add(comment);
    }
    
    public boolean getStatus() {
    	return this.is_research;
    }
    
    public int getCitations() {
    	return this.citations;
    }
    
    @Override 
    public String toString() {
    	return "NEWS: " + this.getId() + "\n" + this.getTopic();
    }
}