package com.upgrade.island3.repository;

import com.upgrade.island3.model.Spot;
import com.upgrade.island3.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * SpotRepository
 *
 * @author Binyamin Pyanin
 * @since 20210214
 */
public interface SpotRepository extends JpaRepository<Spot, Integer> {
    List<Spot> findAllByStatus(Status status);
}
