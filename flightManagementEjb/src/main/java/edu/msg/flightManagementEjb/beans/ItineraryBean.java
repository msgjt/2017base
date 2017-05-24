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
import edu.msg.flightManagementEjb.assemblers.ItineraryAssembler;
import edu.msg.flightManagementEjb.daos.DaoException;
import edu.msg.flightManagementEjb.daos.ItineraryDAO;
import edu.msg.flightManagementEjb.model.Itinerary;
import edu.msg.flightManagementEjbClient.dtos.CompanyDTO;
import edu.msg.flightManagementEjbClient.dtos.ItineraryDTO;
import edu.msg.flightManagementEjbClient.interfaces.ItineraryBeanInterface;
import edu.msg.flightManagementEjbClient.interfaces.RemoteException;

@Stateless
@LocalBean
public class ItineraryBean implements Serializable, ItineraryBeanInterface {

	private static Logger logger = LoggerFactory.getLogger(ItineraryBean.class);
	private static final long serialVersionUID = 2025121820767381901L;

	@EJB
	private ItineraryDAO itineraryDAO;

	private ItineraryAssembler itineraryAssembler = new ItineraryAssembler();

	@Override
	public ItineraryDTO insertItinerary(ItineraryDTO itineraryDTO) throws RemoteException {
		Itinerary itinerary = itineraryAssembler.dtoToModel(itineraryDTO);
		try {
			ItineraryDTO insertDTO = itineraryAssembler.modelToDto(itineraryDAO.insertItinerary(itinerary));
			return insertDTO;
		} catch (DaoException e) {
			logger.error("insert itinerary error " + e);
			throw new RemoteException(e.getMessage(), e);
		}
	}

	@Override
	public ItineraryDTO findById(int itineraryId) throws RemoteException {
		try {
			ItineraryDTO findDTO = itineraryAssembler.modelToDto(itineraryDAO.findById(itineraryId));
			return findDTO;
		} catch (DaoException e) {
			logger.error("findById error " + e);
			throw new RemoteException(e.getMessage(), e);
		}
	}

	@Override
	public ItineraryDTO updateItinerary(ItineraryDTO itineraryDTO) throws RemoteException  {
		Itinerary itinerary = itineraryAssembler.dtoToModel(itineraryDTO);
		try {
			ItineraryDTO updatedDTO = itineraryAssembler.modelToDto(itineraryDAO.updateItinerary(itinerary));
			return updatedDTO;
		} catch (DaoException e) {
			logger.error("update itinerary error " + e);
			throw new RemoteException(e.getMessage(), e);
		}
	}

	@Override
	public void deleteItinerary(ItineraryDTO itineraryDTO) throws RemoteException {
		Itinerary itinerary = itineraryAssembler.dtoToModel(itineraryDTO);
		try {
			itineraryDAO.deleteItinerary(itinerary);
		} catch (DaoException e) {
			logger.error("delete itinerary error " + e);
			throw new RemoteException(e.getMessage(), e);
		}

	}

	@Override
	public void deleteItineraryById(int itineraryId) throws RemoteException {
		try {
			itineraryDAO.deleteItineraryById(itineraryId);
		} catch (DaoException e) {
			logger.error("delete itinerary by Id error " + e);
			throw new RemoteException(e.getMessage(), e);
		}

	}

	@Override
	public List<ItineraryDTO> getItineraries() throws RemoteException  {
		List<ItineraryDTO> itinerariesDTOlist = new ArrayList<>();
		try {
			List<Itinerary> itinerariesList = itineraryDAO.getItineraries();
			for (Itinerary itinerary : itinerariesList) {
				ItineraryDTO itineraryDTO = itineraryAssembler.modelToDto(itinerary);
				itinerariesDTOlist.add(itineraryDTO);
			}
			return itinerariesDTOlist;
		} catch (DaoException e) {
			logger.error("getItineraries error " + e);
			throw new RemoteException(e.getMessage(), e);
		}
	}

	@Override
	public ItineraryDTO getByItineraryName(String itineraryName) throws RemoteException {
		try {
			ItineraryDTO getItinerary = itineraryAssembler.modelToDto(itineraryDAO.getByItineraryName(itineraryName));
			return getItinerary;
		} catch (DaoException e) {
			logger.error("getItineraryByName error " + e);
			throw new RemoteException(e.getMessage(), e);
		}
	}

	@Override
	public List<ItineraryDTO> getByCompany(CompanyDTO company) throws RemoteException {
		List<ItineraryDTO> itinerariesDTOlist = new ArrayList<>();
		try {
			List<Itinerary> itinerariesList = itineraryDAO
					.getByCompany(new CompanyAssembler().dtoToModelSimple(company));
			for (Itinerary itinerary : itinerariesList) {
				ItineraryDTO itineraryDTO = itineraryAssembler.modelToDto(itinerary);
				itinerariesDTOlist.add(itineraryDTO);
			}
			return itinerariesDTOlist;
		} catch (DaoException e) {
			logger.error("getByCompany error " + e);
			throw new RemoteException(e.getMessage(), e);
		}
	}

	@Override
	public void deleteItineraryByFlightId(int flightId) throws RemoteException {
		try {
			itineraryDAO.deleteItineraryByFlightId(flightId);
		} catch (DaoException e) {
			logger.error("delete itinerary by FlightId error " + e);
			throw new RemoteException(e.getMessage(), e);
		}
		
	}



}
