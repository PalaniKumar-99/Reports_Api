package com.example.ReportsApi.service;

import com.example.ReportsApi.entity.EligibilityDetails;
import com.example.ReportsApi.repository.EligibilityDetailsRepo;
import com.example.ReportsApi.request.SearchRequest;
import com.example.ReportsApi.response.SearchResponse;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class EligibilityServiceImpl implements EligibilityService{

    @Autowired
    private EligibilityDetailsRepo repo;
    @Override
    public List<String> getUniquePlanNames() {
        return repo.findPlanNames();
    }

    @Override
    public List<String> getUniquePlanStatuses() {
        return repo.findPlanStatuses();
    }

    @Override
    public List<SearchResponse> search(SearchRequest searchRequest) {
        List<SearchResponse> responses = new ArrayList<>();
        EligibilityDetails builder = new EligibilityDetails();
        String planName = searchRequest.getPlanName();
        if(planName!=null && !planName.equals("")) {
            builder.setPlanName(planName);
        }
        String status = searchRequest.getPlanStatus();
        if(status != null && !status.equals("")) {
            builder.setPlanStatus(status);
        }
        LocalDate startDate = searchRequest.getPlanStartDate();
        if( startDate != null ) {
            builder.setPlanStartDate(startDate);
        }
        LocalDate endDate = searchRequest.getPlanEndDate();
        if( endDate != null ) {
            builder.setPlanEndDate(endDate);
        }
        Example<EligibilityDetails> example = Example.of(builder);
        List<EligibilityDetails> entities = repo.findAll(example);
        for(EligibilityDetails eligibilityDetails : entities) {
            SearchResponse searchResponse = new SearchResponse();
            BeanUtils.copyProperties(eligibilityDetails, searchResponse);
            responses.add(searchResponse);
        }
        return responses;
    }

    @Override
    public void generateExcel(HttpServletResponse response) throws IOException {
        List<EligibilityDetails> eligibilityDetails = repo.findAll();
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
        HSSFSheet sheet = hssfWorkbook.createSheet();
        HSSFRow headerRow = sheet.createRow(0);
        headerRow.createCell(1).setCellValue("Name");
        headerRow.createCell(2).setCellValue("Email");
        headerRow.createCell(3).setCellValue("MobileNo");
        headerRow.createCell(4).setCellValue("Gender");
        headerRow.createCell(5).setCellValue("SSN");
        int i = 1;
        for(EligibilityDetails entity : eligibilityDetails) {
            HSSFRow dataRow = sheet.createRow(i);
            dataRow.createCell(0).setCellValue(entity.getName());
            dataRow.createCell(1).setCellValue(entity.getEmail());
            dataRow.createCell(2).setCellValue(entity.getMobileNo());
            dataRow.createCell(3).setCellValue(entity.getGender());
            dataRow.createCell(4).setCellValue(entity.getSsn());
            i++;
        }
        ServletOutputStream outputStream = response.getOutputStream();
        hssfWorkbook.write(outputStream);
        hssfWorkbook.close();
        outputStream.close();
    }

    @Override
    public void generatePdf(HttpServletResponse response) throws IOException, DocumentException {
        List<EligibilityDetails> eligibilityDetails = repo.findAll();
        Document doc = new Document(PageSize.A4);
        PdfWriter.getInstance(doc, response.getOutputStream());

        doc.open();
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        font.setColor(Color.BLUE);
        Paragraph p = new Paragraph("Search Report", font);
        p.setAlignment(Paragraph.ALIGN_CENTER);
        doc.add(p);

        PdfPTable pdfPTable = new PdfPTable(5);
        pdfPTable.setWidthPercentage(100f);
        pdfPTable.setWidths(new float[]{1.5f,3.5f,3.0f,3.0f,1.5f});
        pdfPTable.setSpacingBefore(10);

        PdfPCell headerCell = new PdfPCell();
        headerCell.setBackgroundColor(Color.CYAN);
        headerCell.setPadding(5);
        font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.white);
        headerCell.setPhrase(new Phrase("Name", font));
        pdfPTable.addCell(headerCell);
        headerCell.setPhrase(new Phrase("Email", font));
        pdfPTable.addCell(headerCell);
        headerCell.setPhrase(new Phrase("MobileNo", font));
        pdfPTable.addCell(headerCell);
        headerCell.setPhrase(new Phrase("Gender", font));
        pdfPTable.addCell(headerCell);
        headerCell.setPhrase(new Phrase("SSN", font));
        pdfPTable.addCell(headerCell);

        for (EligibilityDetails entity : eligibilityDetails) {
            pdfPTable.addCell(entity.getName());
            pdfPTable.addCell(entity.getEmail());
            pdfPTable.addCell(String.valueOf(entity.getMobileNo()));
            pdfPTable.addCell(String.valueOf(entity.getGender()));
            pdfPTable.addCell(String.valueOf(entity.getSsn()));
        }
        doc.add(pdfPTable);
        doc.close();
    }
}
