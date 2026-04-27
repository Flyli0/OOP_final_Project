package model;

import java.util.List;
import java.util.ArrayList;

public abstract class GraduateStudent extends Student {

    private Teacher advisor;
    private String thesisTitle;
    private String researchTopic;
    public List<ResearchPaper> publishedWorks;
    private ResearchProjet research;
    private Researcher researchSupervisor;

    public GraduateStudent() {
        // Инициализируем список, чтобы избежать ошибки NullPointerException
        this.publishedWorks = new ArrayList<>();
    }

    public abstract void submitThesis();

    public void setThesis(String thesisTitle) {
        this.thesisTitle = thesisTitle;
    }

    public void setResearchTopic(String researchTopic) {
        this.researchTopic = researchTopic;
    }

    public String getThesisTitle() {
        return thesisTitle;
    }

    public String getResearchTopic() {
        return researchTopic;
    }

    @Override
    public String toString() {
        return "GraduateStudent{" +
                "thesisTitle='" + thesisTitle + '\'' +
                ", researchTopic='" + researchTopic + '\'' +
                '}';
    }
}