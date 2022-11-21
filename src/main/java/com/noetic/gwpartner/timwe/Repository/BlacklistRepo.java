package com.noetic.gwpartner.timwe.Repository;
import com.noetic.gwpartner.timwe.Entity.blacklist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BlacklistRepo extends JpaRepository<blacklist,Long> {

    @Query(value = "SELECT * FROM tbl_blacklist where msisdn = '\"+ msisdn +\"' and status=1",nativeQuery = true)
    public blacklist checkBlackList(@Param("msisdn") String msisdn);
}
