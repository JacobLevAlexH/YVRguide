package top10YVR.ui;

import top10YVR.model.*;

import javax.swing.*;

/**
 * Created by jacobhsu on 2017-04-12.
 */
public class ServiceCheckBox extends JCheckBox {
    private Filter filter;

    // EFFECTS: constructs check box for given filter
    public ServiceCheckBox(Filter filter) {
        super(filter.getDisplayName());
        this.filter = filter;
    }

    public Filter getService() {
        return filter;
    }
}