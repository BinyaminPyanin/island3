package com.upgrade.island3.repository;

import com.upgrade.island3.model.UserType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTypeRepository extends JpaRepository<UserType, Integer> {

    UserType findUserTypeByType(String type);

}
