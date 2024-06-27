package com.DATA.DataCodeAnalysing.Dto;

import java.util.Map;

import lombok.Data;

@Data
public class DataCodeDetails {
	
	private String dataCode;

	private Map<String, String> placeholderKeyValueMap;
}
