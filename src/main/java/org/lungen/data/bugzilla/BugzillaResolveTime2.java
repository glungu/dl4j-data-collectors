package org.lungen.data.bugzilla;

/**
 * BugzillaResolveTime2
 *
 * @author lungen.tech@gmail.com
 */
public enum BugzillaResolveTime2 {

        HOURS2,
        DAY,
        WEEK,
        MONTH,
        YEAR,
        MORE;

    public static BugzillaResolveTime2 fromHours(long hours) {
        if (hours >= 0 && hours < 2) {
            return BugzillaResolveTime2.HOURS2;
        } else if (hours >= 2 && hours < 24) {
            return BugzillaResolveTime2.DAY;
        } else if (hours >= 24 && hours < 24 * 7) {
            return BugzillaResolveTime2.WEEK;
        } else if (hours >= 24 * 7 && hours < 24 * 30) {
            return BugzillaResolveTime2.MONTH;
        } else if (hours >= 24 * 30 && hours < 24 * 365) {
            return BugzillaResolveTime2.YEAR;
        } else {
            return BugzillaResolveTime2.MORE;
        }
    }

}
