package business;

import java.util.ArrayList;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import db.DBUtil;

public class UserDB {

	// ********************
	// *** Authenticate ***
	// ********************
	public static User authenticateUser(String userName, String password) {
		EntityManager em = DBUtil.getEmFactory().createEntityManager();
		String sql = "SELECT u FROM User u "+
		             "WHERE u.userName = :userName"+
				     "  AND u.password = :password";
		try {
            TypedQuery<User> query = em.createQuery(sql, User.class);
            query.setParameter("userName", userName);
            query.setParameter("password", password);
			User user = query.getSingleResult();
			return user; 
		} catch (NoResultException e){
			return null;
		} finally {
			em.close();
		}
	}
	
	// *******************
	// *** Query All   ***
	// *******************
	public static ArrayList<User> queryAll() {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        try {
            TypedQuery<User> query = em.createQuery("SELECT u FROM User u", User.class);
            ArrayList<User> allUsers = new ArrayList<>(query.getResultList());
            return allUsers;
        }
        finally {
            em.close();
        }
    }
	
	// ********************
	// *** Query by ID  ***
	// ********************
	public static User queryById(int userId) {
		EntityManager em = DBUtil.getEmFactory().createEntityManager();
		try {
			User user = em.find(User.class, userId);
			return user; 
		} finally {
			em.close();
		}
	}
	
	// ***************************
	// *** Query by Unique Key ***
	// ***************************
	public static User queryByUserName(String userName) {
		EntityManager em = DBUtil.getEmFactory().createEntityManager();
		String sql = "SELECT u FROM User u "+
		             "WHERE u.userName = :userName";
		try {
            TypedQuery<User> query = em.createQuery(sql, User.class);
            query.setParameter("userName", userName);
			User user = query.getSingleResult();
			return user; 
		} catch (NoResultException e){
			return null;
		} finally {
			em.close();
		}
	}
	
	// **************
	// *** Insert ***
	// **************
	public static boolean insert(User user) {	
		boolean isSuccessful = false;
		User chkUser = new User();
		chkUser = queryByUserName(user.getUserName());
		if (chkUser != null) {
			System.out.println("%%% Username Already Exists");
		} else {
			EntityManager em = DBUtil.getEmFactory().createEntityManager();
			EntityTransaction trans = em.getTransaction();
			try {	
				trans.begin();
				em.persist(user); 
				trans.commit();
				isSuccessful = true;				
			} catch (Exception e) {
				trans.rollback();
			} finally {
				em.close();
			}
		}
		return isSuccessful;
	}
	
	// **************
	// *** Update ***
	// **************
	public static boolean update(User user) {
		boolean isSuccessful = false;
		User chkUser = new User();
		chkUser = queryById(user.getId());
		if (chkUser == null) {
			System.out.println("%%% Record Does Not Exist");
		} else {
			chkUser = queryByUserName(user.getUserName());
			if ((chkUser != null) && (chkUser.getId() != user.getId())) {
				System.out.println("%%% Record Already Exists");
			} else {
				EntityManager em = DBUtil.getEmFactory().createEntityManager();
				EntityTransaction trans = em.getTransaction();
				try {	
					trans.begin();
					em.merge(user); 
					trans.commit();
					isSuccessful = true;
				} catch (Exception e) {
					trans.rollback();
				} finally {
					em.close();
				}	
			}
		}
		return isSuccessful;
	}
	
	// ***************
	// *** Delete  ***
	// ***************
	public static boolean delete(User user) {	
		boolean isSuccessful = false;
		User chkUser = new User();
		chkUser = queryById(user.getId());
		if (chkUser == null) {
			System.out.println("%%% Record Does Not Exist");
		} else {
			EntityManager em = DBUtil.getEmFactory().createEntityManager();
			EntityTransaction trans = em.getTransaction();
			try {	
				trans.begin();
				em.remove(em.merge(user)); 
				trans.commit();
				isSuccessful = true;	
			} catch (Exception e) {
				trans.rollback();
			} finally {
				em.close();
			}	
		}
		return isSuccessful;
	}
	
}
