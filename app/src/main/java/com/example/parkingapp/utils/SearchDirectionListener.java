package com.example.parkingapp.utils;

import com.example.parkingapp.model.MapRoute;

import java.util.List;

public interface SearchDirectionListener {
    void onDirectionFinderStart();
    void onDirectionFinderSuccess(List<MapRoute> mapRoute);
}
