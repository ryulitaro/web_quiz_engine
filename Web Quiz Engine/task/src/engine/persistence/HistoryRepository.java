package engine.persistence;

import engine.businesslayer.History;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface HistoryRepository extends PagingAndSortingRepository<History, Long> {
    History findByUsernameAndAndId(String username, Long id);

    @Query(value = "SELECT h from History h where h.username= :username")
    Page<History> findAllHistoryWithPagination(Pageable pageable, @Param("username") String username);
}