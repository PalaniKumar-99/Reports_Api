package com.example.ReportsApi.runner;

import com.example.ReportsApi.entity.EligibilityDetails;
import com.example.ReportsApi.repository.EligibilityDetailsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class MyAppRunner implements ApplicationRunner {

    @Autowired
    private EligibilityDetailsRepo repo;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        EligibilityDetails entity1 = new EligibilityDetails();
        entity1.setEligibilityId(1);
        entity1.setName("John");
        entity1.setEmail("john@gmail.com");
        entity1.setMobileNo(12355551L);
        entity1.setGender('M');
        entity1.setSsn(68966896L);
        entity1.setPlanName("SNAP");
        entity1.setPlanStatus("Approved");
        repo.save(entity1);


        EligibilityDetails entity2 = new EligibilityDetails();
        entity2.setEligibilityId(2);
        entity2.setName("Smith");
        entity2.setEmail("smith@gmail.com");
        entity2.setMobileNo(12466611L);
        entity2.setGender('M');
        entity2.setSsn(97966796L);
        entity2.setPlanName("MEDICAID");
        entity2.setPlanStatus("Denied");
        repo.save(entity2);


        EligibilityDetails entity3 = new EligibilityDetails();
        entity3.setEligibilityId(3);
        entity3.setName("Robert");
        entity3.setEmail("robert@gmail.com");
        entity3.setMobileNo(6661451L);
        entity3.setGender('M');
        entity3.setSsn(6823679L);
        entity3.setPlanName("CCAP");
        entity3.setPlanStatus("Closed");
        repo.save(entity3);

    }
}
