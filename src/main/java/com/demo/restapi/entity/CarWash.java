package com.demo.restapi.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "item")
@Table(name = "car_wash")
public class CarWash {

    @ApiModelProperty(hidden = true)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String carwshNm;
    private String ctprvnNm;
    private String signguNm;
    private String carwshInduty;
    private String rdnmadr;
    private String rprsntvNm;
    private String phoneNumber;
    private String qltwtrPrmisnNo;
    private double latitude;
    private double longitude;
    private double hardness;
    private String referenceDate;
    private String insttCode;

}
