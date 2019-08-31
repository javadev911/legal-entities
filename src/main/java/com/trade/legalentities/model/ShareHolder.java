package com.trade.legalentities.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ShareHolder {
    private Integer numberOfShares;
    private String name;
}