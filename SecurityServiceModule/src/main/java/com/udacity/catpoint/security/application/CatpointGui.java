package com.udacity.catpoint.security.application;

import com.udacity.catpoint.security.service.SecurityService;
import com.udacity.catpoint.service.ImageService;
import com.udacity.catpoint.security.data.SecurityRepository;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;

/**
 * This is the primary JFrame for the application that contains all the top-level JPanels.
 *
 * We're not using any dependency injection framework, so this class also handles constructing
 * all our dependencies and providing them to other classes as necessary.
 */
public class CatpointGui extends JFrame {

    private SecurityService securityService;
    private DisplayPanel displayPanel;
    private ControlPanel controlPanel;
    private SensorPanel sensorPanel;
    private ImagePanel imagePanel;

    public CatpointGui(SecurityRepository securityRepository, ImageService imageService) {

        securityService = new SecurityService(securityRepository, imageService);
        displayPanel = new DisplayPanel(securityService);
        controlPanel = new ControlPanel(securityService);
        sensorPanel = new SensorPanel(securityService);
        imagePanel = new ImagePanel(securityService);

        setLocation(100, 100);
        setSize(600, 850);
        setTitle("Very Secure App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new MigLayout());
        mainPanel.add(displayPanel, "wrap");
        mainPanel.add(imagePanel, "wrap");
        mainPanel.add(controlPanel, "wrap");
        mainPanel.add(sensorPanel);

        getContentPane().add(mainPanel);

    }
}
