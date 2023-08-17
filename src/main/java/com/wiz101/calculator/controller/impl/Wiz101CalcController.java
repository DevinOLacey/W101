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
    public ResponseEntity<WizStats> fetchWizardStats(@RequestBody int stats, HttpServletRequest httpServletRequest) {

        System.out.println(stats);

        WizStats wizStats = new WizStats();
//
        wizStats.setCrit(stats);
        wizStats.setDamage(stats);
        wizStats.setEnemyres(stats);
        wizStats.setPeirce(stats);
        wizStats.setSpelldmg(stats);


        return ResponseEntity.status(200).body(wizStats);
    }

}









