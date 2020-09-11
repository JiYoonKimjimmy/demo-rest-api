package com.demo.restapi.model.publicApi;

import com.demo.restapi.entity.CarWash;
import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Getter
@Setter
@XmlRootElement(name = "body")
public class CarWashItems {

    private int pageNo;
    private int totalCount;

    private List<CarWash> items;

    @XmlElementWrapper(name = "items")
    @XmlElement(name = "item")
    public List<CarWash> getItems() {
        return items;
    }

}
