package com.ninhhoangcuong.coronavirustracker.model;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class Report {
   private int latestTotalCase;
   private int diffFromPrevDay;
}
