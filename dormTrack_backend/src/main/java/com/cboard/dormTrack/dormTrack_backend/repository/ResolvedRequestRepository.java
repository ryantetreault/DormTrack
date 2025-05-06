package com.cboard.dormTrack.dormTrack_backend.repository;

import com.cboard.dormTrack.dormTrack_backend.model.ResolvedRequest;
import com.cboard.dormTrack.dormTrack_common.dto.ResolvedRequestDto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import java.util.List;

public interface ResolvedRequestRepository extends Repository<ResolvedRequest, Integer> {

    @Query(value = "SELECT * FROM resolved_maintenance_requests", nativeQuery = true)
    List<ResolvedRequestDto> findAllResolvedRequests();
}