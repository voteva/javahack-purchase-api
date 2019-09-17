package com.purchase.admin.model.out.xml;

import lombok.Data;

import javax.xml.bind.annotation.*;

@Data
@XmlRootElement(name = "Response")
@XmlAccessorType(XmlAccessType.FIELD)
public class SpeechConfigXmlResponse {

    @XmlElement(name = "Say")
    private SayXml say;

    @XmlElement(name = "Gather")
    private GatherXml gather;
}
