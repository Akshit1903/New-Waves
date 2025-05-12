package com.akshit.utils;

import com.akshit.exceptions.ErrorCode;
import com.akshit.exceptions.NewWavesException;
import java.lang.reflect.Field;

public class Utils {

    public static final String REPORT_TO_TEAM_MESSAGE = "Kindly report this to the concerned team";

    public static <S, T> T map(S source,
                               Class<T> targetClass) {
        if (source == null) {
            return null;
        }
        try {
            T target = targetClass.getDeclaredConstructor()
                    .newInstance();

            Field[] sourceFields = source.getClass()
                    .getDeclaredFields();
            Field[] targetFields = targetClass.getDeclaredFields();

            for (Field sourceField : sourceFields) {
                sourceField.setAccessible(true);
                for (Field targetField : targetFields) {
                    if (targetField.getName()
                            .equals(sourceField.getName()) && targetField.getType()
                            .equals(sourceField.getType())) {

                        targetField.setAccessible(true);
                        targetField.set(target, sourceField.get(source));
                    }
                }
            }
            return target;
        } catch (Exception e) {
            final String errorMessage = String.format("Failed to map objects from %s to %s: %s", source.getClass(),
                    targetClass, e.getMessage());
            throw NewWavesException.of(ErrorCode.MAPPING_ERROR, errorMessage);
        }
    }
}
