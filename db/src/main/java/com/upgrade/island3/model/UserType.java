package com.upgrade.island3.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "USER_TYPE")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserType {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_type_sequence")
    @SequenceGenerator(name = "user_type_sequence", sequenceName = "user_type_id_sequence", allocationSize = 1)
    private long id;

    @Column(nullable = false, unique = true)
    private String type;

    @Column
    private String description;

}
