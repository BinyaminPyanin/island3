package com.upgrade.island3.model;

import lombok.*;

import javax.persistence.*;

/**
 * Spot
 *
 * @author Binyamin Pyanin
 * @since 20210214
 */
@Entity
@Table(name = "SPOT")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Spot {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "spot_sequence")
    @SequenceGenerator(name = "spot_sequence", sequenceName = "spot_id_sequence", allocationSize = 1)
    private long id;

    @Version
    private Integer version;

    @Column
    private String name;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "status_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "reservation_status_id_fkey"))
    private Status status;

    @Column
    private String description;
}
