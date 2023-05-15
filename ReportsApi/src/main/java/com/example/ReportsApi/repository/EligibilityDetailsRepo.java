package com.example.ReportsApi.repository;

import com.example.ReportsApi.entity.EligibilityDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EligibilityDetailsRepo extends JpaRepository<EligibilityDetails, Integer> {

    @Query("select distinct(planName) from EligibilityDetails")
    public List<String> findPlanNames();
    @Query("select distinct(planStatus) from EligibilityDetails")
    public List<String> findPlanStatuses();
}
