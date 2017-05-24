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
import javax.faces.event.ValueChangeEvent;
import javax.servlet.http.HttpSession;

import org.primefaces.event.RowEditEvent;

import edu.msg.flightManagement.i18n.LabelProvider;
import edu.msg.flightManagement.util.Constants;
import edu.msg.flightManagementEjbClient.dtos.AirportDTO;
import edu.msg.flightManagementEjbClient.dtos.CompanyDTO;
import edu.msg.flightManagementEjbClient.dtos.FlighttemplateDTO;
import edu.msg.flightManagementEjbClient.dtos.PlaneDTO;
import edu.msg.flightManagementEjbClient.interfaces.AirportBeanInterface;
import edu.msg.flightManagementEjbClient.interfaces.CompanyBeanInterFace;
import edu.msg.flightManagementEjbClient.interfaces.FlightTemplateBeanInterface;
import edu.msg.flightManagementEjbClient.interfaces.PlaneBeanInterface;
import edu.msg.flightManagementEjbClient.interfaces.RemoteException;
import edu.msg.flightManagementEjbClient.interfaces.UserBeanInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ManagedBean(name = "templateBean")
@ViewScoped
public class TemplateBean implements Serializable {

	private static Logger logger = LoggerFactory.getLogger(TemplateBean.class);

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	private static final long serialVersionUID = -7055744268493724808L;

	@EJB
	private AirportBeanInterface ab;
	@EJB
	private CompanyBeanInterFace cb;
	@EJB
	private PlaneBeanInterface pb;
	@EJB
	private FlightTemplateBeanInterface ftb;
	@EJB
	private UserBeanInterface ubi;

	private FlighttemplateDTO flightTemplate;
	private CompanyDTO company;
	private AirportDTO airport1;
	private AirportDTO airport2;
	private PlaneDTO plane;

	private Date startDate;
	private Date endDate;

	private List<FlighttemplateDTO> flightTemplateList = new ArrayList<FlighttemplateDTO>();
	private List<CompanyDTO> companyList = new ArrayList<CompanyDTO>();
	private List<AirportDTO> airportList = new ArrayList<AirportDTO>();
	private List<PlaneDTO> planeList = new ArrayList<PlaneDTO>();
	private String userType;
	private String userName;

	@PostConstruct
	public void init() {

		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);

		userType = session.getAttribute(Constants.USER_TYPE).toString();
		userName = session.getAttribute(Constants.USER_NAME).toString();

		fillCompanyList();
		fillFlightTemplateList();

		try {
			setAirportList(ab.getAvailableAirports());
		} catch (RemoteException e) {
			logger.error(e.getMessage(),e);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, LabelProvider.getLabel(Constants.ERROR),
					LabelProvider.getLabel(Constants.ERROR_GETAIRPORTS)));
		}

		setCompany(getCompanyList().get(0));
		try {
			setPlaneList(pb.getAllPlanesByCompany(company));
		} catch (RemoteException e) {
			logger.error(e.getMessage(),e);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, LabelProvider.getLabel(Constants.ERROR),
					LabelProvider.getLabel(Constants.ERROR_GETPLANES)));
		}

	}

	public String processInsert() {

		flightTemplate = new FlighttemplateDTO();
		flightTemplate.setAirport1(airport1);
		flightTemplate.setAirport2(airport2);
		flightTemplate.setStartDate(startDate);
		flightTemplate.setEndDate(endDate);
		flightTemplate.setCompany(company);
		flightTemplate.setPlane(plane);
		try {
			ftb.insertFlightTemplate(flightTemplate);
		} catch (RemoteException e) {
			logger.error(e.getMessage(),e);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, LabelProvider.getLabel(Constants.ERROR),
					LabelProvider.getLabel(Constants.ERROR_ADDTEMPLATE)));
		}

		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, Constants.OK,
				LabelProvider.getLabel(Constants.TEMPLATECREATED)));

		return Constants.OK;
	}

	public CompanyDTO getCompany() {
		return company;
	}

	public void setCompany(CompanyDTO company) {
		this.company = company;
	}

	public PlaneDTO getPlane() {
		return plane;
	}

	public void setPlane(PlaneDTO plane) {
		this.plane = plane;
	}

	public FlighttemplateDTO getFlightTemplate() {
		return flightTemplate;
	}

	public void setFlightTemplate(FlighttemplateDTO flightTemplate) {
		this.flightTemplate = flightTemplate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public List<CompanyDTO> getCompanyList() {
		return companyList;
	}

	public void setCompanyList(List<CompanyDTO> companyList) {
		this.companyList = companyList;
	}

	public List<AirportDTO> getAirportList() {
		return airportList;
	}

	public void setAirportList(List<AirportDTO> airportList) {
		this.airportList = airportList;
	}

	public List<PlaneDTO> getPlaneList() {
		return planeList;
	}

	public void setPlaneList(List<PlaneDTO> planeList) {
		this.planeList = planeList;
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

	public void onRowEdit(RowEditEvent event) {

		FacesMessage msg = new FacesMessage(LabelProvider.getLabel(Constants.TEMPLATEEDITED));
		FacesContext.getCurrentInstance().addMessage(null, msg);
		try {
			ftb.updateFlightTemplate((FlighttemplateDTO) event.getObject());
		} catch (RemoteException e) {
			logger.error(e.getMessage(),e);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, LabelProvider.getLabel(Constants.ERROR),
					LabelProvider.getLabel(Constants.ERROR_UPDATETEMPLATE)));
		}

	}

	private void fillFlightTemplateList() {
		try {
			if (userType.equals(Constants.ADMINISTRATOR_L)) {
				setFlightTemplateList(ftb.getAvailableTemplates());
			} else {
				setFlightTemplateList(ftb.getFlightTemplatesByCompany(ubi.getByUserName(userName).getCompany()));
			}
		} catch (RemoteException e) {
			logger.error(e.getMessage(),e);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, LabelProvider.getLabel(Constants.ERROR),
					LabelProvider.getLabel(Constants.ERROR_GETTEMPLATES)));
		}
	}

	private void fillCompanyList() {
		try {
			if (userType.equals(Constants.ADMINISTRATOR_L)) {
				setCompanyList(cb.getAvailableCompanies());
			} else {
				int cid = ubi.getByUserName(userName).getCompany().getCompanyId();
				CompanyDTO company = cb.getByCompanyId(cid);
				companyList.add(company);
			}
		} catch (RemoteException e) {
			logger.error(e.getMessage(),e);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, LabelProvider.getLabel(Constants.ERROR),
					LabelProvider.getLabel(Constants.ERROR_GETAVAILABLECOMPANIES)));
		}
	}

	public void onRowCancel(RowEditEvent event) {
		setFlightTemplateList(ftb.getAvailableTemplates());
		FacesMessage msg = new FacesMessage(LabelProvider.getLabel(Constants.EDITCANCELLED));
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public String delete(FlighttemplateDTO dto) {
		FacesMessage msg = new FacesMessage(LabelProvider.getLabel(Constants.TEMPLATEDELETED));
		FacesContext.getCurrentInstance().addMessage(null, msg);
		try {
			ftb.deleteFlightTemplate(dto);
		} catch (RemoteException e) {
			logger.error(e.getMessage(),e);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, LabelProvider.getLabel(Constants.ERROR),
					LabelProvider.getLabel(Constants.ERROR_DELETETEMPLATE)));
		}

		return Constants.OK;
	}

	public List<FlighttemplateDTO> getFlightTemplateList() {
		return flightTemplateList;
	}

	public void setFlightTemplateList(List<FlighttemplateDTO> flightTemplateList) {
		this.flightTemplateList = flightTemplateList;
	}

	public void selectOneMenuListener(ValueChangeEvent event) {
		CompanyDTO comp = (CompanyDTO) event.getNewValue();
		try {
			setPlaneList(pb.getAllPlanesByCompany(comp));
		} catch (RemoteException e) {
			logger.error(e.getMessage(),e);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, LabelProvider.getLabel(Constants.ERROR),
					LabelProvider.getLabel(Constants.ERROR_GETPLANES)));
		}

	}

}
