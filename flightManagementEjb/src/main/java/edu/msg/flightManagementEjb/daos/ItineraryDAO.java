package edu.msg.flightManagementEjb.daos;

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


import edu.msg.flightManagementEjb.model.Company;
import edu.msg.flightManagementEjb.model.Itinerary;

@Stateless(name = "ItineraryDAO", mappedName = "ejb/ItineraryDAO")
public class ItineraryDAO {

	private static Logger logger = LoggerFactory.getLogger(ItineraryDAO.class);

	@PersistenceContext(unitName = "flightManagement")
	private EntityManager entityManager;

	public Itinerary insertItinerary(Itinerary itinerary) throws DaoException {
		try {
			entityManager.persist(itinerary);
			entityManager.flush();
			return itinerary;
		} catch (PersistenceException e) {
			logger.error("Itinerary insertion failed", e);
			throw new DaoException("Itinerary insertion failed",e);
		}
	}

	public Itinerary findById(int itineraryId) {
		try {
			Itinerary itinerary = entityManager.find(Itinerary.class, itineraryId);
			if (itinerary == null) {
				throw new DaoException("Can't find Itinerary for ID " + itineraryId);
			}
			return itinerary;
		} catch (PersistenceException e) {
			logger.error("Itinerary retrieval by id failed", e);
			throw new DaoException("Itinerary retrieval by id failed",e);
		}

	}

	public Itinerary updateItinerary(Itinerary itinerary) {
		try {

			itinerary = entityManager.merge(itinerary);
			entityManager.flush();
			return itinerary;
		} catch (PersistenceException e) {
			logger.error("Itinerary update failed ", e);
			throw new DaoException("Itinerary update failed ",e);
		}
	}

	public void deleteItinerary(Itinerary itinerary) {
		try {
			entityManager.remove(entityManager.merge(itinerary));
		} catch (PersistenceException e) {
			logger.error("Itinerary deletion failed ", e);
			throw new DaoException("Itinerary deletion failed ",e);
		}
	}

	public void deleteItineraryById(int itineraryId) {
		try {
			Itinerary itinerary = entityManager.find(Itinerary.class, itineraryId);
			if (itinerary != null) {
				entityManager.remove(itinerary);
			}
		} catch (PersistenceException e) {
			logger.error("Itinerary deletion by id failed ", e);
			throw new DaoException(e);
		}
	}

	public List<Itinerary> getItineraries() {
		try {
			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<Itinerary> cq = cb.createQuery(Itinerary.class);
			Root<Itinerary> root = cq.from(Itinerary.class);
			CriteriaQuery<Itinerary> allEntities = cq.select(root);
			TypedQuery<Itinerary> tq = entityManager.createQuery(allEntities);
			return tq.getResultList();

		} catch (PersistenceException e) {
			logger.error("Itineraries retrieval failed", e);
			throw new DaoException("Itineraries retrieval failed",e);
		}
	}

	public Itinerary getByItineraryName(String itineraryName) {
		try {
			TypedQuery<Itinerary> query = entityManager
					.createQuery("SELECT i FROM Itinerary i WHERE i.itineraryName = :itineraryName", Itinerary.class);
			query.setParameter("itineraryName", itineraryName);
			List<Itinerary> itineraryList = query.getResultList();
			if (itineraryList.size() == 1) {
				return itineraryList.get(0);
			} else {
				return null;
			}
		} catch (PersistenceException e) {
			logger.error("Itinerary retrieval by Name failed", e);
			throw new DaoException("Itinerary retrieval by Name failed",e);
		}
	}

	public List<Itinerary> getByCompany(Company company) {
		try {
			TypedQuery<Itinerary> query = entityManager
					.createQuery("SELECT i FROM Itinerary i WHERE i.company = :company", Itinerary.class);
			query.setParameter("company", company);
			return query.getResultList();

		} catch (PersistenceException e) {
			logger.error("Itinerary retrieval by Company failed", e);
			throw new DaoException("Itinerary retrieval by Company failed",e);
		}
	}
	
	public void deleteItineraryByAirportId(int airportId) {
		try{
			List<Itinerary> list = getItineraries();
			for(Itinerary i : list) {
				if(i.getAirport1()!=null && i.getAirport1().getAirportId() == airportId) {
					deleteItinerary(i);
				}else {
					if(i.getAirport2() != null && i.getAirport2().getAirportId() == airportId) {
						deleteItinerary(i);
					}
				}
			}
		}catch (PersistenceException e) {
			logger.error("Deletion of  itineraries by airportId failed", e);
			throw new DaoException("Deletion of  itineraries by airportId failed",e);
		}
	}
	
	public void deleteItineraryByFlightId(int flightId) {
		try{
			List<Itinerary> list = getItineraries();
			for(Itinerary i : list) {
				for (int j = 0; j < i.getFlights().size(); j++) {
					if (i.getFlights().get(j).getFlightId() == flightId) {
						deleteItinerary(i);
					}
				}
			}
		}catch (PersistenceException e) {
			logger.error("Delete itinerary by flightId failed", e);
			throw new DaoException("Deletion of  itineraries by flightId failed", e);
		}
	}
	
	

}
