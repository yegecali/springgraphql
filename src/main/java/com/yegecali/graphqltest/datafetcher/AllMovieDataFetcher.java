package com.yegecali.graphqltest.datafetcher;

import com.yegecali.graphqltest.model.Movie;
import com.yegecali.graphqltest.repository.MovieRepo;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AllMovieDataFetcher implements DataFetcher<List<Movie>> {

  @Autowired
  private MovieRepo movieRepo;

  @Override
  public List<Movie> get(DataFetchingEnvironment dataFetchingEnvironment) {
    return movieRepo.findAll();
  }
}
