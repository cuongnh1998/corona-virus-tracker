package com.ninhhoangcuong.coronavirustracker.controller;

import com.ninhhoangcuong.coronavirustracker.model.LocationStats;
import com.ninhhoangcuong.coronavirustracker.model.Report;
import com.ninhhoangcuong.coronavirustracker.service.CoronaVirusDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HomeController {
    @Autowired
    private CoronaVirusDataService coronaVirusDataService;

    @GetMapping("/")
    public String home(Model model) {
        List<LocationStats> allStates = coronaVirusDataService.getConfirmStats();
        allStates.sort(Comparator.comparingInt(LocationStats::getLatestTotalCase).reversed());
        var report = allStates.stream().collect(Collectors.toMap(LocationStats::getCountry,
                locationStats -> new Report(locationStats.getLatestTotalCase(), locationStats.getDiffFromPrevDay()),
                (oldReport, newReport) -> {
                    newReport.setDiffFromPrevDay(oldReport.getDiffFromPrevDay() + newReport.getDiffFromPrevDay());
                    newReport.setLatestTotalCase(oldReport.getLatestTotalCase() + newReport.getLatestTotalCase());
                    return newReport;
                }, LinkedHashMap::new));
        var totalReportedCases = allStates.stream().mapToInt(LocationStats::getLatestTotalCase).sum();
        var totalNewCases = allStates.stream().mapToInt(LocationStats::getDiffFromPrevDay).sum();
        model.addAttribute("report", report);
        model.addAttribute("totalReportedCases", totalReportedCases);
        model.addAttribute("totalNewCases", totalNewCases);
        return "home";
    }
}
