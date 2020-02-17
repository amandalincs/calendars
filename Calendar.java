import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Calendar {
    private int calendarID;
    private String calendarName;
    private boolean isPrivate;
    private String timeZone;
    private String theme;
    private ArrayList<Event> events;
    private ArrayList<User> sharedUsers;
    private Map<String, ArrayList<Event>> dayEventMap;
    private Map<String, ArrayList<Event>> monthEventMap;
    private Map<String, ArrayList<Event>> yearEventMap;
    public static int currentCalendarID;

    Calendar()
    {
        calendarID = ++currentCalendarID;
        events = new ArrayList<Event>();
        sharedUsers = new ArrayList<User>();
        dayEventMap = new HashMap<String, ArrayList<Event>>();
        monthEventMap = new HashMap<String, ArrayList<Event>>();
        yearEventMap = new HashMap<String, ArrayList<Event>>();
    }

    Calendar(String newCalendarName, boolean newIsPrivate, String newTimeZone, String newTheme)
    {
        calendarID = ++currentCalendarID;
        calendarName = newCalendarName;
        isPrivate = newIsPrivate;
        timeZone = newTimeZone;
        theme = newTheme;
        events = new ArrayList<Event>();
        sharedUsers = new ArrayList<User>();
        dayEventMap = new HashMap<String, ArrayList<Event>>();
        monthEventMap = new HashMap<String, ArrayList<Event>>();
        yearEventMap = new HashMap<String, ArrayList<Event>>();
    }

    public int getCalendarID()
    {
        return calendarID;
    }

    public void decrementCalendarID()
    {
        calendarID = --currentCalendarID;
    }

    public String getCalendarName()
    {
        return calendarName;
    }

    public void setCalendarName(String newCalendarName)
    {
        calendarName = newCalendarName;
    }

    public boolean getPrivacy()
    {
        return isPrivate;
    }

    public String getPrivacyStr()
    {
        String privacyStr = "";
        if (isPrivate)
        {
            privacyStr = "private";
        }
        else if (!isPrivate)
        {
            privacyStr = "public";
        }
        return privacyStr;
    }

    public void setPrivacy(String privacy)
    {
        if (privacy.equalsIgnoreCase("private"))
        {
            isPrivate = true;
        }
        else if (privacy.equalsIgnoreCase("public"))
        {
            isPrivate = false;
        }
    }

    public String getTimeZone()
    {
        return timeZone.toUpperCase();
    }

    public void setTimeZone(String newTimeZone)
    {
        timeZone = newTimeZone;
    }

    public void updateAllEventTimes(String newTimeZone)
    {
        if ((timeZone.equalsIgnoreCase("EST"))
        && (newTimeZone.equalsIgnoreCase("PST")))
        {
            for (int i = 0; i < events.size(); i++) {
                events.get(i).convertEventTimes("subtract", 3);
            }
        }

        else if ((timeZone.equalsIgnoreCase("PST"))
                && (newTimeZone.equalsIgnoreCase("EST")))
        {
            for (int i = 0; i < events.size(); i++) {
                events.get(i).convertEventTimes("add", 3);
            }
        }
    }

    public String getTheme()
    {
        return theme.toLowerCase();
    }

    public void setTheme(String newTheme)
    {
        theme = newTheme;
    }

    public ArrayList<Event> getEvents()
    {
        return events;
    }

    public void addEvent(Event newEvent)
    {
        events.add(newEvent);
        dayEventMap.put(newEvent.getDate(), new ArrayList<Event>());
        yearEventMap.put(newEvent.getYear(), new ArrayList<Event>());
        monthEventMap.put(newEvent.getMonth(), new ArrayList<Event>());
    }

    public void deleteEvent(Event e)
    {
        int eventID = e.getEventID();

        dayEventMap.get(e.getDate()).remove(e);
        if (dayEventMap.get(e.getDate()).size() == 0)
        {
            dayEventMap.remove(e.getDate());
        }

        monthEventMap.get(e.getMonth()).remove(e);
        if (monthEventMap.get(e.getMonth()).size() == 0)
        {
            monthEventMap.remove(e.getMonth());
        }

        yearEventMap.get(e.getYear()).remove(e);
        if (yearEventMap.get(e.getYear()).size() == 0)
        {
            yearEventMap.remove(e.getYear());
        }

        events.remove(e);
        if (eventID < getNumEvents())
        {
            e.decrementEventID();
        }
    }

    public Event getEventByID(int eventID)
    {
        return getEvents().get(eventID);
    }

    public int getNumEvents()
    {
        return events.size();
    }

    public ArrayList<User> getSharedUsers()
    {
        return sharedUsers;
    }

    public void addSharedUser(User newUser)
    {
        if (!sharedUsers.contains(newUser))
        {
            sharedUsers.add(newUser);
        }
        else
        {
            System.out.println("ALREADY SHARED WITH " + newUser.getUsername());
            System.out.println();
        }
    }

    public int getNumSharedUsers()
    {
        return sharedUsers.size();
    }

    public void setDayEventMap()
    {
        for(Map.Entry<String, ArrayList<Event>> entry: dayEventMap.entrySet())
        {
            for (int i = 0; i < events.size(); i++)
            {
                if ((events.get(i).getDate().equalsIgnoreCase(entry.getKey()))
                && (!dayEventMap.get(events.get(i).getDate()).contains(events.get(i))))
                {
                    dayEventMap.get(events.get(i).getDate()).add(events.get(i));
                }
            }
        }
    }

    public Map<String, ArrayList<Event>> getDayEventMap()
    {
        return dayEventMap;
    }

    public void setMonthEventMap()
    {
        for(Map.Entry<String, ArrayList<Event>> entry: monthEventMap.entrySet())
        {
            for (int i = 0; i < events.size(); i++)
            {
                if ((events.get(i).getMonth().equalsIgnoreCase(entry.getKey()))
                        && (!monthEventMap.get(events.get(i).getMonth()).contains(events.get(i))))
                {
                    monthEventMap.get(events.get(i).getMonth()).add(events.get(i));
                }
            }
        }
    }

    public Map<String, ArrayList<Event>> getMonthEventMap()
    {
        return monthEventMap;
    }

    public void setYearEventMap()
    {
        for(Map.Entry<String, ArrayList<Event>> entry: yearEventMap.entrySet())
        {
            for (int i = 0; i < events.size(); i++)
            {
                if ((events.get(i).getYear().equalsIgnoreCase(entry.getKey()))
                        && (!yearEventMap.get(events.get(i).getYear()).contains(events.get(i))))
                {
                    yearEventMap.get(events.get(i).getYear()).add(events.get(i));
                }
            }
        }
    }

    public Map<String, ArrayList<Event>> getYearEventMap()
    {
        return yearEventMap;
    }
}