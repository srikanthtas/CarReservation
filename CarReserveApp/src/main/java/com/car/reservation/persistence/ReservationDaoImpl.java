package com.car.reservation.persistence;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.car.reservation.entity.Car;
import com.car.reservation.entity.Customer;



@Repository("reservationDao")
@Transactional
public class ReservationDaoImpl implements ReservationDao {

	@Override
	public List<Car> findCars(String make, String model) {
		return null;
	}

	@Override
	public Customer findCustomer(int customerId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveCar(Car car) {
		// TODO Auto-generated method stub
		
	}
	
	
}
