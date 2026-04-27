package model;

import java.util.Date;

public class ResearchPaper {

    private String title; // ВОТ ЭТА СТРОЧКА БЫЛА ПРОПУЩЕНА!
    private String authors;
    private int citations;
    private Date publicationDate;
    private int pages;

    public ResearchPaper(String title, String authors, int citations, Date publicationDate, int pages) {
        this.title = title;
        this.authors = authors;
        this.citations = citations;
        this.publicationDate = publicationDate;
        this.pages = pages;
    }

    public String getCitation(CitationFormat format) {
        // Если APA горит красным, проверь файл CitationFormat.java — там должен быть вариант APA
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

    public Date getPublicationDate() {
        return publicationDate;
    }

    @Override
    public String toString() {
        return "Paper: " + title + " (Citations: " + citations + ")";
    }
}