package com.adobe.aem.guides.wknd.core.servlets;

import com.adobe.aem.guides.wknd.core.services.RecipeService;
import com.adobe.aem.guides.wknd.core.services.impl.RecipeServiceImpl;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletPaths;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component(
        service = {Servlet.class},
        property = {
                "sling.servlet.methods=" + HttpConstants.METHOD_GET,
                "sling.servlet.paths=" + "/bin/recipes"
        }
)
@SlingServletPaths(value = {"/bin/recipes"})
public class RecipeServlet extends HttpServlet {

    public static final Logger LOGGER = LoggerFactory.getLogger(RecipeServlet.class);
    @Reference
    private RecipeService recipeService;

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException{
        response.setContentType("application/json");
        response.getWriter().write(recipeService.getRecipes());
    }

    @Activate
    protected void activate(ComponentContext context){
        LOGGER.info("Servlet Started");
    }
}

/*
? Implementing this Servlet to call external APIs

* TO test this Servlet, hit below URL in Postman:
http://localhost:4502/bin/recipes
 */