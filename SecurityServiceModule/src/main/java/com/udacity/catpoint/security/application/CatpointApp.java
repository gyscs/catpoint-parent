package com.udacity.catpoint.security.application;

import com.udacity.catpoint.security.data.PretendDatabaseSecurityRepositoryImpl;
import com.udacity.catpoint.security.data.SecurityRepository;
import com.udacity.catpoint.service.FakeImageService;
import com.udacity.catpoint.service.ImageService;

/**
 * This is the main class that launches the application.
 */
public class CatpointApp {
    public static void main(String[] args) {
        SecurityRepository securityRepository = new PretendDatabaseSecurityRepositoryImpl();
        ImageService imageService = new FakeImageService();
        CatpointGui gui = new CatpointGui(securityRepository, imageService);
        gui.setVisible(true);
    }
}
