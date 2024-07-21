package org.example.dawayu_be.users.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class StatusResponse {
    private int status;
    private String TokenOrMessage;
}
