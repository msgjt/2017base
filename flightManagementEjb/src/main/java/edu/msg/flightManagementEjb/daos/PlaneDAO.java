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
import edu.msg.flightManagementEjb.model.Plane;

@Stateless(name = "PlaneDAO", mappedName = "ejb/PlaneDAO")
public class PlaneDAO {

	private static Logger logger = LoggerFactory.getLogger(PlaneDAO.class);
	
	@PersistenceContext(unitName = "flightManagement")
	private EntityManager entityManager;

	public Plane insertPlane(Plane plane) throws DaoException {
		try {
			entityManager.persist(plane);
			entityManager.flush();
			return plane;
		} catch (PersistenceException e) {
			logger.error("Plane insertion failed", e);
			throw new DaoException("Plane insertion failed",e);
		}
	}

	public Plane findById(int planeId) {
		try {
			Plane plane = entityManager.find(Plane.class, planeId);
			if (plane == null) {
				throw new DaoException("Can't find Plane for ID " + planeId);
			}
			return plane;
		} catch (PersistenceException e) {
			logger.error("Plane retrieval by id failed", e);
			throw new DaoException("Plane retrieval by id failed",e);
		}
	}

	public Plane updatePlane(Plane plane) {
		try {
			entityManager.merge(plane);
			entityManager.flush();
			return plane;
		} catch (PersistenceException e) {
			logger.error("Plane update failed ", e);
			throw new DaoException("Plane update failed ",e);
		}
	}

	public void deletePlane(Plane plane) {
		try {
			plane = entityManager.merge(plane);
			plane.setStatus(false);
			entityManager.merge(plane);
		} catch (PersistenceException e) {
			logger.error("Plane deletion failed ", e);
			throw new DaoException("Plane deletion failed ",e);
		}
	}

	public void deletePlaneById(int planeId) {
		try {
			Plane plane = entityManager.find(Plane.class, planeId);
			if (plane != null) {
				plane.setStatus(false);
				entityManager.merge(plane);
			}
		} catch (PersistenceException e) {
			logger.error("Planes deletion by id failed ", e);
			throw new DaoException("Planes deletion by id failed ",e);
		}
	}

	public List<Plane> getPlanes() {
		try {
			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<Plane> cq = cb.createQuery(Plane.class);
			Root<Plane> root = cq.from(Plane.class);
			CriteriaQuery<Plane> allEntities = cq.select(root);
			TypedQuery<Plane> tq = entityManager.createQuery(allEntities);
			return tq.getResultList();

		} catch (PersistenceException e) {
			logger.error("Planes retrieval failed ", e);
			throw new DaoException("Planes retrieval failed ",e);
		}
	}

	public Plane getPlaneByModel(String model) {
		try {
			TypedQuery<Plane> query = entityManager.createQuery("SELECT p FROM Plane p WHERE p.model = :model",
					Plane.class);
			query.setParameter("model", model);
			List<Plane> planeList = query.getResultList();
			if (planeList.size() == 1) {
				return planeList.get(0);
			} else {
				return null;
			}
		} catch (PersistenceException e) {
			logger.error("Plane retrieval by Model failed", e);
			throw new DaoException("Plane retrieval by Model failed",e);
		}
	}

	public List<Plane> getPlanesByCompany(Company company) {
		try {
			TypedQuery<Plane> query = entityManager.createQuery("SELECT p FROM Plane p WHERE p.company = :company AND p.status = :status",
					Plane.class);
			query.setParameter("company", company);
			query.setParameter("status", true);
			return query.getResultList();

		} catch (PersistenceException e) {
			logger.error("Planes retrieval by company failed", e);
			throw new DaoException("Planes retrieval by company failed",e);
		}
	}

	public List<Plane> getAvailablePlanes() {
		try {
			TypedQuery<Plane> query = entityManager.createQuery("SELECT p FROM Plane p WHERE p.status = :status",
					Plane.class);
			query.setParameter("status", true);
			return query.getResultList();

		} catch (PersistenceException e) {
			logger.error("Plane retrieval by true status failed", e);
			throw new DaoException("Plane retrieval by true status failed",e);
		}
	}

	public Integer getMaxId(){
		try{
			return entityManager.createQuery("select max(p.planeId) from Plane p", Integer.class).getSingleResult();
		}catch(PersistenceException e) {
			throw new DaoException("Get max ID failed",e);
		}
	}
}
