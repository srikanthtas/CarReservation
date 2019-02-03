package com.car.reservation.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@DynamicUpdate
@Table(name="RESERVATION")
//@XmlRootElement
public class Reservation implements Serializable {

    public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public ReservationPK getPkey() {
		return pkey;
	}

	public void setPkey(ReservationPK pkey) {
		this.pkey = pkey;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Integer getDays() {
		return days;
	}

	public void setDays(Integer days) {
		this.days = days;
	}

	public Date getEndsOn() {
		return endsOn;
	}

	public void setEndsOn(Date endsOn) {
		this.endsOn = endsOn;
	}

	private static final long serialVersionUID = 1L;
   
    @EmbeddedId
    private ReservationPK pkey;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("carId")
    private Car car;
 
   @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("customerId")
    private Customer customer ;
    
    @Basic(optional = true)
    @NotNull
    @Column(name = "created_on")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn;
    
    @Basic(optional = true)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "days")
    private Integer days;
    
    @Basic(optional = true)
    @NotNull
    @Column(name = "ends_on")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endsOn;
    
    
	public Reservation() {
        
    }
    
	public Reservation(ReservationPK pkey, Customer customer,  Car car, Date createdOn, Integer days, Date endsOn) {
		super();
		this.pkey = pkey;
		this.customer = customer;
		this.car = car;
		this.createdOn = createdOn;
		this.days = days;
		this.endsOn = endsOn;
	}

	public Reservation(Car car, Customer customer) {
		super();
		this.customer = customer;
		this.car = car;
	}
		
	@Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + Objects.hashCode(this.customer);
        hash = 53 * hash + Objects.hashCode(this.car);
        hash = 53 * hash + Objects.hashCode(this.createdOn);
        hash = 53 * hash + Objects.hashCode(this.days);
        hash = 53 * hash + Objects.hashCode(this.endsOn);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Reservation other = (Reservation) obj;
        
        if (!Objects.equals(this.customer, other.customer)) {
            return false;
        }
        if (!Objects.equals(this.car, other.car)) {
            return false;
        }
        if (!Objects.equals(this.createdOn, other.createdOn)) {
            return false;
        }
        if (!Objects.equals(this.days, other.days)) {
            return false;
        }
        if (!Objects.equals(this.endsOn, other.endsOn)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
