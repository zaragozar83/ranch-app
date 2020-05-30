package com.riac.teapcount.ranchapp.service;

import com.riac.teapcount.ranchapp.domain.model.Ranch;

import java.util.List;

public interface RanchService {

    public Ranch getRanchById(int id);
    public List<Ranch> getRanches();
    public Ranch getRanchByName(String name);
    public List<Ranch> getRanchesByCity(String city);
    public Ranch addRanch(Ranch ranch);
}
