package com.upgrade.island3.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "ISLAND_USER")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class IslandUser {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence")
    @SequenceGenerator(name = "user_sequence", sequenceName = "user_id_sequence", allocationSize = 1)
    private long id;

    @Version
    private Integer version;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String email;
}
