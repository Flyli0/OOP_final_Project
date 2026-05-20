package service;

import java.io.*;
import java.util.*;

import config.DbContext;
import model.News;
import model.ResearchProject;
import model.User;

/**
 * 
 */
public class ResearchJournalObserver {

    private static List<ResearchProject> researchProjects;
    private static List<User> subscribers;
    public static final ResearchJournalObserver RJO = new ResearchJournalObserver();
    
    private ResearchJournalObserver() {
    	researchProjects = DbContext.getInstance().allPublished();
    	subscribers = DbContext.getInstance().allSubscribers();
    }

    public static void addSubscriber(User u) {
        DbContext.getInstance().addSubscriber(u);
    }
   
    public static void removeSubscriber(User u) {
        if(subscribers.contains(u)){
            subscribers.remove(u);
            DbContext.saveSubscribers();
        } else {
            System.out.println("User is not a subscriber.");
        }     
    }

    public static void notify( String message) {
        for(User subscriber : subscribers) {
            subscriber.update(message);
        }  
    }
    
    public static ResearchJournalObserver getInstance() {
    	return RJO;
    }
    
    public static List<User> getSubscribers() {
        return subscribers;
    }

    public static void addResearch(ResearchProject rp) {
        DbContext.getInstance().addPublished(rp);
        notify("New research project added: " + rp.getTopic());
    }
}