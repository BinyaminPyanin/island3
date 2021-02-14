package com.upgrade.island3.repository;

import com.upgrade.island3.model.Availability;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.time.LocalDate;
import java.util.List;

@Repository
public class CustomizedAvailabilityRepositoryImpl implements CustomizedAvailabilityRepository {

    private static final String GET_DATES_IN_RANGE_HQL = " from Availability where availableDate between :fromDate and :toDate";
    private static final String GET_ALL_DATES_IN_RANGE_HQL = " from Availability";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Availability> getAvailableDatesByRange(LocalDate fromDate, LocalDate toDate) {
        return getAvailableDatesByRange(fromDate, toDate, false);
    }

    @Override
    public List<Availability> getAllAvailableDates() {
        return getAllAvailableDates(false);
    }

    private Query buildDateRangeQuery(String sqlPrefix, LocalDate fromDate, LocalDate toDate) {
        return this.entityManager.
                createQuery(sqlPrefix).
                setParameter("fromDate", fromDate).
                setParameter("toDate", toDate);
    }

    private List<Availability> getAvailableDatesByRange(LocalDate fromDate, LocalDate toDate, boolean isLocked) {
        Query query = buildDateRangeQuery(GET_DATES_IN_RANGE_HQL, fromDate, toDate);

        if (isLocked) {
            query.setLockMode(LockModeType.OPTIMISTIC_FORCE_INCREMENT);
        }

        return query.getResultList();
    }

    private List<Availability> getAllAvailableDates(boolean isLocked) {
        Query query = this.entityManager.createQuery(GET_ALL_DATES_IN_RANGE_HQL);

        if (isLocked) {
            query.setLockMode(LockModeType.OPTIMISTIC_FORCE_INCREMENT);
        }

        return query.getResultList();
    }

}
