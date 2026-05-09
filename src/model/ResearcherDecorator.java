package model;

import java.util.List;

import config.DbContext;

import java.io.Serializable;
import java.util.ArrayList;
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
    	ResearchPaper res = new ResearchPaper(title, this.getName(), 5, content, pages);
    	papers.add(res);
    }

    @Override
    public double calculateH() {
        return 0.0;
    }

    @Override
    public void printPapers() {
    	for(ResearchPaper rp: papers) {
    		System.out.println(rp);
    	}
    }

    public void addPaper(ResearchPaper p) {
        this.papers.add(p);
    }

    public void addProject(ResearchProject p) {
        this.projects.add(p);
    }
    
    public void closeProject(String topic) {
    	ResearchProject rproj = new ResearchProject(topic,this.papers.size());
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