package com.upgrade.island3.repository;

import com.upgrade.island3.model.Availability;
import com.upgrade.island3.model.Status;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.time.LocalDate;
import java.util.List;

/**
 * CustomizedAvailabilityRepositoryImpl
 *
 * @author Binyamin Pyanin
 * @since 20210215
 */
@Repository
public class CustomizedAvailabilityRepositoryImpl implements CustomizedAvailabilityRepository {

    private static final String GET_DATES_IN_RANGE_HQL = " from Availability where status=:status and availableDate between :fromDate and :toDate";
    private static final String GET_ALL_DATES_IN_RANGE_HQL = " from Availability";
    private static final String GET_AVAILABILITY_BY_DATE_HQL = " from Availability where availableDate=:date";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Availability> getAvailableDatesByRange(LocalDate fromDate, LocalDate toDate, Status status) {
        return getAvailableDatesByRange(fromDate, toDate, status, true);
    }

    @Override
    public List<Availability> getAllAvailableDates() {
        return getAllAvailableDates(false);
    }

    @Override
    public Availability findAvailabilityByDate(LocalDate localDate) {
        Query query = this.entityManager.
                createQuery(GET_AVAILABILITY_BY_DATE_HQL).
                setParameter("date", localDate);

        return (Availability) query.getResultList().get(0);
    }

    private Query buildDateRangeQuery(String sqlPrefix, LocalDate fromDate, LocalDate toDate, Status status) {
        return this.entityManager.
                createQuery(sqlPrefix).
                setParameter("status", status).
                setParameter("fromDate", fromDate).
                setParameter("toDate", toDate);
    }

    private List<Availability> getAvailableDatesByRange(LocalDate fromDate, LocalDate toDate, Status status, boolean isLocked) {
        Query query = buildDateRangeQuery(GET_DATES_IN_RANGE_HQL, fromDate, toDate, status);

        query.setHint("javax.persistence.lock.timeout", 2000);
        query.setHint("javax.persistence.query.timeout", 2000);

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
