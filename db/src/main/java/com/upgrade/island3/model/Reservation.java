package com.upgrade.island3.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "RESERVATION")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reservation_sequence")
    @SequenceGenerator(name = "reservation_sequence", sequenceName = "reservation_id_sequence", allocationSize = 1)
    private long id;

    @Column(nullable = false, unique = true)
    private String uuid;

    @Column(nullable = false)
    private long userId;

    @Column(nullable = false)
    private long statusId;

    @Column(nullable = false)
    private long availabilityId;

    @Column(nullable = false)
    private long price;

    @Column(nullable = false)
    private LocalDate arrivalDate;

    @Column(nullable = false)
    private LocalDate departureDate;

    @Column(nullable = false)
    private LocalDate creationDate;

    @Column(nullable = false)
    private LocalDate updateDate;

}
