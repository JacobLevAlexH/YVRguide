package top10YVR.ui;

import top10YVR.model.Filter;
import top10YVR.model.FilterState;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by jacobhsu on 2017-04-12.
 */
public class ControlPanel extends JPanel {
    private static final int GAP = 10;  // vertical spacing between components in pixels
    private FilterState filterState;
    private ISelectionListener selectionListener;
    private List<ServiceCheckBox> checkBoxes;

    // EFFECTS: constructs panel for displaying user controls
    public ControlPanel(FilterState filterState, ISelectionListener selectionListener) {
        this.filterState = filterState;
        this.selectionListener = selectionListener;
        checkBoxes = new ArrayList<>();
        Box toolHolder = Box.createVerticalBox();
        addCheckBoxes(toolHolder);
        toolHolder.add(Box.createVerticalStrut(GAP));
        addRadioButtons(toolHolder);
        toolHolder.add(Box.createVerticalStrut(GAP));
        addClearBtn(toolHolder);
        add(toolHolder);

    }

    // MODIFIES: this, toolHolder
    // EFFECTS: add one check box for each service to toolHolder and to list of check boxes
    private void addCheckBoxes(Box toolHolder) {
        Box checkBoxHolder = Box.createVerticalBox();
        checkBoxHolder.setAlignmentX(Box.LEFT_ALIGNMENT);

        checkBoxHolder.add(new JLabel("Filter Services..."));
        checkBoxHolder.add(Box.createVerticalStrut(GAP));
        for(Filter next : Filter.values()) {
            ServiceCheckBox checkBox = new ServiceCheckBox(next);
            checkBox.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    Filter filterStateChanged = ((ServiceCheckBox) e.getSource()).getService();

                    if (e.getStateChange() == ItemEvent.SELECTED)
                        filterState.selectFilter(filterStateChanged);
                    else
                        filterState.deselectFilter(filterStateChanged);

                    selectionListener.update();
                }
            });
            checkBoxes.add(checkBox);
            checkBoxHolder.add(checkBox);
        }

        toolHolder.add(checkBoxHolder);
    }

    // MODIFIES: toolHolder
    // EFFECTS: adds radio buttons (all and any) to toolHolder
    private void addRadioButtons(Box toolHolder) {
        Box radioBtnHolder = Box.createHorizontalBox();
        radioBtnHolder.setAlignmentX(Box.LEFT_ALIGNMENT);

        JRadioButton any = new JRadioButton("any filter", true);
        any.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filterState.setSelectAny();
                selectionListener.update();
            }
        });

        JRadioButton all = new JRadioButton("all filter", false);
        all.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filterState.setSelectAll();
                selectionListener.update();
            }
        });

        radioBtnHolder.add(any);
        radioBtnHolder.add(all);

        ButtonGroup radioBtnGroup = new ButtonGroup();
        radioBtnGroup.add(any);
        radioBtnGroup.add(all);

        toolHolder.add(radioBtnHolder);
    }

    // MODIFIES: toolHolder
    // EFFECTS: adds 'clear' button to toolHolder
    private void addClearBtn(Box toolHolder) {
        Box buttonHolder = Box.createHorizontalBox();
        buttonHolder.setAlignmentX(Box.LEFT_ALIGNMENT);
        buttonHolder.add(buildClearBtn());

        toolHolder.add(buttonHolder);
    }

    // EFFECTS: returns 'clear' button
    private JButton buildClearBtn() {
        JButton clearBtn = new JButton("Clear");
        clearBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearCheckBoxes();
                filterState.setSelectedFilter(new HashSet<Filter>());
            }

            private void clearCheckBoxes() {
                for(ServiceCheckBox next : checkBoxes)
                    next.setSelected(false);
            }
        });

        return clearBtn;
    }
}
