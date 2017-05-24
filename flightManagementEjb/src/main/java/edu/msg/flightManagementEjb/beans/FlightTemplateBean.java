package edu.msg.flightManagementEjb.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.msg.flightManagementEjb.assemblers.CompanyAssembler;
import edu.msg.flightManagementEjb.assemblers.FlightTemplateAssembler;
import edu.msg.flightManagementEjb.daos.DaoException;
import edu.msg.flightManagementEjb.daos.FlightTemplateDAO;
import edu.msg.flightManagementEjb.model.Flighttemplate;
import edu.msg.flightManagementEjbClient.dtos.CompanyDTO;
import edu.msg.flightManagementEjbClient.dtos.FlighttemplateDTO;
import edu.msg.flightManagementEjbClient.interfaces.FlightTemplateBeanInterface;
import edu.msg.flightManagementEjbClient.interfaces.RemoteException;

@Stateless
@LocalBean
public class FlightTemplateBean implements Serializable, FlightTemplateBeanInterface {

	private static final long serialVersionUID = 2734012661677131541L;

	private static Logger logger = LoggerFactory.getLogger(FlightTemplateBean.class);

	@EJB
	private FlightTemplateDAO flightTemplateDao;

	private FlightTemplateAssembler flightTemplateAssembler = new FlightTemplateAssembler();
	private CompanyAssembler companyAssembler = new CompanyAssembler();

	@Override
	public FlighttemplateDTO insertFlightTemplate(FlighttemplateDTO flightTemplateDTO) throws RemoteException {
		Flighttemplate flightTemplate = flightTemplateAssembler.dtoToModel(flightTemplateDTO);
		try {
			FlighttemplateDTO insertDTO = flightTemplateAssembler
					.modelToDto(flightTemplateDao.insertFlightTemplate(flightTemplate));
			return insertDTO;
		} catch (DaoException daoe) {
			logger.error("Flight template insertion failed : " + daoe);
			throw new RemoteException(daoe.getMessage(), daoe);
		}
	}

	@Override
	public FlighttemplateDTO updateFlightTemplate(FlighttemplateDTO flightTemplateDTO) throws RemoteException {
		Flighttemplate flightTemplate = flightTemplateAssembler.dtoToModel(flightTemplateDTO);
		try {
			FlighttemplateDTO updatedDTO = flightTemplateAssembler
					.modelToDto(flightTemplateDao.updateFlightTemplate(flightTemplate));
			return updatedDTO;
		} catch (DaoException daoe) {
			logger.error("Flight template update failed : " + daoe);
			throw new RemoteException(daoe.getMessage(), daoe);
		}
	}

	@Override
	public FlighttemplateDTO findById(int flightTemplateId) throws RemoteException {
		try {
			FlighttemplateDTO flightTemplateDTO = flightTemplateAssembler
					.modelToDto(flightTemplateDao.findById(flightTemplateId));
			return flightTemplateDTO;
		} catch (DaoException daoe) {
			logger.error("Flight template retrieval failed : " + daoe);
			throw new RemoteException(daoe.getMessage(), daoe);
		}
	}

	@Override
	public void deleteFlightTemplate(FlighttemplateDTO flightTemplateDTO) throws RemoteException {
		Flighttemplate flightTemplate = flightTemplateAssembler.dtoToModel(flightTemplateDTO);
		try {
			flightTemplateDao.deleteFlightTemplate(flightTemplate);
		} catch (DaoException daoe) {
			logger.error("Flight template delete failed : " + daoe);
			throw new RemoteException(daoe.getMessage(), daoe);
		}
	}

	@Override
	public void deleteFlightTemplateById(int flightTemplateId) throws RemoteException {
		try {
			flightTemplateDao.deleteFlightTemplateById(flightTemplateId);
		} catch (DaoException daoe) {
			logger.error("Flight template delete by Id failed : " + daoe);
			throw new RemoteException(daoe.getMessage(), daoe);
		}
	}

	@Override
	public List<FlighttemplateDTO> getFlightTemplates() throws RemoteException {
		List<FlighttemplateDTO> flightTemplatesDTOlist = new ArrayList<>();
		try {
			List<Flighttemplate> flightTemplatesList = flightTemplateDao.getFlightTemplates();
			for (Flighttemplate flightTemplate : flightTemplatesList) {
				FlighttemplateDTO flightTemplateDto = flightTemplateAssembler.modelToDto(flightTemplate);
				flightTemplatesDTOlist.add(flightTemplateDto);
			}
			return flightTemplatesDTOlist;
		} catch (DaoException daoe) {
			logger.error("getFlightTemplates error : " + daoe);
			throw new RemoteException(daoe.getMessage(), daoe);
		}
	}

	@Override
	public List<FlighttemplateDTO> getFlightTemplatesByCompany(CompanyDTO company) throws RemoteException {
		List<FlighttemplateDTO> templateList = new ArrayList<FlighttemplateDTO>();
		try {
			List<Flighttemplate> test = flightTemplateDao
					.getFlightTemplatesByCompany(companyAssembler.dtoToModelSimple(company));
			for (int i = 0; i < test.size(); i++) {
				templateList.add(flightTemplateAssembler.modelToDto(test.get(i)));
			}
			
			for (Flighttemplate flightTemplate : flightTemplateDao.getFlightTemplatesByNull()) {
				templateList.add(flightTemplateAssembler.modelToDto(flightTemplate));
			}
			return templateList;
		} catch (DaoException daoe) {
			logger.error("getFlightTemplateByCompany error : " + daoe);
			throw new RemoteException(daoe.getMessage(), daoe);
		}
	}

	@Override
	public List<FlighttemplateDTO> getAvailableTemplates() throws RemoteException {
		List<FlighttemplateDTO> flightTemplatesDTOlist = new ArrayList<>();
		try {
			for (Flighttemplate flightTemplate : flightTemplateDao.getAvailableFlightTemplates()) {
				flightTemplatesDTOlist.add(flightTemplateAssembler.modelToDto(flightTemplate));
			}
			
			for (Flighttemplate flightTemplate : flightTemplateDao.getFlightTemplatesByNull()) {
				flightTemplatesDTOlist.add(flightTemplateAssembler.modelToDto(flightTemplate));
			}
			
			return flightTemplatesDTOlist;
		} catch (DaoException daoe) {
			logger.error("getAvailableTemplate failed : " + daoe);
			throw new RemoteException(daoe.getMessage(), daoe);
		}
	}

}
