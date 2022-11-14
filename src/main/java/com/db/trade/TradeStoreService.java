package com.db.trade;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.entity.TradeStore;
import com.jpa.TradeRepo;
import com.request.bean.TradeRequest;

@Component
@Service
public class TradeStoreService {
	@Autowired
	private TradeRepo repository;
	
	public TradeStore createTrade(TradeRequest req) throws Exception {
		if(req.getMaturityDate().before(new Date())) {
			throw new Exception("Maturity Date is less than todays date");
		}
		TradeStore tradeFromDb = repository.findMaxTradeVersion(req.getTradeID(), req.getVersion());
		if (tradeFromDb == null) {
			TradeStore trade = new TradeStore();
			trade.setTradeID(req.getTradeID());
			trade.setVersion(req.getVersion());
			trade.setCounterPartyID(req.getCounterPartyID());
			trade.setBookID(req.getBookId());
			trade.setMaturityDate(req.getMaturityDate());
			trade.setCreatedDate(new Date());
			return repository.save(trade);
		}else if(tradeFromDb.getVersion() == req.getVersion()) {
			tradeFromDb.setCounterPartyID(req.getCounterPartyID());
			tradeFromDb.setMaturityDate(req.getMaturityDate());
			tradeFromDb.setBookID(req.getBookId());
			return repository.save(tradeFromDb);
		}
		throw new Exception("Failed");
	}

	public List<TradeStore> fetchAllTrades() {		
		return repository.findAll();
	}

	public List<TradeStore> fetchByTradeID(String tradeID) {
		return repository.findAllByTradeID(tradeID);
	}
}
