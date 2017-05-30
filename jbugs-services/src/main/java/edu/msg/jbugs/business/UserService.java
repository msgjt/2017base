package edu.msg.jbugs.business;

import javax.ejb.Local;

@Local
public interface UserService {
	String login(String userName, String password);
}
