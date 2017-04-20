package top10YVR.model;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by jacobhsu on 2017-04-12.
 */
public class Attraction {
    private String name;
    private AttractionInfo attractionInfo;
    private Set<Filter> filters;

    // EFFECTS: constructs resource with given name and contact information
    public Attraction(String name, AttractionInfo attractionInfo) {
        filters = new HashSet<>();
        this.name=name ;
        this.attractionInfo = attractionInfo;

    }

    public String getName() {
        return name;
    }

    public AttractionInfo getAttractionInfo() {
        return attractionInfo;
    }

    // EFFECTS: returns true if this resource offers the given filter
    public boolean isFiltered(Filter filter) {
        if (filters.contains(filter)) {
            return true;
        } else
            return false;
    }

    // EFFECTS: returns true if this resource offers all filters in the requestedFilters set; false otherwise
    //          (returns true if given the empty set)
    public boolean allFilteredInSet(Set<Filter> requestedFilters) {
        return filters.containsAll(requestedFilters);
    }

    // EFFECTS: returns true if this resources offers any of the filters in requestedFilters set; false otherwise
    //          (returns false if given the empty set)
    public boolean anyFilteredInSet(Set<Filter> requestedFilters) {

        boolean a = false;
        for (Filter s : requestedFilters) {
            a = a || filters.contains(s);
        }
        return a;
    }

    // MODIFIES: this
    // EFFECTS: adds filter to this resource, if it's not already added
    public void addFilter(Filter filter) {
        filters.add (filter);
    }


    // MODIFIES: this
    // EFFECTS: removes filter from this resource
    public void removeFilter(Filter filter) {
        filters.remove (filter);
    }

    // EFFECTS: returns filters offered by this resource as unmodifiable set
    public Set<Filter> getServices() {
        return filters;
    }
}
