package edu.msg.flightManagement.backingBeans;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.RowEditEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.msg.flightManagement.i18n.LabelProvider;
import edu.msg.flightManagement.util.Constants;
import edu.msg.flightManagementEjbClient.dtos.CompanyDTO;
import edu.msg.flightManagementEjbClient.interfaces.CompanyBeanInterFace;
import edu.msg.flightManagementEjbClient.interfaces.RemoteException;

@ManagedBean
@ViewScoped
public class ManageCompanies implements Serializable {

	private static Logger logger = LoggerFactory.getLogger(ManageCompanies.class);

	private static final long serialVersionUID = -6954974045345294615L;

	@EJB
	private CompanyBeanInterFace companyInterface;

	private List<CompanyDTO> companiesList;

	private String companyName;

	private String address;

	private String phone;

	private String email;

	private boolean status;

	private List<Boolean> stats = Arrays.asList(true, false);

	private CompanyDTO companyDTO;

	@PostConstruct
	public void init() {
		try {
			companiesList = companyInterface.getCompanies();
		} catch (RemoteException e) {
			logger.error(e.getMessage(),e);
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, LabelProvider.getLabel(Constants.ERROR),
							LabelProvider.getLabel(Constants.ERROR_GETAVAILABLECOMPANIES)));
		}

	}

	public List<CompanyDTO> getCompaniesList() {
		return companiesList;
	}

	public void setCompaniesList(List<CompanyDTO> companiesList) {
		this.companiesList = companiesList;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public CompanyDTO getCompanyDTO() {
		return companyDTO;
	}

	public void setCompanyDTO(CompanyDTO companyDTO) {
		this.companyDTO = companyDTO;
	}

	public List<Boolean> getStats() {
		return stats;
	}

	public void setStats(List<Boolean> stats) {
		this.stats = stats;
	}

	public CompanyDTO getCompanyById(int id) {
		CompanyDTO company = new CompanyDTO();
		try {
			company = companyInterface.getByCompanyId(id);
		} catch (RemoteException e) {
			logger.error(e.getMessage(),e);
		}
		return company;
	}

	public boolean validateCompanyNameAndAddress(CompanyDTO selectedCompany) {
		// validate Company Name if it was modified
		String companyName = getCompanyById(selectedCompany.getCompanyId()).getCompanyName();
		if (!companyName.equals(selectedCompany.getCompanyName())
				&& companyInterface.getByCompanyName(selectedCompany.getCompanyName()) != null) {
			FacesMessage msg = new FacesMessage(LabelProvider.getLabel(Constants.ERROR), LabelProvider.getLabel(Constants.COMPANYEXISTS));
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return false;
		}
		if (selectedCompany.getCompanyName().equals("")) {
			FacesMessage msg = new FacesMessage(LabelProvider.getLabel(Constants.ERROR),
					LabelProvider.getLabel(Constants.ERROR_COMPANYNAMEEMPTY));
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return false;
		}

		// validate address
		if (selectedCompany.getAddress().equals("")) {
			FacesMessage msg = new FacesMessage(LabelProvider.getLabel(Constants.ERROR), LabelProvider.getLabel(Constants.ERROR_ADDRESSEMPTY));
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return false;
		}

		if (selectedCompany.getAddress().length() < 5) {
			FacesMessage msg = new FacesMessage(LabelProvider.getLabel(Constants.ERROR), LabelProvider.getLabel(Constants.ERROR_ADDRESSHORT));
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return false;
		}

		if (selectedCompany.getAddress().length() > 50) {
			FacesMessage msg = new FacesMessage(LabelProvider.getLabel(Constants.ERROR), LabelProvider.getLabel(Constants.ERROR_ADDRESSLONG));
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return false;
		}

		return true;
	}

	public boolean validateEmailAndPhone(CompanyDTO selectedCompany) {
		// validate email
		String emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		Pattern pattern = Pattern.compile(emailPattern);
		Matcher matcher = pattern.matcher(selectedCompany.getEmail());

		if (matcher.matches() == false) {
			FacesMessage msg = new FacesMessage(LabelProvider.getLabel(Constants.ERROR), LabelProvider.getLabel(Constants.ERROR_EMAILVALID));
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return false;
		}

		// validate phone
		String phone = selectedCompany.getPhone();
		if (phone.length() == 0) {
			FacesMessage msg = new FacesMessage(LabelProvider.getLabel(Constants.ERROR), LabelProvider.getLabel(Constants.ERROR_PHONEREQUIRED));
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return false;
		}
		if (phone.length() < 10) {
			FacesMessage msg = new FacesMessage(LabelProvider.getLabel(Constants.ERROR), LabelProvider.getLabel(Constants.ERROR_PHONESHORT));
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return false;
		}
		if (phone.length() > 20) {
			FacesMessage msg = new FacesMessage(LabelProvider.getLabel(Constants.ERROR), LabelProvider.getLabel(Constants.ERROR_PHONELONG));
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return false;
		}
		
		
		if (!phone.matches("[0-9]+")) {
			FacesMessage msg = new FacesMessage(LabelProvider.getLabel(Constants.ERROR), LabelProvider.getLabel(Constants.ERROR_PHONEVALID));
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return false;
		}
		return true;
	}

	public void onRowEdit(RowEditEvent event) {
		CompanyDTO selectedCompany = (CompanyDTO) event.getObject();
		CompanyDTO company = getCompanyById(selectedCompany.getCompanyId());

		if (validateCompanyNameAndAddress(selectedCompany) == false
				|| validateEmailAndPhone(selectedCompany) == false) {
			selectedCompany.setEmail(company.getEmail());
			selectedCompany.setAddress(company.getAddress());
			selectedCompany.setPhone(company.getPhone());
			selectedCompany.setCompanyName(company.getCompanyName());
			return;
		}

		try {
			if (companyInterface.getByCompanyId(selectedCompany.getCompanyId()).getStatus() == true
					&& selectedCompany.getStatus() == false) {
				companyInterface.deleteCompany(selectedCompany);
			} else {
				companyInterface.updateCompany(selectedCompany);
			}
		} catch (RemoteException e) {
			logger.error(e.getMessage(),e);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					LabelProvider.getLabel(Constants.ERROR), LabelProvider.getLabel(Constants.ERROR_UPDATECOMPANY)));
		}

		FacesMessage msg = new FacesMessage(LabelProvider.getLabel(Constants.COMPANYEDIT),
				((CompanyDTO) event.getObject()).getCompanyName());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onRowCancel(RowEditEvent event) {
		FacesMessage msg = new FacesMessage(LabelProvider.getLabel(Constants.COMPANYEDITCANCELLED),
				((CompanyDTO) event.getObject()).getCompanyName());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public String addCompany() {
		// validate companyName
		if (companyInterface.getByCompanyName(companyName) != null) {
			// companyName already exists
			FacesMessage msg = new FacesMessage(LabelProvider.getLabel(Constants.ERROR), LabelProvider.getLabel(Constants.COMPANYEXISTS));
			FacesContext.getCurrentInstance().addMessage(null, msg);
		} else {
			companyDTO = new CompanyDTO();
			companyDTO.setCompanyName(companyName);
			companyDTO.setAddress(address);
			companyDTO.setPhone(phone);
			companyDTO.setEmail(email);
			status = true;
			companyDTO.setStatus(status);
			try {
				companyInterface.insertCompany(companyDTO);
			} catch (RemoteException e) {
				logger.error(e.getMessage(),e);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						LabelProvider.getLabel(Constants.ERROR), LabelProvider.getLabel(Constants.ERROR_ADDCOMPANY)));
			}

			FacesMessage msg = new FacesMessage(LabelProvider.getLabel(Constants.COMPANYINSERTED), companyName);
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		return Constants.INSERTED;

	}
}
