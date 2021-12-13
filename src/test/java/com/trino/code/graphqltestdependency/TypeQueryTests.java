package com.trino.code.graphqltestdependency;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.boot.test.tester.AutoConfigureWebGraphQlTester;
import org.springframework.graphql.test.tester.WebGraphQlTester;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes= TypeQueryTests.class)
@AutoConfigureWebGraphQlTester
@AutoConfigureMockMvc
public class TypeQueryTests {


    @Autowired
    WebGraphQlTester graphQlTester;


    @Test
    void allTypes() {
        var allTypes = """
      query types {
          types {
            id
            name

          }
        }""";
        graphQlTester.query(allTypes)
                .execute()
                .path("types[*].name")
                .entityList(String.class)
                .satisfies(names -> assertThat(names).anyMatch(s -> s.startsWith("TEST")));
    }

}