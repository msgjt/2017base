package edu.msg.jbugs.beans;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.msg.jbugs.i18n.LabelProvider;
import edu.msg.jbugs.services.RemoteException;
import edu.msg.jbugs.services.UserFacade;
import edu.msg.jbugs.util.Constants;

@javax.faces.bean.ManagedBean
@RequestScoped
public class LoginBean implements Serializable {

	private static Logger logger = LoggerFactory.getLogger(LoginBean.class);
	private static final long serialVersionUID = 2969127726268121618L;

	private String userName;
	private String password;
	private String userType = "";
	private static final String FAIL = "Fail";
	private static final String OK = "OK";
	private static final String MENUXHTML = "menu.xhtml";
	private static final String MENUFILE = "menuFile";

	@EJB
	private UserFacade userBF;

	public String processLogin() {
		try {
			userType = userBF.validateUserNameAndPassword(userName, password).toLowerCase();
		} catch (RemoteException e) {
			logger.error(e.getMessage(),e);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					LabelProvider.getLabel(Constants.ERROR), LabelProvider.getLabel(Constants.ERROR_VALIDATELOGIN)));
		}

		if (!userType.isEmpty()) {
			return handleLoginSuccess();
		} else {
			return handleLoginFailed();
		}
	}

	private String handleLoginFailed() {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
				LabelProvider.getLabel(Constants.INVALIDCREDENTIALS), FAIL));
		userName = "";
		return "loginFailed";
	}

	private String handleLoginSuccess() {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, OK, LabelProvider.getLabel(Constants.LOGGEDIN)));
		HttpSession session = getSession();
		session.setAttribute(Constants.USER_TYPE, userType);
		session.setAttribute(Constants.USER_NAME, userName);
		session.setAttribute(MENUFILE, userType.replaceAll("\\s", "") + MENUXHTML);
		return "loginOk";
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
