package com.upgrade.island3.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Reservation
 *
 * @author Binyamin Pyanin
 * @since 20210214
 */
@Entity
@Table(name = "RESERVATION")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reservation_sequence")
    @SequenceGenerator(name = "reservation_sequence", sequenceName = "reservation_id_sequence", allocationSize = 1)
    private long id;

    @Version
    private Integer version;

    @Column(nullable = false, unique = true)
    private String bookingUuid;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "status_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "reservation_status_id_fkey"))
    private Status status;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "user_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "reservation_user_id_fkey"))
    private IslandUser user;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "spot_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "reservation_spot_id_fkey"))
    private Spot spot;

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

    @OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<ReservedDate> reservedDates = new ArrayList<ReservedDate>();
}
