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
@Table(name = "park_place")
public class ParkPlace {

    @ApiModelProperty(hidden = true)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String prkplceNo;
    private String prkplceNm;
    private String prkplceSe;
    private String prkplceType;
    private String rdnmadr;
    private String lnmadr;
    private int prkcmprt;
    private int feedingSe;
    private String enforceSe;
    private String operDay;
    private String weekdayOperOpenHhmm;
    private String weekdayOperColseHhmm;
    private String satOperOperOpenHhmm;
    private String satOperCloseHhmm;
    private String holidayOperOpenHhmm;
    private String holidayCloseOpenHhmm;
    private String parkingchrgeInfo;
    private int basicTime;
    private int basicCharge;
    private String addUnitTime;
    private int addUnitCharge;
    private String dayCmmtktAdjTime;
    private int dayCmmtkt;
    private int monthCmmtkt;
    private String metpay;
    private String spcmnt;
    private String institutionNm;
    private String phoneNumber;
    private long latitude;
    private long longitude;
    private String referenceDate;
    private String insttCode;
    private String insttNm;

}
