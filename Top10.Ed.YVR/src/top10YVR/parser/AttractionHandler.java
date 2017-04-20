package top10YVR.parser;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import top10YVR.model.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by jacobhsu on 2017-04-12.
 */
public class AttractionHandler extends DefaultHandler {
    private Guide registry;
    private StringBuilder accumulator;
    private String name;
    private String address;
    private String latString;
    private String lonString;
    private double lat;
    private double lon;
    private String webAddressString;
    private URL webAddress;
    private String phoneNumber;
    private Filter filter;
    private Set<Filter> servicesList;
    private AttractionInfo attractionInfo;
    private Attraction attraction;
    private GeoPoint geoPoint;

    // EFFECTS: constructs attraction handler for XML parser
    public AttractionHandler(Guide registry) {
        this.registry = registry;
        accumulator = new StringBuilder();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
        if (qName.toLowerCase().equals("attractions")) {
            name = null;
            address = null;
            latString = "";
            lonString = "";
            lat = 0;
            lon = 0;
            webAddressString = "";
            webAddress = null;
            phoneNumber = null;
            filter = null;
            servicesList = new HashSet<>();
            attractionInfo = null;
            attraction = null;
            geoPoint = null;
        }
    }


    @Override
    public void endElement (String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
        if (qName.toLowerCase().equals("name")) {
            name = accumulator.toString().trim();
        } else if (qName.toLowerCase().equals("address")) {
            address = accumulator.toString().trim();
        } else if (qName.toLowerCase().equals("lat")) {
            latString = accumulator.toString().trim();
            lat = Double.parseDouble(latString);
        } else if (qName.toLowerCase().equals("lon")) {
            lonString = accumulator.toString().trim();
            lon = Double.parseDouble(lonString);
        } else if (qName.toLowerCase().equals("webaddress")) {
            webAddressString = accumulator.toString().trim();
            try {
                webAddress = new URL(webAddressString);
            } catch (MalformedURLException e) {
            }
        } else if (qName.toLowerCase().equals("location")) {
            geoPoint = new GeoPoint(lat, lon);
        } else if (qName.toLowerCase().equals("phone")) {
            phoneNumber = accumulator.toString().trim();
        } else if (qName.toLowerCase().equals("filter")) {
            if (accumulator.toString().toLowerCase().trim().equals("food")) {
                servicesList.add(Filter.FOOD);}
            else if (accumulator.toString().toLowerCase().trim().equals("activity")) {
                servicesList.add(Filter.ACTIVITY);
            } else if (accumulator.toString().toLowerCase().trim().equals("scenery")) {
                servicesList.add(Filter.SCENERY);
            } else if (accumulator.toString().toLowerCase().trim().equals("shop")) {
                servicesList.add(Filter.SHOP);
            } else if (accumulator.toString().toLowerCase().trim().equals("architecture")) {
                servicesList.add(Filter.ARCHITECTURE);
            } else if (accumulator.toString().toLowerCase().trim().equals("fitness")) {
                servicesList.add(Filter.FITNESS);
            }
        } else if (qName.toLowerCase().equals("attraction")) {
            if (name != null && address != null && geoPoint != null && webAddress != null && phoneNumber != null && servicesList.size() != 0) {
                attractionInfo = new AttractionInfo(address, geoPoint, webAddress, phoneNumber);
                attraction = new Attraction(name, attractionInfo);
                for (Filter s : servicesList) {
                    attraction.addFilter(s);
                }
                registry.addAttraction(attraction);
            }
        } else if (qName.toLowerCase().equals("attractions")) {
            if (registry.getAttractions().size() == 0) {
                throw new SAXException();
            }
        }

        accumulator.setLength(0);

    }

    @Override
    public void characters ( char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
        accumulator.append(ch, start, length);
    }
}