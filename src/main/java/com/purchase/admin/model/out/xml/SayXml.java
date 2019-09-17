package com.purchase.admin.model.out.xml;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class SayXml {
    @XmlAttribute
    private String voice = "alice";
    @XmlAttribute
    private String language = "ru-RU";
    @XmlValue
    private String value;
}
