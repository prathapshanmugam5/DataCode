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
@Table(name="district_master")
public class DistrictMaster {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	private String districtName;
	private String districtCode;
	@ManyToOne
    @JoinColumn(name = "state_id", nullable = false)
    private StateMaster stateId;

}
