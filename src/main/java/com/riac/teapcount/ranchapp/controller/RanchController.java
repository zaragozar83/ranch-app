package com.riac.teapcount.ranchapp.controller;

import com.riac.teapcount.ranchapp.domain.model.Ranch;
import com.riac.teapcount.ranchapp.service.RanchService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("ranches")
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class RanchController {

    private final RanchService ranchService;

    @GetMapping
    public List<Ranch> getRanches() {
        return ranchService.getRanches();
    }

    @GetMapping("/{ranchId}")
    public Ranch getRanchById(@PathVariable Integer ranchId) {

        return ranchService.getRanchById(ranchId);
    }

    @GetMapping("/{ranchName}/name")
    public Ranch getRanchByName(@PathVariable String ranchName) {

        return ranchService.getRanchByName(ranchName);
    }

    @GetMapping("/{cityName}/city")
    public List<Ranch> getRanchByCity(@PathVariable String cityName) {
        return ranchService.getRanchesByCity(cityName);
    }

}
