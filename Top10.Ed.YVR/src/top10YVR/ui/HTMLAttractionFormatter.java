package top10YVR.ui;

import top10YVR.model.AttractionInfo;
import top10YVR.model.Attraction;
import top10YVR.model.Filter;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jacobhsu on 2017-04-12.
 */
public class HTMLAttractionFormatter {
    private Attraction attraction;

    // EFFECTS: constructs HTML formatter for given attraction
    public HTMLAttractionFormatter(Attraction attraction) {
        this.attraction = attraction;
    }

    // EFFECTS: returns HTML representation of attraction as a string
    public String format() {
        String result = "";

        result += toHeader(attraction.getName());
        result += toUnorderedList("Contact information:", getContactInfoAsStringList());
        result += toUnorderedList("Services offered:", getServicesAsStringList());

        return result;
    }

    // EFFECTS: returns contact information as list of strings
    private List<String> getContactInfoAsStringList() {
        List<String> contactItems = new ArrayList<>();
        AttractionInfo attractionInfo = attraction.getAttractionInfo();
        contactItems.add(attractionInfo.getAddress());
        contactItems.add(attractionInfo.getPhoneNumber());
        contactItems.add(toAnchor(attractionInfo.getWebAddress()));

        return contactItems;
    }

    // EFFECTS: returns services associated with attraction as list of strings
    private List<String> getServicesAsStringList() {
        List<String> serviceNames = new ArrayList<>();

        for (Filter next : attraction.getServices()) {
            serviceNames.add(next.getDisplayName());
        }

        return serviceNames;
    }

    // EFFECTS: returns given title formatted as HTML header
    private String toHeader(String title) {
        return "<h2>" + title + "</h2>";
    }

    // EFFECTS: returns given title and list of strings as HTML unordered list
    private String toUnorderedList(String title, List<String> items) {
        String unorderedList = "";

        unorderedList += "<h3>" + title + "</h3>";
        unorderedList += "<ul>";

        for (String item : items) {
            unorderedList += toListItem(item);
        }

        unorderedList += "</ul>";

        return unorderedList;
    }

    // EFFECTS: returns item as HTML list item
    private String toListItem(String item) {
        return "<li>" + item + "</li>";
    }

    // EFFECTS: returns url as HTML anchor
    private String toAnchor(URL url) {
        String anchor = "";

        anchor += "<a href='";
        anchor += url.toString();
        anchor += "'>";
        anchor += url.toString();
        anchor += "</a>";

        return anchor;
    }
}