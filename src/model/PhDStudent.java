package model;

public class PhDStudent extends GraduateStudent {

    private String dissertationTopic;
    private boolean qualifyingExamPassed;

    public PhDStudent(String name, String surname) {
        super(name, surname);
        this.qualifyingExamPassed = false; 
    }
    
    public PhDStudent(String name, String surname, boolean examPass) {
    	super(name,surname);
    	this.qualifyingExamPassed = examPass;
    }

    public void setDissertationTopic(String dissertationTopic) {
        this.dissertationTopic = dissertationTopic;
    }

    public String getDissertationTopic() {
        return dissertationTopic;
    }

    public boolean isQualifyingExamPassed() {
        return qualifyingExamPassed;
    }

    public void passQualifyingExam() {
        this.qualifyingExamPassed = true;
    }

    public void publishDissertation() {
        // TODO: логика публикации диссертации
    }

    @Override
    public void submitThesis() {
        // TODO: логика защиты PhD диссертации
    }
}