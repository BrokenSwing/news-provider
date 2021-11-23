package com.github.covidalert.newsprovider;

import com.c4_soft.springaddons.security.oauth2.test.annotations.keycloak.WithMockKeycloakAuth;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.covidalert.newsprovider.config.RedisTestConfig;
import com.github.covidalert.newsprovider.controllers.NewsController;
import com.github.covidalert.newsprovider.pojos.Article;
import com.github.covidalert.newsprovider.repositories.ArticlesRepository;
import com.google.common.collect.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.keycloak.adapters.springsecurity.KeycloakSecurityComponents;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.web.servlet.MockMvc;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = RedisTestConfig.class)
@AutoConfigureMockMvc
@ComponentScan(basePackageClasses = {KeycloakSecurityComponents.class, KeycloakSpringBootConfigResolver.class})
class MicroserviceTemplateApplicationTests
{

    private static final List<Article> ARTICLES_FIXTURES = Lists.newArrayList(
            new Article("Article 1", "Summary 1", "Link 1", ZonedDateTime.of(2021, 11, 1, 0, 0, 0, 0, ZoneId.systemDefault()))
    );

    @Autowired
    private NewsController newsController;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ArticlesRepository articlesRepository;

    @BeforeEach()
    public void beforeEach()
    {
        this.articlesRepository.deleteAll();
    }

    @Test
    public void contextLoads()
    {
        assertThat(newsController).isNotNull();
    }

    @Test
    @WithMockKeycloakAuth
    public void givenNews_whenPutToApiNews_shouldBeSuccessful() throws Exception
    {
        ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
        String body = mapper.writeValueAsString(ARTICLES_FIXTURES);
        mockMvc.perform(
                        put("/api/news")
                                .contentType("application/json")
                                .content(body)
                )
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @WithMockKeycloakAuth
    public void givenNews_whenPutToApiNews_shouldBeStoredInRedis() throws Exception
    {
        ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
        String body = mapper.writeValueAsString(ARTICLES_FIXTURES);
        mockMvc.perform(
                put("/api/news")
                        .contentType("application/json")
                        .content(body)
        );

        var storedArticles = this.articlesRepository.findAll();
        assertThat(storedArticles).hasSize(ARTICLES_FIXTURES.size());
    }

    @Test
    @WithMockKeycloakAuth
    public void givenNewsInRedis_whenGetToApiNews_shouldReturnStoredNews() throws Exception
    {
        articlesRepository.saveAll(ARTICLES_FIXTURES);
        mockMvc.perform(get("/api/news"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$[0].title", is("Article 1")))
                .andExpect(jsonPath("$[0].summary", is("Summary 1")))
                .andExpect(jsonPath("$[0].link", is("Link 1")));
    }

}
