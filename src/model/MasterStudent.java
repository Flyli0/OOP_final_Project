package model;

public class MasterStudent extends GraduateStudent {

    private boolean thesisRequired;

    public MasterStudent() {
        super();
        this.thesisRequired = true; // По умолчанию для магистрантов диссертация нужна
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