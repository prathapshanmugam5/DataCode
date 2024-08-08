package com.DATA.DataCodeAnalysing.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="taluk_master")
public class TalukMaster {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	private String taluktName;
	private String talukCode;
	@ManyToOne
    @JoinColumn(name = "district_id", nullable = false)
    private DistrictMaster districtId;

}
