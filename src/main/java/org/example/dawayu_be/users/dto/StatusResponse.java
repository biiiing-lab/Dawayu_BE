package org.example.dawayu_be.users.dto;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
public class StatusResponse {
    private int status;
    private String TokenOrMessage;
}
