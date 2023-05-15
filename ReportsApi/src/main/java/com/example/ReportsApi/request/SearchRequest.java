package com.example.ReportsApi.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class SearchRequest {
    private String planName;
    private String planStatus;
    private LocalDate planStartDate;
    private LocalDate planEndDate;
}
