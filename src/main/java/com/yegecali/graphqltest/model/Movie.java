package com.yegecali.graphqltest.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Movie {
  @Id
  private String id;
  private String title;
  private String[] directors;
  private String[] actors;
  private String releaseDate;
}
