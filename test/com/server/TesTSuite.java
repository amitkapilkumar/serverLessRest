package com.server;

import org.junit.Test;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TesTSuite {

    @Test
    public void test() throws IOException {
        String out = new Scanner(new URL("https://api.exchangeratesapi.io/latest?base=USD").openStream(), "UTF-8").next();
        System.out.println(out);
        Pattern p = Pattern.compile("[\"']HKD[\"']\\s*:\\s*(.*?),");
        Matcher m = p.matcher(out);
        if(m.find()) {
            System.out.println(m.group(1));
        }
        System.out.println(p.matcher(out).find());

    }

    @Test
    public void test1() {
        String s = "A1B1C3D1G";
        System.out.println(s.replaceAll("\1([A-Z])", "$1"));
        System.out.println(Integer.toString(10, 2));
        int[] h = new int[];
        
    }

    //String out = new Scanner(new URL("http://www.google.com").openStream(), "UTF-8").useDelimiter("\\A").next();
}
