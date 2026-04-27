package model;

public abstract class UserDecorator implements ImUser {
    protected ImUser decoratedUser;

    public UserDecorator(ImUser decoratedUser) {
        this.decoratedUser = decoratedUser;
    }

    @Override
    public int getId() {
        return decoratedUser.getId();
    }

    @Override
    public String getName() {
        return decoratedUser.getName();
    }

    @Override
    public void setLogin(String login) {
        decoratedUser.setLogin(login);
    }
}