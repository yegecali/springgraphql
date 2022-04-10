package com.yegecali.graphqltest.repository;

import com.yegecali.graphqltest.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepo extends JpaRepository<Movie, String> {
}
