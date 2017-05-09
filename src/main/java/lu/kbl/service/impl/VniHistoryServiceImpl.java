package lu.kbl.service.impl;

import lu.kbl.service.VniHistoryService;
import lu.kbl.domain.VniHistory;
import lu.kbl.repository.VniHistoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service Implementation for managing VniHistory.
 */
@Service
@Transactional
public class VniHistoryServiceImpl implements VniHistoryService{

    private final Logger log = LoggerFactory.getLogger(VniHistoryServiceImpl.class);

    private final VniHistoryRepository vniHistoryRepository;

    public VniHistoryServiceImpl(VniHistoryRepository vniHistoryRepository) {
        this.vniHistoryRepository = vniHistoryRepository;
    }

    /**
     * Save a vniHistory.
     *
     * @param vniHistory the entity to save
     * @return the persisted entity
     */
    @Override
    public VniHistory save(VniHistory vniHistory) {
        log.debug("Request to save VniHistory : {}", vniHistory);
        VniHistory result = vniHistoryRepository.save(vniHistory);
        return result;
    }

    /**
     *  Get all the vniHistories.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<VniHistory> findAll(Pageable pageable) {
        log.debug("Request to get all VniHistories");
        Page<VniHistory> result = vniHistoryRepository.findAll(pageable);
        return result;
    }

    @Override
    public List<VniHistory> findAll(Long fundId) {
        final List<VniHistory> allByFundId = vniHistoryRepository.findAllByFund_Id(fundId);
        return allByFundId;
    }

    /**
     *  Get one vniHistory by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public VniHistory findOne(Long id) {
        log.debug("Request to get VniHistory : {}", id);
        VniHistory vniHistory = vniHistoryRepository.findOne(id);
        return vniHistory;
    }

    /**
     *  Delete the  vniHistory by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete VniHistory : {}", id);
        vniHistoryRepository.delete(id);
    }
}
