package business;

import java.util.ArrayList;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import db.DBUtil;

public class StatusDB {

	// ********************
	// *** Query All    ***
	// ********************
	public static ArrayList<Status> queryAll() {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        try {
            TypedQuery<Status> query = em.createQuery("SELECT s FROM Status s", Status.class);
            ArrayList<Status> allStatuses = new ArrayList<>(query.getResultList());
            return allStatuses;
        }
        finally {
            em.close();
        }
    }
	
	// ********************
	// *** Query by ID  ***
	// ********************
	public static Status queryById(int statusId) {
		EntityManager em = DBUtil.getEmFactory().createEntityManager();
		try {
			Status status = em.find(Status.class, statusId);
			return status; 
		} finally {
			em.close();
		}
	}
	
	// ***************************
	// *** Query by Unique Key ***
	// ***************************
	public static Status queryByDescription(String description) {
		EntityManager em = DBUtil.getEmFactory().createEntityManager();
		String sql = "SELECT s FROM Status s "+
		             "WHERE s.description = :description";
		try {
            TypedQuery<Status> query = em.createQuery(sql, Status.class);
            query.setParameter("description", description);
			Status status = query.getSingleResult();
			return status; 
		} catch (NoResultException e){
			return null;
		} finally {
			em.close();
		}
	}
	
	// **************
	// *** Insert ***
	// **************
	public static boolean insert(Status status) {	
		boolean isSuccessful = false;
		Status chkStatus = new Status();
		chkStatus = queryByDescription(status.getDescription());
		if (chkStatus != null) {
			System.out.println("%%% Description Already Exists");
		} else {
			EntityManager em = DBUtil.getEmFactory().createEntityManager();
			EntityTransaction trans = em.getTransaction();
			try {	
				trans.begin();
				em.persist(status); 
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
	public static boolean update(Status status) {
		boolean isSuccessful = false;
		Status chkStatus = new Status();
		chkStatus = queryById(status.getId());
		if (chkStatus == null) {
			System.out.println("%%% Record Does Not Exist");
		} else {
			chkStatus = queryByDescription(status.getDescription());
			if ((chkStatus != null) && (chkStatus.getId() != status.getId())) {
				System.out.println("%%% Record Already Exists");
			} else {
				EntityManager em = DBUtil.getEmFactory().createEntityManager();
				EntityTransaction trans = em.getTransaction();
				try {	
					trans.begin();
					em.merge(status); 
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
	public static boolean delete(Status status) {	
		boolean isSuccessful = false;
		Status chkStatus = new Status();
		chkStatus = queryById(status.getId());
		if (chkStatus == null) {
			System.out.println("%%% Record Does Not Exist");
		} else {
			EntityManager em = DBUtil.getEmFactory().createEntityManager();
			EntityTransaction trans = em.getTransaction();
			try {	
				trans.begin();
				em.remove(em.merge(status)); 
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