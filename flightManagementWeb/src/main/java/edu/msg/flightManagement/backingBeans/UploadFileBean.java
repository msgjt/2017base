package edu.msg.flightManagement.backingBeans;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.msg.flightManagement.i18n.LabelProvider;
import edu.msg.flightManagement.util.AirportCsv;
import edu.msg.flightManagement.util.CompanyCsv;
import edu.msg.flightManagement.util.Constants;
import edu.msg.flightManagement.util.CsvFactory;
import edu.msg.flightManagement.util.FlightCsv;
import edu.msg.flightManagement.util.ItineraryCsv;
import edu.msg.flightManagement.util.PlaneCsv;
import edu.msg.flightManagement.util.UserCsv;

@javax.faces.bean.ManagedBean
@ViewScoped
public class UploadFileBean implements Serializable {

	private static final long serialVersionUID = 3830658158014859796L;
	private static Logger logger = LoggerFactory.getLogger(PlaneCsv.class);

	@EJB
	private PlaneCsv pcsv;

	@EJB
	private UserCsv ucsv;

	@EJB
	private CompanyCsv ccsv;

	@EJB
	private CsvFactory csvf;

	@EJB
	private AirportCsv acsv;

	@EJB
	private FlightCsv fcsv;

	@EJB
	private ItineraryCsv icsv;

	private String option;

	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
	}

	private DefaultStreamedContent download;

	public void setDownload(DefaultStreamedContent download) {
		this.download = download;
	}

	public DefaultStreamedContent getDownload() {
		return download;
	}

	public void handleFileUpload(FileUploadEvent event) {
		String fileToImport = event.getFile().getFileName();
		StringBuilder sb1 = new StringBuilder();
		sb1.append(fileToImport);
		//FacesMessage message = new FacesMessage(sb1.toString());
		if (option == null) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					LabelProvider.getLabel(Constants.ERROR), LabelProvider.getLabel(Constants.ERROR_TABLENOTSELECTED)));
		} else {
			switch (option) {
			case "Plane":
				if (pcsv.importCsv(fileToImport))
					FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,
							Constants.INFO, sb1.toString()));
				else
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(LabelProvider.getLabel(Constants.ERROR_IMPORT)));
				break;
			case "Airport":
				if (acsv.importCsv(fileToImport))
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
							Constants.INFO, sb1.toString()));
				else
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(LabelProvider.getLabel(Constants.ERROR_IMPORT)));
				break;
			case "User":
				if (ucsv.importCsv(fileToImport))
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
							Constants.INFO, sb1.toString()));
				else
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(LabelProvider.getLabel(Constants.ERROR_IMPORT)));
				break;
			case "Company":
				if (ccsv.importCsv(fileToImport))
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
							Constants.INFO, sb1.toString()));
				else
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(LabelProvider.getLabel(Constants.ERROR_IMPORT)));
				break;
			case "Flight":
				if (fcsv.importCsv(fileToImport))
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
							Constants.INFO, sb1.toString()));
				else
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(LabelProvider.getLabel(Constants.ERROR_IMPORT)));
				break;
			case "Itinerary":
				if (icsv.importCsv(fileToImport))
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
							Constants.INFO, sb1.toString()));
				else
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(LabelProvider.getLabel(Constants.ERROR_IMPORT)));
				break;
			default:
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						LabelProvider.getLabel(Constants.ERROR), LabelProvider.getLabel(Constants.ERROR_WRONGFILE)));
				break;
			}
		}
	}

	public void prepDownload() {
		try {
			String downloadFilePath = null;
			if (option == null) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, LabelProvider.getLabel(Constants.ERROR),
								LabelProvider.getLabel(Constants.ERROR_TABLENOTSELECTED)));
			} else {
				switch (option) {
				case "Plane":
					if (pcsv.exportCsv())
						downloadFilePath = "C:/allplanes.csv";
					else
						FacesContext.getCurrentInstance().addMessage(null,
								new FacesMessage(LabelProvider.getLabel(Constants.ERROR_IMPORT)));
					break;
				case "User":
					if (ucsv.exportCsv())
						downloadFilePath = "C:/allusers.csv";
					else
						FacesContext.getCurrentInstance().addMessage(null,
								new FacesMessage(LabelProvider.getLabel(Constants.ERROR_IMPORT)));
					break;
				case "Airport":
					if (acsv.exportCsv())
						downloadFilePath = "C:/allairports.csv";
					else
						FacesContext.getCurrentInstance().addMessage(null,
								new FacesMessage(LabelProvider.getLabel(Constants.ERROR_IMPORT)));
					break;
				case "Company":
					if (ccsv.exportCsv())
						downloadFilePath = "C:/allcompanies.csv";
					else
						FacesContext.getCurrentInstance().addMessage(null,
								new FacesMessage("An error occured at export"));
					break;
				case "Flight":
					if (fcsv.exportCsv())
						downloadFilePath = "C:/allflights.csv";
					else
						FacesContext.getCurrentInstance().addMessage(null,
								new FacesMessage(LabelProvider.getLabel(Constants.ERROR_IMPORT)));
					break;
				case "Itinerary":
					if (icsv.exportCsv())
						downloadFilePath = "C:/allitineraries.csv";
					else
						FacesContext.getCurrentInstance().addMessage(null,
								new FacesMessage(LabelProvider.getLabel(Constants.ERROR_IMPORT)));
					break;
				default:
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(LabelProvider.getLabel(Constants.ERROR_IMPORT)));
					break;
				}
				File filetoDownload = new File(downloadFilePath);
				InputStream input = new FileInputStream(filetoDownload);
				ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
				setDownload(new DefaultStreamedContent(input, externalContext.getMimeType(filetoDownload.getName()),
						filetoDownload.getName()));
			}
		} catch (FileNotFoundException e) {
			logger.error("Error at downloading csv file ", e);
		}
	}
}
