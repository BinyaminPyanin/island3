package com.upgrade.island3.repository;

import com.upgrade.island3.model.Spot;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * SpotRepository
 *
 * @author Binyamin Pyanin
 * @since 20210214
 */
public interface SpotRepository extends JpaRepository<Spot, Integer> {
}
