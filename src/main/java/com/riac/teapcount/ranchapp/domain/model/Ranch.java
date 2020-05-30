package com.riac.teapcount.ranchapp.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Ranch {

    private int id;

    @NotNull
    private String name;

    @NotNull
    private String city;
}
