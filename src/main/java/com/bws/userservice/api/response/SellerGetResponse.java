package com.bws.userservice.api.response;

import com.bws.userservice.model.dto.SellerDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class SellerGetResponse {

    private List<SellerDTO> sellerDTOList;
}