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
import javax.servlet.http.HttpSession;

import org.primefaces.event.RowEditEvent;

import edu.msg.flightManagement.i18n.LabelProvider;
import edu.msg.flightManagement.util.Constants;
import edu.msg.flightManagementEjbClient.dtos.AirportDTO;
import edu.msg.flightManagementEjbClient.dtos.CompanyDTO;
import edu.msg.flightManagementEjbClient.dtos.FlightDTO;
import edu.msg.flightManagementEjbClient.dtos.ItineraryDTO;
import edu.msg.flightManagementEjbClient.interfaces.AirportBeanInterface;
import edu.msg.flightManagementEjbClient.interfaces.CompanyBeanInterFace;
import edu.msg.flightManagementEjbClient.interfaces.FlightBeanInterface;
import edu.msg.flightManagementEjbClient.interfaces.ItineraryBeanInterface;
import edu.msg.flightManagementEjbClient.interfaces.RemoteException;
import edu.msg.flightManagementEjbClient.interfaces.UserBeanInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ManagedBean
@ViewScoped
public class ItineraryBean implements Serializable {

	private static Logger logger = LoggerFactory.getLogger(ItineraryBean.class);
	private static final long serialVersionUID = 8775926421666870153L;

	@EJB
	private AirportBeanInterface airportInterface;

	@EJB
	private ItineraryBeanInterface itineraryInterface;

	@EJB
	private UserBeanInterface ub;

	@EJB
	private FlightBeanInterface fb;
	@EJB
	private CompanyBeanInterFace cb;

	private ItineraryDTO itinerary;

	private String itineraryName;

	private AirportDTO airport1;
	private AirportDTO airport2;
	private FlightDTO flight;
	private CompanyDTO company;

	public CompanyDTO getCompany() {
		return company;
	}

	public void setCompany(CompanyDTO company) {
		this.company = company;
	}

	public FlightDTO getFlight() {
		return flight;
	}

	public void setFlight(FlightDTO flight) {
		this.flight = flight;
	}

	private List<AirportDTO> airportList = new ArrayList<>();
	private List<ItineraryDTO> itinerariesList = new ArrayList<>();
	private List<FlightDTO> flightList = new ArrayList<>();
	private List<CompanyDTO> companyList = new ArrayList<>();

	public List<CompanyDTO> getCompanyList() {
		return companyList;
	}

	public void setCompanyList(List<CompanyDTO> companyList) {
		this.companyList = companyList;
	}

	@PostConstruct
	public void init() {

		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);

		if (session.getAttribute(Constants.USER_TYPE).equals(Constants.ADMINISTRATOR_L)) {
			try {
				companyList = cb.getAvailableCompanies();
			} catch (RemoteException e) {
				logger.error(e.getMessage(),e);
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, LabelProvider.getLabel(Constants.ERROR),
								LabelProvider.getLabel(Constants.ERROR_GETAVAILABLECOMPANIES)));
			}

		} else if (session.getAttribute(Constants.USER_TYPE).equals(Constants.COMPANY_MANAGER_L)) {
			String userName = session.getAttribute(Constants.USER_NAME).toString();
			try {
				int cid = ub.getByUserName(userName).getCompany().getCompanyId();
				company = cb.getByCompanyId(cid);
				companyList.add(company);
				loadItineraries();
			} catch (RemoteException e) {
				logger.error(e.getMessage(),e);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						Constants.ERROR, LabelProvider.getLabel(Constants.ERROR_GETAVAILABLECOMPANIES)));
			}

		}

	}

	public void loadFlights() {
		if (itinerary.getAirport2() == null) {
			try {
				flightList = fb.getByCompanyAndDate(company, new Date());
			} catch (RemoteException e) {
				logger.error(e.getMessage(),e);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						Constants.ERROR, LabelProvider.getLabel(Constants.ERROR_GETFLIGHTS)));
			}

		} else {

			try {
				flightList = fb.getByCompanyAndDate(company, new Date(), itinerary.getAirport2());
			} catch (RemoteException e) {
				logger.error(e.getMessage(),e);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						Constants.ERROR, LabelProvider.getLabel(Constants.ERROR_GETFLIGHTS)));
			}

		}
	}

	public void loadItineraries() {
		try {
			itinerariesList = itineraryInterface.getByCompany(company);
		} catch (RemoteException e) {
			logger.error(e.getMessage(),e);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					LabelProvider.getLabel(Constants.ERROR), LabelProvider.getLabel(Constants.ERROR_GETITINERARIES)));
		}

	}

	public void addFlight() {

		try {
			if (itinerary.getAirport1() == null) {
				itinerary.setAirport1(flight.getAirport1());
			}
			itinerary.setAirport2(flight.getAirport2());
			itinerary.getFlights().add(flight);
			itineraryInterface.updateItinerary(itinerary);
			loadItineraries();
			loadFlights();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					LabelProvider.getLabel(Constants.FLIGHTADDEDTOITINERARY), Constants.OK);
			FacesContext.getCurrentInstance().addMessage(null, msg);
		} catch (RemoteException e) {
			logger.error(e.getMessage(),e);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					LabelProvider.getLabel(Constants.ERROR), LabelProvider.getLabel(Constants.ERROR_UPDATEITINERARY)));
		}

	}

	public void deleteItinerary(ItineraryDTO itidto) {
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
				LabelProvider.getLabel(Constants.ITINERARYDELETED), itidto.getItineraryName());
		FacesContext.getCurrentInstance().addMessage(null, msg);
		try {
			itineraryInterface.deleteItinerary(itidto);
		} catch (RemoteException e) {
			logger.error(e.getMessage(),e);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					LabelProvider.getLabel(Constants.ERROR), LabelProvider.getLabel(Constants.ERROR_DELETEITINERARY)));
		}

	}
	
	public ItineraryDTO getById(int id){
		ItineraryDTO i = new ItineraryDTO();
		try{
			i = itineraryInterface.findById(id);
		}catch(RemoteException e){
			logger.error(e.getMessage(),e);
		}
		return i;
	}

	public void onRowEdit(RowEditEvent event) {
		ItineraryDTO seleteditinerary = (ItineraryDTO) event.getObject();
		String name  = getById(seleteditinerary.getItineraryId()).getItineraryName();
		
		if(!name.equals(seleteditinerary.getItineraryName()) && itineraryInterface.getByItineraryName(seleteditinerary.getItineraryName()) != null) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					LabelProvider.getLabel(Constants.ERROR), LabelProvider.getLabel(Constants.ITINERARYEXISTS)));
			seleteditinerary.setItineraryName(name);
			return;
		}
		
		
		try {
			itineraryInterface.updateItinerary(((ItineraryDTO) event.getObject()));
		} catch (RemoteException e) {
			logger.error(e.getMessage(),e);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					LabelProvider.getLabel(Constants.ERROR), LabelProvider.getLabel(Constants.ERROR_UPDATEITINERARY)));
		}
		FacesMessage msg = new FacesMessage(LabelProvider.getLabel(Constants.ITINERARYEDIT),
				((ItineraryDTO) event.getObject()).getItineraryName());
		FacesContext.getCurrentInstance().addMessage(null, msg);

	}

	public void onRowCancel(RowEditEvent event) {
		FacesMessage msg = new FacesMessage(LabelProvider.getLabel(Constants.ITINERARYEDITCANCEL),
				((ItineraryDTO) event.getObject()).getItineraryName());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public String addItinerary() {
		// validate itineraryName
		if (itineraryName.length() == 0) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, LabelProvider.getLabel(Constants.ERROR),
					LabelProvider.getLabel(Constants.ITINERARYNAMEREQUIRED));
			FacesContext.getCurrentInstance().addMessage(null, message);
			return Constants.INSERTED;
		}
		if (itineraryName.length() < 3) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,LabelProvider.getLabel(Constants.ERROR),
					LabelProvider.getLabel(Constants.ITINERARYNAMETOOSHORT));
			FacesContext.getCurrentInstance().addMessage(null, message);
			return Constants.INSERTED;
		}

		if (itineraryName.length() > 20) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, LabelProvider.getLabel(Constants.ERROR),
					LabelProvider.getLabel(Constants.ITINERARYNAMETOOLONG));
			FacesContext.getCurrentInstance().addMessage(null, message);
			return Constants.INSERTED;
		}

		// validate itinerary Name
		if (itineraryInterface.getByItineraryName(itineraryName) != null) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, LabelProvider.getLabel(Constants.ERROR),
					LabelProvider.getLabel(Constants.ITINERARYEXISTS));
			FacesContext.getCurrentInstance().addMessage(null, msg);
		} else {
			itinerary = new ItineraryDTO();
			itinerary.setItineraryName(itineraryName);

			itinerary.setFlights(new ArrayList<FlightDTO>());
			itinerary.setCompany(company);

			try {
				itineraryInterface.insertItinerary(itinerary);
			} catch (RemoteException e) {
				logger.error(e.getMessage(),e);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						LabelProvider.getLabel(Constants.ERROR), LabelProvider.getLabel(Constants.ERROR_ADDITINERARY)));
			}

			FacesMessage msg2 = new FacesMessage(FacesMessage.SEVERITY_INFO,
					LabelProvider.getLabel(Constants.ITINERARYINSERTED), itineraryName);
			FacesContext.getCurrentInstance().addMessage(null, msg2);
		}
		return Constants.INSERTED;
	}

	public List<FlightDTO> getFlightList() {
		return flightList;
	}

	public void setFlightList(List<FlightDTO> flightList) {
		this.flightList = flightList;
	}

	public ItineraryDTO getItinerary() {
		return itinerary;
	}

	public void setItinerary(ItineraryDTO itineraryDTO) {
		this.itinerary = itineraryDTO;
	}

	public String getItineraryName() {
		return itineraryName;
	}

	public void setItineraryName(String itineraryName) {
		this.itineraryName = itineraryName;
	}

	public AirportDTO getAirport1() {
		return airport1;
	}

	public void setAirport1(AirportDTO airport1) {
		this.airport1 = airport1;
	}

	public AirportDTO getAirport2() {
		return airport2;
	}

	public void setAirport2(AirportDTO airport2) {
		this.airport2 = airport2;
	}

	public List<AirportDTO> getAirportList() {
		return airportList;
	}

	public void setAirportList(List<AirportDTO> airportList) {
		this.airportList = airportList;
	}

	public List<ItineraryDTO> getItinerariesList() {
		return itinerariesList;
	}

	public void setItinerariesList(List<ItineraryDTO> itinerariesList) {
		this.itinerariesList = itinerariesList;
	}

}
