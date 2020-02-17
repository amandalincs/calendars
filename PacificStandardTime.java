import java.util.ArrayList;

public class PacificStandardTime implements TimeZone {
    public String getTimeZoneName()
    {
        return "PST";
    }
    public void updateAllEventTimes(String newTimeZone, ArrayList<Event> events)
    {
        if (newTimeZone.equalsIgnoreCase("EST"))
        {
            for (int i = 0; i < events.size(); i++)
            {
                events.get(i).convertEventTimes("add", 3);
            }
        }
    }
}