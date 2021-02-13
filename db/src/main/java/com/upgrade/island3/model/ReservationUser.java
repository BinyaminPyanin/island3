package com.upgrade.island3.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "RESERVATION_USER")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationUser {

  @Column(nullable = false)
  private long version;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence")
  @SequenceGenerator(name = "user_sequence", sequenceName = "user_id_sequence", allocationSize = 1)
  private long id;

  @Column(nullable = false)
  private String firstName;

  @Column(nullable = false)
  private String lastName;

  @Column(nullable = false)
  private String email;

  @Column(nullable = false)
  private long statusId;

  @Column(nullable = false)
  private long userTypeId;

  @Column(nullable = false)
  private LocalDate creationDate;

}
