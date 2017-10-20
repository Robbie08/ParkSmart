package com.example.robert.parksmart.infrastructure;

import java.util.HashMap;

/**
 * Created by ortiz on 9/25/2017.
 *
 * Keep track if the user made any errors
 */

public class ServiceResponse {

    private HashMap<String,String> propertyErrors;

    public ServiceResponse(){
        propertyErrors = new HashMap<>();
    }

    public void setPropertyErrors(String property, String error){
        //keep track of the errors the user makes
        propertyErrors.put(property,error);
    }

    public String getPropertyError(String property){
        //will return all the errors the user has made
        return propertyErrors.get(property);
    }

    public boolean didSucceede(){
        //if the user has not made any errors
        return (propertyErrors.size() == 0);
    }
}
