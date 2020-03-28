package com.example.demo.controller;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.model.LocationStats;
import com.example.demo.service.CoronaVirusDataService;

@Controller
public class HomeController {
	
	@Autowired
	CoronaVirusDataService coronaVirusDataService;                                                

	@GetMapping("/")
	public String home(Model model) {
		List<LocationStats> allStats =  coronaVirusDataService.getAllStats();
		
		int totalReportedCases = allStats.stream().mapToInt(t->t.getLatestCaseTotal()).sum();
		int totalNewCases = allStats.stream().mapToInt(t->t.getDiffFromPrevDay()).sum();
		
		model.addAttribute("locationStats", allStats);
		model.addAttribute("totalReportedCases", totalReportedCases);
		model.addAttribute("totalNewCases", totalNewCases);
		
		return "home";
		
	}
}
