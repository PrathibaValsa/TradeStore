package com.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.entity.TradeStore;

@Repository
public interface TradeRepo extends JpaRepository<TradeStore, Long>{
	@Query("Select e from TradeStore e where e.tradeID = ?1 and e.version >= ?2")
	TradeStore findMaxTradeVersion(String tradeID, Integer version);
	
	@Query("Select e from TradeStore e where e.tradeID = ?1")
	List<TradeStore> findAllByTradeID(String tradeID);

}
