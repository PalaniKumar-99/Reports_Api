package com.example.ReportsApi.response;

import lombok.Data;

@Data
public class SearchResponse {
    private Long mobileNo;
    private Character gender;
    private String email;
    private Long ssn;
}
