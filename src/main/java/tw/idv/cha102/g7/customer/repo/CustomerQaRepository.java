package tw.idv.cha102.g7.customer.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tw.idv.cha102.g7.customer.entity.CustomerQa;

import java.util.List;
@Repository
public interface CustomerQaRepository extends JpaRepository<CustomerQa, Integer> {

    @Query(value = "SELECT * FROM qa WHERE qa_state = ?1", nativeQuery = true)
    public List<CustomerQa> findByQaState(Integer qaState);

}
