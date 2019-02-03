package com.car.reservation.service;

import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public interface ReservationService {

	ResponseEntity<String> reserveCar(JsonNode jsonData) throws Exception ;   	
}
