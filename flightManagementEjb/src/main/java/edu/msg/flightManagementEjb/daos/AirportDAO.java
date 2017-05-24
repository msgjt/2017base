package edu.msg.flightManagementEjb.daos;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.msg.flightManagementEjb.model.Airport;




@Stateless(name = "AirportDAO", mappedName = "ejb/AirportDAO")
public class AirportDAO {

	private static Logger logger = LoggerFactory.getLogger(AirportDAO.class);
	
	@PersistenceContext(unitName = "flightManagement")
	private EntityManager entityManager;
	
	@EJB
	private FlightDAO flightDao;
	
	@EJB
	private FlightTemplateDAO flighTemplateDAO;
	
	@EJB
	private ItineraryDAO itineraryDAO;

	public Airport insertAirport(Airport airport) throws DaoException {
		try {
			entityManager.persist(airport);
			entityManager.flush();
			return airport;
		} catch (PersistenceException e) {
			logger.error("Airport insertion failed", e);
			throw new DaoException("Airport insertion failed",e);
		}
	}

	public Airport findById(int airportId) {
		try {
			Airport airport = entityManager.find(Airport.class, airportId);
			if (airport == null) {
				throw new DaoException("Can't find Airport for ID " + airportId);
			}
			return airport;
		} catch (PersistenceException e) {
			logger.error("Airports retrieval by id failed", e);
			throw new DaoException("Airports retrieval by id failed",e);
		}

	}

	public Airport updateAirport(Airport airport) {
		try {
			airport = entityManager.merge(airport);
			entityManager.flush();
			return airport;
		} catch (PersistenceException e) {
			logger.error("Airport update failed ", e);
			throw new DaoException("Airport update failed ",e);
		}
	}

	public void deleteAirport(Airport airport) {
		try {
			airport = entityManager.merge(airport);
			airport.setStatus(false);
			flightDao.deleteFlightsByAirportId(airport.getAirportId());
			itineraryDAO.deleteItineraryByAirportId(airport.getAirportId());
			flighTemplateDAO.deleteTemplateByAirportID(airport.getAirportId());
			airport.setFlights1(new ArrayList<>());
			airport.setFlights2(new ArrayList<>());
			airport.setFlighttemplates1(new ArrayList<>());
			airport.setFlighttemplates2(new ArrayList<>());
			
			airport.setItineraries1(new ArrayList<>());
			airport.setItineraries2(new ArrayList<>());			
			
			entityManager.merge(airport);
			
		} catch (PersistenceException e) {
			logger.error("Airport deletion failed ", e);
			throw new DaoException("Airport deletion failed ",e);
		}
	}

	public void deleteAirportById(int airportId) {
		try {
			Airport airport = entityManager.find(Airport.class, airportId);
			if (airport != null) {
				airport.setStatus(false);
				entityManager.merge(airport);
			}
		} catch (PersistenceException e) {
			logger.error("Airport deletion by id failed ", e);
			throw new DaoException("Airport deletion by id failed ",e);
		}
	}

	public List<Airport> getAirports() {
		try {
			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<Airport> cq = cb.createQuery(Airport.class);
			Root<Airport> root = cq.from(Airport.class);
			CriteriaQuery<Airport> allEntities = cq.select(root);
			TypedQuery<Airport> tq = entityManager.createQuery(allEntities);
			return tq.getResultList();

		} catch (PersistenceException e) {
			logger.error("Airports retrieval failed ", e);
			throw new DaoException("Airports retrieval failed ",e);
		}
	}
	
	public Airport getAirportByName(String airportName) {
		try {
			TypedQuery<Airport> query = entityManager.createQuery("SELECT a FROM Airport a WHERE a.airportName = :airportName",Airport.class);
			query.setParameter("airportName", airportName);
			List<Airport> airportList = query.getResultList();
			if (airportList.size() == 1) {
				return airportList.get(0);
			} else {
				return null;
			}
 		}catch (PersistenceException e) {
			logger.error("Airport retrieval by Name failed", e);
			throw new DaoException("Airport retrieval by Name failed",e);
		}
	}
	
	public List<Airport> getAvailableAirports() {
		try {
			TypedQuery<Airport> query = entityManager.createQuery("SELECT a FROM Airport a WHERE a.status = :status",Airport.class);
			query.setParameter("status", true);
			return query.getResultList();

		} catch (PersistenceException e) {
			logger.error("Airports retrieval by true status failed", e);
			throw new DaoException("Airports retrieval by true status failed",e);
		}
	}
}
