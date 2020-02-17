import java.util.ArrayList;

public class EasternStandardTime implements TimeZone {
    public String getTimeZoneName()
    {
        return "EST";
    }
    public void updateAllEventTimes(String newTimeZone, ArrayList<Event> events)
    {
        if (newTimeZone.equalsIgnoreCase("PST"))
        {
            for (int i = 0; i < events.size(); i++)
            {
                events.get(i).convertEventTimes("subtract", 3);
            }
        }
    }
}