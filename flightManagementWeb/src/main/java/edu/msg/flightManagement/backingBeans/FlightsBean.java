package edu.msg.flightManagement.backingBeans;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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

import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.msg.flightManagement.i18n.LabelProvider;
import edu.msg.flightManagement.util.Constants;
import edu.msg.flightManagementEjbClient.dtos.AirportDTO;
import edu.msg.flightManagementEjbClient.dtos.CompanyDTO;
import edu.msg.flightManagementEjbClient.dtos.FlightDTO;
import edu.msg.flightManagementEjbClient.dtos.FlighttemplateDTO;
import edu.msg.flightManagementEjbClient.dtos.PlaneDTO;
import edu.msg.flightManagementEjbClient.dtos.UserDTO;
import edu.msg.flightManagementEjbClient.interfaces.AirportBeanInterface;
import edu.msg.flightManagementEjbClient.interfaces.CompanyBeanInterFace;
import edu.msg.flightManagementEjbClient.interfaces.FlightBeanInterface;
import edu.msg.flightManagementEjbClient.interfaces.FlightTemplateBeanInterface;
import edu.msg.flightManagementEjbClient.interfaces.ItineraryBeanInterface;
import edu.msg.flightManagementEjbClient.interfaces.PlaneBeanInterface;
import edu.msg.flightManagementEjbClient.interfaces.RemoteException;
import edu.msg.flightManagementEjbClient.interfaces.UserBeanInterface;

@ManagedBean
@ViewScoped
public class FlightsBean implements Serializable {

	private static Logger logger = LoggerFactory.getLogger(FlightsBean.class);

	private SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy");

	private static final long serialVersionUID = 4300230323106321706L;

	@EJB
	private FlightBeanInterface fbi;
	@EJB
	private CompanyBeanInterFace cbi;
	@EJB
	private PlaneBeanInterface pbi;
	@EJB
	private AirportBeanInterface abi;
	@EJB
	private UserBeanInterface userBeanInterface;
	@EJB
	private ItineraryBeanInterface itineraryBeanInterface;

	@EJB
	private FlightTemplateBeanInterface flightTemplateInteface;

	private List<FlightDTO> flightsList = new ArrayList<>();

	private List<CompanyDTO> allCompanies = new ArrayList<>();

	private List<AirportDTO> allAirports = new ArrayList<>();

	private List<PlaneDTO> allPlanes = new ArrayList<>();

	private List<FlighttemplateDTO> flightTemplateList;
	private FlightDTO flightDTO;
	private FlightDTO originalFlightDTO;
	private CompanyDTO company;
	private Date startDate;
	private Date endDate;
	private PlaneDTO planeDTO;
	private AirportDTO airportDTO1;
	private AirportDTO airportDTO2;

	private FlighttemplateDTO flightTemplateDTO;
	private FlightDTO flightFromTemplate;

	public CompanyDTO getCompany() {
		return company;
	}

	public void setCompany(CompanyDTO company) {
		this.company = company;
	}

	public List<PlaneDTO> getAllPlanes() {
		return allPlanes;
	}

	public void setAllPlanes(List<PlaneDTO> allPlanes) {
		this.allPlanes = allPlanes;
	}

	public List<AirportDTO> getAllAirports() {
		return allAirports;
	}

	public void setAllAirports(List<AirportDTO> allAirports) {
		this.allAirports = allAirports;
	}

	public List<CompanyDTO> getAllCompanies() {
		return allCompanies;
	}

	public void setAllCompanies(List<CompanyDTO> allCompanies) {
		this.allCompanies = allCompanies;
	}

	public List<FlightDTO> getFlightsList() {
		return flightsList;
	}

	public void setFlightsList(List<FlightDTO> flightsList) {
		this.flightsList = flightsList;
	}

	public void loadPlanesFromCompany(CompanyDTO c) {
		allPlanes = pbi.getAllPlanesByCompany(c);
	}

	public void loadPlanesforTemplate() {
		allPlanes = pbi.getAllPlanesByCompany(flightTemplateDTO.getCompany());
	}

	public void createFlightFromTemplate() {
		flightFromTemplate = new FlightDTO();

		flightFromTemplate.setAirport1(flightTemplateDTO.getAirport1());
		flightFromTemplate.setAirport2(flightTemplateDTO.getAirport2());
		flightFromTemplate.setCompany(flightTemplateDTO.getCompany());
		flightFromTemplate.setStartDate(flightTemplateDTO.getStartDate());
		flightFromTemplate.setEndDate(flightTemplateDTO.getEndDate());
		flightFromTemplate.setPlane(flightTemplateDTO.getPlane());
		flightFromTemplate.setStatus(true);

	}

	public List<FlighttemplateDTO> getFlightTemplateList() {
		return flightTemplateList;
	}

	public void setFlightTemplateList(List<FlighttemplateDTO> flightTemplateList) {
		this.flightTemplateList = flightTemplateList;
	}

	public FlightDTO getFlightFromTemplate() {
		return flightFromTemplate;
	}

	public void setFlightFromTemplate(FlightDTO flightFromTemplate) {
		this.flightFromTemplate = flightFromTemplate;
	}

	public FlighttemplateDTO getFlightTemplateDTO() {
		return flightTemplateDTO;
	}

	public void setFlightTemplateDTO(FlighttemplateDTO flightTemplateDTO) {
		this.flightTemplateDTO = flightTemplateDTO;
	}

	private boolean status;

	private List<Boolean> stats = Arrays.asList(true, false);

	public List<Boolean> getStats() {
		return stats;
	}

	public void setStats(List<Boolean> stats) {
		this.stats = stats;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public AirportDTO getAirport1() {
		return airportDTO1;
	}

	public void setAirport1(AirportDTO airportDTO1) {
		this.airportDTO1 = airportDTO1;
	}

	public AirportDTO getAirport2() {
		return airportDTO2;
	}

	public void setAirport2(AirportDTO airportDTO2) {
		this.airportDTO2 = airportDTO2;
	}

	public PlaneDTO getPlane() {
		return planeDTO;
	}

	public void setPlane(PlaneDTO planeDTO) {
		this.planeDTO = planeDTO;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@PostConstruct
	public void initBean() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);

		String userType = session.getAttribute(Constants.USER_TYPE).toString();
		String userName = session.getAttribute(Constants.USER_NAME).toString();

		allAirports = abi.getAvailableAirports();

		if (userType.equals(Constants.ADMINISTRATOR_L)) {
			// get all flights and flightTemplates from all companies
			try {
				flightsList = fbi.getAvailableFlights();
			} catch (RemoteException e) {
				logger.error(e.getMessage(), e);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						LabelProvider.getLabel(Constants.ERROR), LabelProvider.getLabel(Constants.ERROR_GETFLIGHTS)));
			}

			try {
				flightTemplateList = flightTemplateInteface.getFlightTemplates();
			} catch (RemoteException e) {
				logger.error(e.getMessage(), e);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						LabelProvider.getLabel(Constants.ERROR), LabelProvider.getLabel(Constants.ERROR_GETTEMPLATES)));
			}

			try {
				allCompanies = cbi.getAvailableCompanies();
			} catch (RemoteException e) {
				logger.error(e.getMessage(), e);
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, LabelProvider.getLabel(Constants.ERROR),
								LabelProvider.getLabel(Constants.ERROR_GETAVAILABLECOMPANIES)));
			}

		} else {
			// get flights and templates from user's company
			UserDTO dto = userBeanInterface.getByUserName(userName);
			CompanyDTO companyDto = dto.getCompany();

			// get flights by company
			try {
				flightsList = fbi.getAllFlightsByCompany(companyDto);
			} catch (RemoteException e) {
				logger.error(e.getMessage(), e);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						LabelProvider.getLabel(Constants.ERROR), LabelProvider.getLabel(Constants.ERROR_GETFLIGHTS)));
			}

			// get templates by company
			try {
				flightTemplateList = flightTemplateInteface.getFlightTemplatesByCompany(companyDto);
			} catch (RemoteException e) {
				logger.error(e.getMessage(), e);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						LabelProvider.getLabel(Constants.ERROR), LabelProvider.getLabel(Constants.ERROR_GETTEMPLATES)));
			}

			try {
				allPlanes = pbi.getAllPlanesByCompany(companyDto);
			} catch (RemoteException e) {
				logger.error(e.getMessage(), e);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						LabelProvider.getLabel(Constants.ERROR), LabelProvider.getLabel(Constants.ERROR_GETPLANES)));
			}

			try {
				allCompanies.add(companyDto);
			} catch (RemoteException e) {
				logger.error(e.getMessage(), e);
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, LabelProvider.getLabel(Constants.ERROR),
								LabelProvider.getLabel(Constants.ERROR_GETAVAILABLECOMPANIES)));
			}

		}

	}

	public boolean verifyIfFieldsForTemplateAreEmpty(FlightDTO flight) {

		if (flight.getAirport1() == null) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					LabelProvider.getLabel(Constants.ERROR), LabelProvider.getLabel(Constants.EMPTYDEPARTURESTRING)));
			return false;
		}
		if (flight.getAirport2() == null) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					LabelProvider.getLabel(Constants.ERROR), LabelProvider.getLabel(Constants.EMPTYARRIVALAIRPORT)));
			return false;
		}
		if (flight.getStartDate() == null) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					LabelProvider.getLabel(Constants.ERROR), LabelProvider.getLabel(Constants.EMPTYSTARTDATE)));
			return false;
		}
		if (flight.getEndDate() == null) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					LabelProvider.getLabel(Constants.ERROR), LabelProvider.getLabel(Constants.EMPTYENDDATE)));
			return false;
		}
		if (flight.getCompany() == null) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					LabelProvider.getLabel(Constants.ERROR), LabelProvider.getLabel(Constants.EMPTYCOMPANY)));
			return false;
		}
		if (flight.getPlane() == null) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					LabelProvider.getLabel(Constants.ERROR), LabelProvider.getLabel(Constants.EMPTYPLANE)));
			return false;
		}
		return true;

	}

	public String addFlightFromTemplate() {
		// check if fields are empty
		if (verifyIfFieldsForTemplateAreEmpty(flightFromTemplate) == false) {
			return Constants.SAVED;
		}

		// check if dates are in the future
		Date date = new Date();
		if (flightFromTemplate.getStartDate().compareTo(date) < 0
				|| flightFromTemplate.getEndDate().compareTo(date) < 0) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					LabelProvider.getLabel(Constants.ERROR), LabelProvider.getLabel(Constants.DATESMUSTBEINTHEFUTURE)));
			return Constants.SAVED;
		}
		// check if airport1 is different then airport2
		if (flightFromTemplate.getAirport1().equals(flightFromTemplate.getAirport2())) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					LabelProvider.getLabel(Constants.ERROR), LabelProvider.getLabel(Constants.SAMEAIRPORT)));
			return Constants.SAVED;
		}

		// chack if the plane isn't assigned to another flight in the given
		// period

		if (!fbi.isPlaneFree(flightFromTemplate.getPlane(), flightFromTemplate.getStartDate(),
				flightFromTemplate.getEndDate(), 0)) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, Constants.INFO,
							LabelProvider.getLabel(Constants.THIS_PLANE) + " " + planeDTO.getModel() + " "
									+ LabelProvider.getLabel(Constants.ALREADY_HAS) + " "
									+ dt.format(flightFromTemplate.getStartDate()) + "-"
									+ dt.format(flightFromTemplate.getEndDate())));
			return Constants.SAVED;
		}

		fbi.insertFlight(flightFromTemplate);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, Constants.INFO,
				LabelProvider.getLabel(Constants.INSERTFLIGHTFROMTEMPLATE)));
		return Constants.SAVED;
	}

	public void onRowEdit2(RowEditEvent event) {
		flightFromTemplate.setAirport1(flightTemplateDTO.getAirport1());
		flightFromTemplate.setAirport2(flightTemplateDTO.getAirport2());
		flightFromTemplate.setStartDate(flightTemplateDTO.getStartDate());
		flightFromTemplate.setEndDate(flightTemplateDTO.getEndDate());
		flightFromTemplate.setCompany(flightTemplateDTO.getCompany());
		flightFromTemplate.setPlane(flightTemplateDTO.getPlane());
	}

	public void onRowCancel2(RowEditEvent event) {
		flightTemplateDTO.setCompany(flightFromTemplate.getCompany());
		FacesMessage msg = new FacesMessage(LabelProvider.getLabel(Constants.EDITFLIGHTFROMTEMPLATE));
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public boolean checkDatesForInsert(Date endDate, Date startDate) {

		if (endDate == null || startDate == null) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					LabelProvider.getLabel(Constants.ERROR), LabelProvider.getLabel(Constants.UNSELECTEDDATES)));
			return false;
		}

		Date date = new Date();
		if (startDate.compareTo(date) < 0 || endDate.compareTo(date) < 0) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					LabelProvider.getLabel(Constants.ERROR), LabelProvider.getLabel(Constants.DATESMUSTBEINTHEFUTURE)));
			return false;
		}

		if (endDate.before(startDate)) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					LabelProvider.getLabel(Constants.ERROR), LabelProvider.getLabel(Constants.ENDDATEMUSTBEGREATER)));

			return false;
		}
		return true;
	}

	public boolean checkAirportsForInsert(AirportDTO a1, AirportDTO a2) {
		if (a1.equals(a2)) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					LabelProvider.getLabel(Constants.ERROR), LabelProvider.getLabel(Constants.SAMEAIRPORT)));

			return false;
		}
		return true;
	}

	public String processInsert() {
		// check if plane is not empty
		if (planeDTO == null) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					LabelProvider.getLabel(Constants.ERROR), LabelProvider.getLabel(Constants.EMPTYPLANE)));
			return Constants.OK;
		}
		if (checkAirportsForInsert(airportDTO1, airportDTO2) && checkDatesForInsert(endDate, startDate)) {
			if (fbi.isPlaneFree(planeDTO, startDate, endDate, 0)) {
				flightDTO = new FlightDTO();
				flightDTO.setCompany(company);
				flightDTO.setStartDate(startDate);
				flightDTO.setEndDate(endDate);
				flightDTO.setPlane(planeDTO);
				flightDTO.setAirport1(airportDTO1);
				flightDTO.setAirport2(airportDTO2);
				status = true;
				flightDTO.setStatus(status);

				try {
					fbi.insertFlight(flightDTO);
				} catch (RemoteException e) {
					logger.error(e.getMessage(), e);
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_ERROR, LabelProvider.getLabel(Constants.ERROR),
									LabelProvider.getLabel(Constants.ERROR_ADDFLIGHT)));
				}

				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
						Constants.INFO, LabelProvider.getLabel(Constants.INSERTEDFLIGHT)));
			} else {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_WARN, Constants.INFO,
								LabelProvider.getLabel(Constants.THIS_PLANE) + " " + planeDTO.getModel() + " "
										+ LabelProvider.getLabel(Constants.ALREADY_HAS) + " " + dt.format(startDate)
										+ "-" + dt.format(endDate)));
			}
		}
		return Constants.OK;

	}

	public FlightDTO getFlight(int id) {
		FlightDTO f = new FlightDTO();
		try {
			f = fbi.getFlightById(id);
		} catch (RemoteException e) {
			logger.error(e.getMessage(), e);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					LabelProvider.getLabel(Constants.ERROR), LabelProvider.getLabel(Constants.ERROR_GETFLIGHTS)));
		}
		return f;
	}

	public void onRowEditInit(RowEditEvent event) {
		originalFlightDTO = (FlightDTO) event.getObject();
	}

	public void onRowEdit(RowEditEvent event) {
		FlightDTO tempFlightDTO = (FlightDTO) event.getObject();

		try {
			if (fbi.isPlaneFree(tempFlightDTO.getPlane(), tempFlightDTO.getStartDate(), tempFlightDTO.getEndDate(),
					originalFlightDTO.getFlightId())) {
				fbi.updateFlight((FlightDTO) event.getObject());
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
						Constants.INFO, LabelProvider.getLabel(Constants.EDITEDFLIGHT)));
			} else {
				// some of the init
				FacesContext facesContext = FacesContext.getCurrentInstance();
				HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
				String userName = session.getAttribute(Constants.USER_NAME).toString();
				UserDTO dto = userBeanInterface.getByUserName(userName);
				CompanyDTO companyDto = dto.getCompany();
				flightsList = fbi.getAllFlightsByCompany(companyDto);
				//
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_WARN, LabelProvider.getLabel(Constants.INFO),
								LabelProvider.getLabel(Constants.THIS_PLANE) + " " + tempFlightDTO.getPlane().getModel()
										+ " " + LabelProvider.getLabel(Constants.ALREADY_HAS) + " "
										+ dt.format(tempFlightDTO.getStartDate()) + "-"
										+ dt.format(tempFlightDTO.getEndDate())));

			}

		} catch (RemoteException e) {
			logger.error(e.getMessage(), e);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					LabelProvider.getLabel(Constants.ERROR), LabelProvider.getLabel(Constants.ERROR_UPDATEFLIGHT)));
		}

	}

	public void onRowCancel(RowEditEvent event) {
		FacesMessage msg = new FacesMessage(Constants.EDITCANCELLED);
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void rowDelete(FlightDTO dto) {
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, Constants.INFO,
				LabelProvider.getLabel(Constants.DELETEDFLIGHTS));
		FacesContext.getCurrentInstance().addMessage(null, msg);
		try {
			itineraryBeanInterface.deleteItineraryByFlightId(dto.getFlightId());
		} catch (RemoteException e) {
			logger.error(e.getMessage(), e);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					LabelProvider.getLabel(Constants.ERROR), LabelProvider.getLabel(Constants.ERROR_DELETEITINERARY)));
		}
		try {
			fbi.deleteFlight(dto);
		} catch (RemoteException e) {
			logger.error(e.getMessage(), e);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					LabelProvider.getLabel(Constants.ERROR), LabelProvider.getLabel(Constants.ERROR_DELETEFLIGHT)));
		}

	}

	public void onDateSelect(SelectEvent event) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
				LabelProvider.getLabel(Constants.DATESSELECTED), format.format(event.getObject())));
	}

	public void click() {
		RequestContext requestContext = RequestContext.getCurrentInstance();
		requestContext.update("form:display");
		requestContext.execute("PF('dlg').show()");
	}

	public void selectOneMenuListener(ValueChangeEvent event) {
		CompanyDTO comp = (CompanyDTO) event.getNewValue();
		try {
			setAllPlanes(pbi.getAllPlanesByCompany(comp));
		} catch (RemoteException e) {
			logger.error(e.getMessage(), e);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					LabelProvider.getLabel(Constants.ERROR), LabelProvider.getLabel(Constants.ERROR_GETPLANES)));
		}

	}

}