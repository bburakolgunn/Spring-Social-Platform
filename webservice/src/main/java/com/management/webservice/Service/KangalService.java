package com.management.webservice.Service;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.management.webservice.Entity.Kangal;
import com.management.webservice.Repository.KangalRepository;
import com.management.webservice.Service.impl.KangalServiceImpl;

@Service
public class KangalService implements KangalServiceImpl {

	 private KangalRepository kangalRepository;

	public KangalService(KangalRepository kangalRepository) {
		super();
		this.kangalRepository = kangalRepository;
	}

	@Override
	public void save(Kangal kangal) {
		kangal.setTimestamp(new Date());
		kangalRepository.save(kangal);
	}
	
	
	
}
