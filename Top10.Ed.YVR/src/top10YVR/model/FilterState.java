package top10YVR.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by jacobhsu on 2017-04-12.
 */
public class FilterState {
    private Set<Filter> selected;
    private Guide guide;
    private boolean isAnySelected;     // true for 'any', false for 'all'

    // EFFECTS: constructs selection state with empty set of selected services and 'any' service selected
    public FilterState(Guide guide) {
        selected = new HashSet<Filter>();
        this.guide = guide;
        isAnySelected = true;
    }

    // MODIFIES: this
    // EFFECTS: select filters  with 'any' of the selected services
    public void setSelectAny() {
        isAnySelected = true;
    }

    // MODIFIES: this
    // EFFECTS: select filters with 'all' of the selected services
    public void setSelectAll() {
        isAnySelected = false;
    }

    // MODIFIES: this
    // EFFECTS: add filter to selected services
    public void selectFilter(Filter filter) {
        selected.add(filter);
    }

    // MODIFIES: this
    // EFFECTS: remove filter from selected services
    public void deselectFilter(Filter filter) {
        selected.remove(filter);
    }

    // MODIFIES: this
    // EFFECTS: set selected services to given set of services
    public void setSelectedFilter(Set<Filter> selected) {
        this.selected = selected;
    }

    // EFFECTS: return set of resources corresponding to current selection;
    // if no services are currently selected, set of all resources is returned, otherwise:
    //    - if 'any' requested, return filters offering any of the selected services
    //    - if 'all' requested, return filters offering all of the selected services
    public Set<Attraction> getAttractionsWithSelectedFilters() {
        Set<Attraction> attractionSet = new HashSet<Attraction>();
        List<Attraction> attractionList;

        if (selected.isEmpty()) {
            attractionList = guide.getAttractions();
            for (Attraction at : attractionList) {
                attractionSet.add(at);
            }
        } else if (isAnySelected) {
            attractionSet = guide.getAttractionsWithAnyFilterInSet(selected);
        }
        else {
            attractionSet = guide.getAttractionsWithAllFiltersInSet(selected);
        }
        return attractionSet;
    }

}
