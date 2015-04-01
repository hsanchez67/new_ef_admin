package com.demandforce.bluebox.admin.utils;

public class IntegerUtils
{
    public static int parseInt(String s, int defaulValue)
    {
        try
        {
            return Integer.parseInt(s);
        } catch (NumberFormatException e)
        {
            return defaulValue;
        }
    }
}
