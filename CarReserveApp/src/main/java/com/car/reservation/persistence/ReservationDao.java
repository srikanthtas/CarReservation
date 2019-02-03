package com.car.reservation.persistence;

import java.util.List;

import com.car.reservation.entity.Car;
import com.car.reservation.entity.Customer;



public interface ReservationDao {
	
	 List<Car> findCars(final String make, final String model);
	 
	 Customer findCustomer(int customerId);
	 
	 public void saveCar(Car car);
	 
}
