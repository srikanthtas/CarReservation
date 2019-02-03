package com.car.reservation;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import com.car.reservation.entity.Car;
import com.car.reservation.entity.Customer;
import com.car.reservation.entity.Reservation;
import com.car.reservation.entity.ReservationPK;
import com.car.reservation.persistence.ReservationDao;
import com.car.reservation.service.RequestRO;
import com.car.reservation.service.ReservationServiceImpl;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(MockitoJUnitRunner.class)
public class CarReservationTest {

	@InjectMocks
	ReservationServiceImpl reservationServiceImpl;

	@Mock
	ReservationDao reservationDao;
	
	static List<Car> cars = new ArrayList<>();

	@BeforeClass
	public static void init() {
		List<Reservation> reservations = new ArrayList<>();
		Car car = new Car("make1", "model1", "desc1", reservations);
		reservations.add(createReservation(car, Calendar.FEBRUARY, 3, 2));
		cars.add(car);

		car = new Car("make1","model1",  "desc2", reservations);
		reservations.add(createReservation(car, Calendar.FEBRUARY, 5, 2));
		cars.add(car);

		car = new Car("make1", "model1", "desc3", reservations);
		reservations.add(createReservation(car, Calendar.FEBRUARY, 8, 2));
		cars.add(car);

		car = new Car("make1", "model1", "desc3", reservations);
		reservations.add(createReservation(car, Calendar.FEBRUARY, 10, 2));
		cars.add(car);
	}

	private static Reservation createReservation(Car car, int month, int startDate, int numberOfDays) {
		Date startDate1 = new GregorianCalendar(2019, month, startDate).getTime();
		Date endDate = new GregorianCalendar(2019, month, startDate + numberOfDays).getTime();
		Reservation reservation = new Reservation(new ReservationPK(1, 2), new Customer(), car, startDate1, numberOfDays, endDate);
		return reservation;
	}

	@Before
	public void initMocks() {
		MockitoAnnotations.initMocks(this);

	}

	@Test
	public void addReservationSuccess() throws Exception {
		RequestRO request = createRequest("make1", "model1", "2019/02/08", 5);
		when(reservationDao.findCustomer(any(Integer.class))).thenReturn(new Customer());
		when(reservationDao.findCars(any(String.class), any(String.class))).thenReturn(cars);
		ObjectMapper mapper = new ObjectMapper();
		JsonNode node = mapper.readTree(mapper.writeValueAsString(request));
		
		reservationServiceImpl.reserveCar(node);
		verify(reservationDao, times(1)).saveCar(any(Car.class));

	}
	
	@Test
	public void addReservationFail() throws Exception {
		RequestRO request = createRequest("make1", "model1", "2019/02/03", 5);
		when(reservationDao.findCustomer(any(Integer.class))).thenReturn(new Customer());
		when(reservationDao.findCars(any(String.class), any(String.class))).thenReturn(cars);
		ObjectMapper mapper = new ObjectMapper();
		JsonNode node = mapper.readTree(mapper.writeValueAsString(request));
		
		ResponseEntity<String> response = reservationServiceImpl.reserveCar(node);
		assertEquals(400, response.getStatusCodeValue());

	}
	

	private RequestRO createRequest(final String make, final String model, final String startDate, final int numberdays) {
		RequestRO request = new RequestRO();
		request.setMake(make);
		request.setModel(model);
		request.setRequestStartDate(startDate);
		request.setNumberOfDays(numberdays);
		return request;
	}

}
