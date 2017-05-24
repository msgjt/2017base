package edu.msg.flightManagementEjb.beans;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.msg.flightManagementEjb.assemblers.AirportAssembler;
import edu.msg.flightManagementEjb.daos.AirportDAO;
import edu.msg.flightManagementEjb.daos.DaoException;
import edu.msg.flightManagementEjb.model.Airport;
import edu.msg.flightManagementEjbClient.dtos.AirportDTO;
import edu.msg.flightManagementEjbClient.interfaces.AirportBeanInterface;
import edu.msg.flightManagementEjbClient.interfaces.RemoteException;

@Stateless
@LocalBean
public class AirportBean implements AirportBeanInterface{

	private static Logger logger = LoggerFactory.getLogger(AirportBean.class);
	
	@EJB
	private AirportDAO airportDao;

	private AirportAssembler airportAssembler = new AirportAssembler();

	@Override
	public AirportDTO insertAirport(AirportDTO airportDTO) throws RemoteException{
		Airport airport  = airportAssembler.dtoToModel(airportDTO);
		try {
			AirportDTO insertDTO = airportAssembler.modelToDto(airportDao.insertAirport(airport));
			return insertDTO;
		} catch(DaoException daoe) {
			logger.error("Airport insertion failed : " + daoe);
			throw new RemoteException(daoe.getMessage(),daoe);
		}
	}

	@Override
	public AirportDTO updateAirport(AirportDTO airportDTO) throws RemoteException {
		Airport airport  = airportAssembler.dtoToModel(airportDTO);
		try {
			AirportDTO updatedDTO = airportAssembler.modelToDto(airportDao.updateAirport(airport));
			return updatedDTO;
		} catch(DaoException daoe) {
			logger.error("Airport update failed : " + daoe);
			throw new RemoteException(daoe.getMessage(),daoe);
		} 
	}

	@Override
	public AirportDTO getAirportByName(String airportName) throws RemoteException{
		try {
			AirportDTO getAirport = airportAssembler.modelToDto(airportDao.getAirportByName(airportName));
			return getAirport;
		}catch(DaoException daoe) {
			logger.error("Airport retrieval failed : " + daoe);
			throw new RemoteException(daoe.getMessage(),daoe);
		}
	}

	@Override
	public AirportDTO findById(int airportId) throws RemoteException{
		try {
			AirportDTO getAirport = airportAssembler.modelToDto(airportDao.findById(airportId));
			return getAirport;
		}catch(DaoException daoe) {
			logger.error("Airport retrieval failed : " + daoe);
			throw new RemoteException(daoe.getMessage(),daoe);
		}
	}

	@Override
	public void deleteAirport(AirportDTO AirportDTO) throws RemoteException {
		Airport airport  = airportAssembler.dtoToModel(AirportDTO);
		try {
			airportDao.deleteAirport(airport);
		} catch(DaoException daoe) {
			logger.error("Airport delete failed : " + daoe);
			throw new RemoteException(daoe.getMessage(),daoe);
		} 
		
	}

	@Override
	public void deleteAirportById(int airportId) throws RemoteException {
		try {
			airportDao.deleteAirportById(airportId);
		} catch(DaoException daoe) {
			logger.error("Delete airport by id failed : " + daoe);
			throw new RemoteException(daoe.getMessage(),daoe);
		} 
		
	}

	@Override
	public List<AirportDTO> getAirports() throws RemoteException{
		List<AirportDTO> airportsDTOlist = new ArrayList<>();
		try {
			List<Airport> airportsList = airportDao.getAirports();
			for (Airport airport :airportsList) {
				AirportDTO airportDto = airportAssembler.modelToDto(airport);
				airportsDTOlist.add(airportDto);
			}
			return airportsDTOlist; 
		}catch(DaoException daoe) {
			logger.error("Get all airport error : " + daoe);	
			throw new RemoteException(daoe.getMessage(),daoe);
		}
		
	}
	
	public List<AirportDTO> getAvailableAirports() throws RemoteException{
		List<AirportDTO> airportsDTOlist = new ArrayList<>();
		try {
			List<Airport> airportsList = airportDao.getAvailableAirports();
			for (Airport airport :airportsList) {
				AirportDTO airportDto = airportAssembler.modelToDto(airport);
				airportsDTOlist.add(airportDto);
			}
			return airportsDTOlist; 
		}catch(DaoException daoe) {
			logger.error("getAvailableAirports error : " + daoe);
			throw new RemoteException(daoe.getMessage(),daoe);
		}
		
	}
	
}
