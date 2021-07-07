package com.shotcutter.library.converter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Slf4j
@Service
public class ConverterService {

    private final Map<Class, Map<Class, Converter>> convertersTree;

    ConverterService(Set<Converter> converters) {
        convertersTree = new HashMap<>();
        converters.forEach(converter -> {
            ParameterizedType actualConverterType = (ParameterizedType)(converter.getClass().getGenericInterfaces()[0]);
            Type[] conversionTypes = actualConverterType.getActualTypeArguments();

            convertersTree.putIfAbsent(((Class) conversionTypes[0]), new HashMap<>());
            convertersTree.get((conversionTypes[0])).put((Class) conversionTypes[1], converter);
        });

        log.info("Converters map successfully initialized");
    }

    public <Original, Target> Target convertTo(Original item,
                                               Class<Target> targetClass) {
        var converter = (Converter<Original, Target>) getConverter(item.getClass(), targetClass);
        return converter.convert(item);
    }

    public <Original, Target> Converter<Original, Target> getConverter(Class<Original> originalClass,
                                                                       Class<Target> targetClass) {
        return convertersTree.get(originalClass).getOrDefault(targetClass, null);
    }

}
