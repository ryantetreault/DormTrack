module com.cboard.dormTrack.dormTrack_common {
    requires lombok;
    requires com.fasterxml.jackson.annotation;
    requires jakarta.persistence;
    exports com.cboard.dormTrack.dormTrack_common.dto;

    opens com.cboard.dormTrack.dormTrack_common.dto to com.fasterxml.jackson.databind;
}