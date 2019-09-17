package com.purchase.admin.model.out.xml;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class GatherXml {
    @XmlAttribute
    private String input = "speech";
    @XmlAttribute
    private String timeout = "5";
    @XmlAttribute
    private String language = "ru-RU";
    @XmlAttribute
    private String action;
}
