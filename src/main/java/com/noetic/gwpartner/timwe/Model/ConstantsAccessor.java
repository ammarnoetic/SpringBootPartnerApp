package com.noetic.gwpartner.timwe.Model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class ConstantsAccessor {

    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private String BUNDLE_NAME = null; //$NON-NLS-1$

    private ResourceBundle RESOURCE_BUNDLE = null;

    public ConstantsAccessor(String bundleName)
    {
        this.resetBundle(bundleName);
    }

    public String getString(String key)
    {
        try
        {
            return RESOURCE_BUNDLE.getString(key);
        }
        catch (MissingResourceException e)
        {
            System.out.println("Error loading constants: " + key);
            return null;
        }
    }

    public int getInt(String key)
    {
        try
        {
            return Integer.parseInt(RESOURCE_BUNDLE.getString(key));
        }
        catch (MissingResourceException e)
        {
            System.out.println("Error loading constants: " + key);
            return -1;
        }
    }

    public Date getDate(String key)
    {
        try
        {
            String dateText = RESOURCE_BUNDLE.getString(key);
            SimpleDateFormat sdf = new SimpleDateFormat(ConstantsAccessor.DATE_TIME_FORMAT);
            return sdf.parse(dateText);
        }
        catch (Exception e)
        {
            System.out.println("Error loading constants: " + key);
            return null;
        }
    }

    private void resetBundle(String bundle_name)
    {
        BUNDLE_NAME = bundle_name;
        try
        {
            RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);
            System.out.println("loaded " + BUNDLE_NAME + " properties...");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
