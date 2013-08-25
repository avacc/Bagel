package com.example.bagel.utils;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by ipedisich on 8/23/13.
 */
public class TextUtils {

    public static ArrayList<String> readTextAsList(Context ctx, int resId)
    {
        InputStream inputStream = ctx.getResources().openRawResource(resId);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        ArrayList<String> lines = new ArrayList<String>();
        try{
        while(reader.ready())
            lines.add(reader.readLine());
        return lines;
        } catch(IOException e)
        {
            e.printStackTrace();
            return new ArrayList<String>();
        }
    }
}
