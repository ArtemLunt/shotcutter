package com.shotcutter.rest.shared;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Optional;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
public class ConverterService {
    Logger logger = LoggerFactory.getLogger(ConverterService.class);

    Map<Class, Map<Class, Converter>> convertersTree;

    ConverterService(Set<Converter> converters) {
        convertersTree = new HashMap<>();
        converters.forEach(converter -> {
            ParameterizedType actualConverterType = (ParameterizedType)(converter.getClass().getGenericInterfaces()[0]);
            Type[] convertionTypes = actualConverterType.getActualTypeArguments();

            convertersTree.putIfAbsent(((Class) convertionTypes[0]), new HashMap<>());
            convertersTree.get(((Class) convertionTypes[0])).put((Class) convertionTypes[1], converter);
        });

        logger.info("Converters map successfully initialized");
    }

    public <Original, Target> Optional<Target> convertTo(Original item, Class<Target> targetClass) {
        return getConverter(item.getClass(), targetClass)
                .map(converter -> ((Converter<Original, Target>)converter).convert(item));
    }

    <Original, Target> Optional<Converter<Original, Target>> getConverter(
            Class<Original> originalClass,
            Class<Target> targetClass
    ) {
        return Optional.of(
                Optional.of(convertersTree.get(originalClass))
                        .map(convertersBranch -> convertersBranch.getOrDefault(targetClass, null))
                        .get()
        );
    }
}
