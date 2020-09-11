package com.demo.restapi.model.publicApi;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Map;

@Getter
@Setter
@XmlRootElement(name = "response")
public class CarWashResponse {

    private Map<String, String> header;

    private CarWashItems body;

}
