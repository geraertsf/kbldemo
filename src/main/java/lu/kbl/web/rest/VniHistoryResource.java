package lu.kbl.web.rest;

import com.codahale.metrics.annotation.Timed;
import lu.kbl.domain.VniHistory;
import lu.kbl.service.VniHistoryService;
import lu.kbl.web.rest.util.HeaderUtil;
import lu.kbl.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing VniHistory.
 */
@RestController
@RequestMapping("/api")
public class VniHistoryResource {

    private final Logger log = LoggerFactory.getLogger(VniHistoryResource.class);

    private static final String ENTITY_NAME = "vniHistory";

    private final VniHistoryService vniHistoryService;

    public VniHistoryResource(VniHistoryService vniHistoryService) {
        this.vniHistoryService = vniHistoryService;
    }

    /**
     * POST  /vni-histories : Create a new vniHistory.
     *
     * @param vniHistory the vniHistory to create
     * @return the ResponseEntity with status 201 (Created) and with body the new vniHistory, or with status 400 (Bad Request) if the vniHistory has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/vni-histories")
    @Timed
    public ResponseEntity<VniHistory> createVniHistory(@RequestBody VniHistory vniHistory) throws URISyntaxException {
        log.debug("REST request to save VniHistory : {}", vniHistory);
        if (vniHistory.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new vniHistory cannot already have an ID")).body(null);
        }
        VniHistory result = vniHistoryService.save(vniHistory);
        return ResponseEntity.created(new URI("/api/vni-histories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /vni-histories : Updates an existing vniHistory.
     *
     * @param vniHistory the vniHistory to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated vniHistory,
     * or with status 400 (Bad Request) if the vniHistory is not valid,
     * or with status 500 (Internal Server Error) if the vniHistory couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/vni-histories")
    @Timed
    public ResponseEntity<VniHistory> updateVniHistory(@RequestBody VniHistory vniHistory) throws URISyntaxException {
        log.debug("REST request to update VniHistory : {}", vniHistory);
        if (vniHistory.getId() == null) {
            return createVniHistory(vniHistory);
        }
        VniHistory result = vniHistoryService.save(vniHistory);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, vniHistory.getId().toString()))
            .body(result);
    }

    /**
     * GET  /vni-histories : get all the vniHistories.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of vniHistories in body
     */
    @GetMapping("/vni-histories")
    @Timed
    public ResponseEntity<List<VniHistory>> getAllVniHistories(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of VniHistories");
        Page<VniHistory> page = vniHistoryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/vni-histories");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);

    }
    /**
     * GET  /vni-histories : get all the vniHistories.
     *
     * @param id the id of the fund that we want the vniHistory to retrieve
     * @return the ResponseEntity with status 200 (OK) and the list of vniHistories in body
     */
    @GetMapping("/vni-histories/fund/{id}")
    @Timed
    public ResponseEntity<List<VniHistory>> getAllVniHistoriesForAFund(@PathVariable Long id) {
        log.debug("REST request to get a page of VniHistories");
        final List<VniHistory> vniHistoryList = vniHistoryService.findAll(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(vniHistoryList));
    }

    /**
     * GET  /vni-histories/:id : get the "id" vniHistory.
     *
     * @param id the id of the vniHistory to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the vniHistory, or with status 404 (Not Found)
     */
    @GetMapping("/vni-histories/{id}")
    @Timed
    public ResponseEntity<VniHistory> getVniHistory(@PathVariable Long id) {
        log.debug("REST request to get VniHistory : {}", id);
        VniHistory vniHistory = vniHistoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(vniHistory));
    }

    /**
     * GET /vni-histories/last/fund/:id : get the "id" vniHistory.
     *
     * @param id the id of the vniHistory to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the vniHistory, or with status 404 (Not Found)
     */
    @GetMapping("/vni-histories/last/fund/{id}")
    @Timed
    public ResponseEntity<VniHistory> getLastVniHistory(@PathVariable Long id) {
        log.debug("REST request to get VniHistory : {}", id);
        VniHistory vniHistory = vniHistoryService.findLasVniForAFund(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(vniHistory));
    }

    /**
     * DELETE  /vni-histories/:id : delete the "id" vniHistory.
     *
     * @param id the id of the vniHistory to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/vni-histories/{id}")
    @Timed
    public ResponseEntity<Void> deleteVniHistory(@PathVariable Long id) {
        log.debug("REST request to delete VniHistory : {}", id);
        vniHistoryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
