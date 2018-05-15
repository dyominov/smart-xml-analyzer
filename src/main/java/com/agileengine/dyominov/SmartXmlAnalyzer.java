package com.agileengine.dyominov;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class SmartXmlAnalyzer {


    private static final String CHARSET_NAME = "utf8";
    //container for all attributes values
    private static final Set<String> allAttributesValues = new HashSet<>();
    //container for all possible elements
    private static final Set<Elements> allPossibleElements = new HashSet<>();


    public static void main(String[] args) throws IOException {

        File originFile;
        File otherFile;
        String targetElementId;

        if (args.length == 3) {
            originFile = new File(args[0]);
            otherFile = new File(args[1]);
            targetElementId = args[2];
        } else if (args.length == 2) {
            originFile = new File(args[0]);
            otherFile = new File(args[1]);
            targetElementId = "make-everything-ok-button";
        } else {
            throw new IllegalArgumentException();
        }

        //get document from origin file
        Document documentFromOriginFile = Jsoup.parse(originFile, CHARSET_NAME, originFile.getAbsolutePath());

        //get target element from origin file
        Element targetElement = documentFromOriginFile.getElementById(targetElementId);

        //get all attributes from target element
        Attributes attributes = targetElement.attributes();

        //add all attributes values to container
        attributes.forEach(i -> allAttributesValues.add(i.getValue()));


        //get document from other File
        Document documentFromOtherFile = Jsoup.parse(otherFile, CHARSET_NAME, otherFile.getAbsolutePath());

        //add all possible elements to container from other file which contains attributes target element
        attributes.forEach(a -> allPossibleElements
                .add(documentFromOtherFile.getElementsByAttributeValue(a.getKey(), a.getValue())));

        // get similar element from other file
        Element similarElement = getSimilarElement();

        // if similar element not found
        if (similarElement == null) {
            System.out.println("Element not found!");
            return;
        }

        //get path similar element
        String path = getPath(similarElement);

        //print path
        System.out.println(path);
    }

    /**
     * method find all relevant element from all containers and return most similar
     *
     * @return element or null
     */
    private static Element getSimilarElement() {
        Element similarElement = null;
        int count = 0;
        int maxCount = 0;

        for (Elements elements : allPossibleElements) {
            for (Element element : elements) {
                for (Attribute attribute : element.attributes()) {
                    if (allAttributesValues.contains(attribute.getValue())) {
                        count++;
                    }
                }
                if (count > maxCount) {
                    maxCount = count;
                    similarElement = element;
                }
                count = 0;
            }
        }

        return similarElement;
    }

    /**
     * @param element method return path to element
     * @return path
     */

    private static String getPath(Element element) {
        Elements parents = element.parents();
        Collections.reverse(parents);
        parents.add(element);
        return parents.stream()
                .map(el -> el.nodeName() + "[" + el.elementSiblingIndex() + "]")
                .collect(Collectors.joining(" > "));
    }
}
