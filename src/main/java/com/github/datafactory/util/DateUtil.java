package com.github.datafactory.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;


public class DateUtil {
    
    static SimpleDateFormat dateTimeFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    static SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
    

        public static Date getCurrentDate() {
            Calendar cal = Calendar.getInstance();
            return cal.getTime();
        }
        public static String getCurrentDateTimeString(){           
            return dateTimeFormatter.format(getCurrentDate());     
        }
        
        public static Date getDateTimeFromString(String date) throws ParseException{
            if (date == null){
                return null;                
            }
            if (date.equals("")){
                return null; 
            }
            if (date.equals("null")){
                return null; 
            }
            return dateTimeFormatter.parse(date);
        }

        public static Date getDateOnlyFromString(String date) throws ParseException {
            if (date == null){
                return null;
            }
            if (date.equals("")){
                return null;
            }
            if (date.equals("null")){
                return null;
            }
            return dateFormatter.parse(date);
        }
        
        public static Date getDateTimeFromString(String date, SimpleDateFormat currentDateFormat) throws ParseException{
            if (date == null){
                return null;                
            }
            if (date.equals("")){
                return null; 
            }
            if (date.equals("null")){
                return null; 
            }
            
            return currentDateFormat.parse(date);
        }
        
        public static String getStringFromDateTime(Date date){
            if (date == null){
                return null;                
            }
            return dateTimeFormatter.format(date);
        }
        public static String getStringFromDateTime(Date date, SimpleDateFormat currentDateFormat){
            if (date == null){
                return null;                
            }
            return currentDateFormat.format(date);
        }
        
        public static String getDateOnlyStringFromDateTime(Date date){
            if (date == null){
                return null;                
            }
            return dateFormatter.format(date);
        }
        
        public static Date getMaxDate(){
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.YEAR, 3000);
            return cal.getTime();
            //return new Date(Long.MAX_VALUE);
        }
        
        public static Date getTomorrowDate(){        
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DAY_OF_YEAR, 1);
            return cal.getTime();
        }
        
        public static Date UTCtoDefaultTimeZone(Date date){
            if (date != null){
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                TimeZone toTimeZone = TimeZone.getDefault();
                int offSetfromUTCtoDefault = toTimeZone.getRawOffset();
                if (offSetfromUTCtoDefault < 0){
                    //add a day
                    calendar.add(Calendar.HOUR, 24);
                }else if (offSetfromUTCtoDefault > 86400000){
                    //remove a day
                    calendar.add(Calendar.HOUR, -24);
                }
                return calendar.getTime();
            }else
                return null;         
        }

    /**
     * Method compares two dates for equality ignoring the time component.
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean areDatesEqualIgnoreTime(Date date1, Date date2)
    {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);

        boolean outcome = false;

        int year1 = cal1.get(Calendar.YEAR);
        int mon1 = cal1.get(Calendar.MONTH);
        int day1 = cal1.get(Calendar.DAY_OF_MONTH);

        int year2 = cal2.get(Calendar.YEAR);
        int mon2 = cal2.get(Calendar.MONTH);
        int day2 = cal2.get(Calendar.DAY_OF_MONTH);

        if (
                year1 == year2
                        && mon1 == mon2
                        && day1 == day2
                )
        {
            outcome = true;
        }

        return outcome;
    } //

}
