package com.moviesreview.api.controllers;

import com.moviesreview.api.services.impl.RaportService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping("/raport")
public class RaportController {
    private final RaportService reportService;

    @Autowired
    public RaportController(RaportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/generate")
    public ResponseEntity<String> getReport() {
        log.info("Generating report");
        reportService.generateReport();
        log.info("Report generated successfully");
        return new ResponseEntity<>("Zbudowano raport", HttpStatus.OK);
    }

}
