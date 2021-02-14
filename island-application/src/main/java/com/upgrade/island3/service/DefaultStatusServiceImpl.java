package com.upgrade.island3.service;

import com.upgrade.island3.model.Status;
import com.upgrade.island3.model.StatusReservation;
import com.upgrade.island3.repository.StatusRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class DefaultStatusServiceImpl implements StatusService {

    private StatusRepository statusRepository;

    @Autowired
    public DefaultStatusServiceImpl(StatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Status findStatus(StatusReservation statusReservation) {
        return getStatus(statusReservation);
    }

    @Override
    @Transactional(readOnly = true)
    public Status getCanceled() {
        return getStatus(StatusReservation.CANCELED);
    }

    @Override
    @Transactional(readOnly = true)
    public Status getAvailable() {
        return getStatus(StatusReservation.AVAILABLE);
    }

    @Override
    @Transactional(readOnly = true)
    public Status getReserved() {
        return getStatus(StatusReservation.RESERVED);
    }

    private Status getStatus(StatusReservation statusReservation) {
        log.info("Fetching {} status", statusReservation.name());
        return this.statusRepository.findStatusByCode(statusReservation.name());
    }

}

