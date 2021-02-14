package com.upgrade.island3.repository;

import com.upgrade.island3.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * ReservationRepository
 *
 * @author Binyamin Pyanin
 * @since 20210214
 */
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
}
