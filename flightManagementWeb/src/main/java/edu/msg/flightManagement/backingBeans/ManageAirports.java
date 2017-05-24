package edu.msg.flightManagement.backingBeans;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.RowEditEvent;

import edu.msg.flightManagement.i18n.LabelProvider;
import edu.msg.flightManagement.util.Constants;
import edu.msg.flightManagementEjbClient.dtos.AirportDTO;
import edu.msg.flightManagementEjbClient.interfaces.AirportBeanInterface;
import edu.msg.flightManagementEjbClient.interfaces.RemoteException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ManagedBean
@ViewScoped
public class ManageAirports implements Serializable {

	private static Logger logger = LoggerFactory.getLogger(ManageAirports.class);
	private static final long serialVersionUID = -5464401066335666102L;

	private List<AirportDTO> airportsList;

	private String airportName;
	private String country;
	private String city;
	private String address;
	private boolean status;

	private AirportDTO airportDTO;

	@EJB
	private AirportBeanInterface airportBean;

	private List<Boolean> stats = Arrays.asList(true, false);

	public List<Boolean> getStats() {
		return stats;
	}

	public void setStats(List<Boolean> stats) {
		this.stats = stats;
	}

	@PostConstruct
	public void init() {
		try {
			airportsList = airportBean.getAirports();
		} catch (RemoteException e) {
			logger.error(e.getMessage(),e);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, LabelProvider.getLabel(Constants.ERROR),
					LabelProvider.getLabel(Constants.ERROR_GETAIRPORTS)));
		}

	}

	public AirportDTO getAirportById(int id) {
		AirportDTO a = new AirportDTO();
		try {
			a= airportBean.findById(id);
		}catch (RemoteException e) {
			logger.error(e.getMessage(),e);
		}
		return a;
	
	}
	public void restoreFields(AirportDTO selectedAirport) {
		AirportDTO ad = getAirportById(selectedAirport.getAirportId());
		selectedAirport.setAirportName(ad.getAirportName());
		selectedAirport.setCountry(ad.getCountry());
		selectedAirport.setCity(ad.getCity());
		selectedAirport.setAddress(ad.getAddress());
		selectedAirport.setStatus(ad.getStatus());
	}
	public boolean validateFieldsforUpdate(AirportDTO airport) {
		//validate if fields are empty
		if(airport.getAirportName().equals("")) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					LabelProvider.getLabel(Constants.ERROR), LabelProvider.getLabel(Constants.AIRPORTNULL)));
			return false;
		}
		if(airport.getCountry().equals("")) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					LabelProvider.getLabel(Constants.ERROR), LabelProvider.getLabel(Constants.ERROR_EMPTYCOUNTRY)));
			return false;
		}
		if(airport.getCity().equals("")) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					LabelProvider.getLabel(Constants.ERROR), LabelProvider.getLabel(Constants.ERROR_EMPTYCITY)));
			return false;
		}
		if(airport.getAddress().equals("")) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					LabelProvider.getLabel(Constants.ERROR), LabelProvider.getLabel(Constants.ERROR_EMPTYADDRESS)));
			return false;
		}
		//validate if airport name already exists
		String airportName = getAirportById(airport.getAirportId()).getAirportName();
		if(!airportName.equals(airport.getAirportName()) && airportBean.getAirportByName(airport.getAirportName()) != null) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					LabelProvider.getLabel(Constants.ERROR), LabelProvider.getLabel(Constants.AIRPORTEXISTS)));
			return false;
		}
		return true;
	}
	public void onRowEdit(RowEditEvent event) {
		AirportDTO selectedAirport = (AirportDTO) event.getObject();
		if(validateFieldsforUpdate(selectedAirport) == false) {
			restoreFields(selectedAirport);
			return;
		}
		try {
			// if status is modified to false delete flight and templates and
			// itineraries
			// associated to the airport
			if (selectedAirport.getStatus() == false) {
				airportBean.deleteAirport(selectedAirport);
			} else {
				airportBean.updateAirport(selectedAirport);
			}

		} catch (RemoteException e) {
			logger.error(e.getMessage(),e);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, LabelProvider.getLabel(Constants.ERROR),
					LabelProvider.getLabel(Constants.ERROR_UPDATEAIRPORT)));
		}
		FacesMessage msg = new FacesMessage(LabelProvider.getLabel(Constants.AIRPORTEDIT),
				selectedAirport.getAirportName());
		FacesContext.getCurrentInstance().addMessage(null, msg);

	}

	public void onRowCancel(RowEditEvent event) {
		FacesMessage msg = new FacesMessage(LabelProvider.getLabel(Constants.AIRPORTEDITCANCELLED),
				((AirportDTO) event.getObject()).getAirportName());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public String addAirport() {
		if (airportName.equals("")) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					LabelProvider.getLabel(Constants.ERROR), LabelProvider.getLabel(Constants.AIRPORTNULL)));
			return "inserted";
		}
		// validate airportName
		if (airportBean.getAirportByName(airportName) != null) {
			// airportName already exists
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, LabelProvider.getLabel(Constants.ERROR),
					(LabelProvider.getLabel(Constants.AIRPORTEXISTS)));
			FacesContext.getCurrentInstance().addMessage(null, msg);
		} else {
			airportDTO = new AirportDTO();
			airportDTO.setAirportName(airportName);
			airportDTO.setCountry(country);
			airportDTO.setCity(city);
			airportDTO.setAddress(address);
			status = true;
			airportDTO.setStatus(status);
			try {
				airportBean.insertAirport(airportDTO);
			} catch (RemoteException e) {
				logger.error(e.getMessage(),e);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						LabelProvider.getLabel(Constants.ERROR), LabelProvider.getLabel(Constants.ERROR_ADDAIRPORT)));
			}

			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					LabelProvider.getLabel(Constants.AIRPORTINSERT), airportName);
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}

		return Constants.INSERTED;
	}

	public void rowDelete(AirportDTO dto) {
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, Constants.INFO,
				LabelProvider.getLabel(Constants.AIRPORTDELETED));
		FacesContext.getCurrentInstance().addMessage(null, msg);
		airportsList.remove(dto);
		try {
			airportBean.deleteAirport(dto);
		} catch (RemoteException e) {
			logger.error(e.getMessage(),e);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, LabelProvider.getLabel(Constants.ERROR),
					LabelProvider.getLabel(Constants.ERROR_DELETEAIRPORT)));
		}

	}

	public String getAirportName() {
		return airportName;
	}

	public void setAirportName(String airportName) {
		this.airportName = airportName;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public AirportDTO getAirportDTO() {
		return airportDTO;
	}

	public void setAirportDTO(AirportDTO airportDTO) {
		this.airportDTO = airportDTO;
	}

	public List<AirportDTO> getAirportsList() {
		return airportsList;
	}

	public void setAirportsList(List<AirportDTO> airportsList) {
		this.airportsList = airportsList;
	}

}
