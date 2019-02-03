package com.car.reservation.service;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.car.reservation.entity.Car;
import com.car.reservation.entity.Customer;
import com.car.reservation.entity.Reservation;
import com.car.reservation.persistence.ReservationDao;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


@Service("reservationService")
public class ReservationServiceImpl implements ReservationService {

    private static final Logger logger = LoggerFactory.getLogger(ReservationServiceImpl.class.getName());
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    ReservationDao reservationDao;

    
    @Override
    @Transactional(isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public ResponseEntity<String> reserveCar(JsonNode jsonData) throws Exception {
    	
    	RequestRO requestRO = mapRequest(jsonData);
    	
    	Date requestStartDate = new SimpleDateFormat("yyyy/MM/dd").parse(requestRO.getRequestStartDate()); 
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(requestStartDate);
    	cal.add(Calendar.DAY_OF_MONTH, requestRO.getNumberOfDays()); 
    	Date requestEDate = new Date(cal.getTimeInMillis());
    	int customerId = 1;
    	
    	Customer customer = reservationDao.findCustomer(customerId);
    	
    	List<Car> cars = reservationDao.findCars(requestRO.getMake(), requestRO.getModel());
    	for(Car car: cars) {
    		if(StringUtils.equals(requestRO.getMake(), car.getMake()) && StringUtils.equals(requestRO.getModel(), car.getModel())) {
    			Reservation reservation = car.getReservations().stream().filter(r -> requestStartDate.after(r.getEndsOn())).findFirst().orElse(null);
				if (reservation != null) {
					car.addCustomer(customer, requestStartDate, requestRO.getNumberOfDays(), requestEDate);
					reservationDao.saveCar(car);
					return new ResponseEntity<>("Reservation confirmed", HttpStatus.BAD_REQUEST);
				}
    		}
    	}
    	
		return new ResponseEntity<>("Cars do not exist for the reservation", HttpStatus.BAD_REQUEST);
    	
       
    }
  
    private RequestRO mapRequest(JsonNode rootNode) throws JsonParseException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		JsonParser jsonParser = mapper.getFactory().createParser(rootNode.toString());
		RequestRO requestRO = mapper.readValue(jsonParser, RequestRO.class);
		return requestRO;
	}
	
	
}
