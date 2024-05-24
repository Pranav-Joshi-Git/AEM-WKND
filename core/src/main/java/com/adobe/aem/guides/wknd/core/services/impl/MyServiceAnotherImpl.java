package com.adobe.aem.guides.wknd.core.services.impl;

import com.adobe.aem.guides.wknd.core.services.MyService;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(
        service = MyService.class,
        immediate = true,
        property = {"country=us"}
)
public class MyServiceAnotherImpl implements MyService{
    private static final Logger LOGGER = LoggerFactory.getLogger(MyServiceImpl.class);

    public String printLogger() {
        return "Test Success! country=us";
    }
}
