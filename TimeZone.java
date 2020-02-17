import java.util.ArrayList;

public interface TimeZone {
    String getTimeZoneName();
    void updateAllEventTimes(String newTimeZone, ArrayList<Event> events);
}