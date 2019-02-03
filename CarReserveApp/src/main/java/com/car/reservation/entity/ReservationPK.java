package com.car.reservation.entity;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.apache.commons.lang3.builder.ToStringBuilder;

@Embeddable
public class ReservationPK implements Serializable {

    private static final long serialVersionUID = 1L;
   
    @Column(name = "customer_id")
    private int customerId;
 
    @Column(name = "car_id")
    private int carId;
    
    
    public ReservationPK() {       
    }
    
    public ReservationPK(int customerId, int carId) {
		super();
		this.customerId = customerId;
		this.carId = carId;
	}

	@Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + Objects.hashCode(this.customerId);
        hash = 53 * hash + Objects.hashCode(this.carId);
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
        final ReservationPK other = (ReservationPK) obj;
        
        if (!Objects.equals(this.customerId, other.customerId)) {
            return false;
        }
        if (!Objects.equals(this.carId, other.carId)) {
            return false;
        }   return true;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
