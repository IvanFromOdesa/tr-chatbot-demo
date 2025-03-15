package dev.ivank.trchatbotdemo.common.mapper;

import dev.ivank.trchatbotdemo.common.IdAware;
import org.modelmapper.Condition;
import org.modelmapper.Converter;
import org.modelmapper.spi.DestinationSetter;
import org.modelmapper.spi.SourceGetter;

import java.util.function.Function;

public class MappingContext<ENTITY extends IdAware<?>, DTO> {
    private final BaseMapper<ENTITY, DTO> entityToDto;
    private final BaseMapper<DTO, ENTITY> dtoToEntity;

    public MappingContext(Class<ENTITY> entityClass, Class<DTO> dtoClass) {
        entityToDto = new BaseMapper<>(entityClass, dtoClass);
        dtoToEntity = new BaseMapper<>(dtoClass, entityClass);
    }

    public Builder newBuilder() {
        return new Builder();
    }

    public class Builder {

        private Builder() {

        }

        public Builder useStrict() {
            getDtoToEntity().strict();
            getEntityToDto().strict();
            return this;
        }

        public Builder useNonNull() {
            getDtoToEntity().nonNull();
            getEntityToDto().nonNull();
            return this;
        }

        @SafeVarargs
        public final <ARG_TYPE> Builder addEntitySkips(DestinationSetter<ENTITY, ARG_TYPE>... skips) {
            for (DestinationSetter<ENTITY, ARG_TYPE> setter : skips) {
                getDtoToEntity().addSkips(setter);
            }
            return this;
        }

        @SafeVarargs
        public final <ARG_TYPE> Builder addDtoSkips(DestinationSetter<DTO, ARG_TYPE>... skips) {
            for (DestinationSetter<DTO, ARG_TYPE> setter : skips) {
                getEntityToDto().addSkips(setter);
            }
            return this;
        }

        public <ARG_TYPE> Builder addEntityMapping(SourceGetter<DTO> getter, DestinationSetter<ENTITY, ARG_TYPE> setter) {
            getDtoToEntity().addMapping(getter, setter);
            return this;
        }

        public <ARG_TYPE> Builder addDtoMapping(SourceGetter<ENTITY> getter, DestinationSetter<DTO, ARG_TYPE> setter) {
            getEntityToDto().addMapping(getter, setter);
            return this;
        }

        public <ARG_FROM_TYPE, ARG_TO_TYPE> Builder addMappingConditionOnEntity(Condition<ARG_FROM_TYPE, ARG_TO_TYPE> condition) {
            getDtoToEntity().addCondition(condition);
            return this;
        }

        public <ARG_FROM_TYPE, ARG_TO_TYPE> Builder addMappingConditionOnDto(Condition<ARG_FROM_TYPE, ARG_TO_TYPE> condition) {
            getEntityToDto().addCondition(condition);
            return this;
        }

        public <ARG_FROM_TYPE, ARG_TO_TYPE> Builder addMappingFieldConditionOnEntity(Condition<ARG_FROM_TYPE, ARG_TO_TYPE> condition, SourceGetter<DTO> getter, DestinationSetter<ENTITY, ARG_TO_TYPE> setter) {
            getDtoToEntity().addFieldCondition(condition, getter, setter);
            return this;
        }

        /**
         * Useful to override global field mapping conditions and apply a converter immediately
         */
        public <ARG_FROM_TYPE, ARG_TO_TYPE> Builder addMappingFieldConditionOnEntityWithConverter(Condition<ARG_FROM_TYPE, ARG_TO_TYPE> condition, SourceGetter<DTO> getter, DestinationSetter<ENTITY, ARG_TO_TYPE> setter, Converter<ARG_FROM_TYPE, ARG_TO_TYPE> converter) {
            getDtoToEntity().addFieldConditionWithConverter(condition, getter, setter, converter);
            return this;
        }

        public <ARG_FROM_TYPE, ARG_TO_TYPE> Builder addMappingFieldConditionOnDto(Condition<ARG_FROM_TYPE, ARG_TO_TYPE> condition, SourceGetter<ENTITY> getter, DestinationSetter<DTO, ARG_TO_TYPE> setter) {
            getEntityToDto().addFieldCondition(condition, getter, setter);
            return this;
        }

        public <ARG_FROM_TYPE, ARG_TO_TYPE> Builder addMappingFieldConditionOnDtoWithConverter(Condition<ARG_FROM_TYPE, ARG_TO_TYPE> condition, SourceGetter<ENTITY> getter, DestinationSetter<DTO, ARG_TO_TYPE> setter, Converter<ARG_FROM_TYPE, ARG_TO_TYPE> converter) {
            getEntityToDto().addFieldConditionWithConverter(condition, getter, setter, converter);
            return this;
        }

        /**
         * @apiNote Be careful as the compiler does not check for ENTITY type in getter.
         */
        public <ARG_TYPE> Builder addEntityFieldProvider(Function<DTO, ARG_TYPE> provider, SourceGetter<DTO> getter, DestinationSetter<ENTITY, ARG_TYPE> setter) {
            getDtoToEntity().addFieldProvider(provider, getter, setter);
            return this;
        }

        /**
         * @apiNote Be careful as the compiler does not check for ENTITY type in getter.
         */
        public <ARG_TYPE> Builder addDtoFieldProvider(Function<ENTITY, ARG_TYPE> provider, SourceGetter<ENTITY> getter, DestinationSetter<DTO, ARG_TYPE> setter) {
            getEntityToDto().addFieldProvider(provider, getter, setter);
            return this;
        }

        public <ARG_FROM_TYPE, ARG_TO_TYPE> Builder addFieldConverterOnEntity(Converter<ARG_FROM_TYPE, ARG_TO_TYPE> converter, SourceGetter<DTO> getter, DestinationSetter<ENTITY, ARG_TO_TYPE> setter) {
            getDtoToEntity().addFieldConverter(converter, getter, setter);
            return this;
        }

        public <ARG_FROM_TYPE, ARG_TO_TYPE> Builder addFieldConverterOnDto(Converter<ARG_FROM_TYPE, ARG_TO_TYPE> converter, SourceGetter<ENTITY> getter, DestinationSetter<DTO, ARG_TO_TYPE> setter) {
            getEntityToDto().addFieldConverter(converter, getter, setter);
            return this;
        }

        public MappingContext<ENTITY, DTO> build() {
            return MappingContext.this;
        }
    }

    public BaseMapper<ENTITY, DTO> getEntityToDto() {
        return entityToDto;
    }

    public BaseMapper<DTO, ENTITY> getDtoToEntity() {
        return dtoToEntity;
    }
}

