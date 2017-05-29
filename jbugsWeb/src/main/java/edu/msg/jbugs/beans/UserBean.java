package edu.msg.jbugs.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.msg.jbugs.dtos.UserDTO;
import edu.msg.jbugs.i18n.LabelProvider;
import edu.msg.jbugs.services.RemoteException;
import edu.msg.jbugs.services.UserFacade;
import edu.msg.jbugs.util.Constants;

@ManagedBean(name = "userBean")
@ViewScoped
public class UserBean implements Serializable {

	private static Logger logger = LoggerFactory.getLogger(UserBean.class);

	private static final long serialVersionUID = 7580127801947224916L;
	@EJB
	private UserFacade ubi;
	private UserDTO user;

	private String firstName;
	private String lastName;
	private String userName;
	private String password1;
	private String password2;
	private String type;

	public List<String> getUserTypes() {
		return userTypes;
	}
	private List<UserDTO> userList = new ArrayList<>();
	private List<String> userTypes = new ArrayList<>();
	
	public void setUserList(List<UserDTO> userList) {
		this.userList = userList;
	}

	public List<UserDTO> getUserList() {
		return userList;
	}

	@PostConstruct
	public void init() {
	
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}


	private boolean showInsertForm;

	public boolean isShowInsertForm() {
		return showInsertForm;
	}

	public void setShowInsertForm(boolean showInsertForm) {
		this.showInsertForm = showInsertForm;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void toggleInsertForm() {
		showInsertForm = !showInsertForm;
	}

	public String processInsert() {

		if (!password1.equals(password2)) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					LabelProvider.getLabel(Constants.ERROR), LabelProvider.getLabel(Constants.WRONGPASS)));
		} else {
			if (ubi.getByUserName(userName) != null) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						LabelProvider.getLabel(Constants.ERROR), LabelProvider.getLabel(Constants.USERNAMEEXISTS)));
			} else {
				user = new UserDTO();
				user.setFirstName(firstName);
				user.setLastName(lastName);
				user.setUserName(userName);
				user.setPassword(password1);
				user.setType(type);
				user.setStatus(true);

				try {
					ubi.insertUser(user);
				} catch (RemoteException e) {
					logger.error(e.getMessage(),e);
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
							LabelProvider.getLabel(Constants.ERROR), LabelProvider.getLabel(Constants.ERROR_ADDUSER)));
				}

				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
						Constants.OK, LabelProvider.getLabel(Constants.USERINSERTED)));
			}
		}

		return Constants.OK;
	}

	public UserDTO getUserById(int id) {
		UserDTO u = new UserDTO();
		try {
			u = ubi.findById(id);
		}catch (RemoteException e) {
			logger.error(e.getMessage(),e);
		}
		return u;
	}

}
