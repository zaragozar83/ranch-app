package com.riac.teapcount.ranchapp.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Ranch {

    private int id;

    @NotNull(message = "The name must not be null")
    @NotEmpty(message = "The name Ranch must to be not empty")
    private String name;

    @NotNull(message = "The city must not be null")
    @NotEmpty(message = "The city for Ranch must to be not empty")
    private String city;
}
