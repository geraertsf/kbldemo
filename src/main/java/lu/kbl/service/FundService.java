package lu.kbl.service;

import lu.kbl.domain.Fund;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing Fund.
 */
public interface FundService {

    /**
     * Save a fund.
     *
     * @param fund the entity to save
     * @return the persisted entity
     */
    Fund save(Fund fund);

    /**
     *  Get all the funds.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Fund> findAll(Pageable pageable);

    /**
     *  Get the "id" fund.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Fund findOne(Long id);

    /**
     *  Delete the "id" fund.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
