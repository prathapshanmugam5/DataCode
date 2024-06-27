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
@Table(name="state_master")
public class StateMaster {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	private String StateName;
	private String StateCode;
	@ManyToOne
    @JoinColumn(name = "country_id", nullable = false)
    private CountryMaster country;

}
