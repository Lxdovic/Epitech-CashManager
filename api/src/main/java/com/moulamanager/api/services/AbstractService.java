package com.moulamanager.api.services;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.FeatureDescriptor;
import java.util.stream.Stream;

public abstract class AbstractService<T> {

    // This method is used to copy properties from one object to another so that we can update the object without having to update every single field.
    protected String[] getNullPropertyNames(T object) {
        BeanWrapper beanWrapper = new BeanWrapperImpl(object);
        return Stream.of(beanWrapper.getPropertyDescriptors())
                .map(FeatureDescriptor::getName)
                .filter(propertyName -> beanWrapper.getPropertyValue(propertyName) == null)
                .toArray(String[]::new);
    }
}
