package model;

import java.io.Serializable;
import java.util.Date;

public class ResearchPaper implements Serializable{

    private String title;
    private String authors;
    private String content;
    private int citations;
    private int pages;
    private Date publicationDate;

    public ResearchPaper(String title, String authors, int citations, Date publicationDate, String content,int pages) {
        this.title = title;
        this.authors = authors;
        this.citations = citations;
        this.publicationDate = publicationDate;
        this.pages = pages;
    }
    
    public ResearchPaper(String title, String authors, int citations, String content, int pages) {
    	this.title = title;
        this.authors = authors;
        this.citations = citations;
        this.content = content;
        this.publicationDate = new Date();
        this.pages = pages;
    }

    public String getCitation(CitationFormat format) {
        if (format == CitationFormat.APA) {
            return authors + ". (" + publicationDate + "). " + title + ".";
        }
        return "@article{" + title + ", author={" + authors + "}}";
    }

    public String getTitle() {
        return title;
    }
    
    public int getPages() {
    	return this.pages;
    }
    
    public void setPages(int pages) {
    	this.pages = pages;
    }

    public int getCitations() {
        return citations;
    }
    
    public String getContent() {
    	return this.content;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    @Override
    public String toString() {
        return "Paper: " + title + " (Citations: " + citations + ")";
    }
}