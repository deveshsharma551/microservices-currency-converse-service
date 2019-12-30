package com.example.currencyconversionservice.proxy;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.currencyconversionservice.model.CurrencyConversionBean;

//@FeignClient(name="currency-exchange-service" , url = "localhost:8000")
@FeignClient(name="currency-exchange-service")
@RibbonClient(name="currency-exchange-service")
public interface CurrencyExchangeProxy {
	
	@GetMapping("/currency/exchange/from/{from}/to/{to}")
	public CurrencyConversionBean retrieveValue(@PathVariable String from, @PathVariable String to);
	
	

}
