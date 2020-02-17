import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Event {
    private int eventID;
    private String eventName;
    private String date;
    private String startTime;
    private String endTime;
    private boolean repeatable;
    public static int currentEventID;

    Event()
    {
        eventID = ++currentEventID;
    }

    Event(String newEventName, String newDate, String newStartTime, String newEndTime, boolean newRepeatable)
    {
        eventID = ++currentEventID;
        eventName = newEventName;
        date = newDate;
        startTime= newStartTime;
        endTime = newEndTime;
        repeatable = newRepeatable;
    }

    public int getEventID()
    {
        return eventID;
    }

    public void decrementEventID()
    {
        eventID = --eventID;
    }

    public String getEventName()
    {
        return eventName;
    }

    public void setEventName(String newEventName)
    {
        eventName = newEventName;
    }

    public String getDate()
    {
        return date;
    }

    public void setDate(SimpleDateFormat sdf, String dateStr) throws ParseException {
        Date dateObj = sdf.parse(dateStr);
        date = dateObj.toString().substring(0, 10).strip() + ", " + dateObj.toString().substring(24);
    }

    public String getYear()
    {
        return date.substring(12);
    }

    public String getMonth()
    {
        return (date.substring(4, 7) + " " + getYear());
    }

    public void convertEventTimes(String action, int numHours)
    {
        String startHourStr = startTime.substring(0, 2);
        String endHourStr = endTime.substring(0, 2);
        int startHourInt = Integer.parseInt(startHourStr);
        int endHourInt = Integer.parseInt(endHourStr);
        if (action == "add")
        {
            startHourInt += numHours;
            endHourInt += numHours;
        }
        else if (action == "subtract")
        {
            startHourInt -= numHours;
            endHourInt -= numHours;
        }
        String newStartTime = "";
        String newEndTime = "";
        if (startHourInt < 10)
        {
            newStartTime = ("0" + startHourInt + startTime.substring(2));
        }
        else if (startHourInt >= 10)
        {
            newStartTime = (startHourInt + startTime.substring(2));
        }

        if (endHourInt < 10)
        {
            newEndTime = ("0" + endHourInt + endTime.substring(2));
        }
        else if (endHourInt >= 10)
        {
            newEndTime = (endHourInt + endTime.substring(2));
        }
        startTime = newStartTime;
        endTime = newEndTime;
    }

    public String getStartTime()
    {
        return startTime;
    }

    public void setStartTime(SimpleDateFormat sdf, String timeStr) throws ParseException
    {
        Date dateObj = sdf.parse(timeStr);
        startTime = dateObj.toString().substring(11,16);
    }

    public String getEndTime()
    {
        return endTime;
    }

    public void setEndTime(SimpleDateFormat sdf, String timeStr) throws ParseException
    {
        Date dateObj = sdf.parse(timeStr);
        endTime = dateObj.toString().substring(11,16);
    }

    public boolean getRepeatable()
    {
        return repeatable;
    }

    public String getRepeatableStr()
    {
        String repeatableStr = "";
        if (repeatable)
        {
            repeatableStr = "Repeats";
        }
        else if (!repeatable)
        {
            repeatableStr = "Does Not Repeat";
        }
        return repeatableStr;
    }

    public void setRepeatable(String newRepeatable)
    {
        if (newRepeatable.equalsIgnoreCase("yes"))
        {
            repeatable = true;
        }
        else if (newRepeatable.equalsIgnoreCase("no"))
        {
            repeatable = false;
        }
    }
}