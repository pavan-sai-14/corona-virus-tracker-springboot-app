package com.pavanreddy.corona_virus_tracker.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.pavanreddy.corona_virus_tracker.model.LocationStats;
import com.pavanreddy.corona_virus_tracker.service.CoronaVirusDataService;

@Controller
public class HomeController {
	
	@Autowired
	private CoronaVirusDataService coronaVirusDataService;
	
	@GetMapping("/")
	public String home(Model model)
	{
		List<LocationStats> allStats= coronaVirusDataService.getAllStats();
		int totalReportedCases=allStats.stream()
										.mapToInt((stats)->stats.getTotalReportedCase())
										.sum();
		int totalNewCases=allStats.stream()
				.mapToInt((stats)->stats.getLatestReportedCase())
				.sum();
		model.addAttribute("totalReportedCases", totalReportedCases);
		model.addAttribute("totalNewCases", totalNewCases);
		model.addAttribute("locationStats",allStats);
		return "home";
	}
	
	@ModelAttribute("virus")
    public LocationStats userRegistrationDto() {
        return new LocationStats();
    }
	
	@PostMapping("/country")
	public String country(@ModelAttribute("virus") LocationStats locationStats,Model model)
	{
		int flag=0;
		for(LocationStats i :coronaVirusDataService.getAllStats())
		{
			if(i.getCountry().equalsIgnoreCase(locationStats.getCountry()))
			{
				flag=1;
				model.addAttribute("locationStat", i);
				break;
			}
		}
		if(flag==0)
		{
			model.addAttribute("locationStat",new LocationStats());
		}
														
		return "country";
	}
	
}
