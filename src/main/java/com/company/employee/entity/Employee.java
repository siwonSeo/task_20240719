package com.company.employee.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
public class Employee {

  @Id
  @NotNull
  @Column(name = "name", nullable = false)
  @Comment("이름")
  private String name;

  @NotNull
  @Column(name = "email", nullable = false)
  @Comment("이메일")
  private String email;

  @NotNull
  @Column(name = "tell", nullable = false)
  @Comment("연락처")
  private String tell;

  @NotNull
  @Column(name = "joined", nullable = false)
  @Comment("입사일")
  private LocalDate joined;

  public Employee(String name, String email, String tell, LocalDate joined) {
    this.name = name;
    this.email = email;
    this.tell = tell;
    this.joined = joined;
  }
}