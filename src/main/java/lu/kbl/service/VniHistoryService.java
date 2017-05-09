package lu.kbl.service;

import lu.kbl.domain.VniHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing VniHistory.
 */
public interface VniHistoryService {

    /**
     * Save a vniHistory.
     *
     * @param vniHistory the entity to save
     * @return the persisted entity
     */
    VniHistory save(VniHistory vniHistory);

    /**
     *  Get all the vniHistories.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<VniHistory> findAll(Pageable pageable);

    /**
     * Get all the vniHistories for a fund.
     * @param fundId id of the fund
     */
    List<VniHistory> findAll(Long fundId);

    /**
     * Get the last vni for a fund.
     * @param fundId id of the fund
     * @return the entity
     */
    public VniHistory findLasVniForAFund(Long fundId);

    /**
     *  Get the "id" vniHistory.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    VniHistory findOne(Long id);

    /**
     *  Delete the "id" vniHistory.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
