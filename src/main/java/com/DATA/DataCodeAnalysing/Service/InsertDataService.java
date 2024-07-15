package com.DATA.DataCodeAnalysing.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.DATA.DataCodeAnalysing.Dto.BaseDTO;

import com.DATA.DataCodeAnalysing.Entity.CountryMaster;


@Service
public interface InsertDataService {

	BaseDTO PostAllCountryDetails(List<CountryMaster> countryMaster);

}
