package com.noetic.gwpartner.timwe.Repository;

import com.noetic.gwpartner.timwe.Entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusRepo extends JpaRepository<Status,Long> {

    @Query(value = "SELECT * FROM tbl_status where id = + status",nativeQuery = true)
    Status select(int status);
}
