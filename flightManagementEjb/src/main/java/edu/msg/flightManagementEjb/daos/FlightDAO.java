package edu.msg.flightManagementEjb.daos;

import java.util.Date;
import java.util.List;

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
import edu.msg.flightManagementEjb.model.Company;
import edu.msg.flightManagementEjb.model.Flight;
import edu.msg.flightManagementEjb.model.Plane;

@Stateless(name = "FlightDAO", mappedName = "ejb/FlightDAO")
public class FlightDAO {

	private static Logger logger = LoggerFactory.getLogger(FlightDAO.class);

	@PersistenceContext(unitName = "flightManagement")
	private EntityManager entityManager;

	public Flight insertFlight(Flight flight) throws DaoException {
		try {
			entityManager.persist(flight);
			entityManager.flush();
			return flight;
		} catch (PersistenceException e) {
			logger.error("Flight insertion failed", e);
			throw new DaoException("Flight insertion failed", e);
		}
	}

	public Flight findById(int flightId) {
		try {
			Flight flight = entityManager.find(Flight.class, flightId);
			if (flight == null) {
				throw new DaoException("Can't find Flight for ID " + flightId);
			}
			return flight;
		} catch (PersistenceException e) {
			logger.error("Flight retrieval by id failed", e);
			throw new DaoException("Flight retrieval by id failed", e);
		}
	}

	public Flight updateFlight(Flight flight) {
		try {
			flight = entityManager.merge(flight);
			entityManager.flush();
			return flight;
		} catch (PersistenceException e) {
			logger.error("Flight update failed ", e);
			throw new DaoException("Flight update failed ", e);
		}
	}

	public void deleteFlight(Flight flight) {
		try {

			flight.setStatus(false);
			entityManager.merge(flight);

		} catch (PersistenceException e) {
			logger.error("Flight deletion failed ", e);
			throw new DaoException("Flight deletion failed ", e);
		}
	}

	public void deleteFlightById(int FlightId) {
		try {
			Flight flight = entityManager.find(Flight.class, FlightId);
			flight.setStatus(false);
			flight = entityManager.merge(flight);
		} catch (PersistenceException e) {
			logger.error("Flight deletion by id failed ", e);
			throw new DaoException("Flight deletion by id failed ", e);
		}
	}

	public List<Flight> getFlights() {
		try {
			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<Flight> cq = cb.createQuery(Flight.class);
			Root<Flight> root = cq.from(Flight.class);
			CriteriaQuery<Flight> allEntities = cq.select(root);
			TypedQuery<Flight> tq = entityManager.createQuery(allEntities);
			return tq.getResultList();

		} catch (PersistenceException e) {
			logger.error("Flights retrieval failed", e);
			throw new DaoException("Flights retrieval failed", e);
		}
	}

	public Flight getFlightById(int id) {
		try {
			Flight flight = entityManager.find(Flight.class, id);
			if (flight == null) {
				throw new DaoException("Can't find Flight for ID " + id);
			}
			return flight;
		} catch (PersistenceException e) {
			logger.error("Flight retrieval by id failed", e);
			throw new DaoException("Flight retrieval by id failed", e);
		}
	}

	public List<Flight> getFlightsByStatus(boolean status) {
		try {
			TypedQuery<Flight> query = entityManager.createQuery("SELECT f FROM Flight f WHERE f.status = :status",
					Flight.class);
			query.setParameter("status", status);
			return query.getResultList();

		} catch (PersistenceException e) {
			logger.error("Flights retrieval by status failed", e);
			throw new DaoException("Flights retrieval by status failed", e);
		}
	}

	public List<Flight> getCompletedFlights(Date endDate) {
		try {
			TypedQuery<Flight> query = entityManager.createQuery("SELECT f FROM Flight f WHERE f.endDate = :endDate",
					Flight.class);
			query.setParameter("endDate", endDate);
			return query.getResultList();

		} catch (PersistenceException e) {
			logger.error("Flights retrieval by end date failed", e);
			throw new DaoException("Flights retrieval by end date failed", e);
		}
	}

	public List<Flight> getByCompanyAndDate(Company company, Date date) {
		try {
			TypedQuery<Flight> query = entityManager.createQuery(
					"SELECT f FROM Flight f WHERE f.startDate > :date AND f.company = :company AND f.status = true",
					Flight.class);
			query.setParameter("date", date);
			query.setParameter("company", company);
			return query.getResultList();
		} catch (PersistenceException e) {
			logger.error("Flights retrieval by company and date failed", e);
			throw new DaoException("Flights retrieval by company and date failed", e);
		}
	}

	public List<Flight> getByPlaneAndDate(Plane plane, Date date) {
		try {
			TypedQuery<Flight> query = entityManager.createQuery(
					"SELECT f FROM Flight f WHERE f.startDate > :date AND f.plane = :plane AND f.status = true",
					Flight.class);
			query.setParameter("date", date);
			query.setParameter("plane", plane);
			return query.getResultList();
		} catch (PersistenceException e) {
			logger.error("Flights retrieval by plane and date failed", e);
			throw new DaoException("Flights retrieval by plane and date failed", e);
		}
	}

	public List<Flight> getByCompanyAndDateWithStartPoint(Company company, Date date, Airport airport) {
		try {
			TypedQuery<Flight> query = entityManager.createQuery(
					"SELECT f FROM Flight f WHERE f.startDate > :date AND f.company = :company AND f.airport1 = :airport AND f.status = true",
					Flight.class);
			query.setParameter("date", date);
			query.setParameter("company", company);
			query.setParameter("airport", airport);
			return query.getResultList();
		} catch (PersistenceException e) {
			logger.error("Flights retrieval by company and date failed", e);
			throw new DaoException("Flights retrieval by company and date failed", e);
		}
	}

	public List<Flight> getAllFlightsByCompany(Company company) {
		try {
			TypedQuery<Flight> query = entityManager.createQuery(
					"SELECT f FROM Flight f WHERE f.company = :company AND f.status = :status", Flight.class);
			query.setParameter("company", company);
			query.setParameter("status", true);
			return query.getResultList();

		} catch (PersistenceException e) {
			logger.error("FLights retrieval by company failed", e);
			throw new DaoException("FLights retrieval by company", e);
		}
	}

	public void deleteFlightsByAirportId(int airportId) {
		try {
			List<Flight> list = getFlightsByStatus(true);
			for (Flight f : list) {
				if (f.getAirport1() != null && f.getAirport1().getAirportId() == airportId) {
					deleteFlight(f);
				} else {
					if (f.getAirport2() != null && f.getAirport2().getAirportId() == airportId) {
						deleteFlight(f);
					}
				}

			}
		} catch (PersistenceException e) {
			logger.error("Flight deletion by airportId failed", e);
			throw new DaoException("Flight deletion by airportId failed", e);
		}

	}

	public boolean isPlaneFree(Plane plane, Date startDate, Date endDate, int selfId) {
		try {
			TypedQuery<Flight> query = entityManager.createQuery(
					"SELECT f FROM Flight f WHERE f.flightId != :selfId AND f.status = :status AND f.plane = :plane AND (:startDate BETWEEN f.startDate AND f.endDate OR :endDate BETWEEN f.startDate AND f.endDate OR f.startDate BETWEEN :startDate AND :endDate OR f.endDate BETWEEN :startDate AND :endDate)",
					Flight.class);
			query.setParameter("plane", plane);
			query.setParameter("startDate", startDate);
			query.setParameter("endDate", endDate);
			query.setParameter("status", true);
			query.setParameter("selfId", selfId);
			return query.getResultList().isEmpty();

		} catch (PersistenceException e) {
			logger.error("Plane free for flight testing failed", e);
			throw new DaoException("Plane free for flight testing failed", e);
		}
	}

}
