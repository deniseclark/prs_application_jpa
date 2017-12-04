package business;

import java.util.ArrayList;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import db.DBUtil;

public class PurchaseRequestDB {

	// ********************
	// *** Query All    ***
	// ********************
	public static ArrayList<PurchaseRequest> queryAll() {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        try {
            TypedQuery<PurchaseRequest> query = em.createQuery("SELECT p FROM PurchaseRequest p", PurchaseRequest.class);
            ArrayList<PurchaseRequest> allPurchaseRequests = new ArrayList<>(query.getResultList());
            return allPurchaseRequests;
        }
        finally {
            em.close();
        }
    }
	
	// ********************
	// *** Query by ID  ***
	// ********************
	public static PurchaseRequest queryById(int purchaseRequestId) {
		EntityManager em = DBUtil.getEmFactory().createEntityManager();
		try {
			PurchaseRequest purchaseRequest = em.find(PurchaseRequest.class, purchaseRequestId);
			return purchaseRequest; 
		} finally {
			em.close();
		}
	}
		
	// **************
	// *** Insert ***
	// **************
	public static boolean insert(PurchaseRequest purchaseRequest) {	
		boolean isSuccessful = false;
		EntityManager em = DBUtil.getEmFactory().createEntityManager();
		EntityTransaction trans = em.getTransaction();
		try {	
			trans.begin();
			em.persist(purchaseRequest); 
			trans.commit();
			isSuccessful = true;				
		} catch (Exception e) {
			trans.rollback();
		} finally {
			em.close();
		}
		return isSuccessful;
	}
	
	// **************
	// *** Update ***
	// **************
	public static boolean update(PurchaseRequest purchaseRequest) {	
		boolean isSuccessful = false;
		EntityManager em = DBUtil.getEmFactory().createEntityManager();
		EntityTransaction trans = em.getTransaction();
		try {	
			trans.begin();
			em.merge(purchaseRequest); 
			trans.commit();
			isSuccessful = true;
		} catch (Exception e) {
			trans.rollback();
		} finally {
			em.close();
		}
		return isSuccessful;
	}
	
	// ***************
	// *** Delete  ***
	// ***************
	public static boolean delete(PurchaseRequest purchaseRequest) {	
		boolean isSuccessful = false;
		EntityManager em = DBUtil.getEmFactory().createEntityManager();
		EntityTransaction trans = em.getTransaction();
		try {	
			trans.begin();
			em.remove(em.merge(purchaseRequest)); 
			trans.commit();
			isSuccessful = true;	
		} catch (Exception e) {
			trans.rollback();
		} finally {
			em.close();
		}
		return isSuccessful;
	}
	
}