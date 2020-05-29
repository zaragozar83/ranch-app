package com.riac.teapcount.ranchapp.service;

import com.riac.teapcount.ranchapp.domain.model.Ranch;
import com.riac.teapcount.ranchapp.exception.RanchException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@NoArgsConstructor
@AllArgsConstructor
public class RanchServiceImpl implements RanchService{

    private List<Ranch> ranches = Arrays.asList(
            Ranch.builder().id(1).name("Riac").city("Pittsburgh").build(),
            Ranch.builder().id(2).name("Bates").city("California").build(),
            Ranch.builder().id(3).name("Pa").city("Michigan").build(),
            Ranch.builder().id(4).name("Roswelt").city("Pittsburgh").build(),
            Ranch.builder().id(5).name("Rasson").city("New York").build(),
            Ranch.builder().id(6).name("Miou").city("Houston").build()
    );

    @Override
    public Ranch getRanchById(int id) {
        return ranches.stream()
                .filter(r -> r.getId() == id)
                .findFirst()
                .orElseThrow(() -> new RanchException(
                        String.format("The Ranch: %d is not found", id))
                );
    }

    @Override
    public List<Ranch> getRanches() {
        return ranches;
    }

    @Override
    public Ranch getRanchByName(String name) {
        return ranches.stream()
                .filter(ranch -> ranch.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new RanchException(
                        String.format("The Ranch: %s is not found")
                ));
    }

    @Override
    public List<Ranch> getRanchesByCity(String city) {
        return ranches.stream()
                .filter(ranch -> ranch.getCity().equals(city))
                .collect(Collectors.toList());
    }
}
