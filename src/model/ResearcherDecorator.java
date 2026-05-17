package model;

import java.util.List;

import config.DbContext;
import service.ResearchPaperComparator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class ResearcherDecorator extends UserDecorator implements Researcher, Serializable {

    private List<ResearchPaper> papers;
    private List<ResearchProject> projects;

    public ResearcherDecorator(User decoratedUser) {
        super(decoratedUser);
        this.papers = new ArrayList<>();
        this.projects = new ArrayList<>();
    }

    @Override
    public void conductResearch(String content, String title, int pages) {
    	ResearchPaper res = new ResearchPaper(title, this.getName(), 0, content, pages);
    	papers.add(res);

        System.out.println("Research paper \"" + title + "\" published by " + this.getName());
    }


    //Calculates the h-index: the largest h such that h papers have >= h citations.
    //Algorithm: sort citation counts descending, then find the crossing point.
    @Override
    public double calculateH() {
        if(papers.isEmpty()) {
            return 0;
        }

        List<Integer> citations = new ArrayList<>();
        for(ResearchPaper rp : papers) {
            citations.add(rp.getCitations());
        }

        Collections.sort(citations, Collections.reverseOrder());
        int h = 0;

        for (int i = 0; i < citations.size(); i++) {
            if (citations.get(i) >= i + 1) {
                h = i + 1;
            } else {
                break;
            }
        }

        return h;
    }

    @Override
    public void printPapers() {
        if(papers.isEmpty()) {
            System.out.println(this.getName() + " has not published any research papers yet.");
            return;
        }

       System.out.println("Research papers published by " + this.getName() + ":");   
    	for(ResearchPaper rp: papers) {
    		System.out.println(rp);
    	}
    }


    public void printPapers(ResearchPaperComparator comparator) {
        if(papers.isEmpty()) {
            System.out.println(this.getName() + " has not published any research papers yet.");
            return;
        }

        List<ResearchPaper> sortedPapers = new ArrayList<>(papers);
        sortedPapers.sort( comparator);
        


        System.out.println("Research papers published by " + this.getName() + " sorted " 
        + comparator.toString() + ":");
        for(ResearchPaper rp: sortedPapers) {

            System.out.printf("  %-45s | Citations: %3d | Pages: %3d | %s%n",
                    rp.getTitle(),
                    rp.getCitations(),
                    rp.getPages(),
                    rp.getPublicationDate() != null ? rp.getPublicationDate() : "N/A");
        }
    }

    public void addPaper(ResearchPaper p) {
        this.papers.add(p);
    }

    public void addProject(ResearchProject p) {
        this.projects.add(p);
    }
    
    public void closeProject(String topic) {
    	ResearchProject rproj = new ResearchProject(topic, this.papers.size());
    	for(ResearchPaper rp: papers) {
    		rproj.addPaper(rp);
    	}

    	this.papers.clear();
       

    }
    
    public void pendProject(String topic) {
    	ResearchProject rp = this.projects.stream().filter(project -> project.getTopic().equals(topic)).findFirst().orElse(null);
    	if(rp==null) {
    		System.out.println("You don't have such project");
    		return;
    	}
    	DbContext.getInstance().pendProject(rp);
    }
}