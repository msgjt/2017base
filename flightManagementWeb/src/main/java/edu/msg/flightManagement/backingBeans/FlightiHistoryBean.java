package edu.msg.flightManagement.backingBeans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import edu.msg.flightManagement.i18n.LabelProvider;
import edu.msg.flightManagement.util.Constants;
import edu.msg.flightManagementEjbClient.dtos.FlightHistoryDTO;
import edu.msg.flightManagementEjbClient.dtos.UserDTO;
import edu.msg.flightManagementEjbClient.interfaces.FlightHistoryBeanInterface;
import edu.msg.flightManagementEjbClient.interfaces.RemoteException;
import edu.msg.flightManagementEjbClient.interfaces.UserBeanInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ManagedBean
@ViewScoped
public class FlightiHistoryBean implements Serializable {
	private static final long serialVersionUID = -5084346550918657531L;

	private static Logger logger = LoggerFactory.getLogger(FlightiHistoryBean.class);

	@EJB
	private FlightHistoryBeanInterface historyInterface;

	@EJB
	private UserBeanInterface userBeanInterface;

	private List<FlightHistoryDTO> flightsHistoryList;

	private FlightHistoryDTO selectedFlight;

	public FlightHistoryDTO getSelectedFlight() {
		return selectedFlight;
	}

	public void setSelectedFlight(FlightHistoryDTO selectedFlight) {
		this.selectedFlight = selectedFlight;
	}

	public List<FlightHistoryDTO> getFlightsHistoryList() {
		return flightsHistoryList;
	}

	public void setFlightsHistoryList(List<FlightHistoryDTO> flightsHistoryList) {
		this.flightsHistoryList = flightsHistoryList;
	}

	@PostConstruct
	public void init() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);

		String userType = session.getAttribute(Constants.USER_TYPE).toString();
		String userName = session.getAttribute(Constants.USER_NAME).toString();

		if (userType.equals(Constants.ADMINISTRATOR_L)) {
			// get all flights from history
			try {
				flightsHistoryList = historyInterface.getFlightHistories();
			} catch (RemoteException e) {
				logger.error(e.getMessage(),e);
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, LabelProvider.getLabel(Constants.ERROR),
								LabelProvider.getLabel(Constants.ERROR_GETFLIGHTHISTORY)));
			}

		} else {
			// get flight history from user's company
			try {
				UserDTO dto = userBeanInterface.getByUserName(userName);
				int id = dto.getCompany().getCompanyId();
				flightsHistoryList = historyInterface.getHistoryFlightsByCompanyId(id);
			} catch (RemoteException e) {
				logger.error(e.getMessage(),e);
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, LabelProvider.getLabel(Constants.ERROR),
								LabelProvider.getLabel(Constants.ERROR_GETFLIGHTHISTORY)));
			}

		}
	}
}
