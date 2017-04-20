package top10YVR.ui;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.viewer.GeoPosition;
import top10YVR.model.Attraction;
import top10YVR.model.GeoPoint;
import top10YVR.model.FilterState;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;
import java.net.URL;

/**
 * Created by jacobhsu on 2017-04-12.
 */
public class InfoBox extends JScrollPane implements MouseListener {
    private static final double X_SCALE = 0.7; // proportion of map window to be used for info window
    private static final double Y_SCALE = 0.3;
    private static final int WIDTH = (int) (Top10VYRApp.MAP_WIDTH * X_SCALE);
    private static final int HEIGHT = (int) (Top10VYRApp.MAP_HEIGHT * Y_SCALE);
    private JEditorPane textPane;
    private FilterState filterState;
    private JXMapViewer mapViewer;

    // EFFECTS: constructs info window, not visible to user
    public InfoBox(FilterState filterState, JXMapViewer mapViewer) {
        super();
        this.filterState = filterState;
        this.mapViewer = mapViewer;

        setPreferredSize(new Dimension(WIDTH, HEIGHT));

        buildTextPane();

        getViewport().setView(textPane);
        setVisible(false);
    }

    // MODIFIES: this
    // EFFECTS: builds pane in which resource info will be displayed
    private void buildTextPane() {
        textPane = new JEditorPane("text/html", "");
        textPane.setEditable(false);
        textPane.setMargin(new Insets(4, 10, 4, 10));

        textPane.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setVisible(false);
            }
        });

        textPane.addHyperlinkListener(new HyperlinkListener() {
            @Override
            public void hyperlinkUpdate(HyperlinkEvent e) {
                if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                    openURLInBrowser(e.getURL());
                }
            }
        });
    }

    //---------------------------------------------------------------------
    // Implementation of MouseListener
    @Override
    public void mouseClicked(MouseEvent e) {
        Attraction selected = getResourceAtPoint(e.getPoint());

        if (selected != null)
            displayResourceInfo(selected);
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
    //---------------------------------------------------------------------

    // EFFECTS: returns resource at given point, or null if no resource sufficiently close to point
    private Attraction getResourceAtPoint(Point point) {
        for (Attraction next : filterState.getAttractionsWithSelectedFilters()) {
            GeoPoint locn = next.getAttractionInfo().getGeoLocation();
            GeoPosition resourceLocn = new GeoPosition(locn.getLatitude(), locn.getLongitude());
            Point2D resourceLocnInViewer = mapViewer.convertGeoPositionToPoint(resourceLocn);
            if (isHit(point, resourceLocnInViewer))
                return next;
        }

        return null;
    }

    // EFFECTS: returns true if point is sufficiently close to resourceLocnInViewer
    private boolean isHit(Point point, Point2D resourceLocnInViewer) {
        return point.y < resourceLocnInViewer.getY() && resourceLocnInViewer.getY() - point.y < 25
                && Math.abs(point.x - resourceLocnInViewer.getX()) < 10;
    }

    // MODIFIES: this
    // EFFECTS: display information for given resource in text pane
    private void displayResourceInfo(Attraction selected) {
        HTMLAttractionFormatter formatter = new HTMLAttractionFormatter(selected);
        textPane.setText(formatter.format());
        setVisible(true);
        getParent().validate();
        getParent().repaint();
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                getVerticalScrollBar().setValue(0);
            }
        });
    }

    // EFFECTS: open url in default web browser; display error message when operation cannot be completed
    private void openURLInBrowser(URL url) {
        Desktop desktop = Desktop.getDesktop();

        try {
            desktop.browse(url.toURI());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this.getParent(),
                    "Unable to open web page", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
