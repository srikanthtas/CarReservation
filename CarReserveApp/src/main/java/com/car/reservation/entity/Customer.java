package com.car.reservation.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@DynamicUpdate
@Table(name="CUSTOMER")
//@XmlRootElement
public class Customer implements Serializable {

   private static final long serialVersionUID = 1L;
   
    @Id @GeneratedValue
    @Column(name = "customer_id")
    private int customerId;
    
    @Basic(optional = true)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "firstname")
    private String firstName;
    
    @Basic(optional = true)
    @NotNull
    @Size(max = 6)
    @Column(name = "lastname")
    private String lastname;
    
    @Basic(optional = true)
    @NotNull
    @Size(max = 6)
    @Column(name = "license")
    private String licenseNumber;
    
    @Basic(optional = true)
    @NotNull
    @Column(name="reserved")
    private Boolean reserved; 

    @OneToMany(
            mappedBy = "car",
            cascade = CascadeType.ALL,
            orphanRemoval = true
        )
    private List<Reservation> reservations = new ArrayList<>();
    
	public List<Reservation> getReservations() {
		return reservations;
	}

	public void setReservations(List<Reservation> reservations) {
		this.reservations = reservations;
	}

	public Customer() {
        setDefaults();
    }
	
	public void addCar(Car car, Date startDate, int numberofDays, Date endDate) {
        Reservation reservation = new Reservation(car, this);
        reservation.setCreatedOn(startDate);
        reservation.setDays(numberofDays);
        reservation.setEndsOn(endDate);
        reservations.add(reservation);
        car.getReservations().add(reservation);
    }
 
    public void removeCar(Car car) {
        for (Iterator<Reservation> iterator = reservations.iterator();
             iterator.hasNext(); ) {
        	Reservation reservation = iterator.next();
 
            if (reservation.getCar().equals(car) &&
                    reservation.getCustomer().equals(this)) {
                iterator.remove();
                reservation.getCar().getReservations().remove(reservation);
                reservation.setCustomer(null);
                reservation.setCar(null);
            }
        }
    }
    
    
    private void setDefaults(){
        this.firstName = "";
        this.lastname = "";
        this.licenseNumber = "";
    }

    public Customer(String firstName, String lastname,  String licenseNumber, boolean reserved) {
		super();
		this.firstName = firstName;
		this.lastname = lastname;
		this.licenseNumber = licenseNumber;
		this.reserved = reserved;
	}

	
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + Objects.hashCode(this.firstName);
        hash = 53 * hash + Objects.hashCode(this.lastname);
        hash = 53 * hash + Objects.hashCode(this.licenseNumber);
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
        final Customer other = (Customer) obj;
        
        if (!Objects.equals(this.firstName, other.firstName)) {
            return false;
        }
        if (!Objects.equals(this.lastname, other.lastname)) {
            return false;
        }
        if (!Objects.equals(this.licenseNumber, other.licenseNumber)) {
            return false;
        }        return true;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
    
    

}
