package com.example.demo;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderAdapter;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

@RestController
@RequestMapping(path = "xxe")
public class XXEControler {

    @GetMapping("/remote-entity")
    public String getExternalDtd1() {
        System.out.println("Accessed external entity!");
        return "some entity";
    }

    @GetMapping("/XMLReader")
    public String xmlreaderadapterCompliant() throws IOException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        XMLReader reader = null;
        try {
            reader = factory.newSAXParser().getXMLReader();
        } catch (SAXException | ParserConfigurationException e) {
            e.printStackTrace();
        }
        var resourceLoader = new DefaultResourceLoader();
        Resource resource = resourceLoader.getResource("with-system-entity.xml");
        try {
            reader.parse(resource.getURI().toString());
        } catch (SAXException e) {
            return "Parsing failed: " + e;
        }
        return "External entity was accessed.";
    }
}