package com.adobe.aem.guides.wknd.core.servlets;

import com.adobe.aem.guides.wknd.core.beans.ArticleListDataBean;
import com.adobe.aem.guides.wknd.core.models.ArticleListModel;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletResourceTypes;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.List;

//Using this to send data - it can convert any java beans into JSON
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;


@Component(service = {Servlet.class}, immediate = true)
@SlingServletResourceTypes(
        resourceTypes = "sling/servlet/default",
        selectors = "getArticleList",
        extensions = "json",
        methods = HttpConstants.METHOD_POST)
public class HeadlessServlet extends SlingAllMethodsServlet {
    public static final Logger LOGGER = LoggerFactory.getLogger(MyServlet.class);
    //Don't hardcode in realtime scenario
    private static final String RESOURCE_PATH = "/content/wknd/us/en/jcr:content/root/container/container/articlelist";

    @Override
    protected void doPost(final SlingHttpServletRequest request,
                          final SlingHttpServletResponse response) throws ServletException, IOException {
        LOGGER.info("Servlet Code Started!");
        ResourceResolver resourceResolver = request.getResourceResolver();
        Resource resource = resourceResolver.getResource(RESOURCE_PATH);

        assert resource != null;
        ArticleListModel articleListModel = resource.adaptTo(ArticleListModel.class);
        assert articleListModel != null;
        List<ArticleListDataBean> articleListDataBeanList = articleListModel.getArticleListDataBeanArrayList();

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(articleListDataBeanList);

        response.setContentType("application/json");
        response.getWriter().write(json);


    }
}
/*
The idea of headless servlet is to keep frontend independent of backend code

Data will be passed as JSON to frontend and, frontend can render it is as per their need
independent on technology used.

By just doing resource.adaptTo()
It will initialize all the things about articleList component
The same flow which comes into picture while loading the page.
Content Path => Identifying components to render => Identifying scripts to execute
=> Loading Model if called from scripts => Executing init() methods, beans etc.


Hit below POST URL in Postman by providing aem username and password in basic auth
http://localhost:4502/content/wknd/us/en.getArticleList.json

getArticleList - is selector of servlet

Output:
[
    {
        "path": "/content/wknd/us/en/Articles/rice-plantation",
        "title": "Rice Plantation",
        "description": "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."
    },
    {
        "path": "/content/wknd/us/en/Articles/family-picnic",
        "title": "Family Picnic",
        "description": "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."
    },
    {
        "path": "/content/wknd/us/en/Articles/flower-garden",
        "title": "Flower Garden",
        "description": "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."
    }
]
*/