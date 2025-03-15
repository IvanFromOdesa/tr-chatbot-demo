package dev.ivank.trchatbotdemo.common;

import dev.ivank.trchatbotdemo.common.mapper.MappingContext;
import org.modelmapper.Condition;
import org.modelmapper.Converter;
import org.modelmapper.Provider;
import org.modelmapper.spi.DestinationSetter;
import org.modelmapper.spi.SourceGetter;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class EntityDtoMappingService<ENTITY extends IdAware<?>, DTO> {
    protected final MappingContext<ENTITY, DTO> mappingContext;

    @Autowired
    public EntityDtoMappingService(MappingContext<ENTITY, DTO> mappingContext) {
        this.mappingContext = mappingContext;
    }

    public ENTITY toEntity(DTO dto) {
        return mappingContext.getDtoToEntity().convert(dto);
    }

    public ENTITY toEntity(DTO dto, Provider<ENTITY> provider) {
        return mappingContext.getDtoToEntity().convert(dto, provider);
    }

    public <ARG_TO_TYPE> ENTITY toEntity(DTO dto, DestinationSetter<ENTITY, ARG_TO_TYPE> toSkip) {
        return mappingContext.getDtoToEntity().convert(dto, toSkip);
    }

    public <ARG_FROM_TYPE, ARG_TO_TYPE> ENTITY toEntity(DTO dto, Converter<ARG_FROM_TYPE, ARG_TO_TYPE> converter, SourceGetter<DTO> getter, DestinationSetter<ENTITY, ARG_TO_TYPE> setter) {
        return mappingContext.getDtoToEntity().convert(dto, converter, getter, setter);
    }

    public <ARG_FROM_TYPE, ARG_TO_TYPE> ENTITY toEntity(DTO dto, Condition<ARG_FROM_TYPE, ARG_TO_TYPE> condition, SourceGetter<DTO> getter, DestinationSetter<ENTITY, ARG_TO_TYPE> setter) {
        return mappingContext.getDtoToEntity().convert(dto, condition, getter, setter);
    }

    public DTO toDto(ENTITY entity) {
        return mappingContext.getEntityToDto().convert(entity);
    }

    public DTO toDto(ENTITY entity, Provider<DTO> provider) {
        return mappingContext.getEntityToDto().convert(entity, provider);
    }

    public <ARG_TO_TYPE> DTO toDto(ENTITY entity, DestinationSetter<DTO, ARG_TO_TYPE> toSkip) {
        return mappingContext.getEntityToDto().convert(entity, toSkip);
    }

    public <ARG_FROM_TYPE, ARG_TO_TYPE> DTO toDto(ENTITY entity, Converter<ARG_FROM_TYPE, ARG_TO_TYPE> converter, SourceGetter<ENTITY> getter, DestinationSetter<DTO, ARG_TO_TYPE> setter) {
        return mappingContext.getEntityToDto().convert(entity, converter, getter, setter);
    }

    public <ARG_FROM_TYPE, ARG_TO_TYPE> DTO toDto(ENTITY entity, Condition<ARG_FROM_TYPE, ARG_TO_TYPE> condition, SourceGetter<ENTITY> getter, DestinationSetter<DTO, ARG_TO_TYPE> setter) {
        return mappingContext.getEntityToDto().convert(entity, condition, getter, setter);
    }
}

