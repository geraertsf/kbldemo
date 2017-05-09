package lu.kbl.repository;

import lu.kbl.domain.VniHistory;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the VniHistory entity.
 */
@SuppressWarnings("unused")
public interface VniHistoryRepository extends JpaRepository<VniHistory,Long> {

    List<VniHistory> findAllByFund_Id(Long fundId);

}
