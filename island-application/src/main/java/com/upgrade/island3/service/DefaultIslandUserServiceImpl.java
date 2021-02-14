package com.upgrade.island3.service;

import com.upgrade.island3.model.IslandUser;
import com.upgrade.island3.repository.IslandUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class DefaultIslandUserServiceImpl implements IslandUserService {

    private IslandUserRepository islandUserRepository;

    @Autowired
    public DefaultIslandUserServiceImpl(IslandUserRepository islandUserRepository) {
        this.islandUserRepository = islandUserRepository;
    }

    @Transactional
    @Override
    public void save(IslandUser islandUser) {
        log.info("Saving user {}", islandUser);
        this.islandUserRepository.save(islandUser);
    }
}
