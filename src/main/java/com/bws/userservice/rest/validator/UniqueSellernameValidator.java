package com.bws.userservice.rest.validator;

import com.bws.userservice.model.entity.Seller;
import com.bws.userservice.repository.SellerRepository;
import com.bws.userservice.rest.validator.annotations.UniqueSeller;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UniqueSellernameValidator implements ConstraintValidator<UniqueSeller,String> {
    private final SellerRepository sellerRepository;

    @Override
    public boolean isValid(String sellerName, ConstraintValidatorContext constraintValidatorContext) {
        Seller seller = sellerRepository.findSellerBySellerName(sellerName);
        return seller == null;
    }
}
