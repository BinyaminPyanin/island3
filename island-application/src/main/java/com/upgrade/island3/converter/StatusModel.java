package com.upgrade.island3.converter;

import lombok.*;

/**
 * StatusModel
 *
 * @author Binyamin Pyanin
 * @since 20210214
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class StatusModel {
    private String code;
    private String description;
}
