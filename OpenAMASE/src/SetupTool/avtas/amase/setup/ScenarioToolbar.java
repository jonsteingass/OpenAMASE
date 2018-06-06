// ===============================================================================
// Authors: AFRL/RQQD
// Organization: Air Force Research Laboratory, Aerospace Systems Directorate, Power and Control Division
// 
// Copyright (c) 2017 Government of the United State of America, as represented by
// the Secretary of the Air Force.  No copyright is claimed in the United States under
// Title 17, U.S. Code.  All Other Rights Reserved.
// ===============================================================================




package avtas.amase.setup;

import afrl.cmasi.AbstractZone;
import afrl.cmasi.AirVehicleConfiguration;
import afrl.cmasi.AreaSearchTask;
import afrl.cmasi.Circle;
import afrl.cmasi.KeepInZone;
import afrl.cmasi.KeepOutZone;
import afrl.cmasi.LineSearchTask;
import afrl.cmasi.LoiterTask;
import afrl.cmasi.PointSearchTask;
import afrl.cmasi.Polygon;
import afrl.cmasi.Rectangle;
import afrl.cmasi.EntityConfiguration;
import avtas.amase.AmasePlugin;
import avtas.app.AppEventManager;
import avtas.lmcp.LMCPObject;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JToolBar;
//added for teas project
import uxas.projects.teas.GPSDeniedZone;

/**
 * A toolbar that triggers the creation of objects on map layers that are interested.
 *
 * @author AFRL/RQQD
 */
public class ScenarioToolbar extends AmasePlugin {

    private AppEventManager eventManager = AppEventManager.getDefaultEventManager();
    ButtonGroup group = new ButtonGroup();
    

    @Override
    public void getToolbarItems(JToolBar toolbar) {

        toolbar.add(createButton("/resources/selectCursor.gif", "Cancel Creation", null));
        toolbar.add(createButton("/resources/uav.png", "Add a new UAV", new AirVehicleConfiguration()));
        toolbar.add(createButton("/resources/entity.png", "Add a new Entity", new EntityConfiguration()));

        toolbar.addSeparator();

        AreaSearchTask task = new AreaSearchTask();
        task.setSearchArea(new Polygon());
        toolbar.add(createButton("/resources/areasearch.png", "Add a Search Area Task", task));
        task = new AreaSearchTask();
        task.setSearchArea(new Rectangle());
        toolbar.add(createButton("/resources/square_areasearch.png", "Add a Rectangular Search Area Task", task));
        task = new AreaSearchTask();
        task.setSearchArea(new Circle());
        toolbar.add(createButton("/resources/circ_areasearch.png", "Add a Circular Search Area Task", task));

        toolbar.add(createButton("/resources/linesearch.png", "Add a Line Search Task", new LineSearchTask()));
        
        toolbar.add(createButton("/resources/pointsearch.png", "Add a Point Surveillance Task", new PointSearchTask()));
        
        toolbar.add(createButton("/resources/loiter_task.png", "Add a Loiter Task", new LoiterTask()));

        toolbar.addSeparator();

        AbstractZone z = new KeepInZone();
        z.setBoundary(new Polygon());
        toolbar.add(createButton("/resources/keepin.png", "Add a Keep-In Zone", z));
        z = new KeepInZone();
        z.setBoundary(new Rectangle());
        toolbar.add(createButton("/resources/square_keepin.png", "Add a Rectangular Keep-In Zone", z));
        z = new KeepInZone();
        z.setBoundary(new Circle());
        toolbar.add(createButton("/resources/circ_keepin.png", "Add a Circular Keep-In Zone", z));

        z = new KeepOutZone();
        z.setBoundary(new Polygon());
        toolbar.add(createButton("/resources/poly_keepout.png", "Add a Keep-Out Zone", z));
        z = new KeepOutZone();
        z.setBoundary(new Rectangle());
        toolbar.add(createButton("/resources/square_keepout.png", "Add a Rectangular Keep-Out Zone", z));
        z = new KeepOutZone();
        z.setBoundary(new Circle());
        toolbar.add(createButton("/resources/circ_keepout.png", "Add a Circular Keep-Out Zone", z));
        
        z = new GPSDeniedZone();
        z.setBoundary(new Polygon());
        toolbar.add(createButton("/resources/poly_gpsdenied.png", "Add a GPS-Denied Zone", z));
        z = new GPSDeniedZone();
        z.setBoundary(new Rectangle());
        toolbar.add(createButton("/resources/square_gpsdenied.png", "Add a Rectangular GPS-Denied Zone", z));
        z = new GPSDeniedZone();
        z.setBoundary(new Circle());
        toolbar.add(createButton("/resources/circ_gpsdenied.png", "Add a Circular GPS-Denied Zone", z));
    }

    
    

    JButton createButton(final String icon, final String tooltip, final LMCPObject newObj) {
        System.out.println(String.valueOf(getClass().getResource(icon)));
        final JButton button = new JButton(new ImageIcon(getClass().getResource(icon)));
        button.setToolTipText(tooltip);
        button.setBorderPainted(false);
        group.add(button);
        button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (eventManager != null) {
                    Object sendObj = newObj;
                    if (newObj != null) {
                        sendObj = newObj.clone();
                    }
                    eventManager.fireEvent(new ToolbarEvent(sendObj), ScenarioToolbar.this);
                }
            }
        });

        return button;
    }

    @Override
    public void eventOccurred(Object event) {
    }

    public static void main(String[] args) {
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JToolBar toolbar = new JToolBar();
        new ScenarioToolbar().getToolbarItems(toolbar);
        f.add(toolbar);
        f.pack();
        f.setVisible(true);
    }
}

/* Distribution A. Approved for public release. 
 *  Case: #88ABW-2015-4601. Date: 24 Sep 2015. */