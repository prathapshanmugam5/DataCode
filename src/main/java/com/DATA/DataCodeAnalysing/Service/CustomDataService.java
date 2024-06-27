package com.DATA.DataCodeAnalysing.Service;

import org.springframework.stereotype.Service;

import com.DATA.DataCodeAnalysing.Dto.BaseDTO;
import com.DATA.DataCodeAnalysing.Dto.DataCodeDetails;


@Service
public interface CustomDataService {

	public BaseDTO getDataFromDataCode(DataCodeDetails dataCode);

}
