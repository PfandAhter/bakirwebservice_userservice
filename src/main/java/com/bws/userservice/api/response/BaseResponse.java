package com.bws.userservice.api.response;

import com.bws.userservice.model.constants.Constants;
import com.bws.userservice.model.constants.ResponseStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonPropertyOrder({
        "Status",
        "ErrorName",
        "Desription"
})
public class BaseResponse {

    @JsonProperty("Status")
    private String status = ResponseStatus.SUCCESS;

    @JsonProperty("ErrorName")
    private String errorCode = Constants.SUCCESS;

    @JsonProperty("Desription")
    private String errorDescription = Constants.SUCCESS;
}