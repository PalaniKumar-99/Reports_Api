package com.example.ReportsApi.service;

import com.example.ReportsApi.request.SearchRequest;
import com.example.ReportsApi.response.SearchResponse;
import com.lowagie.text.DocumentException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public interface EligibilityService {
    public List<String> getUniquePlanNames();
    public List<String> getUniquePlanStatuses();
    public List<SearchResponse> search(SearchRequest searchRequest);
    //public HttpServletResponse generateExcel( );
    public void generateExcel(HttpServletResponse response) throws IOException;
    //public HttpServletResponse generatePdf( response);
    public void generatePdf(HttpServletResponse response) throws IOException, DocumentException;
}
