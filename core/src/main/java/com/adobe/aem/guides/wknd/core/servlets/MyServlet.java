package com.adobe.aem.guides.wknd.core.servlets;


import com.adobe.aem.guides.wknd.core.services.MyService;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletResourceTypes;
import org.eclipse.jetty.server.ResponseWriter;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
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

    @Reference
    public MyService myService; //Here we will refer the interface(not impl class)
    public static final Logger LOGGER = LoggerFactory.getLogger(MyServlet.class);

    @Override
    protected void doPost(final SlingHttpServletRequest req,
                          final SlingHttpServletResponse res) throws ServletException, IOException{
        LOGGER.info("Servlet Code Started!");
        myService.printLogger();
    }
}

/*Create a POST API(provide basic auth as aem username and password) in postman to hit and test this servlet
http://localhost:4502/content/wknd/us/en.myServlet.json

Check the response in logger file



-----PostScript: Used Service in this Servlet-----
SERVICES FLOW:
BUILD TIME:
We have written our interface and implementation class(MyService) and build this code:
It will be part of an AEM bundle - Which will get deployed in AEM application
Bundle follows its lifecycle:
It goes to installed state, it will try to resolve all dependencies

It will analyse that MyServiceImpl is dependent on MyService - it will create relationship(Memory mapping)

RUNTIME:
When we trigger or call our servlet
It comes into Servlet class and finds the @Reference(Which means that some service has been referenced)
Since this reference is of Interface(not Impl class)
It goes to OSGI memory to check the relationship/mapping of this Interface and finds corresponding impl class for it
And it will create an Object for that impl class(not interface object)
And that object will be injected by the OSGI framework into "myService" variable which we have defined


WHAT IF?
There are two implementations of the same Interface, which one will it refer?

 */
