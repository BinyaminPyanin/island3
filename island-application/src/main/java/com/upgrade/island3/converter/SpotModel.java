package com.upgrade.island3.converter;

import lombok.*;

/**
 * SpotModel
 *
 * @author Binyamin Pyanin
 * @since 20210214
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SpotModel {
    private String name;
    private String description;
    private StatusModel status;
}
