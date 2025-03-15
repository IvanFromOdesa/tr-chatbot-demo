package dev.ivank.trchatbotdemo.common;

import dev.ivank.trchatbotdemo.common.valid.BaseErrorMapException;
import dev.ivank.trchatbotdemo.common.valid.ErrorMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

public abstract class AbstractDtoClientService<ENTITY extends IdAware<?>, DTO, ENTITY_SERVICE extends AbstractEntityService<ENTITY, ?, ?>> {
    @Autowired
    protected ENTITY_SERVICE entityService;
    @Autowired
    protected EntityDtoMappingService<ENTITY, DTO> mappingService;

    public Page<DTO> getDtoPage(int page, int size) {
        validate(page, size);
        return getEntityPage(page, size).map(mappingService::toDto);
    }

    public Page<DTO> getDtoPage(int page, int size, String... sortBys) {
        validate(page, size);
        return getEntityPage(page, size, sortBys).map(mappingService::toDto);
    }

    protected Page<ENTITY> getEntityPage(int page, int size) {
        return entityService.getAll(page, size);
    }

    protected Page<ENTITY> getEntityPage(int page, int size, String[] sortBys) {
        return entityService.getAll(page, size, "asc", sortBys);
    }

    private void validate(int page, int size) {
        ErrorMap errorMap = new ErrorMap();
        if (page < 0) {
            errorMap.put("page", "error.page.number.negative");
        }
        if (size <= 0) {
            errorMap.put("size", "error.page.size.negative");
        }
        if (!errorMap.isEmpty()) {
            throw new BaseErrorMapException(errorMap);
        }
    }
}
