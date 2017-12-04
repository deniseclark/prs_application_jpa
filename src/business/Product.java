package business;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "product")
public class Product {
	
	// **************************
	// *** Instance Variables ***
	// **************************
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	// A Product can be Assiged to Only 1 Vendor
	@ManyToOne(fetch=FetchType.LAZY)  // Child Table to Parent Table
	@JoinColumn (name="vendorId")     // From PRODUCT (FK=vendorId) to VENDOR (PK=id)
	private Vendor vendor; 
	
	private String partNumber;
	private String name;
	private double price;
	private String unit;
	private String photoPath;
	private boolean isActive;

	// ********************
	// *** Constructors ***
	// ********************
	public Product() {
	}	

	public Product(int id, Vendor vendor, String partNumber, String name, 
			double price, String unit, String photoPath, boolean isActive) {
		super();
		this.id = id;
		this.vendor = vendor;
		this.partNumber = partNumber;
		this.name = name;
		this.price = price;
		this.unit = unit;
		this.photoPath = photoPath;
		this.isActive = isActive;
	}

	// ***************
	// *** Getters ***
	// ***************
	public int getId() {
		return id;
	}
	public Vendor getVendor() {
		return vendor;
	}
	public String getPartNumber() {
		return partNumber;
	}	
	public String getName() {
		return name;
	}
	public double getPrice() {
		return price;
	}
	public String getUnit() {
		return unit;
	}
	public String getPhotoPath() {
		return photoPath;
	}
	public boolean isActive() {
		return isActive;
	}

	// ***************
	// *** Setters ***
	// ***************
	public void setId(int id) {
		this.id = id;
	}
	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}
	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}	
	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	// **********************************
	// ***@Override - hashCode Method ***
	// **********************************
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + (isActive ? 1231 : 1237);
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((partNumber == null) ? 0 : partNumber.hashCode());
		result = prime * result + ((photoPath == null) ? 0 : photoPath.hashCode());
		long temp;
		temp = Double.doubleToLongBits(price);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((unit == null) ? 0 : unit.hashCode());
		result = prime * result + ((vendor == null) ? 0 : vendor.hashCode());
		return result;
	}

	// ********************************
	// ***@Override - equals Method ***
	// ********************************
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		if (id != other.id)
			return false;
		if (isActive != other.isActive)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (partNumber == null) {
			if (other.partNumber != null)
				return false;
		} else if (!partNumber.equals(other.partNumber))
			return false;
		if (photoPath == null) {
			if (other.photoPath != null)
				return false;
		} else if (!photoPath.equals(other.photoPath))
			return false;
		if (Double.doubleToLongBits(price) != Double.doubleToLongBits(other.price))
			return false;
		if (unit == null) {
			if (other.unit != null)
				return false;
		} else if (!unit.equals(other.unit))
			return false;
		if (vendor == null) {
			if (other.vendor != null)
				return false;
		} else if (!vendor.equals(other.vendor))
			return false;
		return true;
	}

	// **********************************
	// ***@Override - toString Method ***
	// **********************************
	@Override
	public String toString() {
		return "Product [id=" + id + ", vendor=" + vendor + ", partNumber=" + partNumber + ", name=" + name + ", price="
				+ price + ", unit=" + unit + ", photoPath=" + photoPath + ", isActive=" + isActive + "]";
	}
	
}
