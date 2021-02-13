package com.upgrade.island3.repository;

import com.upgrade.island3.model.Availability;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AvailabilityRepository extends JpaRepository<Availability, Integer>, CustomizedAvailabilityRepository {
}
