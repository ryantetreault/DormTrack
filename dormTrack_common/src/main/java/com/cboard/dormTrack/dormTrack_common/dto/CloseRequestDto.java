package com.cboard.dormTrack.dormTrack_common.dto;

public class CloseRequestDto {
    private int requestId;

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public String getResolverName() {
        return resolverName;
    }

    public void setResolverName(String resolverName) {
        this.resolverName = resolverName;
    }

    private String resolverName;

    public CloseRequestDto(int requestId, String resolverName) {
        this.requestId = requestId;
        this.resolverName = resolverName;
    }

    public CloseRequestDto() {}
}
