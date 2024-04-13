package com.example.dto;

import com.example.enums.ResponseType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateGroupResponseDTO {

    private int groupId;

    private ResponseType responseType;

    private String failureMessage;

}
