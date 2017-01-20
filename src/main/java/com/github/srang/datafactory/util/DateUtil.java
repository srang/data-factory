package com.github.srang.datafactory.util;

/*-
 * #%L
 * Data Factory
 * %%
 * Copyright (C) 2017 srang
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;


/**
 * <p>DateUtil class.</p>
 *
 * @author srang
 */
public class DateUtil {
    
    static SimpleDateFormat dateTimeFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    static SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
    

        /**
         * <p>getCurrentDate.</p>
         *
         * @return a {@link java.util.Date} object.
         */
        public static Date getCurrentDate() {
            Calendar cal = Calendar.getInstance();
            return cal.getTime();
        }
        /**
         * <p>getCurrentDateTimeString.</p>
         *
         * @return a {@link java.lang.String} object.
         */
        public static String getCurrentDateTimeString(){           
            return dateTimeFormatter.format(getCurrentDate());     
        }
        
        /**
         * <p>getDateTimeFromString.</p>
         *
         * @param date a {@link java.lang.String} object.
         * @return a {@link java.util.Date} object.
         * @throws java.text.ParseException if any.
         */
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

        /**
         * <p>getDateOnlyFromString.</p>
         *
         * @param date a {@link java.lang.String} object.
         * @return a {@link java.util.Date} object.
         * @throws java.text.ParseException if any.
         */
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
        
        /**
         * <p>getDateTimeFromString.</p>
         *
         * @param date a {@link java.lang.String} object.
         * @param currentDateFormat a {@link java.text.SimpleDateFormat} object.
         * @return a {@link java.util.Date} object.
         * @throws java.text.ParseException if any.
         */
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
        
        /**
         * <p>getStringFromDateTime.</p>
         *
         * @param date a {@link java.util.Date} object.
         * @return a {@link java.lang.String} object.
         */
        public static String getStringFromDateTime(Date date){
            if (date == null){
                return null;                
            }
            return dateTimeFormatter.format(date);
        }
        /**
         * <p>getStringFromDateTime.</p>
         *
         * @param date a {@link java.util.Date} object.
         * @param currentDateFormat a {@link java.text.SimpleDateFormat} object.
         * @return a {@link java.lang.String} object.
         */
        public static String getStringFromDateTime(Date date, SimpleDateFormat currentDateFormat){
            if (date == null){
                return null;                
            }
            return currentDateFormat.format(date);
        }
        
        /**
         * <p>getDateOnlyStringFromDateTime.</p>
         *
         * @param date a {@link java.util.Date} object.
         * @return a {@link java.lang.String} object.
         */
        public static String getDateOnlyStringFromDateTime(Date date){
            if (date == null){
                return null;                
            }
            return dateFormatter.format(date);
        }
        
        /**
         * <p>getMaxDate.</p>
         *
         * @return a {@link java.util.Date} object.
         */
        public static Date getMaxDate(){
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.YEAR, 3000);
            return cal.getTime();
            //return new Date(Long.MAX_VALUE);
        }
        
        /**
         * <p>getTomorrowDate.</p>
         *
         * @return a {@link java.util.Date} object.
         */
        public static Date getTomorrowDate(){        
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DAY_OF_YEAR, 1);
            return cal.getTime();
        }
        
        /**
         * <p>UTCtoDefaultTimeZone.</p>
         *
         * @param date a {@link java.util.Date} object.
         * @return a {@link java.util.Date} object.
         */
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
     * @param date1 a {@link java.util.Date} object.
     * @param date2 a {@link java.util.Date} object.
     * @return a boolean.
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
