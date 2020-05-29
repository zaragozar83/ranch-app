package com.riac.teapcount.ranchapp.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
@Builder
@AllArgsConstructor
public class ExceptionRanchResponse {

    @Builder.Default
    private Date timestamp = new Date();
    private final String message;
    private final String details;

}
