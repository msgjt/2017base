package edu.msg.flightManagement.backingBeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.primefaces.event.RowEditEvent;

import edu.msg.flightManagement.i18n.LabelProvider;
import edu.msg.flightManagement.util.Constants;
import edu.msg.flightManagementEjbClient.dtos.CompanyDTO;
import edu.msg.flightManagementEjbClient.dtos.UserDTO;
import edu.msg.flightManagementEjbClient.interfaces.CompanyBeanInterFace;
import edu.msg.flightManagementEjbClient.interfaces.RemoteException;
import edu.msg.flightManagementEjbClient.interfaces.UserBeanInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ManagedBean(name = "userBean")
@ViewScoped
public class UserBean implements Serializable {

	private static Logger logger = LoggerFactory.getLogger(UserBean.class);

	private static final long serialVersionUID = 7580127801947224916L;
	@EJB
	private UserBeanInterface ubi;
	@EJB
	private CompanyBeanInterFace cb;
	private UserDTO user;

	private String firstName;
	private String lastName;
	private String userName;
	private String password1;
	private String password2;
	private String type;
	private CompanyDTO company;

	public CompanyDTO getCompany() {
		return company;
	}

	public void setCompany(CompanyDTO company) {
		this.company = company;
	}

	public List<String> getUserTypes() {
		return userTypes;
	}

	private List<UserDTO> userList = new ArrayList<>();
	private List<CompanyDTO> companyList = new ArrayList<>();
	private List<String> userTypes = new ArrayList<>();

	public List<CompanyDTO> getCompanyList() {
		return companyList;
	}

	public void setCompanyList(List<CompanyDTO> companyList) {
		this.companyList = companyList;
	}

	public void setUserList(List<UserDTO> userList) {
		this.userList = userList;
	}

	public List<UserDTO> getUserList() {
		return userList;
	}

	@PostConstruct
	public void initBean() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);

		String userType = session.getAttribute(Constants.USER_TYPE).toString();
		String uName = session.getAttribute(Constants.USER_NAME).toString();

		if (userType.equals(Constants.ADMINISTRATOR_L)) {
			try {
				companyList = cb.getAvailableCompanies();
			} catch (RemoteException e) {
				logger.error(e.getMessage(),e);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						LabelProvider.getLabel(Constants.ERROR), LabelProvider.getLabel(Constants.ERROR_GETAVAILABLECOMPANIES)));
			}

			userTypes = new ArrayList<>(Arrays.asList(Constants.ADMINISTRATOR, Constants.COMPANY_MANAGER,
					Constants.FLIGHT_MANAGER, Constants.CREW));
			try {
				userList = ubi.getUsers();
			} catch (RemoteException e) {
				logger.error(e.getMessage(),e);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						LabelProvider.getLabel(Constants.ERROR), LabelProvider.getLabel(Constants.ERROR_GETUSERS)));
			}

		} else if (userType.equals(Constants.COMPANY_MANAGER_L)) {
			try {
				int cid = ubi.getByUserName(uName).getCompany().getCompanyId();

				CompanyDTO selectedCompany = cb.getByCompanyId(cid);
				companyList.add(selectedCompany);
				userTypes = new ArrayList<>(Arrays.asList(Constants.CREW, Constants.FLIGHT_MANAGER));

				userList = ubi.getUsersByTypeAndCompany(Constants.CREW, selectedCompany, true);
				userList.addAll(ubi.getUsersByTypeAndCompany(Constants.CREW, selectedCompany, false));
				userList.addAll(ubi.getUsersByTypeAndCompany(Constants.FLIGHT_MANAGER, selectedCompany, true));
				userList.addAll(ubi.getUsersByTypeAndCompany(Constants.FLIGHT_MANAGER, selectedCompany, false));
			} catch (RemoteException e) {
				logger.error(e.getMessage(),e);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						LabelProvider.getLabel(Constants.ERROR), LabelProvider.getLabel(Constants.ERROR_GETUSERS)));
			}

		}
		showInsertForm = false;
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

	public List<CompanyDTO> companiesList() {
		return companyList;
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
				if (type.equals(Constants.ADMINISTRATOR)) {
					user.setCompany(null);
				} else {
					user.setCompany(company);
				}
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
	
	public void restoreFields(UserDTO selected) {
		UserDTO a = getUserById(selected.getUserId());
		selected.setCompany(a.getCompany());
		selected.setUserName(a.getUserName());
		selected.setFirstName(a.getFirstName());
		selected.setLastName(a.getFirstName());
		selected.setType(a.getType());
		selected.setStatus(a.getStatus());
	}
	public void onRowEdit(RowEditEvent event) {
		
		UserDTO selectedUser = (UserDTO) event.getObject();
		String userName = getUserById(selectedUser.getUserId()).getUserName();
		if(!userName.equals(selectedUser.getUserName()) && ubi.getByUserName(selectedUser.getUserName()) !=null) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, LabelProvider.getLabel(Constants.ERROR),
					LabelProvider.getLabel(Constants.ERROR_USEREXISTS)));
			restoreFields(selectedUser);
			return;
		}
		

		if (selectedUser.getType().equals(Constants.ADMINISTRATOR)) {
			selectedUser.setCompany(null);
		}

		try {
			if (ubi.findById(selectedUser.getUserId()).getStatus() == true && selectedUser.getStatus() == false) {
				ubi.deleteUserById(selectedUser.getUserId());
			} else {
				ubi.updateUser(selectedUser, false);
			}
		} catch (RemoteException e) {
			logger.error(e.getMessage(),e);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
					LabelProvider.getLabel(Constants.ERROR_UPDATEUSER)));
		}
		
		FacesMessage msg = new FacesMessage(LabelProvider.getLabel(Constants.USEREDITED), selectedUser.getUserName());
		FacesContext.getCurrentInstance().addMessage(null, msg);

	}

	public void onRowCancel(RowEditEvent event) {
		FacesMessage msg = new FacesMessage(LabelProvider.getLabel(Constants.USEREDITCANCEL),
				((UserDTO) event.getObject()).getUserName());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
}
