package ie.tcd.pavel.utility;

import java.util.Date;

public class ExerciseAdaptor {

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
        String info = "value:"+value+" unit:"+unit+" valueInMinutes:"+String.valueOf(valueInMinutes);

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

    public static double getDistanceValue(String info)
    {
        String[] parts = info.split(":| ");
        return Double.valueOf(parts[parts.length-1]);
    }

    public static double getTimeValue(String info)
    {
        String[] parts = info.split(":| ");
        return Double.valueOf(parts[parts.length-1]);
    }

    public static double getWeightValue(String info)
    {
        String[] parts = info.split(":| ");
        return Double.valueOf(parts[parts.length-1])*Double.valueOf(parts[parts.length-3]);
    }

    public static double getRepsValue(String info)
    {
        String[] parts = info.split(":| ");
        return Double.valueOf(parts[parts.length-1]);
    }

    public static long getDate(Date date)
    {
        return date.getTime();
    }


}
