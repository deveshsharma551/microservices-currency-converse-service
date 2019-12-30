package com.example.currencyconversionservice.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.currencyconversionservice.model.CurrencyConversionBean;
import com.example.currencyconversionservice.proxy.CurrencyExchangeProxy;

@RestController
public class CurrencyConversionServiceController {
	
	@Autowired
	private Environment environment;
	
	@Autowired
	private CurrencyExchangeProxy proxy;

	
	@GetMapping("/currency/converter/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversionBean retrieveValue(@PathVariable String from, @PathVariable String to,@PathVariable long quantity) {
		Map<String, String> uriVariables = new HashMap<>();
		uriVariables.put("from", from);
		uriVariables.put("to", to);
		ResponseEntity<CurrencyConversionBean> conversionBean = new RestTemplate().getForEntity("http://localhost:8000/currency/exchange/from/{from}/to/{to}",CurrencyConversionBean.class,uriVariables);
		CurrencyConversionBean exchangeValue = new CurrencyConversionBean(conversionBean.getBody().getId(),from,to,conversionBean.getBody().getConversionMultiple(),BigDecimal.valueOf(quantity),BigDecimal.valueOf(quantity).multiply(conversionBean.getBody().getConversionMultiple()),conversionBean.getBody().getPort());
		//exchangeValue.setPort(Integer.parseInt(environment.getProperty("local.server.port")));
		return exchangeValue;
	}
	
	
	
	@GetMapping("/currency/converter/feign/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversionBean retrieveValueFiegn(@PathVariable String from, @PathVariable String to,@PathVariable long quantity) {
		Map<String, String> uriVariables = new HashMap<>();
		uriVariables.put("from", from);
		uriVariables.put("to", to);
		CurrencyConversionBean conversionBean = proxy.retrieveValue(from, to);
		CurrencyConversionBean exchangeValue = new CurrencyConversionBean(conversionBean.getId(),from,to,conversionBean.getConversionMultiple(),BigDecimal.valueOf(quantity),BigDecimal.valueOf(quantity).multiply(conversionBean.getConversionMultiple()),conversionBean.getPort());
		//exchangeValue.setPort(Integer.parseInt(environment.getProperty("local.server.port")));
		return exchangeValue;
	}
	
	

	
}
