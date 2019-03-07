package com.example.android.securityapp;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;



class Approximate {

    private Map dictionary= new HashMap();
    ArrayList<String> states= new ArrayList();
    ArrayList<String> statecode= new ArrayList();

    public Approximate()
    {


        dictionary.put("0","0b000110000111111");
        dictionary.put("1","0b000010000000110");
        dictionary.put("2","0b000000011011011");
        dictionary.put("3","0b000000010001111");
        dictionary.put("4","0b000000011100110");
        dictionary.put("5","0b010000001101001");
        dictionary.put("6","0b000000011111101");
        dictionary.put("7","0b000000000000111");
        dictionary.put("8","0b000000011111111");
        dictionary.put("9","0b000000011101111");


        dictionary.put( "A","0b000000011110111");
        dictionary.put( "B","0b001001010001111");
        dictionary.put( "C","0b000000000111001");
        dictionary.put( "D","0b001001000001111");
        dictionary.put( "E","0b000000001111001");
        dictionary.put( "F","0b000000001110001");
        dictionary.put( "G","0b000000010111101");
        dictionary.put( "H","0b000000011110110");
        dictionary.put( "I","0b001001000001001");
        dictionary.put( "J","0b000000000011110");
        dictionary.put( "K","0b010010001110000");

        dictionary.put( "L","0b000000000111000");
        dictionary.put( "M","0b000010100110110");
        dictionary.put( "N","0b010000100110110");
        dictionary.put( "O","0b000000000111111");
        dictionary.put( "P","0b000000011110011");
        dictionary.put( "Q","0b010000000111111");
        dictionary.put( "R","0b010000011110011");
        dictionary.put( "S","0b000000011101101");
        dictionary.put( "T","0b001001000000001");
        dictionary.put( "U","0b000000000111110");
        dictionary.put( "V","0b000110000110000");
        dictionary.put( "W","0b010100000110110");
        dictionary.put( "X","0b010110100000000");
        dictionary.put( "Y","0b000000011101110");
        dictionary.put( "Z","0b000110000001001");

        states.add("AN");
        states.add("AP");
        states.add("AR");
        states.add("AS");
        states.add("BR");
        states.add("CG");
        states.add("CH");
        states.add("DD");
        states.add("DL");
        states.add("DN");
        states.add("GA");
        states.add("GJ");
        states.add("HR");
        states.add("HP");
        states.add("JH");
        states.add("JK");
        states.add("KA");
        states.add("KL");
        states.add("LD");
        states.add("MH");
        states.add("ML");
        states.add("MN");
        states.add("MP");
        states.add("MZ");
        states.add("NL");
        states.add("OD");
        states.add("PB");
        states.add("PY");
        states.add("RJ");
        states.add("SK");
        states.add("TN");
        states.add("TR");
        states.add("TS");
        states.add("UK");
        states.add("UP");
        states.add("WB");

        for(int i = 0;i<states.size();i++)
        {
            String temp;
            temp= states.get(i);
            statecode.add(dictionary.get(Character.toString(temp.charAt(0))).toString() + dictionary.get(Character.toString(temp.charAt(1))).toString());
        }

        //  System.out.println(statecode);
    }

    public String check(String a){

        int count,max=-1,i;
        String temp;
        String abinary;
        abinary=a;
        //        abinary= (String) dictionary.get(a);

        if(dictionary.isEmpty()) {
            //  System.out.println("empty");
        }
        else {
            Set<String> ed = dictionary.keySet();
            //  System.out.println(ed);
            for(String e:ed) {
                //  System.out.println(e);
                temp=dictionary.get(e.toString()).toString();
                count=0;i=2;
                for(i=2;i<=16;i++){


                    if(abinary.charAt(i)=='1'){
                        if(abinary.charAt(i)==temp.charAt(i)) {
                            count ++;

                        }
                    }

                }
                //  System.out.println(count);
                if(count>max){
                    max=count;
                    a=e.toString();
                }



            }
        }
        return a;
    }

    public String checkToNearestNo(String a){

        float count;
        float max=-1;
        int i;
        String temp;
        String abinary;
        abinary=dictionary.get(a).toString();
        //  System.out.println(abinary);


        if(dictionary.isEmpty()) {
            //  System.out.println("empty");
        }
        else {
            Set<String> ed = dictionary.keySet();
            ArrayList <String> a1=new ArrayList<String>();
            a1.addAll(ed);

            //  System.out.println(ed);
            for(int j=26;j<a1.size();j++) {
                String e=a1.get(j);
                temp=dictionary.get(e).toString();
                //  System.out.println(e+temp);

                count=0;i=2;
                for(i=2;i<=16;i++){

                    if(abinary.charAt(i)=='1'){
                        if(abinary.charAt(i)==temp.charAt(i)) {
                            count ++;
                            ////System.out.println("yess");

                        }

                    }
                    if(abinary.charAt(i)!=temp.charAt(i))
                    {
                        count=(float) (count-0.2);
                        //// System.out.println("yess1");
                    }

                }
                //  System.out.println(count);
                if(count>max){
                    max=count;
                    a=e;
                }



            }
        }
        return a;
    }
    public String checkbyboth(String a){

        float max=-1;
        int i;
        float count;
        String temp;
        String abinary;
        abinary=
                dictionary.get(Character.toString(a.charAt(0))).toString() + dictionary.get(Character.toString(a.charAt(1))).toString();
        //        abinary= (String) dictionary.get(a);

        if(statecode.isEmpty()) {
            //  System.out.println("empty");
        }
        else {

            for(String e:statecode) {
                //  System.out.println(states.get(statecode.indexOf(e)));
                temp=e;
                count=0;i=2;
                for(i=0;i<e.length();i++){

                    if(abinary.charAt(i)=='1'){
                        if(abinary.charAt(i)==temp.charAt(i)) {
                            count ++;

                        }

                    }
                    if(abinary.charAt(i)!=temp.charAt(i))
                    {
                        count=(float) (count-0.2);
                    }


                }
                //  System.out.println(count);
                if(count>max){
                    max=count;
                    a=e.toString();
                }



            }
        }
        return states.get(statecode.indexOf(a));
    }

    public String mainfn(String a)
    {
        if(a.length()==10)
        {
            if(a.charAt(0)>=65&&a.charAt(0)<=90&&a.charAt(1)>=65&&a.charAt(1)<=90) {
                String t=a.substring(0, 2);
                t=checkbyboth(t);
                a=t+a.substring(2);
            }
            if(a.charAt(2)=='O') {
                a = a.substring(0, 2)+"0"+a.substring(3);
            }
            if(a.charAt(3)=='O') {
                a = a.substring(0, 2)+"0"+a.substring(3);
            }
            if(a.charAt(6)<48||a.charAt(6)>57)
            {
                ////System.out.println("Entered1");
                Character c =a.charAt(6);
                String t=c.toString();
                t=checkToNearestNo(t);
                a = a.substring(0, 6)+t+a.substring(7);
            }
            if(a.charAt(7)<48||a.charAt(7)>57)
            {
                ////System.out.println("Entered2");

                Character c =a.charAt(7);
                String t=c.toString();
                t=checkToNearestNo(t);
                a = a.substring(0, 7)+t+a.substring(8);
            }
            if(a.charAt(8)<48||a.charAt(8)>57)
            {
                ////System.out.println("Entered3");

                Character c =a.charAt(8);
                String t=c.toString();
                t=checkToNearestNo(t);
                ////System.out.println(t);
                a = a.substring(0, 8)+t+a.substring(9);
            }
            if(a.charAt(9)<48||a.charAt(9)>57)
            {
                ////System.out.println("Entered4");

                Character c =a.charAt(9);
                String t=c.toString();
                t=checkToNearestNo(t);
                a = a.substring(0, 9)+t;
            }

        }
        return a;

    }

}
