package edu.msg.flightManagement.backingBeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.msg.flightManagement.i18n.LabelProvider;
import edu.msg.flightManagement.util.Constants;
import edu.msg.flightManagementEjbClient.dtos.CompanyDTO;
import edu.msg.flightManagementEjbClient.dtos.FlightDTO;
import edu.msg.flightManagementEjbClient.dtos.UserDTO;
import edu.msg.flightManagementEjbClient.interfaces.CompanyBeanInterFace;
import edu.msg.flightManagementEjbClient.interfaces.FlightBeanInterface;
import edu.msg.flightManagementEjbClient.interfaces.RemoteException;
import edu.msg.flightManagementEjbClient.interfaces.UserBeanInterface;

@ManagedBean(name = "crewAssignmentBean")
@ViewScoped
public class CrewAssignmentBean implements Serializable {

	private static final long serialVersionUID = 4193263513623035468L;

	private static Logger logger = LoggerFactory.getLogger(CrewAssignmentBean.class);

	@EJB
	private FlightBeanInterface fb;
	@EJB
	private UserBeanInterface ub;
	@EJB
	private CompanyBeanInterFace cb;

	public List<FlightDTO> getFlightList() {
		return flightList;
	}

	public void setFlightList(List<FlightDTO> flightList) {
		this.flightList = flightList;
	}

	public List<UserDTO> getUserList() {
		return userList;
	}

	public void setUserList(List<UserDTO> userList) {
		this.userList = userList;
	}

	private List<FlightDTO> flightList;
	private List<UserDTO> userList;
	private List<UserDTO> selectedUsersAdd;
	private List<CompanyDTO> companyList = new ArrayList<>();

	public List<CompanyDTO> getCompanyList() {
		return companyList;
	}

	public void setCompanyList(List<CompanyDTO> companyList) {
		this.companyList = companyList;
	}

	public List<UserDTO> getSelectedUsersAdd() {
		return selectedUsersAdd;
	}

	public void setSelectedUsersAdd(List<UserDTO> selectedUsersAdd) {
		this.selectedUsersAdd = selectedUsersAdd;
	}

	public List<UserDTO> getSelectedUsersRemove() {
		return selectedUsersRemove;
	}

	public void setSelectedUsersRemove(List<UserDTO> selectedUsersRemove) {
		this.selectedUsersRemove = selectedUsersRemove;
	}

	private List<UserDTO> selectedUsersRemove;

	private FlightDTO flight = null;
	private UserDTO user;
	private CompanyDTO company;

	public CompanyDTO getCompany() {
		return company;
	}

	public void setCompany(CompanyDTO company) {
		this.company = company;
	}

	private String flightInfo;
	private String userInfo;

	public String getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(String userInfo) {
		this.userInfo = userInfo;
	}

	public String getFlightInfo() {
		return flightInfo;
	}

	public void setFlightInfo(String flightInfo) {
		this.flightInfo = flightInfo;
	}

	@PostConstruct
	public void initBean() {

		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);

		if (session.getAttribute(Constants.USER_TYPE).equals(Constants.ADMINISTRATOR_L)) {
			try {
				companyList = cb.getAvailableCompanies();
			} catch (RemoteException e) {
				logger.error(e.getMessage(), e);
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, LabelProvider.getLabel(Constants.ERROR),
								LabelProvider.getLabel(Constants.ERROR_GETAVAILABLECOMPANIES)));
			}

		} else {
			String userName = session.getAttribute(Constants.USER_NAME).toString();
			try {
				int cid = ub.getByUserName(userName).getCompany().getCompanyId();
				company = cb.getByCompanyId(cid);
				companyList.add(company);
				loadFlightsAndUsers();
			} catch (RemoteException e) {
				logger.error(e.getMessage(), e);
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, LabelProvider.getLabel(Constants.ERROR),
								LabelProvider.getLabel(Constants.ERROR_GETAVAILABLECOMPANIES)));
			}

		}

	}

	public void loadFlightsAndUsers() {
		try {
			flightList = fb.getByCompanyAndDate(company, new Date());
			userList = ub.getUsersByTypeAndCompany(Constants.CREW, company, true);
		} catch (RemoteException e) {
			logger.error(e.getMessage(), e);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					LabelProvider.getLabel(Constants.ERROR), LabelProvider.getLabel(Constants.ERROR_FLIGHTSANDUSERS)));
		}

	}

	public FlightDTO getFlight() {
		return flight;
	}

	public void setFlight(FlightDTO flight) {
		this.flight = flight;
	}

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

	public void processAssign(ActionEvent event) {

		StringBuilder msg = new StringBuilder(
				LabelProvider.getLabel(Constants.CREWADDED) + flight.getFlightId() + ": ");
		boolean areAdded = false;

		try {
			for (UserDTO u : selectedUsersAdd) {
				if (!u.getFlights().contains(flight) && !flight.getUsers().contains(u)) {
					msg.append("\n" + u.getUserName());

					flight.getUsers().add(u);
					fb.updateFlight(flight);
					u.getFlights().add(flight);
					ub.updateUser(u, false);
					areAdded = true;
					loadFlightsAndUsers();
				}
			}
		} catch (RemoteException e) {
			logger.error(e.getMessage(), e);
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, LabelProvider.getLabel(Constants.ERROR),
							LabelProvider.getLabel(Constants.ERROR_FLIGHTSANDUSERSASSIGNEMENT)));
		}

		if (areAdded)
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, Constants.OK, msg.toString()));

		// RequestContext.getCurrentInstance().update("mainForm:form:userMenuAdd");

	}

	public void processRemove(ActionEvent event) {

		StringBuilder msg = new StringBuilder(
				LabelProvider.getLabel(Constants.CREWREMOVED) + flight.getFlightId() + ": ");

		boolean areRemoved = false;
		try {
			for (UserDTO u : selectedUsersRemove) {
				if (u.getFlights().contains(flight) || flight.getUsers().contains(u)) {
					msg.append("\n" + u.getUserName());

					flight.getUsers().remove(u);
					fb.updateFlight(flight);
					u.getFlights().remove(flight);
					ub.updateUser(u, false);
					areRemoved = true;
					loadFlightsAndUsers();
				}
			}
		} catch (RemoteException e) {
			logger.error(e.getMessage(), e);
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, LabelProvider.getLabel(Constants.ERROR),
							LabelProvider.getLabel(Constants.ERROR_FLIGHTSANDUSERSASSIGNEMENT)));
		}

		if (areRemoved)
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, Constants.OK, msg.toString()));
	}

}
