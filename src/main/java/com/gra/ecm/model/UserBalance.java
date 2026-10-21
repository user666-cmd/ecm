package com.gra.ecm.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class UserBalance {
    private Integer id;
    private Integer userId;
    private BigDecimal balance;
    private BigDecimal frozenAmount;
}