package com.upgrade.island3.repository;

import com.upgrade.island3.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * StatusRepository
 *
 * @author Binyamin Pyanin
 * @since 20210214
 */
public interface StatusRepository extends JpaRepository<Status, Integer> {
    Status findStatusByCode(String code);
}
