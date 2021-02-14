package com.upgrade.island3.model;

import lombok.*;

import javax.persistence.*;

/**
 * Status
 *
 * @author Binyamin Pyanin
 * @since 20210214
 */
@Entity
@Table(name = "STATUS")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Status {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "status_sequence")
    @SequenceGenerator(name = "status_sequence", sequenceName = "status_id_sequence", allocationSize = 1)
    private long id;

    @Version
    private Integer version;

    @Column(nullable = false, unique = true)
    private String code;

    @Column
    private String description;
}
