package service;

import java.io.*;
import java.util.*;

import model.News;

/**
 * 
 */
public class NewsService {
    private static List<News> news;
    public void publishResearch() {
        // TODO implement here
    }

    public void generateTopResearch() {
        // TODO implement here
    }

    public void addComment(News n, String s) {
        // TODO implement here
    }
    
    public static List<News> getNews(){
    	return NewsService.news;
    }
}