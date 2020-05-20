package ie.tcd.pavel.utility;

import java.util.Date;

public class ChallengeAdaptor {

    public static String getDistanceFieldInfo(String value, String unit)
    {
        long valueInMeters = Long.valueOf(value);
        switch (unit)
        {
           case "km": valueInMeters*=1000;break;
           case "mi": valueInMeters =(long)(valueInMeters*1609.34);break;
           case "ft":valueInMeters =(long)(valueInMeters*0.3);break;
           default:break;
        }
        String info = "value:"+value+" unit:"+unit+" valueInMeters:"+String.valueOf(valueInMeters);

        return  info;
    }

    public static String getTimeFieldInfo(String value, String unit)
    {
        long valueInMinutes = Long.valueOf(value);
        if(unit.equals("hr"))
        {
            valueInMinutes*=60;
        }
        String info = "value:"+value+" unit:"+unit+" valueInMeters:"+String.valueOf(valueInMinutes);

        return  info;
    }

    public static String getWeightFieldInfo(String amount, String unit, String reps)
    {
        long amountInKg = Long.valueOf(amount);

        if(unit.equals("lb"))
        {
            amountInKg =(long)(amountInKg*0.454);
        }

        String info ="amount:"+amount+" unit:"+unit+" reps:"+reps+" amountInKg:"+amountInKg;

        return info;
    }

    public static String getRepFieldInfo(String reps )
    {
        return "reps:"+reps;
    }


    public static long getDate(Date date)
    {
        return date.getTime();
    }


}
