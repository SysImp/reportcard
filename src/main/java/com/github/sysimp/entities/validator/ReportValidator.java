package com.github.sysimp.entities.validator;

import com.github.sysimp.entities.Report;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import java.time.LocalDate;
import java.time.LocalTime;

@Component
public class ReportValidator extends BaseValidator{
    private static final Logger LOG = LogManager.getLogger(ReportValidator.class);

    @Override
    public boolean supports(Class<?> aClass) {
        return Report.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Report report = (Report) o;
        LOG.debug("Validator Report: {}", report);

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "employee", "validator.report.employee.isNull");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "specialty", "validator.report.specialty.isNull");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "strDate", "validator.report.date.isNull");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "strTimeStart", "validator.report.startTime.isNull");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "strTimeEnd", "validator.report.endTime.isNull");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "validator.report.description.isNullOrEmpty");

        checkDate(report, errors);
        checkTime(report, errors, true);
        checkTime(report, errors, false);

        if (report.getLocalTimeStart() != null && report.getLocalTimeEnd() != null &&
                report.getLocalTimeStart().compareTo(report.getLocalTimeEnd()) > 0) {
                errors.rejectValue("strTimeStart", "validator.report.startTime.isGreat");
                errors.rejectValue("strTimeEnd", "validator.report.endTime.isSmall");
        }

        LOG.debug("Validator Report isFinish: {}", !errors.hasErrors());
    }

    private void checkDate(Report report, Errors errors) {
        LocalDate localDate = parseDate(report.getStrDate());
        if (localDate == null) {
            errors.rejectValue("strDate", "validator.report.date.isBad");
        } else {
            report.setLocalDate(localDate);
        }
    }

    private void checkTime(Report report, Errors errors, boolean isStartTime) {
        LocalTime localTime = parseTime(isStartTime ? report.getStrTimeStart(): report.getStrTimeEnd());
        if (localTime == null) {
            errors.rejectValue(isStartTime ? "strTimeStart" : "strTimeEnd", "validator.report.time.isBad");
        } else if (isStartTime) {
            report.setLocalTimeStart(localTime);
        } else {
            report.setLocalTimeEnd(localTime);
        }
    }
}
