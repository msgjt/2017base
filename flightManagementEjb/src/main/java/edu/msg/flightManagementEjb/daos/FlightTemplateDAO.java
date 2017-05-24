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
import edu.msg.flightManagementEjb.model.Flighttemplate;
import edu.msg.flightManagementEjb.model.Plane;


@Stateless(name = "FlightTemplateDAO", mappedName = "ejb/FlightTemplateDAO")
public class FlightTemplateDAO {

	private static Logger logger = LoggerFactory.getLogger(FlightTemplateDAO.class);

	@PersistenceContext(unitName = "flightManagement")
	private EntityManager entityManager;
	
	public Flighttemplate insertFlightTemplate(Flighttemplate flightTemplate) throws DaoException {
		try {
			entityManager.persist(flightTemplate);
			entityManager.flush();
			return flightTemplate;
		} catch (PersistenceException e) {
			logger.error("Flighttemplate insertion failed", e);
			throw new DaoException("Flighttemplate insertion failed",e);
		}
	}

	public Flighttemplate findById(int flightTemplateId) {
		try {
			Flighttemplate flightTemplate = entityManager.find(Flighttemplate.class, flightTemplateId);
			if (flightTemplate == null) {
				throw new DaoException("Can't find Template for ID " + flightTemplateId);
			}
			return flightTemplate;
		} catch (PersistenceException e) {
			logger.error("Template retrieval by id failed", e);
			throw new DaoException("Template retrieval by id failed",e);
		}

	}

	
	public Flighttemplate updateFlightTemplate(Flighttemplate flightTemplate) {
		try {	
			flightTemplate = entityManager.merge(flightTemplate);
			entityManager.flush();
			return flightTemplate;
		} catch (PersistenceException e) {
			logger.error("Flight Template update failed ", e);
			throw new DaoException("Flight Template update failed ",e);
		}
	}

	public void deleteFlightTemplate(Flighttemplate flightTemplate) {
		try {
			entityManager.remove(entityManager.merge(flightTemplate));
			entityManager.flush();
		} catch (PersistenceException e) {
			logger.error("Flight Template deletion failed ", e);
			throw new DaoException("Flight Template deletion failed ",e);
		}
	}

	public void deleteFlightTemplateById(int flightTemplateId) {
		try {
			Flighttemplate flightTemplate = entityManager.find(Flighttemplate.class, flightTemplateId);
			if (flightTemplate != null) {
				entityManager.remove(flightTemplate);
				entityManager.flush();
			}
		} catch (PersistenceException e) {
			logger.error("Templates deletion by id failed ", e);
			throw new DaoException("Templates deletion by id failed ",e);
		}
	}

	public List<Flighttemplate> getFlightTemplates() {
		try {
			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<Flighttemplate> cq = cb.createQuery(Flighttemplate.class);
			Root<Flighttemplate> root = cq.from(Flighttemplate.class);
			CriteriaQuery<Flighttemplate> allEntities = cq.select(root);
			TypedQuery<Flighttemplate> tq = entityManager.createQuery(allEntities);
			return tq.getResultList();

		} catch (PersistenceException e) {
			logger.error("Flight Templates retrieval failed", e);
			throw new DaoException(e);
		}
	}

	public List<Flighttemplate> getFlightTemplatesByCompany(Company company) {
		try {
			TypedQuery<Flighttemplate> query = entityManager.createQuery("SELECT f FROM Flighttemplate f WHERE f.company = :company", Flighttemplate.class);
			query.setParameter("company", company);
			return query.getResultList();

		} catch (PersistenceException e) {
			logger.error("Flight template retrieval by company failed", e);
			throw new DaoException("Flight template retrieval by company failed",e);
		}
	}
	
	public List<Flighttemplate> getFlightTemplatesByPlane(Plane plane) {
		try {
			TypedQuery<Flighttemplate> query = entityManager.createQuery("SELECT f FROM Flighttemplate f WHERE f.plane = :plane", Flighttemplate.class);
			query.setParameter("plane", plane);
			return query.getResultList();

		} catch (PersistenceException e) {
			logger.error("Flight template retrieval by company failed", e);
			throw new DaoException("Flight template retrieval by company failed",e);
		}
	}
	
	public List<Flighttemplate> getAvailableFlightTemplates() {
		try {
			TypedQuery<Flighttemplate> query = entityManager.createQuery("SELECT f FROM Flighttemplate f , Company c WHERE f.company = c AND c.status = :status", Flighttemplate.class);
			query.setParameter("status", true);
			return query.getResultList();

		} catch (PersistenceException e) {
			logger.error("Available Flight Templates retrieval failed", e);
			throw new DaoException("Available Flight Templates retrieval failed",e);
		}
	}
	
	public void deleteTemplateByAirportID(int airportId) {
		try {
			List<Flighttemplate> list =getFlightTemplates();
			for (Flighttemplate f : list) {
				if( (f.getAirport1() !=null) && (f.getAirport1().getAirportId() == airportId)) {
					deleteFlightTemplate(f);
				}else {
					if((f.getAirport2()!=null ) && (f.getAirport2().getAirportId() == airportId)) {
						deleteFlightTemplate(f);
					}
				}		
			}
		}catch (PersistenceException e) {
			logger.error("Flight templates deletion by airportid failed", e);
			throw new DaoException("Flight templates deletion by airportid failed",e);
		}
	}
	
	public List<Flighttemplate> getFlightTemplatesByNull() {
		try {
			TypedQuery<Flighttemplate> query = entityManager.createQuery("SELECT f FROM Flighttemplate f WHERE f.company IS NULL", Flighttemplate.class);
			return query.getResultList();

		} catch (PersistenceException e) {
			logger.error("Flight template retrieval by company failed", e);
			throw new DaoException("Flight template retrieval by company failed",e);
		}
	}
}
