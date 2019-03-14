package com.github.sysimp.services;

import com.github.sysimp.entities.Report;
import com.github.sysimp.entities.SearchReportModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SearchService {
    private static final Logger LOG = LogManager.getLogger(SearchService.class);

    private final SearchReportModel searchReportModel;

    public SearchService(SearchReportModel searchReportModel) {
        LOG.debug("init {}", searchReportModel);
        this.searchReportModel = searchReportModel;
    }

    public Specification<Report> getSpecification() {
        LOG.debug("getSpecification {}", searchReportModel);

        List<Specification<Report>> specifications = new ArrayList<>();
        specifications.add(createSpecification("employee", searchReportModel.getEmployee(), Operation.EQUALS));
        specifications.add(createSpecification("specialty", searchReportModel.getSpecialty(), Operation.EQUALS));
        specifications.add(createSpecification("description", searchReportModel.getDescription(), Operation.LIKE));
        specifications.add(createSpecification("localTimeStart",  parseTime(searchReportModel.getLocalTimeStart()), Operation.GREATER));
        specifications.add(createSpecification("localTimeEnd",  parseTime(searchReportModel.getLocalTimeEnd()), Operation.LESS));
        specifications.add(createSpecification("localDate",  parseDate(searchReportModel.getLocalDateStart()), Operation.GREATER));
        specifications.add(createSpecification("localDate",  parseDate(searchReportModel.getLocalDateEnd()), Operation.LESS));

        specifications.removeIf(Objects::isNull);
        return mergeSpecifications(specifications);
    }



    private Specification<Report> createSpecification(String nameCriteria, Object objectCriteria, Operation operation) {
        if (objectCriteria == null) {
            return null;
        } else if (objectCriteria instanceof String && ((String) objectCriteria).isEmpty()) {
            return null;
        }

        return new ReportSpecification(nameCriteria, operation, objectCriteria);
    }

    private Specification<Report> mergeSpecifications(List<Specification<Report>> specifications) {
        LOG.debug("mergeSpecifications method {}", specifications.size());
        if (specifications.size() == 0) {
            return null;
        }

        Specification<Report> specification = specifications.get(0);
        for (int i = 1; i < specifications.size(); i++) {
            if (specifications.get(i) != null) {
                specification = specification.and(specifications.get(i));
            }
        }

        return specification;
    }

    private LocalDate parseDate(String date) {
        LocalDate localDate;
        try {
            localDate = LocalDate.parse(date);
        } catch (DateTimeParseException e) {
            return null;
        }
        return localDate;
    }

    private LocalTime parseTime(String time) {
        LocalTime localTime;
        try {
            localTime = LocalTime.parse(time);
        } catch (DateTimeParseException e) {
            return null;
        }
        return localTime;
    }

    private static class ReportSpecification implements Specification<Report> {
        private final String nameCriteria;
        private final Object objectCriteria;
        private final Operation operation;

        ReportSpecification(String nameCriteria, Operation operation, Object objectCriteria) {
            this.nameCriteria = nameCriteria;
            this.operation = operation;
            this.objectCriteria = objectCriteria;
        }

        @Override
        public Predicate toPredicate(Root<Report> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
            LOG.debug("get Specification: {}, {}, {}", nameCriteria, objectCriteria, operation);
            if (operation == Operation.LIKE) {
                return criteriaBuilder.like(root.get(nameCriteria), "%" + objectCriteria + "%");
            }
            else if (operation == Operation.EQUALS) {
                return criteriaBuilder.equal(root.get(nameCriteria), objectCriteria);
            }
            else if (operation == Operation.GREATER) {
                return criteriaBuilder.greaterThanOrEqualTo(root.<Comparable>get(nameCriteria), (Comparable) objectCriteria);
            }
            else if (operation == Operation.LESS) {
                return criteriaBuilder.lessThanOrEqualTo(root.<Comparable>get(nameCriteria), (Comparable) objectCriteria);
            }

            return null;
        }
    }
}

enum Operation {
    LIKE, EQUALS, GREATER, LESS
}
