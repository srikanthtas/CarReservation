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
@Table(name="CAR")
//@XmlRootElement
public class Car implements Serializable {

   
	private static final long serialVersionUID = 1L;
   
    @Id @GeneratedValue
    @Column(name = "customer_id")
    private int carId;
    
    @Basic(optional = true)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "car_make")
    private String make;
    
    @Basic(optional = true)
    @NotNull
    @Size(max = 6)
    @Column(name = "car_model")
    private String model;
    
    @Basic(optional = true)
    @NotNull
    @Size(max = 6)
    @Column(name = "car_description")
    private String description;

    @OneToMany(
            mappedBy = "customer",
            cascade = CascadeType.ALL,
            orphanRemoval = true
        )
    private List<Reservation> reservations = new ArrayList<>();
    
    
	public Car(String make, String model, String description, List<Reservation> reservations) {
		super();
		this.make = make;
		this.model = model;
		this.description = description;
		this.reservations = reservations;
	}
    public void addCustomer(Customer customer, Date startDate, int numberofDays, Date endDate) {
        Reservation reservation = new Reservation(this, customer);
        reservation.setCreatedOn(startDate);
        reservation.setDays(numberofDays);
        reservation.setEndsOn(endDate);
        reservations.add(reservation);
        customer.getReservations().add(reservation);
    }
 
    public void removeCutomer(Customer customer) {
        for (Iterator<Reservation> iterator = reservations.iterator();
             iterator.hasNext(); ) {
        	Reservation reservation = iterator.next();
 
            if (reservation.getCar().equals(this) &&
                    reservation.getCustomer().equals(customer)) {
                iterator.remove();
                reservation.getCustomer().getReservations().remove(reservation);
                reservation.setCustomer(null);
                reservation.setCar(null);
            }
        }
    }
    
	public Car() {
		 setDefaults();
    }
    private void setDefaults(){
        this.make = "";
        this.model = "";
        this.description = "";
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + Objects.hashCode(this.make);
        hash = 53 * hash + Objects.hashCode(this.model);
        hash = 53 * hash + Objects.hashCode(this.description);
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
        final Car other = (Car) obj;
        
        if (!Objects.equals(this.make, other.make)) {
            return false;
        }
        if (!Objects.equals(this.model, other.model)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }        return true;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public int getCarId() {
		return carId;
	}
	public void setCarId(int carId) {
		this.carId = carId;
	}
	public String getMake() {
		return make;
	}
	public void setMake(String make) {
		this.make = make;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public List<Reservation> getReservations() {
		return reservations;
	}
	public void setReservations(List<Reservation> reservations) {
		this.reservations = reservations;
	}
	

	
}
