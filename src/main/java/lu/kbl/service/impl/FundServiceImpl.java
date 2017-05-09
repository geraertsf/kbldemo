package lu.kbl.service.impl;

import lu.kbl.service.FundService;
import lu.kbl.domain.Fund;
import lu.kbl.repository.FundRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service Implementation for managing Fund.
 */
@Service
@Transactional
public class FundServiceImpl implements FundService{

    private final Logger log = LoggerFactory.getLogger(FundServiceImpl.class);

    private final FundRepository fundRepository;

    public FundServiceImpl(FundRepository fundRepository) {
        this.fundRepository = fundRepository;
    }

    /**
     * Save a fund.
     *
     * @param fund the entity to save
     * @return the persisted entity
     */
    @Override
    public Fund save(Fund fund) {
        log.debug("Request to save Fund : {}", fund);
        Fund result = fundRepository.save(fund);
        return result;
    }

    /**
     *  Get all the funds.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Fund> findAll(Pageable pageable) {
        log.debug("Request to get all Funds");
        Page<Fund> result = fundRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get all the funds.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Fund> findAllWithCountries(Pageable pageable) {
        log.debug("Request to get all Funds");
        Page<Fund> result = fundRepository.findAllWithEagerRelationships(pageable);



        return result;
    }



    /**
     *  Get one fund by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Fund findOne(Long id) {
        log.debug("Request to get Fund : {}", id);
        Fund fund = fundRepository.findOneWithEagerRelationships(id);
        return fund;
    }

    /**
     *  Delete the  fund by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Fund : {}", id);
        fundRepository.delete(id);
    }
}
