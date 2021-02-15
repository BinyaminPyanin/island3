package com.upgrade.island3.converter;

import lombok.*;

/**
 * IslandUserModel
 *
 * @author Binyamin Pyanin
 * @since 20210214
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class IslandUserModel {
    private String firstName;
    private String lastName;
    private String email;
}
