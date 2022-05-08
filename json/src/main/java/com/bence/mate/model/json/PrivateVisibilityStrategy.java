package com.bence.mate.model.json;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import javax.json.bind.config.PropertyVisibilityStrategy;

/**
 * Enables private property visibility
 * and disables method visibility for JSON-B processings.
 */
public class PrivateVisibilityStrategy  implements PropertyVisibilityStrategy {

    @Override
    public boolean isVisible(Field field) {
        return true;
    }

    @Override
    public boolean isVisible(Method method) {
        return false;
    }
}