package model;

import java.util.List;
import java.util.ArrayList;

public class ResearchProjet {

    private int numOfPages;
    private String topic;
    private List<Researcher> participants;

    public ResearchProjet(String topic, int numOfPages) {
        this.topic = topic;
        this.numOfPages = numOfPages;
        this.participants = new ArrayList<>(); // Обязательно инициализируем список
    }

    public void addParticipant(Researcher researcher) {
        if (!participants.contains(researcher)) {
            participants.add(researcher);
        }
    }

    public String getTopic() {
        return topic;
    }

    public int getNumOfPages() {
        return numOfPages;
    }

    public List<Researcher> getParticipants() {
        return participants;
    }

    @Override
    public String toString() {
        return "Research Project: " + topic + " (Pages: " + numOfPages + ")";
    }
}