package service;

import java.io.*;
import java.util.*;
import java.util.Comparator;

import config.DbContext;
import model.Comment;
import model.ImUser;
import model.News;
import model.ResearchProject;
import model.User;

public class NewsService {

    public static void publishResearch(ResearchProject rp) {
        News n = new News(rp.getTopic(), rp);
        DbContext.getInstance().addNews(n);
    }
    
    public static News publishNews(String header, String topic, String content, User author) {
        News n = new News(header,topic,content,author);
        DbContext.getInstance().addNews(n);
        return n;
    }

    public static News generateTopResearch() {
    	return NewsService.getNews().stream()
    	        .filter(newsL -> newsL.getStatus())
    	        .max((x, y) ->
                Integer.compare(x.getCitations(), y.getCitations()))
    	        .orElse(null);
    }

    public static void addComment(News n, String s, User author) {
    	Comment com = new Comment(author,s);
    	n.addComment(com);
    	DbContext.saveNews();
    }
    
    public static List<News> getNews(){
    	return DbContext.getInstance().allNews();
    }
}

