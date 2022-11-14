package com.db.trade;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.Alphanumeric;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.entity.TradeStore;
import com.jpa.TradeRepo;
import com.request.bean.TradeRequest;

@SuppressWarnings("deprecation")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

@TestMethodOrder(Alphanumeric.class)
public class TradeStoreControllerTest {
	@LocalServerPort
	private int port;
	
	@Autowired
	private TradeRepo repo;

	private String baseUrl = "http://localhost";

	private static RestTemplate restTemplate;

	@BeforeAll
	public static void init() {
		restTemplate = new RestTemplate();			
	}

	@BeforeEach
	public void setUp() {
		baseUrl = baseUrl.concat(":").concat(port + "").concat("/db/store");
	}

	@Test
	public void testTradeMaturityDate() {
		assertThrows(Exception.class, () -> {
			TradeRequest trade = new TradeRequest("T1", 1, "CP-2", "B1", "2022-11-12");
			restTemplate.postForObject(baseUrl, trade, TradeStore.class);
		});
	}

	@Test
	public void testTradeAdd() {
		TradeRequest trade = new TradeRequest("T1", 1, "CP-2", "B1", "2023-11-12");
		TradeStore res = restTemplate.postForObject(baseUrl, trade, TradeStore.class);
		assertEquals(res.getTradeID(), "T1");
	}

	@Test
	public void testTradeVersion() {
		assertThrows(Exception.class, () -> {
			TradeRequest trade1 = new TradeRequest("T1", 0, "CP-2", "B1", "2023-11-12");
			restTemplate.postForObject(baseUrl, trade1, TradeStore.class);
		});
	}

	@Test
	public void testUpdateTradeWithHigherVersion() {
		TradeRequest trade = new TradeRequest("T1", 2, "CP-3", "B3", "2024-11-12");
		TradeStore res = restTemplate.postForObject(baseUrl, trade, TradeStore.class);
		assertEquals(res.getVersion(), 2);
	}
	
	@Test
	public void testUpdateTradeWithHigherVersion_1() {
		TradeRequest trade = new TradeRequest("T1", 2, "CP-4", "B4", "2024-11-12");
		TradeStore res = restTemplate.postForObject(baseUrl, trade, TradeStore.class);
		assertEquals(res.getBookID(), "B4");
	}
	
	@Test
	public void testTradeStoreFetchAll() {
		ResponseEntity res = restTemplate.getForEntity(baseUrl, Object.class);
		List<TradeStore> list = (List<TradeStore>) res.getBody();		
		assertTrue(!list.isEmpty());
	}
	
	@Test
	public void testTradeStoreGetByID() {
		TradeStore trade = new TradeStore();
		trade.setTradeID("T4");
		trade.setVersion(3);
		trade.setCounterPartyID("CP-3");
		trade.setBookID("B2");
		try {
			trade.setMaturityDate(new SimpleDateFormat("yyyy-MM-dd").parse("2022-11-12"));
		} catch (ParseException e) {			
			e.printStackTrace();
		}
		trade.setCreatedDate(new Date());
		repo.save(trade);
		List<TradeStore> t = repo.findAllByTradeID("T4");
		
		assertFalse(t.get(0).getExpired());
		
		
	}

}
