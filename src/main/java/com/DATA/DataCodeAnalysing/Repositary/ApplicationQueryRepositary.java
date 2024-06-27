package com.DATA.DataCodeAnalysing.Repositary;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.DATA.DataCodeAnalysing.Entity.AppliCationQuery;


@Repository
public interface ApplicationQueryRepositary extends JpaRepository<AppliCationQuery,String>{

}
