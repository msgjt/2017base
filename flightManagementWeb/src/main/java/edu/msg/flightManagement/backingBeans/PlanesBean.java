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
import edu.msg.flightManagementEjbClient.dtos.PlaneDTO;
import edu.msg.flightManagementEjbClient.interfaces.CompanyBeanInterFace;
import edu.msg.flightManagementEjbClient.interfaces.PlaneBeanInterface;
import edu.msg.flightManagementEjbClient.interfaces.RemoteException;
import edu.msg.flightManagementEjbClient.interfaces.UserBeanInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ManagedBean(name = "planesBean")
@ViewScoped
public class PlanesBean implements Serializable {

	private static Logger logger = LoggerFactory.getLogger(PlanesBean.class);

	private static final long serialVersionUID = 4300230323106321706L;

	@EJB
	private PlaneBeanInterface pb;

	@EJB
	private CompanyBeanInterFace cb;

	@EJB
	private UserBeanInterface ub;

	private List<PlaneDTO> planesList;
	private String model;
	private String capacity;
	private boolean status;
	private CompanyDTO company;
	private PlaneDTO plane;
	private List<Boolean> statusList = Arrays.asList(true, false);
	private List<CompanyDTO> companyList = new ArrayList<>();

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
				logger.error(e.getMessage(), e);
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, LabelProvider.getLabel(Constants.ERROR),
								LabelProvider.getLabel(Constants.ERROR_GETAVAILABLECOMPANIES)));
			}
			try {
				planesList = pb.getAllPlanes();
			} catch (RemoteException e) {
				logger.error(e.getMessage(), e);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						LabelProvider.getLabel(Constants.ERROR), LabelProvider.getLabel(Constants.ERROR_GETPLANES)));
			}

		} else if (userType.equals(Constants.COMPANY_MANAGER_L)) {
			CompanyDTO selectedCompany = new CompanyDTO();
			try {
				selectedCompany = ub.getByUserName(uName).getCompany();
				companyList.add(selectedCompany);
			} catch (RemoteException e) {
				logger.error(e.getMessage(), e);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						LabelProvider.getLabel(Constants.ERROR), LabelProvider.getLabel(Constants.ERROR_GETUSER)));
			}

			try {
				planesList = pb.getAllPlanesByCompany(selectedCompany);
			} catch (RemoteException e) {
				logger.error(e.getMessage(), e);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						LabelProvider.getLabel(Constants.ERROR), LabelProvider.getLabel(Constants.ERROR_GETPLANES)));
			}

		}
		showInsertForm = false;
	}

	public List<CompanyDTO> getCompanyList() {
		return companyList;
	}

	public void setCompanyList(List<CompanyDTO> companyList) {
		this.companyList = companyList;
	}

	public CompanyDTO getCompany() {
		return company;
	}

	public void setCompany(CompanyDTO company) {
		this.company = company;
	}

	public List<Boolean> getStatusList() {
		return statusList;
	}

	public void setStatusList(List<Boolean> statusList) {
		this.statusList = statusList;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getCapacity() {
		return capacity;
	}

	public void setCapacity(String capacity) {
		this.capacity = capacity;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public PlaneDTO getPlane() {
		return plane;
	}

	public void setPlane(PlaneDTO plane) {
		this.plane = plane;
	}

	public List<PlaneDTO> getAllPlanes() {
		return planesList;
	}

	public boolean getEditable() {
		return true;
	}

	private boolean showInsertForm;

	public boolean isShowInsertForm() {
		return showInsertForm;
	}

	public void setShowInsertForm(boolean showInsertForm) {
		this.showInsertForm = showInsertForm;
	}

	public void toggleInsertForm() {
		showInsertForm = !showInsertForm;
	}

	public String processInsert() {

		plane = new PlaneDTO();
		plane.setModel(model);
		plane.setCapacity(Integer.valueOf(capacity));
		status = true;
		plane.setStatus(status);
		plane.setCompany(company);
		try {
			pb.insertPlane(plane);
		} catch (RemoteException e) {
			logger.error(e.getMessage(), e);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					LabelProvider.getLabel(Constants.ERROR), LabelProvider.getLabel(Constants.ERROR_ADDPLANE)));
		}

		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, Constants.OK,
				LabelProvider.getLabel(Constants.PLANEINSERTED)));

		return Constants.OK;
	}

	public PlaneDTO getPlaneById(int id) {
		PlaneDTO p = new PlaneDTO();
		try {
			p = pb.getPlaneById(id);
		} catch (RemoteException e) {
			logger.error(e.getMessage(), e);
		}
		return p;
	}

	public void restoreFields(PlaneDTO selectedPlane) {
		PlaneDTO plane = getPlaneById(selectedPlane.getPlaneId());
		selectedPlane.setCapacity(plane.getCapacity());
		selectedPlane.setCompany(plane.getCompany());
		selectedPlane.setModel(plane.getModel());
		selectedPlane.setStatus(plane.getStatus());
	}

	public boolean validateEmptyFields(PlaneDTO p) {
		if (p.getModel().equals("")) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					LabelProvider.getLabel(Constants.ERROR), LabelProvider.getLabel(Constants.ERROR_EMPTYMODEL)));
			return false;
		}
		if (String.valueOf(p.getCapacity()).equals("")) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					LabelProvider.getLabel(Constants.ERROR), LabelProvider.getLabel(Constants.ERROR_EMPTYCAPACITY)));
			return false;
		}
		if (p.getCompany().equals("")) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					LabelProvider.getLabel(Constants.ERROR), LabelProvider.getLabel(Constants.ERROR_EMPTYCAPACITY)));
			return false;
		}
		return true;
	}

	public void onRowEdit(RowEditEvent event) {

		PlaneDTO selectedPlane = (PlaneDTO) event.getObject();

		if (validateEmptyFields(selectedPlane) == false) {
			restoreFields(selectedPlane);
			return;
		}

		try {
			if (pb.getPlaneById(selectedPlane.getPlaneId()).getStatus() == true && selectedPlane.getStatus() == false) {
				pb.deletePlane(selectedPlane);
			} else {
				pb.updatePlane(selectedPlane);
			}
		} catch (RemoteException e) {
			logger.error(e.getMessage(), e);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					LabelProvider.getLabel(Constants.ERROR), LabelProvider.getLabel(Constants.ERROR_UPDATEPLANE)));
		}
		FacesMessage msg = new FacesMessage(LabelProvider.getLabel(Constants.PLANEEDIT),
				((PlaneDTO) event.getObject()).getModel());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onRowCancel(RowEditEvent event) {
		FacesMessage msg = new FacesMessage(LabelProvider.getLabel(Constants.PLANEEDITCANCELLED),
				((PlaneDTO) event.getObject()).getModel());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

}
