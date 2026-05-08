package model;

import java.io.Serializable;

public abstract class UserDecorator extends User implements Serializable{

	private static final long serialVersionUID = 1L;
	protected ImUser decoratedUser;

    public UserDecorator(User decoratedUser) {
    	super(decoratedUser.getLogin(),decoratedUser.getPassword(),0);
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