package com.wiz101.calculator.controller;


import com.wiz101.calculator.dto.WizStats;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

@RequestMapping("/")
@CrossOrigin(origins = "*")
public interface Wiz101Controller {


    @PostMapping("/")
    public ResponseEntity<WizStats> fetchWizardStats(
            @RequestBody int stats, HttpServletRequest httpServletRequest);
}
