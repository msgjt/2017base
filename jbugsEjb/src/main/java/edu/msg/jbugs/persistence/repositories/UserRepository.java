package edu.msg.jbugs.persistence.repositories;

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

import edu.msg.jbugs.persistence.entities.User;
import edu.msg.jbugs.util.PasswordEncrypter;

@Stateless(name = "UserDAO", mappedName = "ejb/UserDAO")
public class UserRepository {

	private static Logger logger = LoggerFactory.getLogger(UserRepository.class);

	@PersistenceContext(unitName = "flightManagement")
	private EntityManager entityManager;

	public void insertUser(User user) throws DaoException {
		try {
			entityManager.persist(user);
			entityManager.flush();

		} catch (PersistenceException e) {
			logger.error("User insertion failed", e);
			throw new DaoException("User insertion failed", e);
		}
	}

	public User findById(int userId) {
		try {
			User user = entityManager.find(User.class, userId);
			if (user == null) {
				throw new DaoException("Can't find User for ID " + userId);
			}
			return user;
		} catch (PersistenceException e) {
			logger.error("Users retrieval by id failed", e);
			throw new DaoException("Users retrieval by id failed", e);
		}

	}

	public User updateUser(User user) {
		try {

			user = entityManager.merge(user);
			entityManager.flush();
			return user;
		} catch (PersistenceException e) {
			logger.error("User update failed ", e);
			throw new DaoException("User update failed ", e);
		}
	}

	public void deleteUser(User user) {
		try {
			user.setStatus(false);
			entityManager.merge(user);

		} catch (PersistenceException e) {
			logger.error("User deletion failed ", e);
			throw new DaoException("User deletion failed ", e);
		}
	}

	public void deleteUserById(int userId) {
		try {
			User user = entityManager.find(User.class, userId);
			if (user != null) {
				user.setStatus(false);
				entityManager.merge(user);
			}
		} catch (PersistenceException e) {
			logger.error("Users deletion by id failed ", e);
			throw new DaoException("Users deletion by id failed ", e);
		}
	}

	public List<User> getUsers() {
		try {
			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<User> cq = cb.createQuery(User.class);
			Root<User> root = cq.from(User.class);
			CriteriaQuery<User> allEntities = cq.select(root);
			TypedQuery<User> tq = entityManager.createQuery(allEntities);
			return tq.getResultList();

		} catch (PersistenceException e) {
			logger.error("Users retrieval failed", e);
			throw new DaoException("Users retrieval failed", e);
		}
	}

	public List<User> getUsersByType(String type) {
		try {
			TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u WHERE u.type = :type", User.class);
			query.setParameter("type", type);
			return query.getResultList();

		} catch (PersistenceException e) {
			logger.error("Users retrieval by type failed", e);
			throw new DaoException("Users retrieval by type failed", e);
		}
	}

	public String validateUserNameAndPassword(String userName, String password) {
		try {

			password = PasswordEncrypter.getGeneratedHashedPassword(password, "");

			TypedQuery<User> query = entityManager.createQuery(
					"SELECT u FROM User u WHERE u.userName = :userName AND u.password = :password AND u.status = true",
					User.class);

			query.setParameter("userName", userName);
			query.setParameter("password", password);

			List<User> userList = query.getResultList();
			if (userList.size() == 1) {
				return userList.get(0).getType().toString();
			} else {
				return "";
			}
		} catch (PersistenceException e) {
			logger.error("Validation of username and password failed", e);
			throw new DaoException("Validation of username and password failed", e);
		}

	}

	public User getByUserName(String userName) {
		try {
			TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u WHERE u.userName = :userName",
					User.class);
			query.setParameter("userName", userName);
			List<User> userList = query.getResultList();
			if (userList.size() == 1) {
				return userList.get(0);
			} else {
				return null;
			}
		} catch (PersistenceException e) {
			logger.error("User retrieval by Name failed", e);
			throw new DaoException("User retrieval by Name failed", e);
		}
	}

	public List<User> getAvailableUsers() {
		try {
			TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u WHERE u.status = :status",
					User.class);
			query.setParameter("status", true);
			return query.getResultList();

		} catch (PersistenceException e) {
			logger.error("User retrieval by true status failed", e);
			throw new DaoException("User retrieval by true status failed", e);
		}
	}

}
