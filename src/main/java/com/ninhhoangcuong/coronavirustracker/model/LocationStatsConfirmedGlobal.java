package com.ninhhoangcuong.coronavirustracker.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class LocationStatsConfirmedGlobal {
    private String state;
    private String country;
    private int latestTotalCase;
    private int diffFromPrevDay;
}
