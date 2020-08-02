package com.ninhhoangcuong.coronavirustracker.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"state","latestTotalCase","diffFromPrevDay"})
public class LocationStats {
    private String state;
    private String country;
    private int latestTotalCase;
    private int diffFromPrevDay;
}

