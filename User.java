import java.util.*;

public class User {
    private int userID;
    private String username;
    private ArrayList<Calendar> calendars;
    public static int currentUserID;

    User(String newUserName)
    {
        userID = ++currentUserID;
        username = newUserName;
        calendars = new ArrayList<Calendar>();
    }

    public int getUserID()
    {
        return userID;
    }

    public String getUsername()
    {
        return username;
    }

    public ArrayList<Calendar> getCalendars()
    {
        return calendars;
    }

    public void addCalendar(Calendar c)
    {
        calendars.add(c);
    }

    public void deleteCalendar(Calendar c)
    {
        int calendarID = c.getCalendarID();
        calendars.remove(c);
        if (calendarID < getNumCalendars())
        {
            c.decrementCalendarID();
        }
    }

    public Calendar getCalendarByID(int calendarID)
    {
        return getCalendars().get(calendarID);
    }

    public int getNumCalendars()
    {
        return calendars.size();
    }
}