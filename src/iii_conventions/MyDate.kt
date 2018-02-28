package iii_conventions

import com.sun.org.apache.bcel.internal.generic.MONITORENTER
import java.sql.Time

data class MyDate(
        val year: Int,
        val month: Int,
        val dayOfMonth: Int
) : Comparable<MyDate> {

    override fun compareTo(other: MyDate): Int {
        if(year != other.year)
            return year.compareTo(other.year)

        if(month != other.month)
            return month.compareTo(other.month)

        return dayOfMonth.compareTo(other.dayOfMonth)
    }

    operator fun plus(interval: RepeatedTimeInterval): MyDate {
        return addTimeIntervals(interval.interval, interval.times)
    }

    operator fun plus(interval: TimeInterval): MyDate {
        return addTimeIntervals(interval, 1)
    }

}

operator fun MyDate.rangeTo(other: MyDate): DateRange = DateRange(this, other)

data class RepeatedTimeInterval(val interval: TimeInterval, val times: Int)

enum class TimeInterval {
    DAY,
    WEEK,
    YEAR;

    operator fun times(value: Int): RepeatedTimeInterval {
        return RepeatedTimeInterval(this, value)
    }
}

class DateRangeIterator(val first: MyDate, val last: MyDate) : Iterator<MyDate> {

    private var hasNext: Boolean = first <= last
    private var next = first

    override fun hasNext(): Boolean = hasNext

    override fun next(): MyDate {
        val value = next

        next = next.nextDay()
        hasNext = next <= last

        return value
    }

}

class DateRange(
        override val start: MyDate,
        override val endInclusive: MyDate
) : ClosedRange<MyDate>, Iterable<MyDate> {

    override fun iterator(): Iterator<MyDate> {
        return DateRangeIterator(start, endInclusive)
    }

}