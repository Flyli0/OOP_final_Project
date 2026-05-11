package service;

import java.io.*;
import java.util.*;

import model.ResearchProject;
import model.User;

/**
 * 
 */
public class ResearchJournalObserver {

    /**
     * Default constructor
     */
    public ResearchJournalObserver() {
    }

    /**
     * 
     */
    private List<ResearchProject> researchProjects;

    /**
     * 
     */
    private List<User> subscribers;

    /**
     * @param User u 
     * @return
     */
    public void addSubscriber(User u) {
        // TODO implement here
        subscribers.add(u);
    }
    /**
     * @param User u 
     * @return
     */

    public void removeSubscriber(User u) {
        // TODO implement here
        if(subscribers.contains(u)){
            subscribers.remove(u);
        } else {
            System.out.println("User is not a subscriber.");
        } 

        
    }

    /**
     * @param String message 
     * @return
     */
    public void notify( String message) {
        for(User subscriber : subscribers) {
            subscriber.update(message);
        }
        
    }

    /**
     * @return
     */
    public List<User> getSubscribers() {
        // TODO implement here
        return subscribers;
    }

    /**
     * @param ResearchProjet rp 
     * @return
     */
    public void addResearch(ResearchProject rp) {
        researchProjects.add(rp);
        notify("New research project added: " + rp.getTopic());
    }

}