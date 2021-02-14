package com.upgrade.island3.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "AVAILABILITY")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Availability {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "availability_sequence")
    @SequenceGenerator(name = "availability_sequence", sequenceName = "availability_id_sequence", allocationSize = 1)
    private long id;

    @Version
    private Integer version;

    @Column(nullable = false, unique = true)
    private LocalDate availableDate;

    @OneToMany(mappedBy = "availability", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ReservedDate> reservedDates;
}
