package com.request.bean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;



public class TradeRequest {
	private String tradeID;	
	private Integer version;
	private String counterPartyID;
	private String bookId;
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date maturityDate;
	
	public TradeRequest(String tradeID, int version, String counterPartyID,String bookID, String maturityDate) {
		this.tradeID = tradeID;
		this.version = version;
		this.counterPartyID = counterPartyID;
		this.bookId = bookID;
		try {
			this.maturityDate = new SimpleDateFormat("yyyy-MM-dd").parse(maturityDate);
		} catch (ParseException e) {			
			e.printStackTrace();
		}		
	}
	public String getTradeID() {
		return tradeID;
	}
	public void setTradeID(String tradeID) {
		this.tradeID = tradeID;
	}
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	public String getCounterPartyID() {
		return counterPartyID;
	}
	public void setCounterPartyID(String counterPartyID) {
		this.counterPartyID = counterPartyID;
	}
	public String getBookId() {
		return bookId;
	}
	public void setBookId(String bookId) {
		this.bookId = bookId;
	}
	public Date getMaturityDate() {
		return maturityDate;
	}
	public void setMaturityDate(Date maturityDate) {
		this.maturityDate = maturityDate;
	}
	
	
}
