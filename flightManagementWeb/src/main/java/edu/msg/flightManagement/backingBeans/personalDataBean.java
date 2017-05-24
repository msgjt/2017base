package edu.msg.flightManagement.backingBeans;

import java.io.IOException;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.msg.flightManagement.i18n.LabelProvider;
import edu.msg.flightManagement.util.Constants;
import edu.msg.flightManagementEjbClient.dtos.UserDTO;
import edu.msg.flightManagementEjbClient.interfaces.RemoteException;
import edu.msg.flightManagementEjbClient.interfaces.UserBeanInterface;

@ManagedBean
@ViewScoped
public class personalDataBean implements Serializable {

	private static Logger logger = LoggerFactory.getLogger(personalDataBean.class);

	private static final long serialVersionUID = -8165452707828870514L;

	private String password1 = null; // new password
	private String password2 = null; // confirmed new password
	private String password = null; // old password/ password to confirm
	private boolean editDisabled = true;
	private boolean editButton = true;
	private boolean cancelButton = false;

	private UserDTO user;

	@EJB
	private UserBeanInterface userBean;

	// for session management only
	private FacesContext facesContext;
	private String userN;
	private HttpSession session;

	private String checkPasswords(String password1, String password2) {
		if (!password1.isEmpty() && !password2.isEmpty()) {
			if (password1.equals(password2)) {
				return Constants.MATCH; // passwords are equal
			} else if (!password1.isEmpty() || !password2.isEmpty()) {
				return Constants.DIFFER; // passwords don't match, but field
											// aren't
				// empty
			}
		}
		return Constants.EMPTY; // fields are empty
	}

	public void setNewPassword() {
		user.setPassword(password1);
	}

	public void passwordsDiffer() {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, LabelProvider.getLabel(Constants.ERROR),
				LabelProvider.getLabel(Constants.WRONGPASS));
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public void confirmChanges() {
		// if one of the new password fields isn't empty then a match will be
		// checked
		user.setPassword(password); // old password set by default
		switch (checkPasswords(password1, password2)) {
		case Constants.DIFFER: {
			passwordsDiffer();
			return;
		}
		case Constants.MATCH: {
			setNewPassword();
			break;
		}
		default:
			break;
		}
		// if new password fields are empty then no new password will be given

		// checking if the username doesn't exist in the database
		if (!(user.getUserName().equals(userN)) && (userBean.getByUserName(user.getUserName()) != null)) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					LabelProvider.getLabel(Constants.ERROR), LabelProvider.getLabel(Constants.USERNAMEEXISTS));
			FacesContext.getCurrentInstance().addMessage(null, message);
			return;
		}
		// checking if currentpassword is correct
		try {
			if (!"".equals(userBean.validateUserNameAndPassword(userN, password))) {
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, Constants.INFO,
						LabelProvider.getLabel(Constants.DATAUPDATED));
				FacesContext.getCurrentInstance().addMessage(null, message);
				// if this point is reached then an update will be commited!
				userBean.updateUser(user, true);
				// update session name with username
				session.setAttribute(Constants.USER_NAME, user.getUserName());
				setEditDisabled(true);
				setEditButton(true);
				setCancelButton(false);
				return;
			} else {
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,
						LabelProvider.getLabel(Constants.WARNING), LabelProvider.getLabel(Constants.INVALIDPASS));
				FacesContext.getCurrentInstance().addMessage(null, message);
				return;
			}
		} catch (RemoteException e) {
			logger.error(e.getMessage(), e);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					LabelProvider.getLabel(Constants.ERROR), LabelProvider.getLabel(Constants.ERROR_UPDATEUSER)));
		}
	}

	@PostConstruct
	public void init() {
		facesContext = FacesContext.getCurrentInstance();
		session = (HttpSession) facesContext.getExternalContext().getSession(false);
		userN = session.getAttribute(Constants.USER_NAME).toString();
		try {
			user = userBean.getByUserName(userN);
		} catch (RemoteException e) {
			logger.error(e.getMessage(), e);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					LabelProvider.getLabel(Constants.ERROR), LabelProvider.getLabel(Constants.ERROR_GETUSER)));
		}

	}

	public void enableEditButton() {
		// disable Cancel Button
		init();
		cancelButton = false;
		editButton = true;
		setEditDisabled(true);
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		try {
			ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
		} catch (IOException e) {
			logger.error(Constants.PAGENOTFOUND + ((HttpServletRequest) ec.getRequest()).getRequestURI().toString(), e);
		}
	}

	public void disableEditButton() {
		// enable Cancel Button
		setEditDisabled(false);
		editButton = false;
		cancelButton = true;
	}

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

	public String getPassword1() {
		return password1;
	}

	public void setPassword1(String password1) {
		this.password1 = password1;
	}

	public String getPassword2() {
		return password2;
	}

	public void setPassword2(String password2) {
		this.password2 = password2;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isEditDisabled() {
		return editDisabled;
	}

	public void setEditDisabled(boolean editDisabled) {
		this.editDisabled = editDisabled;
	}

	public boolean isEditButton() {
		return editButton;
	}

	public void setEditButton(boolean editButton) {
		this.editButton = editButton;
	}

	public boolean isCancelButton() {
		return cancelButton;
	}

	public void setCancelButton(boolean cancelButton) {
		this.cancelButton = cancelButton;
	}

}
