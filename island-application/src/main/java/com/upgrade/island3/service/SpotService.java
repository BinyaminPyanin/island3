package com.upgrade.island3.service;

import com.upgrade.island3.model.Spot;

/**
 * SpotService
 *
 * @author Binyamin Pyanin
 * @since 20210214
 */
public interface SpotService {
    long countAll();

    long countAvailable();

    Spot randomSpot();

    Spot randomAvailableSpot();
}
