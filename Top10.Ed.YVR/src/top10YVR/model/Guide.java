package top10YVR.model;

import java.util.*;

/**
 * Created by jacobhsu on 2017-04-12.
 */
public class Guide {
    private List<Attraction> attractions;

    // EFFECTS: constructs empty resource registry
    public Guide() {
        attractions = new LinkedList<>();
    }

    // MODIFIES: this
    // EFFECTS: add attraction to registry, maintains attractions in the order added to registry
    public void addAttraction(Attraction attraction) {
        attractions.add (attraction);
    }

    // EFFECTS: returns attractions in registry as unmodifiable list (in order that they were added
    // to registry)
    public List<Attraction> getAttractions() {
        return attractions;
    }

    // EFFECTS: returns set of attractions in registry that offer the given filter
    public Set<Attraction> getAttractionWithFilter (Filter filter) {
        Set<Attraction> se = new HashSet<Attraction>();
        for (Attraction at : attractions) {
            if (at.isFiltered(filter)) {
                se.add (at);
            }
        }
        return se;
    }

    // EFFECTS: returns set of attractions in registry that offer all the services in requestedFilters set
    public Set<Attraction> getAttractionsWithAllFiltersInSet(Set<Filter> requestedFilters) {
        Set<Attraction> se = new HashSet <Attraction>();
        for (Attraction at : attractions) {
            if (at.allFilteredInSet(requestedFilters)) {
                se.add (at);
            }
        }
        return se;
    }

    // EFFECTS: returns set of attractions in registry that off any of the services in requestedFilters set
    public Set<Attraction> getAttractionsWithAnyFilterInSet(Set<Filter> requestedFilters) {
        Set<Attraction> se = new HashSet <Attraction>();
        for (Attraction at : attractions) {
            if (at.anyFilteredInSet(requestedFilters)) {
                se.add (at);
            }
        }
        return se;
    }


}