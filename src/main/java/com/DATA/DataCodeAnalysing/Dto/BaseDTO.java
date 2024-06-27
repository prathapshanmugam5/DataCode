package com.DATA.DataCodeAnalysing.Dto;

import lombok.Data;

@Data
public class BaseDTO {

	private Integer statusCode;
	private String errorMessage;
	private Object responseContent;
}
