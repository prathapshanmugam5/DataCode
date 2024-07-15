package com.DATA.DataCodeAnalysing.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.DATA.DataCodeAnalysing.Dto.BaseDTO;

import com.DATA.DataCodeAnalysing.Entity.CountryMaster;
import com.DATA.DataCodeAnalysing.Repositary.CountryMasterRepositary;
import com.DATA.DataCodeAnalysing.Service.InsertDataService;

@Service
public class InsertDataServiceImpl implements InsertDataService {

	
	@Autowired
	CountryMasterRepositary countryMasterRepositary;
	

	@Override
	public BaseDTO  PostAllCountryDetails(List<CountryMaster> countryMaster) {
		
		BaseDTO baseDTO=new BaseDTO();
		
		List<CountryMaster> countryMasterResult=countryMasterRepositary.saveAll(countryMaster);
		
		baseDTO.setResponseContent(countryMasterResult);
		baseDTO.setStatusCode(0);
		baseDTO.setErrorMessage("Success");
		
		return baseDTO;
	}

}
