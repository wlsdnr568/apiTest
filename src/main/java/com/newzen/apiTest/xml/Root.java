package com.newzen.apiTest.xml;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
 
@XmlRootElement(name = "root")
public class Root {
 
    private String result;
    
    public Root(){
        
    }
 
    public Root(String result) {
        super();
        this.result = result;
    }
 
    @XmlElement
    public String getResult() {
        return result;
    }
 
    public void setResult(String result) {
        this.result = result;
    }
 
    @Override
    public String toString() {
        return "Root [result=" + result + "]";
    }
    
}

