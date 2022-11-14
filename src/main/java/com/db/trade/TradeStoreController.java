package com.db.trade;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.entity.TradeStore;
import com.request.bean.TradeRequest;

@RestController
@RequestMapping("/db/store")
public class TradeStoreController {
	@Autowired
	private TradeStoreService service;
	
	/**
	 * Create or update trade
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@PostMapping
	public TradeStore createTrade(@RequestBody TradeRequest request) throws Exception{
		return service.createTrade(request);		
	}
	
	/**
	 * Get all the trade details present in the store
	 * @return List<TradeStore>
	 */
	@GetMapping
	public List<TradeStore> fetchTrades(){
		return service.fetchAllTrades();		
	}
	
	
	/**
	 * Get all the trade details by TradID
	 * @return List<TradeStore>
	 */
	@GetMapping("/id")
	public List<TradeStore> fetchByTradeID(String tradeID){
		return service.fetchByTradeID(tradeID);		
	}
	
}
