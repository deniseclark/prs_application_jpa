package business;

import java.util.ArrayList;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import db.DBUtil;

public class ProductDB {

	// ********************
	// *** Query All    ***
	// ********************
	public static ArrayList<Product> queryAll() {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        try {
            TypedQuery<Product> query = em.createQuery("SELECT p FROM Product p", Product.class);
            ArrayList<Product> allProducts = new ArrayList<>(query.getResultList());
            return allProducts;
        }
        finally {
            em.close();
        }
    }
	
	// ********************
	// *** Query by ID  ***
	// ********************
	public static Product queryById(int productId) {
		EntityManager em = DBUtil.getEmFactory().createEntityManager();
		try {
			Product product = em.find(Product.class, productId);
			return product; 
		} finally {
			em.close();
		}
	}
	
	// ************************************
	// *** Query by PartNumber/VendorId ***
	// ************************************
	public static Product queryByPartNumber(String partNumber, Vendor vendor) {
		EntityManager em = DBUtil.getEmFactory().createEntityManager();
		String sql = "SELECT p FROM Product p "+
		             "WHERE p.partNumber = :partNumber "+
				     "  AND p.vendor = :vendor";
		try {
            TypedQuery<Product> query = em.createQuery(sql, Product.class);
            query.setParameter("partNumber", partNumber);
            query.setParameter("vendor", vendor);
            Product product = query.getSingleResult();
			return product; 
		} catch (NoResultException e){
			return null;
		} finally {
			em.close();
		}
	}
	// **************
	// *** Insert ***
	// **************
	public static boolean insert(Product product) {	
		boolean isSuccessful = false;
		Product chkProduct = new Product();
		Vendor chkVendor = new Vendor();
		chkVendor = product.getVendor();
		chkProduct = queryByPartNumber(product.getPartNumber(), chkVendor);
		if (chkProduct != null) {
			System.out.println("%%% Product Code Already Exists");
		} else {
			EntityManager em = DBUtil.getEmFactory().createEntityManager();
			EntityTransaction trans = em.getTransaction();
			try {	
				trans.begin();
				em.persist(product); 
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
	public static boolean update(Product product) {
		boolean isSuccessful = false;
		Product chkProduct = new Product();
		chkProduct = queryById(product.getId());
		Vendor chkVendor = product.getVendor();
		if ((chkProduct == null) || (chkVendor == null)) {
			System.out.println("%%% Record Does Not Exist");
		} else {
			chkProduct = queryByPartNumber(product.getPartNumber(), chkVendor);
			if ((chkProduct != null) && (chkProduct.getId() != product.getId())) {
				System.out.println("%%% Record Already Exists");
			} else {
				EntityManager em = DBUtil.getEmFactory().createEntityManager();
				EntityTransaction trans = em.getTransaction();
				try {	
					trans.begin();
					em.merge(product); 
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
	public static boolean delete(Product product) {	
		boolean isSuccessful = false;
		Product chkProduct = new Product();
		chkProduct = queryById(product.getId());
		if (chkProduct == null) {
			System.out.println("%%% Record Does Not Exist");
		} else {
			EntityManager em = DBUtil.getEmFactory().createEntityManager();
			EntityTransaction trans = em.getTransaction();
			try {	
				trans.begin();
				em.remove(em.merge(product)); 
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