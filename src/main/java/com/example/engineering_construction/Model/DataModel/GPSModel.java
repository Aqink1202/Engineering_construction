package com.example.engineering_construction.Model.DataModel;

import lombok.Data;

@Data
public class GPSModel {
    private Double lat;
    private Double lon;

    public GPSModel() {
    }

    public GPSModel(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    @Override
    public String toString() {
        return "{" + lat + "," + lon + "}";
    }
}
