package business;

import java.util.ArrayList;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import db.DBUtil;

public class PurchaseRequestLineItemDB {

	// ********************
	// *** Query All    ***
	// ********************
	public static ArrayList<PurchaseRequestLineItem> queryAll() {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        try {
            TypedQuery<PurchaseRequestLineItem> query = em.createQuery("SELECT p FROM PurchaseRequestLineItem p", PurchaseRequestLineItem.class);
            ArrayList<PurchaseRequestLineItem> allPurchaseRequestLineItems = new ArrayList<>(query.getResultList());
            return allPurchaseRequestLineItems;
        }
        finally {
            em.close();
        }
    }
	
	// ********************
	// *** Query by ID  ***
	// ********************
	public static PurchaseRequestLineItem queryById(int purchaseRequestLineItemId) {
		EntityManager em = DBUtil.getEmFactory().createEntityManager();
		try {
			PurchaseRequestLineItem purchaseRequestLineItem = em.find(PurchaseRequestLineItem.class, purchaseRequestLineItemId);
			return purchaseRequestLineItem; 
		} finally {
			em.close();
		}
	}
		
	// **************
	// *** Insert ***
	// **************
	public static boolean insert(PurchaseRequestLineItem purchaseRequestLineItem) {	
		boolean isSuccessful = false;
		EntityManager em = DBUtil.getEmFactory().createEntityManager();
		EntityTransaction trans = em.getTransaction();
		try {	
			trans.begin();
			em.persist(purchaseRequestLineItem); 
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
	public static boolean update(PurchaseRequestLineItem purchaseRequestLineItem) {	
		boolean isSuccessful = false;
		EntityManager em = DBUtil.getEmFactory().createEntityManager();
		EntityTransaction trans = em.getTransaction();
		try {	
			trans.begin();
			em.merge(purchaseRequestLineItem); 
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
	public static boolean delete(PurchaseRequestLineItem purchaseRequestLineItem) {	
		boolean isSuccessful = false;
		EntityManager em = DBUtil.getEmFactory().createEntityManager();
		EntityTransaction trans = em.getTransaction();
		try {	
			trans.begin();
			em.remove(em.merge(purchaseRequestLineItem)); 
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