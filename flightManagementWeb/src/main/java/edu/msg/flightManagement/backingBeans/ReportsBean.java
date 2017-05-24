package edu.msg.flightManagement.backingBeans;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.http.HttpSession;

import edu.msg.flightManagement.i18n.LabelProvider;
import edu.msg.flightManagement.util.Constants;
import edu.msg.flightManagementEjbClient.dtos.CompanyDTO;
import edu.msg.flightManagementEjbClient.dtos.FlightDTO;
import edu.msg.flightManagementEjbClient.dtos.PlaneDTO;
import edu.msg.flightManagementEjbClient.dtos.UserDTO;
import edu.msg.flightManagementEjbClient.interfaces.CompanyBeanInterFace;
import edu.msg.flightManagementEjbClient.interfaces.FlightBeanInterface;
import edu.msg.flightManagementEjbClient.interfaces.RemoteException;
import edu.msg.flightManagementEjbClient.interfaces.UserBeanInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ManagedBean
@ViewScoped
public class ReportsBean implements Serializable {

	private static Logger logger = LoggerFactory.getLogger(ReportsBean.class);

	private static final long serialVersionUID = 194278954106477614L;

	// general purpose
	private List<FlightDTO> flightList = new ArrayList<>();
	private List<CompanyDTO> companyList = new ArrayList<>();
	private List<PlaneDTO> planesList = new ArrayList<>();
	private List<UserDTO> employeeList = new ArrayList<>();
	private SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy");
	private Date fromDate = null;
	private Date toDate = null;
	private String fromDateString;
	private String toDateString;
	private CompanyDTO selectedCompany;
	private CompanyDTO selectedCompany2;
	private CompanyDTO selectedCompany3;
	private PlaneDTO selectedPlane;
	private UserDTO selectedEmployee;
	private Date currentDate = new Date();
	// rendered statuses of the report contents (Menu/Comp./Plane/Emp.))
	private boolean mRendered;
	private boolean cRendered;
	private boolean pRendered;
	private boolean eRendered;
	private int nrFlights;
	private int nrEmployees;
	private int nrPlanes;
	private int totalFlights;

	// session related
	private UserDTO user = new UserDTO();
	private FacesContext facesContext;
	private String userN;
	private HttpSession session;

	@EJB
	private UserBeanInterface userBean;
	@EJB
	private FlightBeanInterface flightBean;
	@EJB
	private CompanyBeanInterFace companyBean;

	@PostConstruct
	public void init() {
		// session management
		facesContext = FacesContext.getCurrentInstance();
		session = (HttpSession) facesContext.getExternalContext().getSession(false);
		userN = session.getAttribute(Constants.USER_NAME).toString();
		// general items
		setmRendered(true);
		try {
			user = userBean.getByUserName(userN);
		} catch (RemoteException e) {
			logger.error(e.getMessage(), e);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					LabelProvider.getLabel(Constants.ERROR), LabelProvider.getLabel(Constants.ERROR_GETUSER)));
		}

		if (Constants.ADMINISTRATOR.equals(user.getType())) {
			try {
				setCompanyList(companyBean.getCompanies());
			} catch (RemoteException e) {
				logger.error(e.getMessage(), e);
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, LabelProvider.getLabel(Constants.ERROR),
								LabelProvider.getLabel(Constants.ERROR_GETAVAILABLECOMPANIES)));
			}

		} else {
			if ("COMPANY MANAGER".equals(user.getType())) {
				try {
					companyList.add(companyBean.getByCompanyId(user.getCompany().getCompanyId()));
				} catch (RemoteException e) {
					logger.error(e.getMessage(), e);
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_ERROR, LabelProvider.getLabel(Constants.ERROR),
									LabelProvider.getLabel(Constants.ERROR_GETAVAILABLECOMPANIES)));
				}

			} else {
				setmRendered(false);
			}
		}

		// COMPANY|PLANES|EMPLOYEE items
		if (!companyList.isEmpty()) {
			if (selectedCompany == null) {
				selectedCompany = companyList.get(0);
			}
			if (selectedCompany2 == null) {
				selectedCompany2 = companyList.get(0);
			}
			if (selectedCompany3 == null) {
				selectedCompany3 = companyList.get(0);
			}
			try {
				planesList = selectedCompany.getPlanes();
			} catch (RemoteException e) {
				logger.error(e.getMessage(), e);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						LabelProvider.getLabel(Constants.ERROR), LabelProvider.getLabel(Constants.ERROR_GETPLANES)));
			}

			try {
				employeeList = selectedCompany.getUsers();
			} catch (RemoteException e) {
				logger.error(e.getMessage(), e);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						LabelProvider.getLabel(Constants.ERROR), LabelProvider.getLabel(Constants.ERROR_GETUSERS)));
			}

		}
	}

	public void companyReport() {
		if (fromDate == null) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					LabelProvider.getLabel(Constants.ERROR), LabelProvider.getLabel(Constants.REPORTSSTARTDATE));
			FacesContext.getCurrentInstance().addMessage(null, message);
			return;
		}
		if (toDate == null) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					LabelProvider.getLabel(Constants.ERROR), LabelProvider.getLabel(Constants.REPORTSENDDATE));
			FacesContext.getCurrentInstance().addMessage(null, message);
			return;
		}

		setFlightList(selectedCompany.getFlights());
		sortFlightList(flightList);

		setNrEmployees(selectedCompany.getUsers().size());
		setTotalFlights(flightList.size());
		setNrPlanes(selectedCompany.getPlanes().size());

		fromDateString = dt.format(fromDate);
		toDateString = dt.format(toDate);
		setFlightList(flightsBetween(flightList, fromDate, toDate));
		setNrFlights(flightList.size());
	}

	// leave flights starting only between fromDate and toDate
	private List<FlightDTO> flightsBetween(List<FlightDTO> flightList, Date fromDate, Date toDate) {
		if (fromDate.compareTo(toDate) <= 0) {
			// remove deleted flights
			List<FlightDTO> tempList = new ArrayList<>();
			for (FlightDTO flight : flightList) {
				if (flight.getStatus()) {
					tempList.add(flight);
				}
			}
			flightList = tempList;
			// remove flights before "fromDate"
			while ((!flightList.isEmpty()) && (flightList.get(0).getStartDate().compareTo(fromDate) < 0)) {
				flightList.remove(0);
			}
			while ((!flightList.isEmpty())
					&& (flightList.get(flightList.size() - 1).getStartDate().compareTo(toDate) > 0)) {
				flightList.remove(flightList.size() - 1);
			}
			cRendered = true;
		} else {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					LabelProvider.getLabel(Constants.ERROR), LabelProvider.getLabel(Constants.REPORTSDATECOMPARE));
			FacesContext.getCurrentInstance().addMessage(null, message);
		}
		return flightList;
	}

	public void planeReport() {

		try {
			flightList = flightBean.getAllFlights();
		} catch (RemoteException e) {
			logger.error(e.getMessage(), e);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					LabelProvider.getLabel(Constants.ERROR), LabelProvider.getLabel(Constants.ERROR_GETFLIGHTS)));
		}
		// remove deleted flights
		List<FlightDTO> tempList = new ArrayList<>();
		for (FlightDTO flight : flightList) {
			if (flight.getStatus()) {
				tempList.add(flight);
			}
		}
		flightList = tempList;
		sortFlightList(flightList);
		// remove other flights than for this plane
		int i = 0;
		while (i < flightList.size()) {
			if (!(flightList.get(i).getPlane().equals(selectedPlane))) {
				flightList.remove(i);
			} else {
				i++;
			}
		}
		setTotalFlights(flightList.size());
		setpRendered(true);

	}

	public void employeeReport() {
		flightList = selectedEmployee.getFlights();
		// remove deleted flights
		List<FlightDTO> tempList = new ArrayList<>();
		for (FlightDTO flight : flightList) {
			if (flight.getStatus()) {
				tempList.add(flight);
			}
		}
		flightList = tempList;

		sortFlightList(flightList);
		totalFlights = flightList.size();
		seteRendered(true);
	}

	public void changePlaneList(ValueChangeEvent event) {
		selectedCompany2 = (CompanyDTO) event.getNewValue();
		if (selectedCompany2 != null) {
			planesList = selectedCompany2.getPlanes();
		}
	}

	public void changeEmployeeList(ValueChangeEvent event) {
		selectedCompany3 = (CompanyDTO) event.getNewValue();
		if (selectedCompany3 != null) {
			employeeList = selectedCompany3.getUsers();
		}
	}

	// Sort a flightlist ascending by departure time
	private List<FlightDTO> sortFlightList(List<FlightDTO> flightList) {
		if (!flightList.isEmpty()) {
			Collections.sort(flightList, new Comparator<FlightDTO>() {
				@Override
				public int compare(FlightDTO o1, FlightDTO o2) {
					return o1.getStartDate().compareTo(o2.getStartDate());
				}
			});
		}
		return flightList;
	}

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

	public List<FlightDTO> getFlightList() {
		return flightList;
	}

	public void setFlightList(List<FlightDTO> flightList) {
		this.flightList = flightList;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public Date getCurrentDate() {
		return currentDate;
	}

	public void setCurrentDate(Date currentDate) {
		this.currentDate = currentDate;
	}

	public String getFromDateString() {
		return fromDateString;
	}

	public void setFromDateString(String fromDateString) {
		this.fromDateString = fromDateString;
	}

	public String getToDateString() {
		return toDateString;
	}

	public void setToDateString(String toDateString) {
		this.toDateString = toDateString;
	}

	public int getNrFlights() {
		return nrFlights;
	}

	public void setNrFlights(int nrFlights) {
		this.nrFlights = nrFlights;
	}

	public int getNrEmployees() {
		return nrEmployees;
	}

	public void setNrEmployees(int nrEmployees) {
		this.nrEmployees = nrEmployees;
	}

	public boolean iscRendered() {
		return cRendered;
	}

	public int getNrPlanes() {
		return nrPlanes;
	}

	public void setNrPlanes(int nrPlanes) {
		this.nrPlanes = nrPlanes;
	}

	public int getTotalFlights() {
		return totalFlights;
	}

	public void setTotalFlights(int totalFlights) {
		this.totalFlights = totalFlights;
	}

	public CompanyDTO getSelectedCompany() {
		return selectedCompany;
	}

	public void setSelectedCompany(CompanyDTO selectedCompany) {
		this.selectedCompany = selectedCompany;
	}

	public List<CompanyDTO> getCompanyList() {
		return companyList;
	}

	public void setCompanyList(List<CompanyDTO> companyList) {
		this.companyList = companyList;
	}

	public List<PlaneDTO> getPlanesList() {
		return planesList;
	}

	public void setPlanesList(List<PlaneDTO> planesList) {
		this.planesList = planesList;
	}

	public PlaneDTO getSelectedCompanyPlanes() {
		return selectedPlane;
	}

	public void setSelectedCompanyPlanes(PlaneDTO selectedCompanyPlanes) {
		this.selectedPlane = selectedCompanyPlanes;
	}

	public boolean ispRendered() {
		return pRendered;
	}

	public void setpRendered(boolean pRendered) {
		this.pRendered = pRendered;
	}

	public PlaneDTO getSelectedPlane() {
		return selectedPlane;
	}

	public void setSelectedPlane(PlaneDTO selectedPlane) {
		this.selectedPlane = selectedPlane;
	}

	public UserDTO getSelectedEmployee() {
		return selectedEmployee;
	}

	public void setSelectedEmployee(UserDTO selectedEmployee) {
		this.selectedEmployee = selectedEmployee;
	}

	public void setcRendered(boolean cRendered) {
		this.cRendered = cRendered;
	}

	public List<UserDTO> getEmployeeList() {
		return employeeList;
	}

	public void setEmployeeList(List<UserDTO> employeeList) {
		this.employeeList = employeeList;
	}

	public boolean iseRendered() {
		return eRendered;
	}

	public void seteRendered(boolean eRendered) {
		this.eRendered = eRendered;
	}

	public CompanyDTO getSelectedCompany2() {
		return selectedCompany2;
	}

	public void setSelectedCompany2(CompanyDTO selectedCompany2) {
		this.selectedCompany2 = selectedCompany2;
	}

	public CompanyDTO getSelectedCompany3() {
		return selectedCompany3;
	}

	public void setSelectedCompany3(CompanyDTO selectedCompany3) {
		this.selectedCompany3 = selectedCompany3;
	}

	public boolean ismRendered() {
		return mRendered;
	}

	public void setmRendered(boolean mRendered) {
		this.mRendered = mRendered;
	}

}
