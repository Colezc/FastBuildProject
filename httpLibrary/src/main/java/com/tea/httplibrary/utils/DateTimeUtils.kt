package com.tea.httplibrary.utils

import android.annotation.SuppressLint
import java.text.DateFormat
import java.text.ParseException
import java.text.ParsePosition
import java.text.SimpleDateFormat
import java.util.*


/**

author ：zc
date : 2019/8/29 11:14
description :
 */
class DateTimeUtils() {

    companion object {

        lateinit var format: SimpleDateFormat

        /** 日期格式：yyyy-MM-dd HH:mm:ss  */
        val DF_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss"

        /** 日期格式：yyyy-MM-dd HH:mm  */
        val DF_YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm"

        /** 日期格式：yyyy-MM-dd  */
        val DF_YYYY_MM_DD = "yyyy-MM-dd"

        /** 日期格式：HH:mm:ss  */
        val DF_HH_MM_SS = "HH:mm:ss"

        /** 日期格式：HH:mm  */
        val DF_HH_MM = "HH:mm"

        private val minute = (60 * 1000).toLong()// 1分钟
        private val hour = 60 * minute// 1小时
        private val day = 24 * hour// 1天
        private val month = 31 * day// 月
        private val year = 12 * month// 年


        /**
         * 将日期格式化成友好的字符串：几分钟前、几小时前、几天前、几月前、几年前、刚刚
         *
         * @param date
         * @return
         */
        fun formatFriendly(date: Date?): String? {
            if (date == null) {
                return null
            }
            val diff = Date().getTime() - date!!.getTime()
            var r: Long = 0
            if (diff > year) {
                r = diff / year
                return r.toString() + "年前"
            }
            if (diff > month) {
                r = diff / month
                return r.toString() + "个月前"
            }
            if (diff > day) {
                r = diff / day
                return r.toString() + "天前"
            }
            if (diff > hour) {
                r = diff / hour
                return r.toString() + "个小时前"
            }
            if (diff > minute) {
                r = diff / minute
                return r.toString() + "分钟前"
            }
            return "刚刚"
        }

        /**
         * 将日期以yyyy-MM-dd HH:mm:ss格式化
         *
         * @param dateL
         * 日期
         * @return
         */
        @SuppressLint("SimpleDateFormat")
        fun formatDateTime(dateL: Long): String {
            val sdf = SimpleDateFormat(DF_YYYY_MM_DD_HH_MM_SS)
            val date = Date(dateL)
            return sdf.format(date)
        }

        /**
         * 将日期以yyyy-MM-dd HH:mm:ss格式化
         *
         * @param dateL
         * 日期
         * @return
         */
        @SuppressLint("SimpleDateFormat")
        fun formatDateTime(dateL: Long, formater: String): String {
            val sdf = SimpleDateFormat(formater)
            return sdf.format(Date(dateL))
        }


        /**
         * 将日期以yyyy-MM-dd HH:mm:ss格式化
         * @param date 日期
         * @param formater
         * @return
         */
        @SuppressLint("SimpleDateFormat")
        fun formatDateTime(date: Date, formater: String): String {
            val sdf = SimpleDateFormat(formater)
            return sdf.format(date)
        }

        /**
         * 将日期字符串转成日期
         *
         * @param strDate
         * 字符串日期
         * @return java.util.date日期类型
         */
        @SuppressLint("SimpleDateFormat")
        fun parseDate(strDate: String): Date? {
            val dateFormat = SimpleDateFormat(DF_YYYY_MM_DD_HH_MM_SS)
            var returnDate: Date? = null
            try {
                returnDate = dateFormat.parse(strDate)
            } catch (e: ParseException) {

            }

            return returnDate

        }

        /**
         * 获取系统当前日期
         *
         * @return
         */
        fun gainCurrentDate(): Date {
            return Date()
        }

        /**
         * 验证日期是否比当前日期早
         *
         * @param target1
         * 比较时间1
         * @param target2
         * 比较时间2
         * @return true 则代表target1比target2晚或等于target2，否则比target2早
         */
        fun compareDate(target1: Date, target2: Date): Boolean {
            var flag = false
            try {
                val target1DateTime = formatDateTime(target1, DF_YYYY_MM_DD_HH_MM_SS)
                val target2DateTime = formatDateTime(target2, DF_YYYY_MM_DD_HH_MM_SS)
                if (target1DateTime.compareTo(target2DateTime) <= 0) {
                    flag = true
                }
            } catch (e: Exception) {
                println("比较失败，原因：" + e.message)
            }

            return flag
        }

        /**
         * 对日期进行增加操作
         *
         * @param target
         * 需要进行运算的日期
         * @param hour
         * 小时
         * @return
         */
        fun addDateTime(target: Date?, hour: Double): Date? {
            return if (null == target || hour < 0) {
                target
            } else Date(target!!.getTime() + (hour * 60.0 * 60.0 * 1000.0).toLong())

        }

        /**
         * 对日期进行相减操作
         *
         * @param target
         * 需要进行运算的日期
         * @param hour
         * 小时
         * @return
         */
        fun subDateTime(target: Date?, hour: Double): Date? {
            return if (null == target || hour < 0) {
                target
            } else Date(target!!.getTime() - (hour * 60.0 * 60.0 * 1000.0).toLong())

        }

        /** 获取系统时间的方法:月/日 时:分:秒  */
        fun getFormateDate(): String {
            val calendar = Calendar.getInstance()
            val month = calendar.get(Calendar.MONTH) + 1
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val minute = calendar.get(Calendar.MINUTE)
            val second = calendar.get(Calendar.SECOND)

            return (if (month < 10) "0$month" else month).toString() + "/" + (if (day < 10) "0$day" else day).toString() + "  " + (if (hour < 10) "0$hour" else hour) + ":" + (if (minute < 10) "0$minute" else minute) + ":" + if (second < 10) "0$second" else second
        }

        /** 获取系统时间的方法:时:分:秒  */
        fun getHourAndMinute(): String {
            val calendar = Calendar.getInstance()
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val minute = calendar.get(Calendar.MINUTE)
            return (if (hour < 10) "0$hour" else hour).toString() + ":" + if (minute < 10) "0$minute" else minute
        }

        /** 获取系统时间的方法:时  */
        fun getHour(): String {
            val calendar = Calendar.getInstance()
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            return (if (hour < 10) "0$hour" else hour).toString() + ""
        }

        /**
         * 将2017-07-10 00:00:00 换2017-07-10
         *
         * @param strDate
         * @return
         */
        fun strFormatStr(strDate: String): String {
            return if (strDate == "") {
                ""
            } else dateToStr(strToDate(strDate))
        }

        /**
         * 2015-01-07 15:05:34
         *
         * @param strDate
         * @return
         */
        @SuppressLint("SimpleDateFormat")
        fun strToDateHHMMSS(strDate: String): Date {
            val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val pos = ParsePosition(0)
            return formatter.parse(strDate, pos)
        }

        /**
         * 2015-01-07
         *
         * @param strDate
         * @return
         */
        @SuppressLint("SimpleDateFormat")
        fun strToDate(strDate: String): Date {
            val formatter = SimpleDateFormat("yyyy-MM-dd")
            val pos = ParsePosition(0)
            return formatter.parse(strDate, pos)
        }

        /**
         * 2015.01.07
         *
         * @param strDate
         * @return
         */
        @SuppressLint("SimpleDateFormat")
        fun strToDateDorp(strDate: String): Date {
            val formatter = SimpleDateFormat("yyyy.MM.dd")
            val pos = ParsePosition(0)
            return formatter.parse(strDate, pos)
        }

        @SuppressLint("SimpleDateFormat")
        fun dateToStr(dateDate: Date): String {
            val formatter = SimpleDateFormat("yyyy-MM-dd")
            return formatter.format(dateDate)
        }

        /** 传入一个String转化为long  */
        @SuppressLint("SimpleDateFormat")
        @Throws(ParseException::class)
        fun stringParserLong(param: String): Long? {
            format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            return format.parse(param).getTime()
        }

        /** 当前时间转换为long  */
        @SuppressLint("SimpleDateFormat")
        @Throws(ParseException::class)
        fun currentDateParserLong(): Long? {
            format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            return format.parse(format.format(Calendar.getInstance().getTime())).getTime()
        }

        /** 当前时间 如: 2013-04-22 10:37:00  */
        @SuppressLint("SimpleDateFormat")
        fun getCurrentDate(): String {
            format = SimpleDateFormat("yyyy-MM-dd HH:mm")
            return format.format(Calendar.getInstance().getTime())
        }

        /** 当前时间 如: 10:37  */
        @SuppressLint("SimpleDateFormat")
        fun getCurrentDateHHMM(): String {
            format = SimpleDateFormat("HH:mm")
            return format.format(Calendar.getInstance().getTime())
        }

        /**
         * 当前时间 如: 10:37
         *
         * @throws ParseException
         */
        @SuppressLint("SimpleDateFormat")
        fun getCurrentDateHHMMSS(): String {
            format = SimpleDateFormat("HH:mm:ss")
            return format.format(Calendar.getInstance().getTime())
        }

        /** 当前时间 如: 20130422  */
        @SuppressLint("SimpleDateFormat")
        fun getCurrentDateString(): String {
            format = SimpleDateFormat("yyyyMMddHHmm")
            return format.format(Calendar.getInstance().getTime())
        }

        /** 当前时间 如: 2013-04-22  */
        @SuppressLint("SimpleDateFormat")
        fun getCurrentTime(): String {
            format = SimpleDateFormat("yyyy-MM-dd")
            return format.format(Calendar.getInstance().getTime())
        }

        @SuppressLint("SimpleDateFormat")
        fun getSWAHDate(): String {
            val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            return format.format(Calendar.getInstance().getTime())
        }

        @SuppressLint("SimpleDateFormat")
        @Throws(ParseException::class)
        fun stringToLongD(param: String): Long? {
            val format = SimpleDateFormat("yyyy-MM-dd")
            return format.parse(param.substring(0, param.length - 4)).getTime()
        }

        @SuppressLint("SimpleDateFormat")
        @Throws(ParseException::class)
        fun stringToLong(param: String): Long? {
            val format = SimpleDateFormat("yyyyMMddHHmm")
            return format.parse(param).getTime()
        }

        /**
         * 获取两个日期之间的间隔天数
         *
         * @return
         */
        @SuppressLint("SimpleDateFormat")
        fun getGapCount(startDate: Date, endDate: Date): Int {
            val fromCalendar = Calendar.getInstance()
            fromCalendar.setTime(startDate)
            fromCalendar.set(Calendar.HOUR_OF_DAY, 0)
            fromCalendar.set(Calendar.MINUTE, 0)
            fromCalendar.set(Calendar.SECOND, 0)
            fromCalendar.set(Calendar.MILLISECOND, 0)

            val toCalendar = Calendar.getInstance()
            toCalendar.setTime(endDate)
            toCalendar.set(Calendar.HOUR_OF_DAY, 0)
            toCalendar.set(Calendar.MINUTE, 0)
            toCalendar.set(Calendar.SECOND, 0)
            toCalendar.set(Calendar.MILLISECOND, 0)

            return (toCalendar.getTime().getTime().toInt() - fromCalendar.getTime().getTime()).toInt() / (1000 * 60 * 60 * 24)
        }

        /**
         * 日期转换成Java字符串
         *
         * @param date
         * @return str
         */
        @SuppressLint("SimpleDateFormat")
        fun DateToStr(date: Date): String {

            val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            return format.format(date)
        }


        /**
         * 字符串转换成日期
         *
         * @param str
         * @return date
         */
        @SuppressLint("SimpleDateFormat")
        fun StrToDate(str: String): Date? {

            val format = SimpleDateFormat("yyyy-MM-dd")
            var date: Date? = null
            try {
                date = format.parse(str)
            } catch (e: ParseException) {
                e.printStackTrace()
            }

            return date
        }


        /**
         * 字符串转换成日期
         *
         * @param str
         * @return date
         */
        @SuppressLint("SimpleDateFormat")
        fun StrToDateDrop(str: String): Date? {

            val format = SimpleDateFormat("yyyy.MM.dd")
            var date: Date? = null
            try {
                date = format.parse(str)
            } catch (e: ParseException) {
                e.printStackTrace()
            }

            return date
        }

        /**
         *
         * @param time
         * @return
         */
        @SuppressLint("SimpleDateFormat")
        fun getLongTime(time: String): Long {
            var ct: Long = 0
            try {
                format = SimpleDateFormat("HH:mm:ss")
                ct = format.parse(time).getTime()
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return ct
        }

        /**
         * 判断两日期是否同一天
         *
         * @param str1
         * @param str2
         * @return
         */
        @SuppressLint("SimpleDateFormat")
        fun isSameDay(str1: String, str2: String): Boolean {

            var day1: Date? = null
            var day2: Date? = null
            day1 = strToDate(str1)
            day2 = strToDate(str2)

            val sdf = SimpleDateFormat("yyyy-MM-dd")

            val ds1 = sdf.format(day1)

            val ds2 = sdf.format(day2)

            return if (ds1 == ds2) {
                true
            } else {
                false
            }

        }

        /**
         * 获取两个日期的时间差
         */
        @SuppressLint("SimpleDateFormat")
        fun getTimeInterval(date: String): Int {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            var interval = 0
            try {
                val currentTime = Date()// 获取现在的时间
                val beginTime = dateFormat.parse(date)
                interval = (beginTime.time.toInt() - currentTime.time.toInt()) / 1000// 时间差
                // 单位秒
            } catch (e: ParseException) {
                e.printStackTrace()
            }

            return interval
        }

        /**
         * 获取两个日期的时间差 yyyy.MM.dd HH.mm.ss
         */
        @SuppressLint("SimpleDateFormat")
        fun getInterval(bDate: String, eDate: String): Int {
            val dateFormat = SimpleDateFormat("yyyy.MM.dd")
            var interval = 0
            try {
                val currentTime = dateFormat.parse(eDate)// 获取现在的时间
                val beginTime = dateFormat.parse(bDate)
                interval = beginTime.time.toInt() - currentTime.time.toInt()// 时间差
                // 单位秒
            } catch (e: ParseException) {
                e.printStackTrace()
            }

            return interval
        }

        /**
         * 获取两个日期的时间差 yyyy.MM.dd HH.mm.ss
         */
        @SuppressLint("SimpleDateFormat")
        fun getInterval1(bDate: String, eDate: String): Int {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            var interval = 0
            try {
                val currentTime = dateFormat.parse(eDate)// 获取现在的时间
                val beginTime = dateFormat.parse(bDate)
                interval = beginTime.time.toInt() - currentTime.time.toInt()// 时间差
                // 单位秒
            } catch (e: ParseException) {
                e.printStackTrace()
            }

            return interval
        }

        /**
         * 两个时间之差 求出一个long Time
         * @param date
         * @return
         */
        @SuppressLint("SimpleDateFormat")
        fun getTime(date: String): Long {

            val df = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            var diff: Long = 0
            try {
                val currentTime = Date()// 获取现在的时间
                val getdate = df.parse(date)
                diff = getdate.getTime() - currentTime.getTime()

            } catch (e: Exception) {
            }

            return diff
        }
        fun getTime(date: Long): Long {
            var diff: Long = 0
            try {
                val currentTime = Date()// 获取现在的时间

                diff = date - currentTime.getTime()

            } catch (e: Exception) {
            }

            return diff
        }

        fun getTime(date: String,date2: String): Long {

            val df = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            var diff: Long = 0
            try {
                val getdate = df.parse(date)
                val getdate2 = df.parse(date2)
                diff = getdate.getTime() - getdate2.getTime()

            } catch (e: Exception) {
            }

            return diff
        }


        /**
         * 日期转换成Java字符串
         * @param DATE1
         * @param DATE2
         * @return
         */
        @SuppressLint("SimpleDateFormat")
        fun compare_date(DATE1: String, DATE2: String): Int {
            val df = SimpleDateFormat("yyyy-MM-dd")
            try {
                val dt1 = df.parse(DATE1)
                val dt2 = df.parse(DATE2)
                return if (dt1.getTime() >= dt2.getTime()) {
                    1
                } else if (dt1.getTime() < dt2.getTime()) {
                    -1
                } else {
                    0
                }
            } catch (exception: Exception) {
                exception.printStackTrace()
            }

            return 0
        }

        /**
         * 传入时间 算出星期几
         *
         * @param str
         * 2014年1月3日
         * @param days
         * 1:2014年1月4日 类推
         * @return
         */
        @SuppressLint("SimpleDateFormat")
        fun formatDate(str: String, days: Int): String {

            var dateStr = ""
            try {
                val df = DateFormat.getDateInstance(DateFormat.LONG)
                val date = df.parse(str)
                val dateFormat = SimpleDateFormat("yyyy-MM-dd")
                val c = Calendar.getInstance()
                val d = dateFormat.parse(dateFormat.format(date))
                c.setTime(d)
                c.add(Calendar.DAY_OF_MONTH, days)
                when (c.get(Calendar.DAY_OF_WEEK) - 1) {
                    0 -> dateStr = "周日"
                    1 -> dateStr = "周一"
                    2 -> dateStr = "周二"
                    3 -> dateStr = "周三"
                    4 -> dateStr = "周四"
                    5 -> dateStr = "周五"
                    6 -> dateStr = "周六"
                    else -> {
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return dateStr
        }


        /**
         * 将秒数转换为日时分秒，
         * @param second
         * @return
         */
        fun secondToTime(second: Long): String {
            var second = second
            val days = second / 86400            //转换天数
            second = second % 86400            //剩余秒数
            val hours = second / 3600            //转换小时
            second = second % 3600                //剩余秒数
            val minutes = second / 60            //转换分钟
            second = second % 60                //剩余秒数
            return if (days > 0) {
                days.toString() + "天" + hours + "小时" + minutes + "分" + second + "秒"
            } else {
                return "" + minutes + "分" + second + "秒"
//                return hours.toString() + "时" + minutes + "分" + second + "秒"
            }
        }

        /**
         * 将秒数转换为日时分秒，
         * @param second
         * @return
         */
        fun secondToTime2(second: Long): String {
            var second = second
            val days = second / 86400            //转换天数
            second %= 86400            //剩余秒数
            val hours = second / 3600            //转换小时
            second %= 3600                //剩余秒数
            val minutes = second / 60            //转换分钟
            second %= 60                //剩余秒数
            return if (hours > 0) {
                "${days}天$hours:${(if (minutes < 10) "0$minutes" else minutes)}:${(if (second < 10) "0$second" else second)}"
            } else {
                return "${(if (minutes < 10) "0$minutes" else minutes)}:${(if (second < 10) "0$second" else second)}"
            }
        }

        //时间List 由近到远排序
        fun sortTime(list: MutableList<String>){
            var temp: String
            if (list.size > 1) {
                for (p in 0 until list.size - 1) {
                    for (q in p + 1 until list.size) {
                        var time1 = list[p]
                        var time2 = list[q]
                        var format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss") //此处对应需要转化的时间格式，若转化时间为“2007-9-15”，将"yyyy.MM.dd"改为“yyyy-MM-dd”
                        var date1: Date
                        try {
                            date1 = format.parse(time1)
                            var date2 = format.parse(time2)

                            if (date1.before(date2)) {
                                temp = time2
                                list[p] = time1
                                list[q] = temp
                            }
                        } catch (e: ParseException) {
                            e.printStackTrace()
                        }
                    }
                }
            }
        }

//        String date = "2015-12-7T16:00:00.000Z";
//        date = date.replace("Z", " UTC");//注意是空格+UTC
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");//注意格式化的表达式
//        Date d = format.parse(date );

    }
}