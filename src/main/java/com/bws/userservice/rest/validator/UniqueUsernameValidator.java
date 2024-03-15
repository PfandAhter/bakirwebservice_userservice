package com.bws.userservice.rest.validator;

import com.bws.userservice.model.entity.User;
import com.bws.userservice.repository.UserRepository;
import com.bws.userservice.rest.validator.annotations.UniqueUsername;
import jakarta.validation.ConstraintValidator;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {

    private final UserRepository userRepository;

    @Override
    public boolean isValid(String username, jakarta.validation.ConstraintValidatorContext constraintValidatorContext) {
        User user = userRepository.findByUsername(username);
        return user == null;
    }
}
