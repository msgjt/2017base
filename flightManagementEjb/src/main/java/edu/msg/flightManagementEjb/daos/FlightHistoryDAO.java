package edu.msg.flightManagementEjb.daos;

import java.util.ArrayList;
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

import edu.msg.flightManagementEjb.model.Flight;
import edu.msg.flightManagementEjb.model.FlightHistory;

@Stateless(name = "FlightHistoryDAO", mappedName = "ejb/FlightHistoryDAO")
public class FlightHistoryDAO {
	
	private static Logger logger = LoggerFactory.getLogger(FlightHistoryDAO.class);

	@PersistenceContext(unitName = "flightManagement")
	private EntityManager entityManager;
	
	public FlightHistory insertFlightHistory(FlightHistory flightHistory) throws DaoException {
		try {
			entityManager.persist(flightHistory);
			entityManager.flush();
			return flightHistory;
		} catch (PersistenceException e) {
			logger.error("FlightHistory insertion failed", e);
			throw new DaoException("FlightHistory insertion failed",e);
		}
	}
	
	public List<FlightHistory> getFlightHistories() {
		try {
			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<FlightHistory> cq = cb.createQuery(FlightHistory.class);
			Root<FlightHistory> root = cq.from(FlightHistory.class);
			CriteriaQuery<FlightHistory> allEntities = cq.select(root);
			TypedQuery<FlightHistory> tq = entityManager.createQuery(allEntities);
			return tq.getResultList();

		} catch (PersistenceException e) {
			logger.error("FlightHistory retrieval failed", e);
			throw new DaoException("FlightHistory insertion failed",e);
		}
	}
	
	public List<Flight> getFlightsFromHistory() {
		try {
			List<FlightHistory> list = getFlightHistories();
			List<Flight> flightList = new ArrayList<>();
			for (FlightHistory f : list) {
				flightList.add(f.getFlight());
			}
			return flightList;
		}catch (PersistenceException e) {
			logger.error("Flight retrieval from flight history failed", e);
			throw new DaoException("Flight retrieval from flight history failed",e);
		}
	}
	
	
	public List<FlightHistory> getHistoryFlightsByCompanyId(int companyId) {
		try {
			List<FlightHistory> flights = getFlightHistories();
			List<FlightHistory> flightsByCompanyId = new ArrayList<>();
			for (FlightHistory fh : flights) {
				if(fh.getFlight().getCompany().getCompanyId() == companyId) {
					flightsByCompanyId.add(fh);
				}
			}
			return flightsByCompanyId;
		}catch (PersistenceException e) {
			logger.error("FlightHistory retrieval by company Id failed", e);
			throw new DaoException("FlightHistory retrieval by company Id failed",e);
		}
	}
	
	
}
