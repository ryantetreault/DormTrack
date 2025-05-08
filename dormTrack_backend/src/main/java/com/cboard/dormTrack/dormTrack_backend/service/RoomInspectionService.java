package com.cboard.dormTrack.dormTrack_backend.service;

import com.cboard.dormTrack.dormTrack_common.dto.RoomInspectionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomInspectionService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<RoomInspectionDto> getAllInspectionsFromView() {
        String sql = "SELECT * FROM room_inspection_view";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            RoomInspectionDto dto = new RoomInspectionDto();
            dto.setInspectionId(rs.getInt("inspectionId"));
            dto.setRaName(rs.getString("raName"));
            dto.setRoomLabel(rs.getString("roomLabel"));
            dto.setInspectionDate(rs.getDate("inspectionDate").toLocalDate());
            dto.setResult(rs.getString("result"));
            dto.setNotes(rs.getString("notes"));
            return dto;
        });
    }
}