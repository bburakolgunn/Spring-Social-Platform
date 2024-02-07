package com.management.webservice.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.management.webservice.Entity.Kangal;
import com.management.webservice.Service.KangalService;
import com.management.webservice.shared.GenericMessage;

import jakarta.validation.Valid;

@RestController
public class KangalController {
	
	private KangalService kangalService;
	
	public KangalController(KangalService kangalService) {
		super();
		this.kangalService = kangalService;
	}


	@PostMapping("api/v1/kangals")
	GenericMessage saveKangal(@Valid @RequestBody Kangal kangal){
		kangalService.save(kangal);
		return new GenericMessage("Kangal is saved");
	}
	
}
