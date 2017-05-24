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

@Stateless(name = "CompanyDAO", mappedName = "ejb/CompanyDAO")
public class CompanyDAO {

	private Logger logger = LoggerFactory.getLogger(CompanyDAO.class);

	@PersistenceContext(unitName = "flightManagement")
	private EntityManager entityManager;

	public Company insertCompany(Company company) throws DaoException {
		try {
			entityManager.persist(company);
			entityManager.flush();
			return company;
		} catch (PersistenceException e) {
			logger.error("Company insertion failed", e);
			throw new DaoException("Company insertion failed",e);
		}
	}

	public Company findById(Long companyId) {
		try {
			Company company = entityManager.find(Company.class, companyId);
			if (company == null) {
				throw new DaoException("Can't find Company for ID " + companyId);
			}
			return company;
		} catch (PersistenceException e) {
			logger.error("Company retrieval by id failed", e);
			throw new DaoException("Company retrieval by id failed",e);
		}

	}

	public Company updateCompany(Company company) {
		try {

			company = entityManager.merge(company);
			entityManager.flush();
			return company;
		} catch (PersistenceException e) {
			logger.error("Company update failed ", e);
			throw new DaoException("Company update failed ",e);
		}
	}

	public void deleteCompany(Company company) {
		try {
			company.setStatus(false);
			company = entityManager.merge(company);
			entityManager.flush();
		} catch (PersistenceException e) {
			logger.error("Company deletion failed ", e);
			throw new DaoException("Company deletion failed ",e);
		}
	}

	public void deleteCompanyById(int companyId) {
		try {
			Company company = entityManager.find(Company.class, companyId);
			if (company != null) {
				company.setStatus(false);
				company = entityManager.merge(company);
				entityManager.flush();
			}
		} catch (PersistenceException e) {
			logger.error("Company deletion by id failed ", e);
			throw new DaoException("Company deletion by id failed ",e);
		}
	}

	public List<Company> getCompanies() {
		try {
			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<Company> cq = cb.createQuery(Company.class);
			Root<Company> root = cq.from(Company.class);
			CriteriaQuery<Company> allEntities = cq.select(root);
			TypedQuery<Company> tq = entityManager.createQuery(allEntities);
			return tq.getResultList();

		} catch (PersistenceException e) {
			logger.error("Companies retrieval failed", e);
			throw new DaoException("Companies retrieval failed",e);
		}
	}

	public Company getByCompanyName(String companyName) {
		try {
			TypedQuery<Company> query = entityManager
					.createQuery("SELECT c FROM Company c WHERE c.companyName = :companyName", Company.class);
			query.setParameter("companyName", companyName);
			List<Company> userList = query.getResultList();
			if (userList.size() == 1) {
				return userList.get(0);
			} else {
				return null;
			}
		} catch (PersistenceException e) {
			logger.error("Company retrieval by Name failed", e);
			throw new DaoException("Company retrieval by Name failed",e);
		}
	}

	public Company getByCompanyId(int id) {
		try {
			Company company = entityManager.find(Company.class, id);
			if (company == null) {
				throw new DaoException("Can't find Company for ID " + id);
			}
			return company;
		} catch (PersistenceException e) {
			logger.error("Companies retrieval by id failed", e);
			throw new DaoException("Companies retrieval by id failed",e);
		}
	}

	public List<Company> getAvailableCompanies() {
		try {
			TypedQuery<Company> query = entityManager.createQuery("SELECT c FROM Company c WHERE c.status = :status",
					Company.class);
			query.setParameter("status", true);
			return query.getResultList();

		} catch (PersistenceException e) {
			logger.error("Company retrieval by true status failed", e);
			throw new DaoException("Company retrieval by true status failed",e);
		}
	}

}
