package edu.msg.flightManagementEjb.beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.msg.flightManagementEjb.assemblers.CompanyAssembler;
import edu.msg.flightManagementEjb.daos.CompanyDAO;
import edu.msg.flightManagementEjb.daos.DaoException;
import edu.msg.flightManagementEjb.daos.FlightDAO;
import edu.msg.flightManagementEjb.daos.FlightTemplateDAO;
import edu.msg.flightManagementEjb.daos.ItineraryDAO;
import edu.msg.flightManagementEjb.daos.PlaneDAO;
import edu.msg.flightManagementEjb.daos.UserDAO;
import edu.msg.flightManagementEjb.model.Company;
import edu.msg.flightManagementEjb.model.Flight;
import edu.msg.flightManagementEjb.model.Flighttemplate;
import edu.msg.flightManagementEjb.model.Itinerary;
import edu.msg.flightManagementEjb.model.Plane;
import edu.msg.flightManagementEjb.model.User;
import edu.msg.flightManagementEjbClient.dtos.CompanyDTO;
import edu.msg.flightManagementEjbClient.interfaces.CompanyBeanInterFace;
import edu.msg.flightManagementEjbClient.interfaces.RemoteException;

@Stateless
@LocalBean
public class CompanyBean implements CompanyBeanInterFace {

	private static Logger logger = LoggerFactory.getLogger(CompanyBean.class);

	@EJB
	private UserDAO userDao;
	@EJB
	private FlightDAO flightDao;
	@EJB
	private ItineraryDAO itineraryDao;
	@EJB
	private FlightTemplateDAO flightTemplateDao;
	@EJB
	private CompanyDAO companyDao;

	@EJB
	private PlaneDAO planeDao;

	private CompanyAssembler companyAssembler = new CompanyAssembler();

	@Override
	public CompanyDTO insertCompany(CompanyDTO companyDTO) throws RemoteException{
		Company company = companyAssembler.dtoToModelSimple(companyDTO);
		try {
			CompanyDTO insertDTO = companyAssembler.modelToDto(companyDao.insertCompany(company));
			return insertDTO;
		} catch (DaoException e) {
			logger.error("insert company error" + e);
			throw new RemoteException(e.getMessage(),e);
		}
	}

	@Override
	public CompanyDTO updateCompany(CompanyDTO companyDTO) throws RemoteException {
		Company company = companyAssembler.dtoToModelSimple(companyDTO);
		try {
			CompanyDTO updatedDTO = companyAssembler.modelToDto(companyDao.updateCompany(company));
			return updatedDTO;
		} catch (DaoException e) {
			logger.error("update company error" + e);
			throw new RemoteException(e.getMessage(),e);
		}
	}

	@Override
	public List<CompanyDTO> getCompanies() throws RemoteException{
		List<CompanyDTO> companies = new ArrayList<CompanyDTO>();

		try {
			for (Company company : companyDao.getCompanies()) {
				companies.add(companyAssembler.modelToDto(company));
			}
			return companies;
		} catch (DaoException e) {
			logger.error("getCompanies error" + e);
			throw new RemoteException(e.getMessage(),e);
		}
		
	}

	@Override
	public void deleteCompany(CompanyDTO companyDTO) throws RemoteException{
		Company company = companyAssembler.dtoToModel(companyDTO);
		try {
			companyDao.deleteCompany(company);
			for (User u : userDao.getUsersByCompany(company)) {
				userDao.deleteUser(u);
			}
			for (Flight f : flightDao.getByCompanyAndDate(company, new Date())) {
				flightDao.deleteFlight(f);
			}
			for (Itinerary i : itineraryDao.getByCompany(company)) {
				itineraryDao.deleteItinerary(i);
			}
			for (Flighttemplate ft : flightTemplateDao.getFlightTemplatesByCompany(company)) {
				flightTemplateDao.deleteFlightTemplate(ft);
			}
			for (Plane p : planeDao.getPlanesByCompany(company)) {
				planeDao.deletePlane(p);
			}
		} catch (DaoException e) {
			logger.error("delete company error" + e);
			throw new RemoteException(e.getMessage(),e);
		}
	}

	@Override
	public CompanyDTO getByCompanyName(String companyName) throws RemoteException{
		try {
			CompanyDTO getCompany = companyAssembler.modelToDto(companyDao.getByCompanyName(companyName));
			return getCompany;
		} catch (DaoException e) {
			logger.error("getByCompanyName error" + e);
			throw new RemoteException(e.getMessage(),e);
		}
	}

	@Override
	public CompanyDTO getByCompanyId(int id) throws RemoteException{
		try {
			CompanyDTO company = companyAssembler.modelToDto(companyDao.getByCompanyId(id));
			return company;
		} catch (DaoException e) {
			logger.error("getByCompanyId error" + e);
			throw new RemoteException(e.getMessage(),e);
		}

	}

	@Override
	public List<CompanyDTO> getAvailableCompanies() throws RemoteException {
		List<CompanyDTO> companiesDTOlist = new ArrayList<>();
		try {
			List<Company> companiesList = companyDao.getAvailableCompanies();
			for (Company company : companiesList) {
				CompanyDTO companyDTO = companyAssembler.modelToDto(company);
				companiesDTOlist.add(companyDTO);
			}
			return companiesDTOlist;
		} catch (DaoException e) {
			logger.error("getAvailableCompanies error" + e);
			throw new RemoteException(e.getMessage(),e);
		}
	}

}
