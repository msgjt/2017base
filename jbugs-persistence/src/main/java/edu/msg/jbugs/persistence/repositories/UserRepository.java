package edu.msg.jbugs.persistence.repositories;

import java.util.List;


import javax.ejb.LocalBean;
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

import edu.msg.jbugs.persistence.entities.UserEntity;
import edu.msg.jbugs.util.PasswordEncrypter;

@Stateless(name = "UserRepository", mappedName = "ejb/UserRepository")
@LocalBean
public class UserRepository {

	private static Logger logger = LoggerFactory.getLogger(UserRepository.class);

	@PersistenceContext(unitName = "jbugs")
	private EntityManager entityManager;

	public void insert(UserEntity user) throws RepositoryException {
		try {
			entityManager.persist(user);
			entityManager.flush();

		} catch (PersistenceException e) {
			logger.error("UserEntity insertion failed", e);
			throw new RepositoryException("UserEntity insertion failed",e);
		}
	}

	public UserEntity find(int id) {
		try {
			UserEntity user = entityManager.find(UserEntity.class, id);
			if (user == null) {
				throw new RepositoryException("Can't find UserEntity for ID " + id);
			}
			return user;
		} catch (PersistenceException e) {
			logger.error("UserEntitys retrieval by id failed", e);
			throw new RepositoryException("UserEntitys retrieval by id failed",e);
		}

	}

	public UserEntity update(UserEntity user) {
		try {

			user = entityManager.merge(user);
			entityManager.flush();
			return user;
		} catch (PersistenceException e) {
			logger.error("UserEntity update failed ", e);
			throw new RepositoryException("UserEntity update failed ",e);
		}
	}

	public void delete(UserEntity user) {
		try {

			user.setStatus(false);
			entityManager.merge(user);

		} catch (PersistenceException e) {
			logger.error("UserEntity deletion failed ", e);
			throw new RepositoryException("UserEntity deletion failed ",e);
		}
	}

	public void delete(int id) {
		try {
			UserEntity user = entityManager.find(UserEntity.class, id);
			if (user != null) {
				user.setStatus(false);
				entityManager.merge(user);
			}
		} catch (PersistenceException e) {
			logger.error("UserEntitys deletion by id failed ", e);
			throw new RepositoryException("UserEntitys deletion by id failed ",e);
		}
	}

	public List<UserEntity> findAll() {
		try {
			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<UserEntity> cq = cb.createQuery(UserEntity.class);
			Root<UserEntity> root = cq.from(UserEntity.class);
			CriteriaQuery<UserEntity> allEntities = cq.select(root);
			TypedQuery<UserEntity> tq = entityManager.createQuery(allEntities);
			return tq.getResultList();

		} catch (PersistenceException e) {
			logger.error("UserEntitys retrieval failed", e);
			throw new RepositoryException("UserEntitys retrieval failed",e);
		}
	}

	public List<UserEntity> findByType(String type) {
		try {
			TypedQuery<UserEntity> query = entityManager.createQuery("SELECT u FROM user u WHERE u.type = :type", UserEntity.class);
			query.setParameter("type", type);
			return query.getResultList();

		} catch (PersistenceException e) {
			logger.error("UserEntitys retrieval by type failed", e);
			throw new RepositoryException("UserEntitys retrieval by type failed",e);
		}
	}

	public String validateUserNameAndPassword(String userName, String password) {
		try {
			//password = PasswordEncrypter.getGeneratedHashedPassword(password, "");
			TypedQuery<UserEntity> query = entityManager.createQuery(
					"SELECT u FROM UserEntity u WHERE u.userName = :userName AND u.password = :password AND u.status = true",
					UserEntity.class);

			query.setParameter("userName", userName);
			query.setParameter("password", password);
			List<UserEntity> userList = query.getResultList();
			if (userList.size() == 1) {
				return userList.get(0).getType().toString();
			} else {
				return "";
			}
		} catch (PersistenceException e) {
			logger.error("Validation of username and password failed", e);
			throw new RepositoryException("Validation of username and password failed",e);
		}

	}

	public UserEntity getByUserEntityName(String userName) {
		try {
			TypedQuery<UserEntity> query = entityManager.createQuery("SELECT u FROM user u WHERE u.userName = :userName",
					UserEntity.class);
			query.setParameter("userName", userName);
			List<UserEntity> users = query.getResultList();
			if (users.size() == 1) {
				return users.get(0);
			} else {
				return null;
			}
		} catch (PersistenceException e) {
			logger.error("UserEntity retrieval by Name failed", e);
			throw new RepositoryException("UserEntity retrieval by Name failed",e);
		}
	}
	
}
