package service;

import java.io.*;
import java.util.*;

import model.ResearchProject;
import model.User;

/**
 * 
 */
public class ResearchJournalObserver {

    private static List<ResearchProject> researchProjects;
    private static List<User> subscribers;
    
    private ResearchJournalObserver() {
    	researchProjects = new ArrayList<ResearchProject>();
    	subscribers = new ArrayList<User>();
    }

    public static void addSubscriber(User u) {
        subscribers.add(u);
    }
   
    public static void removeSubscriber(User u) {
        if(subscribers.contains(u)){
            subscribers.remove(u);
        } else {
            System.out.println("User is not a subscriber.");
        }     
    }

    public static void notify( String message) {
        for(User subscriber : subscribers) {
            subscriber.update(message);
        }  
    }
    
    public static List<User> getSubscribers() {
        return subscribers;
    }

    public void addResearch(ResearchProject rp) {
        researchProjects.add(rp);
        notify("New research project added: " + rp.getTopic());
    }
}