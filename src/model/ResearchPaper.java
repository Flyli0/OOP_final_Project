package model;

import java.util.Date;

public class ResearchPaper {

    private String title;
    private String authors;
    private String content;
    private int citations;
    private Date publicationDate;

    public ResearchPaper(String title, String authors, int citations, Date publicationDate, String content) {
        this.title = title;
        this.authors = authors;
        this.citations = citations;
        this.publicationDate = publicationDate;
    }
    
    public ResearchPaper(String title, String authors, int citations, String content) {
    	this.title = title;
        this.authors = authors;
        this.citations = citations;
        this.content = content;
        this.publicationDate = new Date();
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