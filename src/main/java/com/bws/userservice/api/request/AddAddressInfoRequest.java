package com.bws.userservice.api.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddAddressInfoRequest extends BaseRequest{

    private String address;
    private String phone;
    private String district;
    private String cityName;
    private String postalCode;
    private String countryName;

}
