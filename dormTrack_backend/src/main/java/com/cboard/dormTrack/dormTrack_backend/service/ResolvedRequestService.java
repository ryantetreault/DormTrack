package com.cboard.dormTrack.dormTrack_backend.service;

import com.cboard.dormTrack.dormTrack_common.dto.ResolvedRequestDto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResolvedRequestService {

    private final JdbcTemplate jdbcTemplate;

    public ResolvedRequestService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ResolvedRequestDto> getAllResolvedRequests() {
        String sql = "SELECT * FROM resolved_maintenance_requests";

        RowMapper<ResolvedRequestDto> mapper = (rs, rowNum) -> {
            ResolvedRequestDto dto = new ResolvedRequestDto();
            dto.setRequestId(rs.getInt("request_id"));
            dto.setStudentName(rs.getString("student_name"));
            dto.setIssueDescription(rs.getString("issue_description"));
            dto.setResolvedBy(rs.getString("resolved_by"));
            dto.setResolutionDate(rs.getDate("resolution_date").toLocalDate());
            return dto;
        };

        return jdbcTemplate.query(sql, mapper);
    }
}