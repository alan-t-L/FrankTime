package com.alan.franktime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.junit.Test;

public class FrankTimeFormatterTest {

    @Test
    public void sevenOclock() {
        assertEquals("twenty past a third to seven", FrankTimeFormatter.format(7, 0));
    }

    @Test
    public void oneMinutePastSeven() {
        assertEquals("nineteen minutes to a third past seven", FrankTimeFormatter.format(7, 1));
    }

    @Test
    public void fivePastSeven() {
        assertEquals("fifteen to a third past seven", FrankTimeFormatter.format(7, 5));
    }

    @Test
    public void tenPastSeven() {
        assertEquals("ten to a third past seven", FrankTimeFormatter.format(7, 10));
    }

    @Test
    public void fourteenPastSeven() {
        assertEquals("six minutes to a third past seven", FrankTimeFormatter.format(7, 14));
    }

    @Test
    public void quarterPastSeven() {
        assertEquals("five to a third past seven", FrankTimeFormatter.format(7, 15));
    }

    @Test
    public void oneMinuteBeforeFirstThird() {
        assertEquals("one minute to a third past seven", FrankTimeFormatter.format(7, 19));
    }

    @Test
    public void firstThirdAnchor() {
        assertEquals("a third past seven", FrankTimeFormatter.format(7, 20));
    }

    @Test
    public void oneMinuteAfterFirstThird() {
        assertEquals("one minute past two thirds to eight", FrankTimeFormatter.format(7, 21));
    }

    @Test
    public void fivePastFirstThird() {
        assertEquals("five past two thirds to eight", FrankTimeFormatter.format(7, 25));
    }

    @Test
    public void halfPastSeven() {
        assertEquals("ten past two thirds to eight", FrankTimeFormatter.format(7, 30));
    }

    @Test
    public void thirtyFivePastSeven() {
        assertEquals("fifteen past two thirds to eight", FrankTimeFormatter.format(7, 35));
    }

    @Test
    public void thirtyEightPastSeven() {
        assertEquals("eighteen minutes past two thirds to eight", FrankTimeFormatter.format(7, 38));
    }

    @Test
    public void oneMinuteBeforeSecondThird() {
        assertEquals("nineteen minutes past two thirds to eight", FrankTimeFormatter.format(7, 39));
    }

    @Test
    public void secondThirdAnchor() {
        assertEquals("a third to eight", FrankTimeFormatter.format(7, 40));
    }

    @Test
    public void oneMinuteAfterSecondThird() {
        assertEquals("one minute past a third to eight", FrankTimeFormatter.format(7, 41));
    }

    @Test
    public void quarterToEight() {
        assertEquals("five past a third to eight", FrankTimeFormatter.format(7, 45));
    }

    @Test
    public void tenToEight() {
        assertEquals("ten past a third to eight", FrankTimeFormatter.format(7, 50));
    }

    @Test
    public void fiveToEight() {
        assertEquals("fifteen past a third to eight", FrankTimeFormatter.format(7, 55));
    }

    @Test
    public void twoMinutesToEight() {
        assertEquals("eighteen minutes past a third to eight", FrankTimeFormatter.format(7, 58));
    }

    @Test
    public void oneMinuteToEight() {
        assertEquals("nineteen minutes past a third to eight", FrankTimeFormatter.format(7, 59));
    }

    @Test
    public void midnightMapsToTwelve() {
        assertEquals("twenty past a third to twelve", FrankTimeFormatter.format(0, 0));
    }

    @Test
    public void oneMinuteBeforeMidnight() {
        assertEquals("nineteen minutes past a third to twelve", FrankTimeFormatter.format(23, 59));
    }

    @Test
    public void elevenTwenty() {
        assertEquals("a third past eleven", FrankTimeFormatter.format(23, 20));
    }

    @Test
    public void elevenForty() {
        assertEquals("a third to twelve", FrankTimeFormatter.format(23, 40));
    }

    @Test
    public void invalidHourBelowZero() {
        assertThrows(IllegalArgumentException.class, () -> FrankTimeFormatter.format(-1, 0));
    }

    @Test
    public void invalidHourAboveTwentyThree() {
        assertThrows(IllegalArgumentException.class, () -> FrankTimeFormatter.format(24, 0));
    }

    @Test
    public void invalidMinuteBelowZero() {
        assertThrows(IllegalArgumentException.class, () -> FrankTimeFormatter.format(7, -1));
    }

    @Test
    public void invalidMinuteAboveFiftyNine() {
        assertThrows(IllegalArgumentException.class, () -> FrankTimeFormatter.format(7, 60));
    }
}
