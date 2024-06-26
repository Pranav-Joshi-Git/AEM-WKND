package com.adobe.aem.guides.wknd.core.services.impl;

import com.adobe.aem.guides.wknd.core.services.MyService;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(
        service = MyService.class,
        immediate = true,
        property = {"country=in"}
)
public class MyServiceImpl implements MyService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyServiceImpl.class);

    public String printLogger() {
        return "Test Successful! country=in";
    }
}
/*
Declarative Services
In service we are mentioning which service(interface) it implements
immediate = true => Denotes that immediately after deployment,
object will be created for this class

And to use this service in other Servlets,
we will be using @Reference to use
it is used in myServlet

 */
