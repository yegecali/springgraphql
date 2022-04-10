package com.yegecali.graphqltest.provider;

import com.yegecali.graphqltest.datafetcher.AllMovieDataFetcher;
import com.yegecali.graphqltest.datafetcher.MovieDataFetcher;
import com.yegecali.graphqltest.model.Movie;
import com.yegecali.graphqltest.repository.MovieRepo;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.stream.Stream;

@Component
public class GraphqlProvider {

  @Autowired
  private AllMovieDataFetcher allMovieDataFetcher;
  @Autowired
  private MovieDataFetcher movieDataFetcher;

  @Autowired
  private MovieRepo movieRepo;

  @Value("classpath:schema.graphql")
  private Resource resource;
  private GraphQL graphQL;

  @PostConstruct
  public void init() throws IOException {
    File file = resource.getFile();
    TypeDefinitionRegistry typeDefinitionRegistry = new SchemaParser().parse(file);
    RuntimeWiring runtimeWiring =builRuntimeWiring();
    GraphQLSchema graphQLSchema = new SchemaGenerator().makeExecutableSchema(typeDefinitionRegistry, runtimeWiring);
    this.graphQL = GraphQL.newGraphQL(graphQLSchema).build();
    loadDataIntoHSQL();
  }

  private RuntimeWiring builRuntimeWiring(){
    return RuntimeWiring.newRuntimeWiring()
      .type("Query",
        typewiring -> typewiring
          .dataFetcher("allMovies", allMovieDataFetcher)
          .dataFetcher("movie", movieDataFetcher))
      .build();
  }
  private void loadDataIntoHSQL() {
    Stream.of(Movie.builder().id("1001").title("Guason")
        .actors(new String[]{"Joaquin Phoenix", "Robert De Niro"})
        .directors(new String[]{"Todd Phillips"}).releaseDate("3 de octubre de 2019")
        .build(),
      Movie.builder().id("1002").title("Avengers: Endgame")
        .actors(new String[]{"Robert Downey Jr.", "Scarlett Johansson", "Chris Evans"})
        .directors(new String[]{"Anthony Russo", "Joe Russo"}).releaseDate("22 de abril de 2019")
        .build()
    ).forEach(movie -> movieRepo.save(movie));
  }
  @Bean
  public GraphQL graphQL(){
    return graphQL;
  }

}
