package dev.ivank.trchatbotdemo.common.mapper;

import org.modelmapper.Condition;
import org.modelmapper.Conditions;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.Provider;
import org.modelmapper.TypeMap;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.DestinationSetter;
import org.modelmapper.spi.SourceGetter;

import java.util.function.Function;

public class BaseMapper<FROM, TO> {
    private final ModelMapper mapper;
    private final Class<FROM> fromClass;
    private final Class<TO> toClass;

    public BaseMapper(Class<FROM> fromClass, Class<TO> toClass) {
        this.mapper = new ModelMapper();
        this.fromClass = fromClass;
        this.toClass = toClass;
    }

    public TO convert(FROM from) {
        return map(from);
    }

    public TO convert(FROM from, Provider<TO> defaultTo) {
        getTypeMap().setProvider(defaultTo);
        return map(from);
    }

    public <ARG_TYPE> TO convert(FROM from, DestinationSetter<TO, ARG_TYPE> toSkip) {
        addSkips(toSkip);
        return map(from);
    }

    public <ARG_TYPE> BaseMapper<FROM, TO> addSkips(DestinationSetter<TO, ARG_TYPE> toSkip) {
        TypeMap<FROM, TO> properties = getTypeMap();
        properties.addMappings(mapper -> mapper.skip(toSkip));
        return this;
    }

    public <ARG_FROM_TYPE, ARG_TO_TYPE> TO convert(FROM from, Converter<ARG_FROM_TYPE, ARG_TO_TYPE> converter, SourceGetter<FROM> getter, DestinationSetter<TO, ARG_TO_TYPE> setter) {
        addFieldConverter(converter, getter, setter);
        return map(from);
    }

    public <ARG_FROM_TYPE, ARG_TO_TYPE> BaseMapper<FROM, TO> addFieldConverter(Converter<ARG_FROM_TYPE, ARG_TO_TYPE> converter, SourceGetter<FROM> getter, DestinationSetter<TO, ARG_TO_TYPE> setter) {
        addFieldConditionWithConverter(c -> true, getter, setter, converter);
        return this;
    }

    public <ARG_FROM_TYPE, ARG_TO_TYPE> TO convert(FROM from, Condition<ARG_FROM_TYPE, ARG_TO_TYPE> condition, SourceGetter<FROM> getter, DestinationSetter<TO, ARG_TO_TYPE> setter) {
        addFieldCondition(condition, getter, setter);
        return map(from);
    }

    public <ARG_FROM_TYPE, ARG_TO_TYPE> BaseMapper<FROM, TO> addFieldCondition(Condition<ARG_FROM_TYPE, ARG_TO_TYPE> condition, SourceGetter<FROM> getter, DestinationSetter<TO, ARG_TO_TYPE> setter) {
        getTypeMap().addMappings(mapper -> mapper.when(condition).map(getter, setter));
        return this;
    }

    public <ARG_FROM_TYPE, ARG_TO_TYPE> BaseMapper<FROM, TO> addFieldConditionWithConverter(Condition<ARG_FROM_TYPE, ARG_TO_TYPE> condition, SourceGetter<FROM> getter, DestinationSetter<TO, ARG_TO_TYPE> setter, Converter<ARG_FROM_TYPE, ARG_TO_TYPE> converter) {
        getTypeMap().addMappings(mapper -> mapper.when(condition).using(converter).map(getter, setter));
        return this;
    }

    public <ARG_TYPE> BaseMapper<FROM, TO> addFieldProvider(Function<FROM, ARG_TYPE> provider, SourceGetter<FROM> getter, DestinationSetter<TO, ARG_TYPE> setter) {
        Converter<FROM, ARG_TYPE> converter = ctx -> provider.apply(ctx.getSource());
        getTypeMap().addMappings(mapper -> mapper.using(converter).map(getter, setter));
        return this;
    }

    public <ARG_TO_TYPE> BaseMapper<FROM, TO> addMapping(SourceGetter<FROM> getter, DestinationSetter<TO, ARG_TO_TYPE> setter) {
        getTypeMap().addMapping(getter, setter);
        return this;
    }

    private TypeMap<FROM, TO> getTypeMap() {
        TypeMap<FROM, TO> typeMap = mapper.getTypeMap(fromClass, toClass);
        if (typeMap != null) {
            return typeMap;
        }
        return mapper.createTypeMap(fromClass, toClass);
    }

    public TO map(FROM from, TO to) {
        mapper.map(from, to);
        return to;
    }

    private TO map(FROM from) {
        return mapper.map(from, toClass);
    }

    public BaseMapper<FROM, TO> nonNull() {
        mapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
        return this;
    }

    public BaseMapper<FROM, TO> strict() {
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        mapper.getConfiguration().setAmbiguityIgnored(true);
        return this;
    }

    public <ARG_FROM_TYPE, ARG_TO_TYPE> BaseMapper<FROM, TO> addCondition(Condition<ARG_FROM_TYPE, ARG_TO_TYPE> condition) {
        mapper.getConfiguration().setPropertyCondition(condition);
        return this;
    }

    public static <TYPE> TYPE merge(TYPE from, TYPE to) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
        mapper.map(from, to);
        return to;
    }
}
