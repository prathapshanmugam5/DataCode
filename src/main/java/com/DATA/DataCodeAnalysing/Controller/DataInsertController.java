package com.DATA.DataCodeAnalysing.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.DATA.DataCodeAnalysing.Dto.BaseDTO;
import com.DATA.DataCodeAnalysing.Dto.CountryMasterDTO;
import com.DATA.DataCodeAnalysing.Entity.CountryMaster;
import com.DATA.DataCodeAnalysing.Service.InsertDataService;

@RestController
@RequestMapping("/insert-data")
public class DataInsertController {
	
	@Autowired
	InsertDataService  insertDataService;
	
	
	@PostMapping("/add-country-list")
	public BaseDTO PostAllCountryDetails(@RequestBody List<CountryMaster> CountryMaster){
		return insertDataService.PostAllCountryDetails(CountryMaster);
		
	}
	
	

}
