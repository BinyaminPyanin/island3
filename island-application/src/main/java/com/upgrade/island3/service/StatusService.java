package com.upgrade.island3.service;

import com.upgrade.island3.model.Status;
import com.upgrade.island3.model.StatusReservation;

/**
 * StatusService
 *
 * @author Binyamin Pyanin
 * @since 20210214
 */
public interface StatusService {
    Status findStatus(StatusReservation statusReservation);

    Status getCanceled();

    Status getAvailable();

    Status getReserved();
}
