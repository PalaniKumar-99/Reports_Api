package com.example.ReportsApi.controller;

import com.example.ReportsApi.entity.EligibilityDetails;
import com.example.ReportsApi.request.SearchRequest;
import com.example.ReportsApi.response.SearchResponse;
import com.example.ReportsApi.service.EligibilityService;
import com.lowagie.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
public class EligibilityController {

    @Autowired
    private EligibilityService eligibilityService;

    @GetMapping("/plans")
    public ResponseEntity<List<String>> getPlanNames() {
        List<String> uniquePlanNames = eligibilityService.getUniquePlanNames();
        return  new ResponseEntity<>(uniquePlanNames, HttpStatus.OK);
    }

    @GetMapping("/statuses")
    public ResponseEntity<List<String>> getStatuses() {
        List<String> uniquePlanStatuses = eligibilityService.getUniquePlanStatuses();
        return new ResponseEntity<>(uniquePlanStatuses, HttpStatus.OK);
    }

    @PostMapping("/search")
    public ResponseEntity<List<SearchResponse>> search(@RequestBody SearchRequest request) {
        List<SearchResponse> search = eligibilityService.search(request);
        return new ResponseEntity<>(search, HttpStatus.OK);
    }

    @GetMapping("/excel")
    public void generateExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/Octet-stream");

        String headKey = "Content-Disposition";
        String value = "attachment;filename=data.xls";

        response.setHeader(headKey, value);
        eligibilityService.generateExcel(response);
    }

    @GetMapping("/pdf")
    public void generatePdf(HttpServletResponse response) throws DocumentException, IOException {
        response.setContentType("application/pdf");

        String headerKey = "Content-Disposition";
        String value = "attachment;filename=data.pdf";

        response.setHeader(headerKey, value);
        eligibilityService.generatePdf(response);
    }
}
