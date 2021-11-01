package com.github.covidalert.newsprovider.controllers;

import com.github.covidalert.newsprovider.pojos.Article;
import com.github.covidalert.newsprovider.repositories.ArticlesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/news")
public class NewsController
{

    @Autowired
    private ArticlesRepository articlesRepository;

    @PutMapping
    public void updateNews(@RequestBody @Valid List<Article> articles)
    {
        articlesRepository.saveAll(articles);
    }

    @GetMapping
    public Iterable<Article> getArticles()
    {
        return articlesRepository.findAll();
    }

}
