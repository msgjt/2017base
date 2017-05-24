package edu.msg.flightManagement.backingBeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import edu.msg.flightManagement.i18n.LabelProvider;
import edu.msg.flightManagement.util.Constants;
import edu.msg.flightManagementEjbClient.dtos.FlightDTO;
import edu.msg.flightManagementEjbClient.dtos.UserDTO;
import edu.msg.flightManagementEjbClient.interfaces.FlightBeanInterface;
import edu.msg.flightManagementEjbClient.interfaces.RemoteException;
import edu.msg.flightManagementEjbClient.interfaces.UserBeanInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ManagedBean
@RequestScoped
public class ViewFlightsBean implements Serializable {

	private static Logger logger = LoggerFactory.getLogger(ViewFlightsBean.class);

	private static final long serialVersionUID = 1822123748730195763L;

	private UserDTO user;

	// for session management only
	private FacesContext facesContext;
	private String userN;
	private HttpSession session;

	@EJB
	private UserBeanInterface userBean;
	@EJB
	private FlightBeanInterface flightBean;

	private List<FlightDTO> flights;
	private List<FlightDTO> pastFlights = new ArrayList<>();
	private List<FlightDTO> newFlights = new ArrayList<>();

	@PostConstruct
	public void init() {

		facesContext = FacesContext.getCurrentInstance();
		session = (HttpSession) facesContext.getExternalContext().getSession(false);
		userN = session.getAttribute("userName").toString();
		try {
			user = userBean.getByUserName(userN);
		} catch (RemoteException e) {
			logger.error(e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
					LabelProvider.getLabel(Constants.ERROR_GETUSER)));
		}
		try {
			flights = user.getFlights(); // belongs to current user
		} catch (RemoteException e) {
			logger.error(e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
					LabelProvider.getLabel(Constants.ERROR_GETFLIGHTS)));
		}

		// sort by departure date
		if (!flights.isEmpty()) {
			Collections.sort(flights, new Comparator<FlightDTO>() {
				@Override
				public int compare(FlightDTO o1, FlightDTO o2) {
					return o1.getStartDate().compareTo(o2.getStartDate());
				}
			});
			try {
				// construct new flightDTO with associated users
				for (int i = 0; i < flights.size(); i++) {
					newFlights.add(flightBean.getFlightById(flights.get(i).getFlightId()));
				}
				// separate tables for past and upcoming flights
				Date date = new Date();
				while (newFlights.get(0).getStartDate().compareTo(date) < 0) {
					pastFlights.add(newFlights.get(0));
					newFlights.remove(0);
				}
			} catch (RemoteException e) {
				logger.error(e.getMessage());
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Error", LabelProvider.getLabel(Constants.ERROR_GETFLIGHTS)));
			}
		}

	}

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

	public List<FlightDTO> getFlights() {
		return flights;
	}

	public void setFlights(List<FlightDTO> flights) {
		this.flights = flights;
	}

	public List<FlightDTO> getPastFlights() {
		return pastFlights;
	}

	public void setPastFlights(List<FlightDTO> pastFlights) {
		this.pastFlights = pastFlights;
	}

	public List<FlightDTO> getNewFlights() {
		return newFlights;
	}

	public void setNewFlights(List<FlightDTO> newFlights) {
		this.newFlights = newFlights;
	}

}
