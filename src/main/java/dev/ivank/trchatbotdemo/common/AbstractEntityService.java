package dev.ivank.trchatbotdemo.common;

import dev.ivank.trchatbotdemo.common.mapper.BaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public abstract class AbstractEntityService<ENTITY extends IdAware<ID>, ID extends Serializable, DAO extends JpaRepository<ENTITY, ID>> {
    @Autowired
    protected DAO dao;

    public ENTITY save(ENTITY e) {
        return dao.save(e);
    }

    public List<ENTITY> saveAll(List<ENTITY> entities) {
        return dao.saveAll(entities);
    }

    @Transactional
    public ENTITY update(ID id, ENTITY e) {
        return update(e, () -> dao.findById(id));
    }

    @Transactional
    public ENTITY update(ENTITY e, Supplier<Optional<ENTITY>> findByCallback) {
        Optional<ENTITY> by = findByCallback.get();
        if (by.isPresent()) {
            return save(merge(e, by.get()));
        }
        return save(e);
    }

    @Transactional
    public void updateAll(Collection<ENTITY> entities) {
        updateAll(entities, e -> getById(e.getId()), ArrayList::new);
    }

    protected void updateAll(Collection<ENTITY> entities, Function<ENTITY, Optional<ENTITY>> findByCallback) {
        updateAll(entities, findByCallback, ArrayList::new);
    }

    protected <C extends Collection<ENTITY>> void updateAll(Collection<ENTITY> entities, Supplier<C> collectionSupplier) {
        updateAll(entities, e -> getById(e.getId()), collectionSupplier);
    }

    /**
     * @apiNote WARNING: This will retrieve all entities from the database that have the same IDs
     * as those in the provided collection, allowing for merging to be performed. Use carefully.
     * @param entities collection of entities to be saved at once
     * @param findByCallback callback function that takes entity from collection and returns an optional of existing db entity
     * @param collectionSupplier a supplier that provides a transform collection
     */
    protected <C extends Collection<ENTITY>> void updateAll(Collection<ENTITY> entities, Function<ENTITY, Optional<ENTITY>> findByCallback, Supplier<C> collectionSupplier) {
        dao.saveAll(entities.stream().map(e -> findByCallback.apply(e).map(en -> merge(e, en)).orElse(e)).collect(Collectors.toCollection(collectionSupplier)));
    }

    /**
     * Standard merging mechanism
     * @param e
     * @param byId
     * @return
     */
    protected ENTITY merge(ENTITY e, ENTITY byId) {
        return BaseMapper.merge(e, byId);
    }

    @Transactional
    public void delete(ID id) {
        dao.findById(id).ifPresentOrElse(dao::delete, () -> {
            throw getNotFoundException(id);
        });
    }

    public Optional<ENTITY> getById(ID id) {
        return dao.findById(id);
    }

    public ENTITY getByIdOrThrow(ID id) {
        return getById(id).orElseThrow(() -> getNotFoundException(id));
    }

    protected EntityNotFoundException getNotFoundException(Serializable id) {
        return new EntityNotFoundException("error.notFound", id);
    }

    public Page<ENTITY> getAll(int page, int size) {
        return dao.findAll(getPageable(page, size));
    }

    public Page<ENTITY> getAll(int page, int size, String direction, String... sortBys) {
        return dao.findAll(getPageable(page, size, direction, sortBys));
    }

    public Page<ENTITY> getAll(int page, int size, Example<ENTITY> entityExample, String direction, String... sortBys) {
        return dao.findAll(entityExample, getPageable(page, size, direction, sortBys));
    }

    protected static PageRequest getPageable(int page, int size) {
        return PageRequest.of(page, size);
    }

    protected static PageRequest getPageable(int page, int size, String direction, String[] sortBys) {
        return PageRequest.of(page, size, Sort.by(getOrders(direction, sortBys)));
    }

    private static List<Sort.Order> getOrders(String direction, String[] sortBys) {
        List<Sort.Order> orders = new ArrayList<>();
        Sort.Direction dir = Sort.Direction.fromString(direction);
        for (String sortBy : sortBys) {
            orders.add(new Sort.Order(dir, sortBy));
        }
        return orders;
    }
}