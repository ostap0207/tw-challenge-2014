package wise.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import wise.models.*;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long>{
	
	@Query("SELECT a FROM Account a WHERE a.phone=:phone")
	public Account findByPhone(@Param("phone") String phone);
}
