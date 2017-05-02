package lu.kbl.web.rest;

import lu.kbl.KbldemoApp;

import lu.kbl.domain.VniHistory;
import lu.kbl.repository.VniHistoryRepository;
import lu.kbl.service.VniHistoryService;
import lu.kbl.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.math.BigDecimal;
import java.util.List;

import static lu.kbl.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the VniHistoryResource REST controller.
 *
 * @see VniHistoryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = KbldemoApp.class)
public class VniHistoryResourceIntTest {

    private static final ZonedDateTime DEFAULT_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final BigDecimal DEFAULT_VALUE = new BigDecimal(1);
    private static final BigDecimal UPDATED_VALUE = new BigDecimal(2);

    @Autowired
    private VniHistoryRepository vniHistoryRepository;

    @Autowired
    private VniHistoryService vniHistoryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restVniHistoryMockMvc;

    private VniHistory vniHistory;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        VniHistoryResource vniHistoryResource = new VniHistoryResource(vniHistoryService);
        this.restVniHistoryMockMvc = MockMvcBuilders.standaloneSetup(vniHistoryResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VniHistory createEntity(EntityManager em) {
        VniHistory vniHistory = new VniHistory()
            .date(DEFAULT_DATE)
            .value(DEFAULT_VALUE);
        return vniHistory;
    }

    @Before
    public void initTest() {
        vniHistory = createEntity(em);
    }

    @Test
    @Transactional
    public void createVniHistory() throws Exception {
        int databaseSizeBeforeCreate = vniHistoryRepository.findAll().size();

        // Create the VniHistory
        restVniHistoryMockMvc.perform(post("/api/vni-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vniHistory)))
            .andExpect(status().isCreated());

        // Validate the VniHistory in the database
        List<VniHistory> vniHistoryList = vniHistoryRepository.findAll();
        assertThat(vniHistoryList).hasSize(databaseSizeBeforeCreate + 1);
        VniHistory testVniHistory = vniHistoryList.get(vniHistoryList.size() - 1);
        assertThat(testVniHistory.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testVniHistory.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void createVniHistoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = vniHistoryRepository.findAll().size();

        // Create the VniHistory with an existing ID
        vniHistory.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVniHistoryMockMvc.perform(post("/api/vni-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vniHistory)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<VniHistory> vniHistoryList = vniHistoryRepository.findAll();
        assertThat(vniHistoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllVniHistories() throws Exception {
        // Initialize the database
        vniHistoryRepository.saveAndFlush(vniHistory);

        // Get all the vniHistoryList
        restVniHistoryMockMvc.perform(get("/api/vni-histories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vniHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(sameInstant(DEFAULT_DATE))))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.intValue())));
    }

    @Test
    @Transactional
    public void getVniHistory() throws Exception {
        // Initialize the database
        vniHistoryRepository.saveAndFlush(vniHistory);

        // Get the vniHistory
        restVniHistoryMockMvc.perform(get("/api/vni-histories/{id}", vniHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(vniHistory.getId().intValue()))
            .andExpect(jsonPath("$.date").value(sameInstant(DEFAULT_DATE)))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingVniHistory() throws Exception {
        // Get the vniHistory
        restVniHistoryMockMvc.perform(get("/api/vni-histories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVniHistory() throws Exception {
        // Initialize the database
        vniHistoryService.save(vniHistory);

        int databaseSizeBeforeUpdate = vniHistoryRepository.findAll().size();

        // Update the vniHistory
        VniHistory updatedVniHistory = vniHistoryRepository.findOne(vniHistory.getId());
        updatedVniHistory
            .date(UPDATED_DATE)
            .value(UPDATED_VALUE);

        restVniHistoryMockMvc.perform(put("/api/vni-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedVniHistory)))
            .andExpect(status().isOk());

        // Validate the VniHistory in the database
        List<VniHistory> vniHistoryList = vniHistoryRepository.findAll();
        assertThat(vniHistoryList).hasSize(databaseSizeBeforeUpdate);
        VniHistory testVniHistory = vniHistoryList.get(vniHistoryList.size() - 1);
        assertThat(testVniHistory.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testVniHistory.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingVniHistory() throws Exception {
        int databaseSizeBeforeUpdate = vniHistoryRepository.findAll().size();

        // Create the VniHistory

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restVniHistoryMockMvc.perform(put("/api/vni-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vniHistory)))
            .andExpect(status().isCreated());

        // Validate the VniHistory in the database
        List<VniHistory> vniHistoryList = vniHistoryRepository.findAll();
        assertThat(vniHistoryList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteVniHistory() throws Exception {
        // Initialize the database
        vniHistoryService.save(vniHistory);

        int databaseSizeBeforeDelete = vniHistoryRepository.findAll().size();

        // Get the vniHistory
        restVniHistoryMockMvc.perform(delete("/api/vni-histories/{id}", vniHistory.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<VniHistory> vniHistoryList = vniHistoryRepository.findAll();
        assertThat(vniHistoryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VniHistory.class);
    }
}
