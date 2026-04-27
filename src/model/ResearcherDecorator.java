package model;

import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;

public class ResearcherDecorator extends UserDecorator implements Researcher {

    private List<ResearchPaper> papers;
    // Оставил опечатку Projet, как у тебя в названии файла слева
    private List<ResearchProjet> projects;

    public ResearcherDecorator(ImUser decoratedUser) {
        super(decoratedUser);
        this.papers = new ArrayList<>();
        this.projects = new ArrayList<>();
    }

    @Override
    public void conductResearch() {
        // TODO: логика проведения исследования
    }

    @Override
    public double calculateH() {
        return 0.0;
    }

    @Override
    public void printPapers() {
        // Базовый метод печати
    }

    public void addPaper(ResearchPaper p) {
        this.papers.add(p);
    }

    public void addProject(ResearchProjet p) {
        this.projects.add(p);
    }
}