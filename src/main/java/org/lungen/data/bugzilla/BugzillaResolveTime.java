package org.lungen.data.bugzilla;

/**
 * BugzillaResolveTime
 *
 * @author lungen.tech@gmail.com
 */
public enum BugzillaResolveTime {

    DAY,
    WEEK,
    MORE;

    public static BugzillaResolveTime fromHours(long hours) {
        if (hours >= 0 && hours < 24) {
            return BugzillaResolveTime.DAY;
        } else if (hours >= 24 && hours < 24 * 7) {
            return BugzillaResolveTime.WEEK;
        } else if (hours >= 24 * 7) {
            return BugzillaResolveTime.MORE;
        } else {
            throw new IllegalArgumentException("Hours value: " + hours);
        }
    }

}
