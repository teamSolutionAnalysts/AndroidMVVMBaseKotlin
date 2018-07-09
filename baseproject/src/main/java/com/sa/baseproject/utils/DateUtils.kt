package com.sa.baseproject.utils


import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by altafhussain.shaikh on 4/21/2016.
 */
object DateUtils {

    val MMDDYYY = "MM/dd/yyyy"
    val MM_DD_YYYY = "MM-dd-yyyy"
    val MMDDYYHHMM = "MM/dd/yyyy HH:mm"

    var dateFormateTwentyFourHour = SimpleDateFormat(MMDDYYHHMM, Locale.getDefault())

    val HHMM12HOURS = "hh:mm aaa"
    val simpleDateFormateMM = "MM/dd/yyyy hh:mm aa"
    val simpleDateFormate24MM = "MM-dd-yyyy hh:mm aa"
    val dateISOFormate = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
    var formatter = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())

    var simpleDateTimeFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault()) //2016-05-03T09:45:15.724Z
    var twentyFourHrFormat: DateFormat = SimpleDateFormat("HH:mm", Locale.getDefault()) //HH for hour of the day (0 - 23)
    var twelveHrFormat: DateFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
    var formateHHMMYYYhhmm = SimpleDateFormat(simpleDateFormateMM, Locale.getDefault())
    var formatterDash = SimpleDateFormat("MM-dd-yyyy", Locale.getDefault())
    var formatterSeconds = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    var formatterDate12Hours = SimpleDateFormat(simpleDateFormate24MM, Locale.getDefault())
    var formatter12HoursSpace = SimpleDateFormat(simpleDateFormateMM, Locale.getDefault())
    var fomratterTimeDate = SimpleDateFormat("hh:mm a MM/dd/yyyy", Locale.getDefault())


    /**
     * Calendar objects are rather expensive: for heavy usage it's a good idea to use a single instance per thread
     * instead of calling Calendar.getInstance() multiple times. Calendar.getInstance() creates a new instance each
     * time.
     */
    class DefaultCalendarThreadLocal : ThreadLocal<Calendar>() {
        override fun initialValue(): Calendar {
            return Calendar.getInstance()
        }
    }

    private val calendarThreadLocal = DefaultCalendarThreadLocal()

    fun getTimeForDay(year: Int, month: Int, day: Int): Long {
        return getTimeForDay(calendarThreadLocal.get(), year, month, day)
    }

    /**
     * @param calendar helper object needed for conversion
     */
    fun getTimeForDay(calendar: Calendar, year: Int, month: Int, day: Int): Long {
        calendar.clear()
        calendar.set(year, month - 1, day)
        return calendar.timeInMillis
    }

    /**
     * Sets hour, minutes, seconds and milliseconds to the given values. Leaves date info untouched.
     */
    fun setTime(calendar: Calendar, hourOfDay: Int, minute: Int, second: Int, millisecond: Int) {
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
        calendar.set(Calendar.MINUTE, minute)
        calendar.set(Calendar.SECOND, second)
        calendar.set(Calendar.MILLISECOND, millisecond)
    }

    /**
     * Readable yyyyMMdd int representation of a day, which is also sortable.
     */
    fun getDayAsReadableInt(time: Long): Int {
        val cal = calendarThreadLocal.get()
        cal.timeInMillis = time
        return getDayAsReadableInt(cal)
    }

    /**
     * Readable yyyyMMdd representation of a day, which is also sortable.
     */
    fun getDayAsReadableInt(calendar: Calendar): Int {
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val month = calendar.get(Calendar.MONTH) + 1
        val year = calendar.get(Calendar.YEAR)
        return year * 10000 + month * 100 + day
    }

    /**
     * Returns midnight of the given day.
     */
    fun getTimeFromDayReadableInt(day: Int): Long {
        return getTimeFromDayReadableInt(calendarThreadLocal.get(), day, 0)
    }

    /**
     * @param calendar helper object needed for conversion
     */
    fun getTimeFromDayReadableInt(calendar: Calendar, readableDay: Int, hour: Int): Long {
        val day = readableDay % 100
        val month = readableDay / 100 % 100
        val year = readableDay / 10000

        calendar.clear() // We don't set all fields, so we should clear the calendar first
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.DAY_OF_MONTH, day)
        calendar.set(Calendar.MONTH, month - 1)
        calendar.set(Calendar.YEAR, year)

        return calendar.timeInMillis
    }

    fun getDayDifferenceOfReadableInts(dayOfBroadcast1: Int, dayOfBroadcast2: Int): Int {
        val time1 = getTimeFromDayReadableInt(dayOfBroadcast1)
        val time2 = getTimeFromDayReadableInt(dayOfBroadcast2)

        // Don't use getDayDifference(time1, time2) here, it's wrong for some days.
        // Do float calculation and rounding at the end to cover daylight saving stuff etc.
        val daysFloat = (time2 - time1).toFloat() / 1000f / 60f / 60f / 24f
        return Math.round(daysFloat)
    }

    fun getDayDifference(time1: Long, time2: Long): Int {
        return ((time2 - time1) / 1000 / 60 / 60 / 24).toInt()
    }

    fun addDays(time: Long, days: Int): Long {
        val calendar = calendarThreadLocal.get()
        calendar.timeInMillis = time
        calendar.add(Calendar.DAY_OF_YEAR, days)
        return calendar.timeInMillis
    }

    fun addDays(calendar: Calendar, days: Int) {
        calendar.add(Calendar.DAY_OF_YEAR, days)
    }

    fun convertIsoFormatetoString(isoType: String, formate: String): String {
        //        yyyy-mm-dd 'T' HH:MM:SS.mmm-HH:SS
        val orignalFormate = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())

        var converTedDate: Date? = null
        try {
            converTedDate = orignalFormate.parse(isoType.substring(0, 24))
        } catch (ex: Exception) {
            ex.printStackTrace()
        }

        //        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        val formatter = SimpleDateFormat(formate, Locale.getDefault())
        return formatter.format(converTedDate)
        //        System.out.println(".....Date..."+newFormat);
    }

    fun getnextYearDate(): String {
        val format = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())

        val cal = Calendar.getInstance()
        val today = cal.time
        cal.add(Calendar.YEAR, 1) // to get previous year add -1
        val nextYear = cal.time
        return format.format(nextYear)
    }

    fun convertWele24Formate(time: String): String {
        val displayFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        val parseFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
        var date: Date? = null
        try {
            date = parseFormat.parse(time)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        //        System.out.println(parseFormat.format(date) + " = " + displayFormat.format(date));
        return displayFormat.format(date)
    }

    fun convertTwentyFourTwelve(time: String): String {
        val displayFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
        val parseFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        var date: Date? = null
        try {
            date = parseFormat.parse(time)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        //        System.out.println(parseFormat.format(date) + " = " + displayFormat.format(date));
        return displayFormat.format(date)
    }

    fun convertDateTimeToDate(time: String): String {
        val displayFormat = SimpleDateFormat(MMDDYYHHMM, Locale.getDefault())
        val parseFormat = SimpleDateFormat(MMDDYYY, Locale.getDefault())
        var date: Date? = null
        try {
            date = displayFormat.parse(time)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        //        System.out.println(parseFormat.format(date) + " = " + displayFormat.format(date));
        return parseFormat.format(date)
    }

    fun getCurrentDateTime(simpleDateFormateMM: String): String {
        val Datetime: String
        val c = Calendar.getInstance()
        //        SimpleDateFormat dateformat = new SimpleDateFormat("MM/dd/yyyy hh:mm aa");
        val dateformat = SimpleDateFormat(simpleDateFormateMM, Locale.getDefault())
        Datetime = dateformat.format(c.time)
        println(Datetime)
        return Datetime
    }


    fun hoursDifference(date1: Date, date2: Date): Float {
        val MILLI_TO_HOUR = 1000 * 60 * 60
        return (date1.time - date2.time).toFloat() / MILLI_TO_HOUR
    }

    //1 minute = 60 seconds
    //1 hour = 60 x 60 = 3600
    //1 day = 3600 x 24 = 86400
    fun printDifference(startDate: Date, endDate: Date) {

        //milliseconds
        var different = endDate.time - startDate.time

        println("startDate : " + startDate)
        println("endDate : " + endDate)
        println("different : " + different)

        val secondsInMilli: Long = 1000
        val minutesInMilli = secondsInMilli * 60
        val hoursInMilli = minutesInMilli * 60
        val daysInMilli = hoursInMilli * 24

        val elapsedDays = different / daysInMilli
        different = different % daysInMilli

        val elapsedHours = different / hoursInMilli
        different = different % hoursInMilli

        val elapsedMinutes = different / minutesInMilli
        different = different % minutesInMilli

        val elapsedSeconds = different / secondsInMilli

        System.out.printf(
                "%d days, %d hours, %d minutes, %d seconds%n",
                elapsedDays,
                elapsedHours, elapsedMinutes, elapsedSeconds)

    }

    fun convertStringDate(strDate: String, formate: String): Date? {
        //        SimpleDateFormat sdf = new SimpleDateFormat(DateUtils.simpleDateFormateMM, Locale.US);
        val sdf = SimpleDateFormat(formate, Locale.getDefault())
        var dateStart: Date? = null
        try {
            dateStart = sdf.parse(strDate)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        val myCal = Calendar.getInstance()
        myCal.time = dateStart
        return dateStart
    }

    fun convertStringDateFormatter(strDate: String, parseFormate: String): Date? {
        //        SimpleDateFormat sdf = new SimpleDateFormat(DateUtils.simpleDateFormateMM, Locale.US);
        val sdf = SimpleDateFormat(parseFormate, Locale.getDefault())
        var dateStart: Date? = null
        try {
            dateStart = sdf.parse(strDate)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        val myCal = Calendar.getInstance()
        myCal.time = dateStart
        return dateStart
    }

    fun convertTimeZondeDefault(timeZone: String, dateTime: String): String {
        //        String s = "2011-01-01 12:00:00";
        val df = SimpleDateFormat(HHMM12HOURS, Locale.getDefault())
        df.timeZone = TimeZone.getTimeZone(timeZone)
        var timestamp: Date? = null
        try {
            timestamp = df.parse(dateTime)
            df.timeZone = TimeZone.getDefault()
            println(df.format(timestamp))
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return df.format(timestamp)
    }

    fun convertDateTimeZone(timeZone: String, dateTime: String, formatter: String, strOutPuttformatter: String): String {
        //        String s = "2011-01-01 12:00:00";
        val inputFormat = SimpleDateFormat(
                formatter, Locale.getDefault())
        val outPutFormate = SimpleDateFormat(
                strOutPuttformatter, Locale.getDefault())
        //        DateFormat df = new SimpleDateFormat(formatter);
        inputFormat.timeZone = TimeZone.getTimeZone(timeZone)
        var timestamp: Date? = null

        try {
            timestamp = inputFormat.parse(dateTime)
            //             timestamp = df.parse(dateTime);
            outPutFormate.format(timestamp)
            outPutFormate.timeZone = TimeZone.getDefault()
            //            System.out.println(df.format(timestamp));
        } catch (e: Exception) {
            e.printStackTrace()
        }

        var format = ""
        try {
            format = outPutFormate.format(timestamp)
        } catch (e: Exception) {
            e.printStackTrace()

        }

        return format
    }

    fun convertTimetoGMT(timeZone: String, dateTime: String, formatter: String, strOutPuttformatter: String): String {
        //        String s = "2011-01-01 12:00:00";
        val inputFormat = SimpleDateFormat(
                formatter, Locale.getDefault())
        val outPutFormate = SimpleDateFormat(
                strOutPuttformatter, Locale.getDefault())
        //        DateFormat df = new SimpleDateFormat(formatter);
        inputFormat.timeZone = TimeZone.getDefault()
        var timestamp: Date? = null

        try {
            timestamp = inputFormat.parse(dateTime)
            //             timestamp = df.parse(dateTime);
            outPutFormate.format(timestamp)
            outPutFormate.timeZone = TimeZone.getTimeZone(timeZone)
            //            System.out.println(df.format(timestamp));
        } catch (e: Exception) {
            e.printStackTrace()
        }

        var format = ""
        try {
            format = outPutFormate.format(timestamp)
        } catch (e: Exception) {
            e.printStackTrace()

        }

        return format
    }

    fun dateFormateToOtherFormate(dateTime: String, formatter: String, strOutPuttformatter: String): String {
        //        String s = "2011-01-01 12:00:00";
        val inputFormat = SimpleDateFormat(formatter, Locale.getDefault())
        val outPutFormate = SimpleDateFormat(strOutPuttformatter, Locale.getDefault())
        //        DateFormat df = new SimpleDateFormat(formatter);
        //        inputFormat.setTimeZone(TimeZone.getTimeZone(timeZone));
        var timestamp: Date? = null

        try {
            timestamp = inputFormat.parse(dateTime)
            //             timestamp = df.parse(dateTime);
            outPutFormate.format(timestamp)
            //            outPutFormate.setTimeZone(TimeZone.getDefault());
            //            System.out.println(df.format(timestamp));
        } catch (e: Exception) {
            e.printStackTrace()
        }

        var format = ""
        try {
            format = outPutFormate.format(timestamp)
        } catch (e: Exception) {
            e.printStackTrace()

        }

        return format
    }


    fun convertTimeZondeDefaultTo24Hrs(timeZone: String, dateTime: String,
                                       dateFormat: SimpleDateFormat, dateParse: SimpleDateFormat): String {
        //        String s = "2011-01-01 12:00:00";
        dateParse.timeZone = TimeZone.getTimeZone(timeZone)
        var timestamp: Date? = null
        try {
            timestamp = dateParse.parse(dateTime)
            dateParse.timeZone = TimeZone.getDefault()
            println(dateFormat.format(timestamp))
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return dateFormat.format(timestamp)
    }
}
