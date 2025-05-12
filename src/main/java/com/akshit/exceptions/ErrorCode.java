package com.akshit.exceptions;

import com.akshit.utils.Utils;
import jakarta.ws.rs.core.Response;
import lombok.Getter;

@Getter
public enum ErrorCode {
    OAUTH2_GOOGLE_USER_NOT_FOUND("User not authorized", "Kindly login again", Response.Status.FORBIDDEN),
    DAO_ERROR("Database error", Utils.REPORT_TO_TEAM_MESSAGE, Response.Status.INTERNAL_SERVER_ERROR),
    MAPPING_ERROR("Mapping Error", Utils.REPORT_TO_TEAM_MESSAGE, Response.Status.INTERNAL_SERVER_ERROR),
    FILTER_RESOURCE_INFO_METHOD_MISSING("Some error occured!", Utils.REPORT_TO_TEAM_MESSAGE,
            Response.Status.INTERNAL_SERVER_ERROR);

    private final String summary;
    private final String description;
    private final Response.Status code;

    ErrorCode(String summary,
              String description,
              Response.Status code) {
        this.summary = summary;
        this.description = description;
        this.code = code;
    }
}
