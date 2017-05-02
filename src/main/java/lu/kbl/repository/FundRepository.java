package lu.kbl.repository;

import lu.kbl.domain.Fund;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Fund entity.
 */
@SuppressWarnings("unused")
public interface FundRepository extends JpaRepository<Fund,Long> {

    @Query("select distinct fund from Fund fund left join fetch fund.countries")
    List<Fund> findAllWithEagerRelationships();

    @Query("select fund from Fund fund left join fetch fund.countries where fund.id =:id")
    Fund findOneWithEagerRelationships(@Param("id") Long id);

}
