package com.adobe.aem.guides.wknd.core.servlets;


import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletResourceTypes;
import org.eclipse.jetty.server.ResponseWriter;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;

@Component(service = {Servlet.class})   //Registering this piece of code as servlet in OSGI container
@SlingServletResourceTypes(             //Based on first three parameters sling framework identifies which servlet to execute
        resourceTypes = "sling/servlet/default",
        selectors = "myServlet",
        extensions = "json",
        methods = HttpConstants.METHOD_POST)
public class MyServlet extends SlingAllMethodsServlet {

    public static final Logger LOGGER = LoggerFactory.getLogger(MyServlet.class);

    @Override
    protected void doPost(final SlingHttpServletRequest req,
                          final SlingHttpServletResponse res) throws ServletException, IOException{
        LOGGER.info("Servlet Code Started!");
    }
}

/*Create a POST API(provide basic auth as aem username and password) in postman to hit and test this servlet
http://localhost:4502/content/wknd/us/en.myServlet.json

Check the response in logger file

 */
