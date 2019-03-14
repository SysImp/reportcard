package com.github.sysimp.entities.validator;

import com.github.sysimp.entities.SearchReportModel;
import com.github.sysimp.services.ReportService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import java.time.LocalDate;
import java.time.LocalTime;

@Component
public class SearchValidator extends BaseValidator{
    private static final Logger LOG = LogManager.getLogger(SearchValidator.class);

    private ReportService reportService;

    @Autowired
    SearchValidator(ReportService reportService) {
        this.reportService = reportService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return SearchReportModel.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        SearchReportModel searchReportModel = (SearchReportModel) o;
        LOG.debug("Validator SearchReportModel: {}", searchReportModel);

        if (searchReportModel.getEmployee() != null && !reportService.isContainsEmployee(searchReportModel.getEmployee())) {
            errors.rejectValue("employee", "validator.search.employee.isFake");
        }
        if (searchReportModel.getSpecialty() != null && !reportService.isContainsSpecialty(searchReportModel.getSpecialty())) {
            errors.rejectValue("specialty", "validator.search.specialty.isFake");
        }

        LocalTime localTimeStart = parseTime(searchReportModel.getLocalTimeStart());
        LocalTime localTimeEnd = parseTime(searchReportModel.getLocalTimeEnd());
        LocalDate localDateStart = parseDate(searchReportModel.getLocalDateStart());
        LocalDate localDateEnd = parseDate(searchReportModel.getLocalDateEnd());

        checkTimeField(searchReportModel.getLocalTimeStart(), "localTimeStart", errors, localTimeStart);
        checkTimeField(searchReportModel.getLocalTimeEnd(), "localTimeEnd", errors, localTimeEnd);

        checkDateField(searchReportModel.getLocalDateStart(), "localDateStart", errors, localDateStart);
        checkDateField(searchReportModel.getLocalDateEnd(), "localDateEnd", errors, localDateEnd);

        if (localTimeStart != null && localTimeEnd != null &&
                localTimeStart.compareTo(localTimeEnd) > 0) {
            errors.rejectValue("localTimeStart", "validator.report.startTime.isGreat");
            errors.rejectValue("localTimeEnd", "validator.report.endTime.isSmall");
        }

        if (localDateStart != null && localDateEnd != null &&
                localDateStart.compareTo(localDateEnd) > 0) {
            errors.rejectValue("localDateStart", "validator.search.startDate.isGreat");
            errors.rejectValue("localDateEnd", "validator.search.endDate.isSmall");
        }

        LOG.debug("Validator SearchReportModel isFinish: {}", !errors.hasErrors());
    }

    private void checkTimeField(String value, String name, Errors errors, LocalTime time) {
        if (!value.isEmpty() && time == null) {
            errors.rejectValue(name, "validator.report.time.isBad");
        }
    }

    private void checkDateField(String value, String name, Errors errors, LocalDate date) {
        if (!value.isEmpty() && date == null) {
            errors.rejectValue(name, "validator.report.date.isBad");
        }
    }
}
