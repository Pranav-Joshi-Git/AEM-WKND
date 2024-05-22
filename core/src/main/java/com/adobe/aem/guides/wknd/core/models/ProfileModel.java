package com.adobe.aem.guides.wknd.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;

import java.util.List;

@Model(adaptables = Resource.class)
public class ProfileModel {

    @ChildResource
    private List<String> hobbies;
    @ChildResource
    private List<Experience> previousExperience;

    public List<Experience> getPreviousExperience() {
        return previousExperience;
    }

    public List<String> getHobbies() {
        return hobbies;
    }
}