package com.bws.userservice.api.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class AddPhotoRequest {
    private MultipartFile photo;
}
