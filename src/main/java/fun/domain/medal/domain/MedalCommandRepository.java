package fun.domain.medal.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MedalCommandRepository extends JpaRepository<Medal, Long> {

    @Query("""
                select m
                from Medal m
                where m.medalType in :medalTypes
            """)
    List<Medal> findByMedalTypes(@Param("medalType") final List<MedalType> medalTypes);
}
