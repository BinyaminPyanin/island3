package com.upgrade.island3.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "AVAILABILITY")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Availability {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "availability_sequence")
    @SequenceGenerator(name = "availability_sequence", sequenceName = "availability_id_sequence", allocationSize = 1)
    private long id;

    @Column(nullable = false)
    private long spotId;

    @Column(nullable = false, unique = true)
    private LocalDate availableDate;

}
