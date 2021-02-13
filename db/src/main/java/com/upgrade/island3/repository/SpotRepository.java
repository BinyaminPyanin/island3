package com.upgrade.island3.repository;

import com.upgrade.island3.model.Spot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpotRepository extends JpaRepository<Spot, String> {

    Spot findSpotByName(String name);

}
