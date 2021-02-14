package com.upgrade.island3.repository;

import com.upgrade.island3.model.IslandUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IslandUserRepository extends JpaRepository<IslandUser, Integer> {
}
