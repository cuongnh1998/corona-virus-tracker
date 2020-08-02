package com.ninhhoangcuong.coronavirustracker.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class LocationStatsDeathsGlobal {
    private String state;
    private String country;
    private int latestTotalCase;
    private int diffFromPrevDay;
}
