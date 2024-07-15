package com.DATA.DataCodeAnalysing.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.DATA.DataCodeAnalysing.Dto.BaseDTO;
import com.DATA.DataCodeAnalysing.Dto.DataCodeDetails;
import com.DATA.DataCodeAnalysing.Service.CustomDataService;

import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("/customdata")
public class CustomDataController {
	
	
	@Autowired
	CustomDataService customDataService;
	
	
	@PostMapping("/getdata")
	public BaseDTO getDataFromDataCode(@RequestBody DataCodeDetails dataCode ) {
		
		return customDataService.getDataFromDataCode(dataCode);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
