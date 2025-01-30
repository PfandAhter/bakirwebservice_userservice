package com.bws.userservice.api.response;

import com.bws.userservice.model.constants.Constants;
import com.bws.userservice.model.constants.ResponseStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({
        "Status",
        "Error",
        "Description"
})
public class BaseResponse {

    @JsonProperty("Status")
    private String status = ResponseStatus.SUCCESS;

    @JsonProperty("Error")
    private String error = Constants.SUCCESS;

    @JsonProperty("Description")
    private String description = Constants.SUCCESS;
}