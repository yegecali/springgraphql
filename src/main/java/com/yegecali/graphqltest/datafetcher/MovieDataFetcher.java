package com.yegecali.graphqltest.datafetcher;

import com.yegecali.graphqltest.model.Movie;
import com.yegecali.graphqltest.repository.MovieRepo;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MovieDataFetcher implements DataFetcher<Movie> {

  @Autowired
  private MovieRepo movieRepo;

  @Override
  public Movie get(DataFetchingEnvironment dataFetchingEnvironment) {
    String id = dataFetchingEnvironment.getArgument("id");
    return movieRepo.findById(id).orElse(null);
  }
}
