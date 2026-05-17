package service;

import java.io.*;
import java.util.*;

import model.ResearchProject;
import model.User;

/**
 * 
 */
public class ResearchJournalObserver {

    
    public ResearchJournalObserver() {
    }

    private List<ResearchProject> researchProjects;

    private List<User> subscribers;

    
    public void addSubscriber(User u) {
        subscribers.add(u);
    }
   

    public void removeSubscriber(User u) {
        if(subscribers.contains(u)){
            subscribers.remove(u);
        } else {
            System.out.println("User is not a subscriber.");
        } 

        
    }

   
    public void notify( String message) {
        for(User subscriber : subscribers) {
            subscriber.update(message);
        }
        
    }

    
    public List<User> getSubscribers() {
        return subscribers;
    }

    
    public void addResearch(ResearchProject rp) {
        researchProjects.add(rp);
        notify("New research project added: " + rp.getTopic());
    }

}