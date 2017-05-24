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

import edu.msg.flightManagementEjb.assemblers.AirportAssembler;
import edu.msg.flightManagementEjb.assemblers.CompanyAssembler;
import edu.msg.flightManagementEjb.assemblers.FlightAssembler;
import edu.msg.flightManagementEjb.assemblers.PlaneAssembler;
import edu.msg.flightManagementEjb.daos.DaoException;
import edu.msg.flightManagementEjb.daos.FlightDAO;
import edu.msg.flightManagementEjb.model.Flight;
import edu.msg.flightManagementEjbClient.dtos.AirportDTO;
import edu.msg.flightManagementEjbClient.dtos.CompanyDTO;
import edu.msg.flightManagementEjbClient.dtos.FlightDTO;
import edu.msg.flightManagementEjbClient.dtos.PlaneDTO;
import edu.msg.flightManagementEjbClient.interfaces.FlightBeanInterface;
import edu.msg.flightManagementEjbClient.interfaces.RemoteException;

@Stateless
@LocalBean
public class FlightBean implements Serializable, FlightBeanInterface {

	private static Logger logger = LoggerFactory.getLogger(FlightBean.class);
	private static final long serialVersionUID = 1831249472598672324L;

	@EJB
	private FlightDAO flightDAO;

	private FlightAssembler flightAssembler = new FlightAssembler();
	private CompanyAssembler companyAssembler = new CompanyAssembler();

	@Override
	public FlightDTO insertFlight(FlightDTO flight) throws RemoteException {
		try {
			return flightAssembler.modelToDto(flightDAO.insertFlight(flightAssembler.dtoToModelSimple(flight)));
		} catch (DaoException e) {
			logger.error("Flight insertion failed", e);
			throw new RemoteException(e.getMessage(), e);
		}
	}

	@Override
	public FlightDTO updateFlight(FlightDTO flight) throws RemoteException {
		try {
			return flightAssembler.modelToDto(flightDAO.updateFlight(flightAssembler.dtoToModelSimple(flight)));
		} catch (DaoException e) {
			logger.error("Flight update failed", e);
			throw new RemoteException(e.getMessage(), e);
		}
	}

	@Override
	public List<FlightDTO> getAllFlights() throws RemoteException {
		List<FlightDTO> flightList = new LinkedList<FlightDTO>();
		try {
			for (int i = 0; i < flightDAO.getFlights().size(); i++) {
				flightList.add(flightAssembler.modelToDto(flightDAO.getFlights().get(i)));
			}
			return flightList;
		} catch (DaoException e) {
			logger.error("Flight retrieval failed", e);
			throw new RemoteException(e.getMessage(), e);
		}
	}

	@Override
	public void deleteFlight(FlightDTO flight) throws RemoteException {
		try {
			flightDAO.deleteFlight(flightAssembler.dtoToModelSimple(flight));
		} catch (DaoException e) {
			logger.error("Flight deletion failed", e);
			throw new RemoteException(e.getMessage(), e);
		}
	}

	@Override
	public FlightDTO getFlightById(int id) throws RemoteException {
		FlightDTO flightDTO = new FlightDTO();
		try {
			flightDTO = flightAssembler.modelToDto(flightDAO.getFlightById(id));
		} catch (DaoException e) {
			logger.error("Flight retrieval failed", e);
			throw new RemoteException(e.getMessage(), e);
		}
		return flightDTO;
	}

	@Override
	public List<FlightDTO> getAvailableFlights() throws RemoteException {
		List<FlightDTO> flightsDTOlist = new ArrayList<>();
		try {
			List<Flight> flightsList = flightDAO.getFlightsByStatus(true);
			for (Flight flight : flightsList) {
				FlightDTO flightDTO = flightAssembler.modelToDto(flight);
				flightsDTOlist.add(flightDTO);
			}
			return flightsDTOlist;
		} catch (DaoException e) {
			logger.error("Available Flights retrieval failed!", e);
			throw new RemoteException(e.getMessage(), e);

		}
	}

	@Override
	public List<FlightDTO> getAllFlightsByCompany(CompanyDTO company) throws RemoteException {
		List<FlightDTO> flightsList = new LinkedList<FlightDTO>();
		try {
			List<Flight> test = flightDAO.getAllFlightsByCompany(companyAssembler.dtoToModelSimple(company));
			for (int i = 0; i < test.size(); i++) {
				flightsList.add(flightAssembler.modelToDto(test.get(i)));
			}
			return flightsList;
		} catch (DaoException e) {
			logger.error("Flights retrieval failed", e);
			throw new RemoteException(e.getMessage(), e);
		}
	}

	@Override
	public List<FlightDTO> getByCompanyAndDate(CompanyDTO company, Date date) throws RemoteException {
		List<FlightDTO> flightsDTOlist = new ArrayList<>();
		try {
			List<Flight> flightsList = flightDAO.getByCompanyAndDate(new CompanyAssembler().dtoToModelSimple(company),
					date);
			for (Flight flight : flightsList) {
				FlightDTO flightDTO = flightAssembler.modelToDtoSimple(flight);
				flightsDTOlist.add(flightDTO);
			}
			return flightsDTOlist;
		} catch (DaoException e) {
			logger.error("Flight retrieval error " + e);
			throw new RemoteException(e.getMessage(), e);
		}

	}

	public List<FlightDTO> getByCompanyAndDate(CompanyDTO company, Date date, AirportDTO airport)
			throws RemoteException {
		List<FlightDTO> flightsDTOlist = new ArrayList<>();
		try {
			List<Flight> flightsList = flightDAO.getByCompanyAndDateWithStartPoint(
					new CompanyAssembler().dtoToModelSimple(company), date, new AirportAssembler().dtoToModel(airport));
			for (Flight flight : flightsList) {
				FlightDTO flightDTO = flightAssembler.modelToDtoSimple(flight);
				flightsDTOlist.add(flightDTO);
			}
			return flightsDTOlist;
		} catch (DaoException e) {
			logger.error("Flight retrieval failed, Login!", e);
			throw new RemoteException(e.getMessage(), e);
		}

	}

	@Override
	public boolean isPlaneFree(PlaneDTO planeDto, Date startDate, Date endDate, int selfId) {
			try{
				return flightDAO.isPlaneFree(new PlaneAssembler().dtoToModel(planeDto), startDate, endDate, selfId);
			}catch(DaoException e) {
				logger.error("Flight free testing Login!", e);
				throw new RemoteException(e.getMessage(), e);
			}
	}

}
