package com.github.covidalert.newsprovider.pojos;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import java.util.Objects;

@RedisHash("article")
public class Article
{

    @Id
    @NotNull
    @NotEmpty
    private String title;

    @NotNull
    @NotEmpty
    private String summary;

    @NotNull
    @NotEmpty
    private String link;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
    private ZonedDateTime date;

    public Article()
    {
    }

    public Article(String title, String summary, String link, ZonedDateTime date)
    {
        this.title = title;
        this.summary = summary;
        this.link = link;
        this.date = date;
    }

    public String getTitle()
    {
        return title;
    }

    public String getSummary()
    {
        return summary;
    }

    public String getLink()
    {
        return link;
    }

    public ZonedDateTime getDate()
    {
        return date;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public void setSummary(String summary)
    {
        this.summary = summary;
    }

    public void setLink(String link)
    {
        this.link = link;
    }

    public void setDate(ZonedDateTime date)
    {
        this.date = date;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }
        Article article = (Article) o;
        return title.equals(article.title) && summary.equals(article.summary) && link.equals(article.link) && date.equals(article.date);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(title, summary, link, date);
    }

    @Override
    public String toString()
    {
        return "Article{" +
                "title='" + title + '\'' +
                ", summary='" + summary + '\'' +
                ", link='" + link + '\'' +
                ", date=" + date +
                '}';
    }
}
