package model;

public class MasterStudent extends GraduateStudent {

    private boolean thesisRequired;

    public MasterStudent(String login, String password ) {
        super(login, password);
        this.thesisRequired = true;     
        }

    public void setThesisRequirement(boolean thesisRequired) {
        this.thesisRequired = thesisRequired;
    }

    public boolean isThesisRequired() {
        return thesisRequired;
    }

    @Override
    public void submitThesis() {
        // TODO: логика защиты магистерской диссертации (Part C)
    }
}