package com.DATA.DataCodeAnalysing.Repositary;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.DATA.DataCodeAnalysing.Entity.CountryMaster;

@Repository
public interface CountryMasterRepositary extends JpaRepository<CountryMaster, Long>{

}
