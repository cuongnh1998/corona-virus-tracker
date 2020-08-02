package com.ninhhoangcuong.coronavirustracker.controller;

import com.ninhhoangcuong.coronavirustracker.model.LocationStats;
import com.ninhhoangcuong.coronavirustracker.service.CoronaVirusDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Comparator;
import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private CoronaVirusDataService coronaVirusDataService;
    @GetMapping("/")
    public String home(Model model){
        List<LocationStats> allStates = coronaVirusDataService.getConfirmStats();
        allStates.sort(Comparator.comparingInt(LocationStats::getLatestTotalCase).reversed());
        int totalReportedCases = allStates.stream().mapToInt(LocationStats::getLatestTotalCase).sum();
        int totalNewCases = allStates.stream().mapToInt(LocationStats::getDiffFromPrevDay).sum();
        model.addAttribute("locationStatses",coronaVirusDataService.getConfirmStats());
        model.addAttribute("totalReportedCases",totalReportedCases);
        model.addAttribute("totalNewCases",totalNewCases);
        return "home";
    }
}
