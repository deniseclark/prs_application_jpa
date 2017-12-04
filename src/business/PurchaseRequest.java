package business;

import java.time.LocalDate;
import java.util.ArrayList;
import javax.persistence.AttributeConverter;
import javax.persistence.CascadeType;
import javax.persistence.Converter;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "purchase_request")
@Converter(autoApply = true)
public class PurchaseRequest implements AttributeConverter<java.time.LocalDate, java.sql.Date>{

	// **************************
	// *** Instance Variables ***
	// **************************
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@ManyToOne
	@JoinColumn (name="userId")  // The DB Column Name of the FK per this Table
	private User user; 
	private String description;
	private String justification;
	private LocalDate dateNeeded;
	private String deliveryMode;
	@ManyToOne
	@JoinColumn (name="statusId")  
	private Status status; 
	private double total;
	private LocalDate submittedDate;
	private String reasonForRejection;
	private boolean isActive;
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinColumn(name="purchaseRequestId")
	private ArrayList<PurchaseRequestLineItem> prLineItems;
	
	// ********************
	// *** Constructors ***
	// ********************
	public PurchaseRequest() {
		id = 0;
		user = new User();
		description = "";
		justification = "";
		dateNeeded = LocalDate.now();
		deliveryMode = "";
		status = new Status();
		total = 0.0;
		submittedDate = null;
		reasonForRejection = "";
		setActive(true); 
		prLineItems = new ArrayList<>();
	}
	
	public PurchaseRequest(int id, User user, String description, 
			               String justification, LocalDate dateNeeded, String deliveryMode, 
			               Status status, double total, LocalDate submittedDate,
			               String reasonForRejection, boolean isActive) {
		this.id = id;
		this.user = new User();
		this.description = description;
		this.justification = justification;
		this.dateNeeded = dateNeeded;
		this.deliveryMode = deliveryMode;
		this.status = new Status();
		this.total = total;
		this.submittedDate = submittedDate;
		this.reasonForRejection = reasonForRejection;
		setActive(isActive);
		this.prLineItems = new ArrayList<>();
	}
	
	public PurchaseRequest(User user, String description, 
            String justification, LocalDate dateNeeded, String deliveryMode, 
            Status status, double total, LocalDate submittedDate,
            String reasonForRejection, boolean isActive) {
		this.id = 0;
		this.user = new User();
		this.description = description;
		this.justification = justification;
		this.dateNeeded = dateNeeded;
		this.deliveryMode = deliveryMode;
		this.status = new Status();
		this.total = total;
		this.submittedDate = submittedDate;
		this.reasonForRejection = reasonForRejection;
		setActive(isActive);
		this.prLineItems = new ArrayList<>();
	}
	
	// ****************
	// ***  Getters ***
	// ****************
	public int getId() {
		return id;
	}
	public User getUser() {
		return user;
	}
	public String getDescription() {
		return description;
	}	
	public String getJustification() {
		return justification;
	}
	public LocalDate getDateNeeded() {
		return dateNeeded;
	}
	public String getDeliveryMode() {
		return deliveryMode;
	}	
	public Status getStatus() {
		return status;
	}
	public double getTotal() {
		return total;
	}
	public LocalDate getSubmittedDate() {
		return submittedDate;
	}
	public String getReasonForRejection() {
		return reasonForRejection;
	}
	public boolean isActive() {
		return isActive;
	}
	public ArrayList<PurchaseRequestLineItem> getPrLineItems() {
		return prLineItems;
	}

	// ****************
	// ***  Setters ***
	// ****************
	public void setId(int id) {
		this.id = id;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setJustification(String justification) {
		this.justification = justification;
	}
	public void setDateNeeded(LocalDate dateNeeded) {
		this.dateNeeded = dateNeeded;
	}
	public void setDeliveryMode(String deliveryMode) {
		this.deliveryMode = deliveryMode;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public void setSubmittedDate(LocalDate submittedDate) {
		this.submittedDate = submittedDate;
	}
	public void setReasonForRejection(String reasonForRejection) {
		this.reasonForRejection = reasonForRejection;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	public void setPrLineItems(ArrayList<PurchaseRequestLineItem> prLineItems) {
		this.prLineItems = prLineItems;
	}

	// **********************************
	// ***@Override - hashCode Method ***
	// **********************************
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dateNeeded == null) ? 0 : dateNeeded.hashCode());
		result = prime * result + ((deliveryMode == null) ? 0 : deliveryMode.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + id;
		result = prime * result + (isActive ? 1231 : 1237);
		result = prime * result + ((justification == null) ? 0 : justification.hashCode());
		result = prime * result + ((prLineItems == null) ? 0 : prLineItems.hashCode());
		result = prime * result + ((reasonForRejection == null) ? 0 : reasonForRejection.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((submittedDate == null) ? 0 : submittedDate.hashCode());
		long temp;
		temp = Double.doubleToLongBits(total);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((user == null) ? 0 : user.hashCode());
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
		PurchaseRequest other = (PurchaseRequest) obj;
		if (dateNeeded == null) {
			if (other.dateNeeded != null)
				return false;
		} else if (!dateNeeded.equals(other.dateNeeded))
			return false;
		if (deliveryMode == null) {
			if (other.deliveryMode != null)
				return false;
		} else if (!deliveryMode.equals(other.deliveryMode))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id != other.id)
			return false;
		if (isActive != other.isActive)
			return false;
		if (justification == null) {
			if (other.justification != null)
				return false;
		} else if (!justification.equals(other.justification))
			return false;
		if (prLineItems == null) {
			if (other.prLineItems != null)
				return false;
		} else if (!prLineItems.equals(other.prLineItems))
			return false;
		if (reasonForRejection == null) {
			if (other.reasonForRejection != null)
				return false;
		} else if (!reasonForRejection.equals(other.reasonForRejection))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (submittedDate == null) {
			if (other.submittedDate != null)
				return false;
		} else if (!submittedDate.equals(other.submittedDate))
			return false;
		if (Double.doubleToLongBits(total) != Double.doubleToLongBits(other.total))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}

	// **********************************
	// ***@Override - toString Method ***
	// **********************************
	@Override
	public String toString() {
		return "PurchaseRequest [id=" + id + ", user=" + user + ", "
				+ "description=" + description + ", justification="
				+ justification + ", dateNeeded=" + dateNeeded + ", "
				+ ", deliveryMode=" + deliveryMode + ", status="
				+ status + ", total=" + total + ", submittedDate=" 
				+ submittedDate + "\nLineItems "+prLineItems +"]";
	}

    @Override
    public java.sql.Date convertToDatabaseColumn(java.time.LocalDate attribute) {
        return attribute == null ? null : java.sql.Date.valueOf(attribute);
    }

    @Override
    public java.time.LocalDate convertToEntityAttribute(java.sql.Date dbData) {
        return dbData == null ? null : dbData.toLocalDate();
    }
}
