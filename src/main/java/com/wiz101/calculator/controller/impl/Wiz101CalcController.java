package com.wiz101.calculator.controller.impl;

import com.wiz101.calculator.controller.Wiz101Controller;
import com.wiz101.calculator.dto.WizStats;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/v1")
@CrossOrigin(origins = "*")
public class Wiz101CalcController implements Wiz101Controller {
    @PostMapping("/fetchWizardStats")
    @Override
    public ResponseEntity<WizStats> fetchWizardStats(@RequestBody WizStats stats, HttpServletRequest httpServletRequest) {

        System.out.println(stats);

        /*
         Setting Wizard and Spell Base Stats
        */
        WizStats resObj = new WizStats();
        resObj = stats;


        return ResponseEntity.status(200).body(stats);
    }

}









