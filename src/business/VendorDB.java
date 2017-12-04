package business;

import java.util.ArrayList;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import db.DBUtil;

public class VendorDB {

	// ********************
	// *** Query All    ***
	// ********************
	public static ArrayList<Vendor> queryAll() {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        try {
            TypedQuery<Vendor> query = em.createQuery("SELECT v FROM Vendor v", Vendor.class);
            ArrayList<Vendor> allVendors = new ArrayList<>(query.getResultList());
            return allVendors;
        }
        finally {
            em.close();
        }
    }
	
	// ********************
	// *** Query by ID  ***
	// ********************
	public static Vendor queryById(int vendorId) {
		EntityManager em = DBUtil.getEmFactory().createEntityManager();
		try {
			Vendor vendor = em.find(Vendor.class, vendorId);
			return vendor; 
		} finally {
			em.close();
		}
	}
	
	// *********************
	// *** Query by Code ***
	// *********************
	public static Vendor queryByCode(String code) {
		EntityManager em = DBUtil.getEmFactory().createEntityManager();
		String sql = "SELECT v FROM Vendor v "+
		             "WHERE v.code = :code";
		try {
            TypedQuery<Vendor> query = em.createQuery(sql, Vendor.class);
            query.setParameter("code", code);
			Vendor vendor = query.getSingleResult();
			return vendor; 
		} catch (NoResultException e){
			return null;
		} finally {
			em.close();
		}
	}
	// **************
	// *** Insert ***
	// **************
	public static boolean insert(Vendor vendor) {	
		boolean isSuccessful = false;
		Vendor chkVendor = new Vendor();
		chkVendor = queryByCode(vendor.getCode());
		if (chkVendor != null) {
			System.out.println("%%% Vendor Code Already Exists");
		} else {
			EntityManager em = DBUtil.getEmFactory().createEntityManager();
			EntityTransaction trans = em.getTransaction();
			try {	
				trans.begin();
				em.persist(vendor); 
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
	public static boolean update(Vendor vendor) {
		boolean isSuccessful = false;
		Vendor chkVendor = new Vendor();
		chkVendor = queryById(vendor.getId());
		if (chkVendor == null) {
			System.out.println("%%% Record Does Not Exist");
		} else {
			chkVendor = queryByCode(vendor.getCode());
			if ((chkVendor != null) && (chkVendor.getId() != vendor.getId())) {
				System.out.println("%%% Record Already Exists");
			} else {
				EntityManager em = DBUtil.getEmFactory().createEntityManager();
				EntityTransaction trans = em.getTransaction();
				try {	
					trans.begin();
					em.merge(vendor); 
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
	public static boolean delete(Vendor vendor) {	
		boolean isSuccessful = false;
		Vendor chkVendor = new Vendor();
		chkVendor = queryById(vendor.getId());
		if (chkVendor == null) {
			System.out.println("%%% Record Does Not Exist");
		} else {
			EntityManager em = DBUtil.getEmFactory().createEntityManager();
			EntityTransaction trans = em.getTransaction();
			try {	
				trans.begin();
				em.remove(em.merge(vendor)); 
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