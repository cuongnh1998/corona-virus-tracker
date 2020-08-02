package com.ninhhoangcuong.coronavirustracker.model;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Report {
    private String country;
    private List<LocationStats> locationStats;
    private Long totalCase;
    private Long newCase;
    private Long totalDeaths;
    private Long newDeaths;
    private Long totalRecovered;
    private Long newRecovered;
}
