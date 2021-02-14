package com.upgrade.island3.model;

import lombok.*;

import javax.persistence.*;

/**
 * ReservedDate
 *
 * @author Binyamin Pyanin
 * @since 20210214
 */
@Entity
@Table(name = "RESERVED_DATE")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ReservedDate {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reservation_date_sequence")
    @SequenceGenerator(name = "reservation_date_sequence", sequenceName = "reserved_date_id_sequence", allocationSize = 1)
    private long id;

    @Version
    private Integer version;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "AVAILABILITY_ID", referencedColumnName = "id", nullable = false)
    private Availability availability;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "RESERVATION_ID", referencedColumnName = "id", nullable = false)
    private Reservation reservation;
}
