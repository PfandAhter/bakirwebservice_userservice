package com.bws.userservice.api.request;

import com.bws.userservice.model.constants.ErrorCodeConstants;
import com.bws.userservice.rest.validator.annotations.UniqueSeller;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SellerAddRequest extends UserAddRequest{

    @UniqueSeller(message = ErrorCodeConstants.SELLER_NAME_IN_USE)
    @NotEmpty(message = ErrorCodeConstants.SELLER_NAME_NOT_NULL)
    private String sellerName;

    @NotEmpty(message = "STATUS NOT NULL")
    private String status;

}
