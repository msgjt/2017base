/**
 *  Copyright (C) 2017 java training
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package edu.msg.jbugs.beans;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.msg.jbugs.business.RemoteException;
import edu.msg.jbugs.business.UserService;
import edu.msg.jbugs.i18n.LabelProvider;
import edu.msg.jbugs.util.MessageConstants;
import edu.msg.jbugs.util.NavigationConstants;

@ManagedBean
@RequestScoped
public class LoginBean implements Serializable {
	private static final long serialVersionUID = 2969127726268121618L;
	private static Logger LOG = LoggerFactory.getLogger(LoginBean.class);
	private static final String FAIL = "Fail";
	private static final String OK = "OK";
	private static final String MENUXHTML = "menu.xhtml";
	private static final String MENUFILE = "menuFile";
	
	private String userName;
	private String password;
	private String userType = "";

	@EJB
	private UserService userService;
	
	public String processLogin() {
		try {
			userType = userService.login(userName, password);
		} catch (RemoteException e) {
			LOG.error(e.getMessage(),e);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					LabelProvider.getLabel(MessageConstants.ERROR), LabelProvider.getLabel(MessageConstants.ERROR_VALIDATELOGIN)));
		}

		if (!userType.isEmpty()) {
			return handleLoginSuccess();
		} else {
			return handleLoginFailed();
		}
	}

	private String handleLoginFailed() {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
				LabelProvider.getLabel(MessageConstants.INVALIDCREDENTIALS), FAIL));
		userName = "";
		return NavigationConstants.LOGIN_FAILED;
	}

	private String handleLoginSuccess() {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, OK, LabelProvider.getLabel(MessageConstants.LOGGEDIN)));
		HttpSession session = getSession();
		session.setAttribute(MessageConstants.USER_TYPE, userType);
		session.setAttribute(MessageConstants.USER_NAME, userName);
		//session.setAttribute(MENUFILE, userType.replaceAll("\\s", "") + MENUXHTML);
		session.setAttribute(MENUFILE, "default-" + MENUXHTML);
		return NavigationConstants.LOGIN_OK;
	}

	private HttpSession getSession() {
		return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
