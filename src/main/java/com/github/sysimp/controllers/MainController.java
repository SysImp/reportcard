package com.github.sysimp.controllers;

import com.github.sysimp.entities.Report;
import com.github.sysimp.entities.SearchReportModel;
import com.github.sysimp.entities.validator.ReportValidator;
import com.github.sysimp.entities.validator.SearchValidator;
import com.github.sysimp.services.ReportService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Comparator;

@Controller
public class MainController {
    private static final Logger LOG = LogManager.getLogger(MainController.class);

    private ReportValidator reportValidator;
    private SearchValidator searchValidator;
    private ReportService reportService;

    @Autowired
    MainController(ReportService reportService, ReportValidator reportValidator,
                   SearchValidator searchValidator) {
        this.reportService = reportService;
        this.reportValidator = reportValidator;
        this.searchValidator = searchValidator;
    }

    @RequestMapping(value = {"/index", ""}, method = RequestMethod.GET)
    public String getMain(Model model) {
        LOG.info("RequestMapping: '/index' or ''");

        loadModel(model,null);
        model.addAttribute("newReport", new Report());
        model.addAttribute("searchReport", new SearchReportModel());

        return "index";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String getReport(Model model,
    @ModelAttribute("newReport") @Validated Report report, BindingResult result) {
        LOG.info("RequestMapping: '/create'");
        reportValidator.validate(report, result);
        model.addAttribute("searchReport", new SearchReportModel());

        if (!result.hasErrors()) {
            LOG.debug("Create new 'Report': {}", report);
            reportService.saveReport(report);
            report = new Report();
            model.addAttribute("newReport", report);
        }

        loadModel(model, null);
        return "index";
    }

    @RequestMapping (value = "/search", method = RequestMethod.POST)
    public String getSearch(Model model,
    @ModelAttribute("searchReport") @Validated SearchReportModel searchReportModel, BindingResult result) {
        LOG.info("RequestMapping: '/search'");
        searchValidator.validate(searchReportModel, result);
        model.addAttribute("newReport", new Report());

        if (!result.hasErrors()) {
            LOG.debug("Search enable: {}", searchReportModel);
            loadModel(model, searchReportModel);
        } else {
            loadModel(model, null);
        }
        return "index";
    }

    private void loadModel(Model model, SearchReportModel searchReportModel) {
        LOG.debug("loadModel: search = {}", searchReportModel);
        Sort sort = new Sort(Sort.Direction.ASC, "name");
        Comparator<Report> comparator = Comparator.comparing(o -> o.getEmployee().getName());

        model.addAttribute("reports", searchReportModel != null
            ? reportService.getListReportsBySearchModel(searchReportModel, comparator)
            : reportService.getListReports(comparator));
        model.addAttribute("employees", reportService.getListEmployees(sort));
        model.addAttribute("specialties", reportService.getListSpecialties(sort));
    }
}
