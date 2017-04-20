package top10YVR.model;

/**
 * Created by jacobhsu on 2017-04-12.
 */
public enum Filter {
    FITNESS ("Fitness"),
    FOOD("Food"),
    ACTIVITY("Activity"),
    SCENERY("Scenery"),
    SHOP("Shop"),
    ARCHITECTURE("Architecture");

    private String displayName;

    Filter(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }


}