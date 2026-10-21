package com.gra.ecm.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Address {
    private Integer id;
    private Integer userId;
    private String receiverName;
    private String receiverPhone;
    private String province;
    private String city;
    private String district;
    private String detailedAddress;
    private Integer isDefault;
}