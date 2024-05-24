package com.adobe.aem.guides.wknd.core.models;


import com.adobe.aem.guides.wknd.core.beans.ArticleListDataBean;
import com.day.cq.wcm.api.Page;
import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Model(adaptables = Resource.class,
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ArticleListModel {

    private static final Logger LOGGER = LoggerFactory.getLogger(ArticleListModel.class);
    @Inject
    private String articleRootPath;
    List<ArticleListDataBean> articleListDataBeanArrayList = null;
    @Self
    Resource resource;

    public String getArticleRootPath(){
        return articleRootPath;
    }

    @PostConstruct
    protected void init(){
        ResourceResolver resourceResolver = resource.getResourceResolver();
        Session session = resourceResolver.adaptTo(Session.class);
        QueryBuilder builder = resourceResolver.adaptTo(QueryBuilder.class);

        Map<String, String> predicate = new HashMap<>();
        predicate.put("path", articleRootPath);
        predicate.put("type", "cq:Page");

        LOGGER.info("Predicate values :: {}", predicate);
        Query query = null;

        try {
            assert builder != null;
            query = builder.createQuery(PredicateGroup.create(predicate), session);
        }
        catch (Exception e){
            LOGGER.error("Error in query ::", e);
        }

        assert query != null;
        SearchResult searchResult = query.getResult();

        articleListDataBeanArrayList = new ArrayList<ArticleListDataBean>();

        for(Hit hit : searchResult.getHits()){

            ArticleListDataBean articleListDataBean = new ArticleListDataBean();
            String path = null;
            try{
                path = hit.getPath();
                Resource articleResource = resourceResolver.getResource(path);

                assert articleResource != null;
                Page articlePage = articleResource.adaptTo(Page.class); //This can get access to jcr:content of page

                assert articlePage != null;
                String title = articlePage.getTitle();

                String description = articlePage.getDescription();

                articleListDataBean.setPath(path);
                articleListDataBean.setTitle(title);
                articleListDataBean.setDescription(description);

                LOGGER.info("\nPath: {} \nTitle: {} \nDescription: {}", path, title, description);

                articleListDataBeanArrayList.add(articleListDataBean);


            }
            catch (RepositoryException e){
                throw new RuntimeException(e);
            }
        }
    }

    public List<ArticleListDataBean> getArticleListDataBeanArrayList() {
        return articleListDataBeanArrayList;
    }
}


/*
TODO: Refactor this code

Refer the link to refactor the code:
Ideally, we should create an interface and impl class for ArticleList Model where everything is handled


https://sankhamtech.com/index.php/courses/aem-backend-concepts/lessons/improving-aem-sling-model-with-service-layer-integration/
 */