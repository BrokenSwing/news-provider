package com.github.covidalert.newsprovider.repositories;

import com.github.covidalert.newsprovider.pojos.Article;
import org.springframework.data.repository.CrudRepository;

public interface ArticlesRepository extends CrudRepository<Article, String>
{
}
