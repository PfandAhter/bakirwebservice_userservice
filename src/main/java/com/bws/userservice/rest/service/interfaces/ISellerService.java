package com.bws.userservice.rest.service.interfaces;

import com.bws.userservice.api.request.BaseRequest;
import com.bws.userservice.api.request.SellerAddRequest;
import com.bws.userservice.api.response.BaseResponse;
import com.bws.userservice.api.response.SellerGetResponse;
import com.bws.userservice.exception.AccessDeniedException;
import com.bws.userservice.exception.CreateFailedException;
import com.bws.userservice.exception.NotFoundException;

public interface ISellerService {
    SellerGetResponse sellerGetResponse(String sellers, BaseRequest baseRequest) throws NotFoundException;

    BaseResponse registerSeller(SellerAddRequest request) throws CreateFailedException;

    String extractSellerName(BaseRequest request) throws NotFoundException;

    BaseResponse activateSellerByAdmin(String sellerId, BaseRequest baseRequest) throws AccessDeniedException, NotFoundException;

}
