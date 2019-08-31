package com.trade.legalentities.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LegalEntity {
    private Integer id;
    private String name;
    private String incorporationDate;
    private String countryOfIncorporation;
    private Integer totalNumberOfShares;
    private List<ShareHolder> shareHolders;
} 