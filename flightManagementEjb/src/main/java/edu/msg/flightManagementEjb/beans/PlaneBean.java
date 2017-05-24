package edu.msg.flightManagementEjb.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.msg.flightManagementEjb.assemblers.CompanyAssembler;
import edu.msg.flightManagementEjb.assemblers.PlaneAssembler;
import edu.msg.flightManagementEjb.daos.CompanyDAO;
import edu.msg.flightManagementEjb.daos.DaoException;
import edu.msg.flightManagementEjb.daos.FlightDAO;
import edu.msg.flightManagementEjb.daos.FlightTemplateDAO;
import edu.msg.flightManagementEjb.daos.ItineraryDAO;
import edu.msg.flightManagementEjb.daos.PlaneDAO;
import edu.msg.flightManagementEjb.model.Company;
import edu.msg.flightManagementEjb.model.Flight;
import edu.msg.flightManagementEjb.model.Flighttemplate;
import edu.msg.flightManagementEjb.model.Plane;
import edu.msg.flightManagementEjbClient.dtos.CompanyDTO;
import edu.msg.flightManagementEjbClient.dtos.PlaneDTO;
import edu.msg.flightManagementEjbClient.interfaces.PlaneBeanInterface;
import edu.msg.flightManagementEjbClient.interfaces.RemoteException;

@Stateless
@LocalBean
public class PlaneBean implements Serializable, PlaneBeanInterface{

	private static final long serialVersionUID = 3293579625182907147L;
	
	private static Logger logger = LoggerFactory.getLogger(PlaneBean.class);
	
	@EJB
	private PlaneDAO planeDAO;
	@EJB
	private FlightDAO flightDao;
	@EJB
	private ItineraryDAO itineraryDao;
	@EJB
	private FlightTemplateDAO flightTemplateDao;
	@EJB 
	private CompanyDAO companyDAO;
	
	private PlaneAssembler planeAssembler = new PlaneAssembler();
	private CompanyAssembler companyAssembler = new CompanyAssembler();
	
	@Override
	public PlaneDTO insertPlane(PlaneDTO plane) throws RemoteException {
		try {
			plane.setPlaneId(planeDAO.getMaxId()+1);
			CompanyDTO cdto = plane.getCompany();
			
			if(cdto.getPlanes().contains(plane) == false){
				cdto.addPlane(plane);
				Company company = new CompanyAssembler().dtoToModel(cdto);		
				companyDAO.updateCompany(company);
			}
			return plane;
			
		} catch(DaoException e) {
			logger.error("plane insertion failed",e);
			throw new RemoteException(e.getMessage(),e);
		}
	}

	@Override
	public PlaneDTO updatePlane(PlaneDTO plane) throws RemoteException {
		try {
			return planeAssembler.modelToDto(planeDAO.updatePlane(planeAssembler.dtoToModel(plane)));
		} catch(DaoException e) {
			logger.error("plane update failed",e);
			throw new RemoteException(e.getMessage(),e);
		}
	}

	@Override
	public List<PlaneDTO> getAllPlanes() throws RemoteException {
		List<PlaneDTO> planeList = new LinkedList<PlaneDTO>();
		try {
			for (int i = 0; i < planeDAO.getPlanes().size(); i++) {
				planeList.add(planeAssembler.modelToDto(planeDAO.getPlanes().get(i)));
			}
			return planeList;
		} catch(DaoException e) {
			logger.error("plane retrieval failed",e);
			throw new RemoteException(e.getMessage(),e);
		}
	}

	@Override
	public PlaneDTO getPlaneById(int planeId) throws RemoteException {
		PlaneDTO plane = new PlaneDTO();
		try {
			plane = planeAssembler.modelToDto(planeDAO.findById(planeId));
			return plane;
		} catch (DaoException e) {
			logger.error("plane retrieval by id failed",e);
			throw new RemoteException(e.getMessage(),e);
		}
	}

	@Override
	public PlaneDTO getPlaneByModel(String model) throws RemoteException {
		try {
			PlaneDTO planeDTO = planeAssembler.modelToDto(planeDAO.getPlaneByModel(model));
			return planeDTO;
		}catch(DaoException e) {
			logger.error("plane retrieval by model failed",e);
			throw new RemoteException(e.getMessage(),e);
		}
	}

	@Override
	public List<PlaneDTO> getAllPlanesByCompany(CompanyDTO company) throws RemoteException {
		List<PlaneDTO> planeList = new LinkedList<PlaneDTO>();
		try {
			List<Plane> test = planeDAO.getPlanesByCompany(companyAssembler.dtoToModel(company));
			for (int i = 0; i < test.size(); i++) {
				planeList.add(planeAssembler.modelToDto(test.get(i)));
			}
			return planeList;
		} catch(DaoException e) {
			logger.error("getplanesbycompany failed",e);
			throw new RemoteException(e.getMessage(),e);
		}
	}

	@Override
	public List<PlaneDTO> getAvailablePlanes() throws RemoteException {
		List<PlaneDTO> planesDTOlist = new ArrayList<>();
		try {
			List<Plane> planesList = planeDAO.getAvailablePlanes();
			for (Plane plane :planesList) {
				PlaneDTO planeDTO = planeAssembler.modelToDto(plane);
				planesDTOlist.add(planeDTO);
			}
			return planesDTOlist; 
		}catch(DaoException e) {
			logger.error("getAvailablePlanes failed",e);
			throw new RemoteException(e.getMessage(),e);
			
		}
	}

	@Override
	public void deletePlane(PlaneDTO planeDTO) throws RemoteException {
		Plane plane = planeAssembler.dtoToModel(planeDTO);
		try {
			planeDAO.deletePlane(plane);
			for (Flight f : flightDao.getByPlaneAndDate(plane, new Date())) {
				itineraryDao.deleteItineraryByFlightId(f.getFlightId());
				flightDao.deleteFlight(f);
			}
			for (Flighttemplate ft : flightTemplateDao.getFlightTemplatesByPlane(plane)) {
				flightTemplateDao.deleteFlightTemplate(ft);
			}
		} catch (DaoException e) {
			logger.error("Delete plane error " + e);
			throw new RemoteException(e.getMessage(),e);
		}
	}

}
