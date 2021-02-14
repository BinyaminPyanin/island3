package com.upgrade.island3.service;

import com.upgrade.island3.model.Spot;
import com.upgrade.island3.repository.SpotRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.ThreadLocalRandom;

@Service
@Slf4j
public class DefaultSpotServiceImpl implements SpotService {

    private SpotRepository spotRepository;

    @Autowired
    public DefaultSpotServiceImpl(SpotRepository spotRepository) {
        this.spotRepository = spotRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public long countAll() {
        long count = this.spotRepository.count();
        log.info("Total spots {}", count);
        return count;
    }

    @Override
    @Transactional(readOnly = true)
    public Spot randomSpot() {
        long counter = countAll();
        int index = ThreadLocalRandom.current().nextInt((int) counter);

        Page<Spot> spotPage = this.spotRepository.findAll(PageRequest.of(index, 1));

        Spot spot = null;
        if (spotPage.hasContent()) {
            spot = spotPage.getContent().get(0);
        }

        log.info("Random spot {}", spot);

        return spot;
    }
}
