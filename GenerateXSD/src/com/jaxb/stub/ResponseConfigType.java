//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.08.23 at 08:57:57 AM IST 
//


package com.jaxb.stub;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for responseConfigType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="responseConfigType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="responseName" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="matchers" type="{}matchersType"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "responseConfigType", propOrder = {
    "responseName",
    "matchers"
})
public class ResponseConfigType {

    @XmlElement(required = true)
    protected String responseName;
    @XmlElement(required = true)
    protected MatchersType matchers;

    /**
     * Gets the value of the responseName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResponseName() {
        return responseName;
    }

    /**
     * Sets the value of the responseName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResponseName(String value) {
        this.responseName = value;
    }

    /**
     * Gets the value of the matchers property.
     * 
     * @return
     *     possible object is
     *     {@link MatchersType }
     *     
     */
    public MatchersType getMatchers() {
        return matchers;
    }

    /**
     * Sets the value of the matchers property.
     * 
     * @param value
     *     allowed object is
     *     {@link MatchersType }
     *     
     */
    public void setMatchers(MatchersType value) {
        this.matchers = value;
    }

}
