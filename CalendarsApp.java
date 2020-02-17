import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class CalendarsApp {
    private ArrayList<User> users;
    private User currentUser;
    private Scanner input;

    CalendarsApp(Scanner newInput)
    {
        users = new ArrayList<User>();
        input = newInput;
    }

    public ArrayList<User> getUsers()
    {
        return users;
    }

    public int getNumUsers()
    {
        return users.size();
    }

    public User getCurrentUser()
    {
        return currentUser;
    }

    public void setCurrentUser(User newUser)
    {
        currentUser = newUser;
    }

    public void addUser(User newUser)
    {
        users.add(newUser);
    }

    public void addCalendar()
    {
        Calendar newCalendar = new Calendar();
        System.out.println("[CALENDAR] Give your Calendar a name:");
        String calendarName = input.nextLine();
        newCalendar.setCalendarName(calendarName);
        System.out.println("[CALENDAR] Select a privacy option (public/private): ");
        String privacyStr = input.nextLine();
        newCalendar.setPrivacy(privacyStr);
        System.out.println("[CALENDAR] Select a time zone (EST/PST):");
        String timeZone = input.nextLine();
        newCalendar.setTimeZone(timeZone);
        System.out.println("[CALENDAR] Select a theme (light/dark):");
        String theme = input.nextLine();
        newCalendar.setTheme(theme);
        if (privacyStr.equalsIgnoreCase("private"))
        {
            newCalendar.addSharedUser(currentUser);
        }
        else if (privacyStr.equalsIgnoreCase("public"))
        {
            for (int i = 0; i < users.size(); i++)
            {
                newCalendar.addSharedUser(users.get(i));
            }
        }
        currentUser.addCalendar(newCalendar);
        System.out.println("CALENDAR ADDED: " + getCalendarDetailsStr(newCalendar));

        printCurrentUsersCalendars();
        System.out.println();
    }

    public void deleteCalendar()
    {
        int numCalendars = currentUser.getNumCalendars();
        if (numCalendars == 0) {
            System.out.println("No Calendars to delete!");
        }
        else {
            printCurrentUsersCalendars();
            System.out.println("[CALENDAR] Enter the number of the Calendar you wish to delete:");
            int id = Integer.parseInt(input.nextLine());
            Calendar c = currentUser.getCalendarByID(id-1);
            String calendarDetailsStr = getCalendarDetailsStr(c);
            currentUser.deleteCalendar(c);

            System.out.println("CALENDAR DELETED: " + calendarDetailsStr);
            printCurrentUsersCalendars();
        }
        System.out.println();
    }

    public void updateCalendarSettings()
    {
        int numCalendars = currentUser.getNumCalendars();
        if (numCalendars == 0) {
            System.out.println("No Calendars to update!");
        }
        else {
            printCurrentUsersCalendars();
            System.out.println("[SETTINGS] Enter the number of the Calendar whose settings you wish to update:");
            int id = Integer.parseInt(input.nextLine());
            Calendar c = currentUser.getCalendarByID(id-1);
            String calendarDetailsStr = getCalendarDetailsStr(c);
            System.out.println("UPDATING SETTINGS FOR: " + calendarDetailsStr);
            System.out.println("[SETTINGS] Select the setting you wish to update (name/privacy/timezone/theme):");
            String setting = input.nextLine();
            if (setting.equalsIgnoreCase("name"))
            {
                System.out.println("[SETTINGS] Enter a new Calendar name:");
                String newName = input.nextLine();
                c.setCalendarName(newName);
                System.out.println("CALENDAR NAME CHANGED: " + calendarDetailsStr + " --> " + getCalendarDetailsStr(c));
            }
            else if (setting.equalsIgnoreCase("privacy"))
            {
                System.out.println("[SETTINGS] Select a new privacy option (public/private):");
                String newPrivacy= input.nextLine();
                c.setPrivacy(newPrivacy);
                System.out.println("CALENDAR PRIVACY CHANGED: "+ calendarDetailsStr + " --> " + getCalendarDetailsStr(c));
            }
            else if (setting.equalsIgnoreCase("timezone"))
            {
                System.out.println("[SETTINGS] Select a new time zone (EST/PST):");
                String newTimeZone= input.nextLine();
                c.updateAllEventTimes(newTimeZone);
                c.setTimeZone(newTimeZone);
                System.out.println("CALENDAR TIME ZONE CHANGED: "+ calendarDetailsStr + " --> " + getCalendarDetailsStr(c));
                System.out.println("UPDATING ALL EVENT TIMES...");
                printCurrentUsersEvents(c);
                System.out.println();
            }
            else if (setting.equalsIgnoreCase("theme"))
            {
                System.out.println("[SETTINGS] Select a new theme (light/dark):");
                String newTheme= input.nextLine();
                c.setTheme(newTheme);
                System.out.println("CALENDAR THEME CHANGED: "+ calendarDetailsStr + " --> " + getCalendarDetailsStr(c));
            }
            printCurrentUsersCalendars();
        }
        System.out.println();
    }

    public void shareCalendar()
    {
        int numCalendars = currentUser.getNumCalendars();
        if (numCalendars == 0) {
            System.out.println("No Calendars to share!");
        }
        else {
            printCurrentUsersCalendars();
            System.out.println("[SHARE] Enter the number of the Calendar you wish to share:");
            int id = Integer.parseInt(input.nextLine());
            Calendar c = currentUser.getCalendarByID(id-1);
            String calendarDetailsStr = getCalendarDetailsStr(c);
            System.out.println("UPDATING PERMISSIONS FOR: " + calendarDetailsStr);
            printAllUsers();
            System.out.println("[SHARE] Enter the number of the User you wish to share with:");
            int uid = Integer.parseInt(input.nextLine());
            User u = users.get(uid-1);
            c.addSharedUser(u);
            System.out.println("CALENDAR SHARED WITH USER: " + u.getUsername());

            printCurrentUsersCalendars();
        }
        System.out.println();
    }

    public void updateCalendarEvents() throws ParseException {
        int numCalendars = currentUser.getNumCalendars();
        if (numCalendars == 0) {
            System.out.println("No Calendars to update!");
        }
        else {
            printCurrentUsersCalendars();
            System.out.println("[EVENT] Enter the number of the Calendar whose events you wish to update:");
            int id = Integer.parseInt(input.nextLine());
            Calendar c = currentUser.getCalendarByID(id-1);
            printCurrentUsersEvents(c);
            System.out.println("[EVENT] Select an EVENT action (add/delete/edit):");
            String eventAction = input.nextLine();
            if (eventAction.equalsIgnoreCase("add"))
            {
                addCalendarEvent(c);
            }

            else if (eventAction.equalsIgnoreCase("delete"))
            {
                deleteCalendarEvent(c);
            }

            else if (eventAction.equalsIgnoreCase("edit"))
            {
                editCalendarEvents(c);
            }
            printCurrentUsersEvents(c);
        }
        System.out.println();
    }

    public void displayView(String view)
    {
        int numCalendars = currentUser.getNumCalendars();
        if (numCalendars == 0) {
            System.out.println("No Calendars to view!");
            System.out.println();
        }
        else {
            printCurrentUsersCalendars();
            System.out.println("[VIEW] Enter the number of the Calendar you wish to view:");
            int id = Integer.parseInt(input.nextLine());
            Calendar c = currentUser.getCalendarByID(id-1);

            if (view.equalsIgnoreCase("day"))
            {
                printEventsByDay(c);
            }

            else if (view.equalsIgnoreCase("month"))
            {
                printEventsByMonth(c);
            }

            else if (view.equalsIgnoreCase("year"))
            {
                printEventsByYear(c);
            }
        }
    }

    public void createNewUser()
    {
        System.out.println("[USER] Creating a new User...");
        System.out.println("[USER] Enter a username:");
        String newName = input.nextLine();
        User u = new User(newName);
        addUser(u);
        for (int i = 0; i < currentUser.getCalendars().size(); i++)
        {
            if (currentUser.getCalendars().get(i).getPrivacy() == false)
            {
                currentUser.getCalendars().get(i).addSharedUser(u);
            }
        }
        System.out.println("USER CREATED: " + u.getUsername());
        printAllUsers();
        System.out.println();
    }

    public void switchCurrentUser()
    {
        printAllUsers();
        System.out.println("[USER] Enter the number of the User you wish to switch to:");
        int id = Integer.parseInt(input.nextLine());
        User u = users.get(id-1);
        setCurrentUser(u);
        System.out.println("SWITCHED TO USER: " + u.getUsername());
    }

    private void addCalendarEvent(Calendar c) throws ParseException
    {
        Event newEvent = new Event();
        System.out.println("[EVENT] Enter the Event name:");
        String eventName = input.nextLine();
        newEvent.setEventName(eventName);
        System.out.println("[EVENT] Enter the Event date (MM/dd/yyyy):");
        String dateStr = input.nextLine();
        SimpleDateFormat sdfDate = new SimpleDateFormat("MM/dd/yyyy");
        newEvent.setDate(sdfDate, dateStr);
        System.out.println("[EVENT] Enter the Event start time (HH:mm):");
        String timeStr = input.nextLine();
        SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm");
        newEvent.setStartTime(sdfTime, timeStr);
        System.out.println("[EVENT] Enter the Event end time (HH:mm):");
        String timeStr2 = input.nextLine();
        SimpleDateFormat sdfTime2 = new SimpleDateFormat("HH:mm");
        newEvent.setEndTime(sdfTime2, timeStr2);
        System.out.println("[EVENT] Does this event repeat? (yes/no):");
        String repeatableStr = input.nextLine();
        newEvent.setRepeatable(repeatableStr);
        c.addEvent(newEvent);
        System.out.println("EVENT ADDED: " + getEventDetailsStr(newEvent));
    }

    private void deleteCalendarEvent(Calendar c)
    {
        int numEvents = c.getNumEvents();
        if (numEvents == 0) {
            System.out.println("No Events to delete!");
        }
        else {
            printCurrentUsersEvents(c);
            System.out.println("[EVENT] Enter the number of the Event you wish to delete:");
            int id = Integer.parseInt(input.nextLine());
            Event e = c.getEventByID(id-1);
            String eventDetailsStr = getEventDetailsStr(e);
            c.deleteEvent(e);
            System.out.println("EVENT DELETED: " + eventDetailsStr);
        }
    }

    private void editCalendarEvents(Calendar c) throws ParseException
    {
        int numEvents = c.getNumEvents();
        if (numEvents == 0) {
            System.out.println("No Events to edit!");
        }
        else {
            printCurrentUsersEvents(c);
            System.out.println("[EVENT] Enter the number of the Event you wish to edit:");
            int id = Integer.parseInt(input.nextLine());
            Event e = c.getEventByID(id-1);
            String eventDetailsStr = getEventDetailsStr(e);
            System.out.println("EDITING EVENT: " + eventDetailsStr);
            System.out.println("[EVENT] Select the info you wish to edit (name/date/start/end/repeat):");
            String info = input.nextLine();
            if (info.equalsIgnoreCase("name"))
            {
                System.out.println("[EVENT] Enter a new Event name:");
                String newName = input.nextLine();
                e.setEventName(newName);
                System.out.println("EVENT NAME CHANGED: " + eventDetailsStr + " --> " + getEventDetailsStr(e));
            }
            else if (info.equalsIgnoreCase("date"))
            {
                System.out.println("[EVENT] Enter a new date (MM/dd/yyyy):");
                String newDate= input.nextLine();
                SimpleDateFormat sdfDate = new SimpleDateFormat("MM/dd/yyyy");
                e.setDate(sdfDate, newDate);
                System.out.println("EVENT DATE CHANGED: "+ eventDetailsStr + " --> " + getEventDetailsStr(e));
            }
            else if (info.equalsIgnoreCase("start"))
            {
                System.out.println("[EVENT] Enter a new start time (HH:mm):");
                String timeStr = input.nextLine();
                SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm");
                e.setStartTime(sdfTime, timeStr);
                System.out.println("EVENT START TIME CHANGED: "+ eventDetailsStr + " --> " + getEventDetailsStr(e));
            }
            else if (info.equalsIgnoreCase("end"))
            {
                System.out.println("[EVENT] Enter a new end time (HH:mm):");
                String timeStr2 = input.nextLine();
                SimpleDateFormat sdfTime2 = new SimpleDateFormat("HH:mm");
                e.setEndTime(sdfTime2, timeStr2);
                System.out.println("EVENT END TIME CHANGED: "+ eventDetailsStr + " --> " + getEventDetailsStr(e));
            }
            else if (info.equalsIgnoreCase("repeat"))
            {
                System.out.println("[EVENT] Select a new repeat option (yes/no):");
                String repeatStr = input.nextLine();
                e.setRepeatable(repeatStr);
                System.out.println("EVENT REPEAT CHANGED: "+ eventDetailsStr + " --> " + getEventDetailsStr(e));
            }
        }
        System.out.println();
    }

    private String getCalendarDetailsStr(Calendar c)
    {
        return ("(" + c.getCalendarName() + ": " + c.getPrivacyStr()
                + ", " + c.getTimeZone() + ", " + c.getTheme() + ") - SHARED WITH: " + getSharedUsersStr(c));
    }

    private String getSharedUsersStr(Calendar c)
    {
        String sharedUsersStr = "";
        if (c.getNumSharedUsers() == 1)
        {
            sharedUsersStr = c.getSharedUsers().get(0).getUsername();
        }
        else if (c.getNumSharedUsers() > 1)
        {
            for (int i = 0; i < c.getNumSharedUsers()-1; i++)
            {
                sharedUsersStr += c.getSharedUsers().get(i).getUsername();
                sharedUsersStr += ", ";
            }
            sharedUsersStr += c.getSharedUsers().get(c.getNumSharedUsers()-1).getUsername();
        }
        return sharedUsersStr;
    }

    private String getEventDetailsStr(Event e)
    {
        return ("(" + e.getEventName() + ": " + e.getDate() + " | " + e.getStartTime() + "-"
                + e.getEndTime() + " | " + e.getRepeatableStr() + ")");
    }

    private void printAllUsers() {
        System.out.println("ALL USERS:");
        int numUsers = getNumUsers();
        for (int i = 0; i < numUsers; i++) {
            User u = users.get(i);
            int indexStr = i+1;
            System.out.println(indexStr + ". " + u.getUsername());
        }
    }

    private void printCurrentUsersCalendars() {
        System.out.println(currentUser.getUsername() + "'s CALENDARS:");
        int numCalendars = currentUser.getNumCalendars();
        if (numCalendars == 0) {
            System.out.println("-----NONE-----");
        }
        else {
            for (int i = 0; i < numCalendars; i++) {
                Calendar c = currentUser.getCalendars().get(i);
                int indexStr = i+1;
                System.out.println(indexStr + ". " + getCalendarDetailsStr(c));
            }
        }
    }

    private void printCurrentUsersEvents(Calendar c) {
        System.out.println(currentUser.getUsername() + "'s " + c.getCalendarName() + " EVENTS:");
        int numEvents = c.getNumEvents();
        if (numEvents == 0) {
            System.out.println("-----NONE-----");
        }
        else {
            for (int i = 0; i < numEvents; i++) {
                Event e = c.getEvents().get(i);
                int indexStr = i+1;
                System.out.println(indexStr + ". " + getEventDetailsStr(e));
            }
        }
    }

    private void printEventsByDay(Calendar c)
    {
        System.out.println("-----DAY VIEW-----");
        System.out.println(currentUser.getUsername() + "'s " + c.getCalendarName() + " EVENTS:");
        int numEvents = c.getNumEvents();
        if (numEvents == 0) {
            System.out.println("-----NONE-----");
        }
        c.setDayEventMap();
        for (Map.Entry<String, ArrayList<Event>> entry: c.getDayEventMap().entrySet())
        {
            System.out.println(entry.getKey() + ":\n-----------------");
            for (int i = 0; i < entry.getValue().size(); i++)
            {
                System.out.println("\t" + getEventDetailsStr(entry.getValue().get(i)));
            }
            System.out.println();
        }
    }

    private void printEventsByMonth(Calendar c)
    {
        System.out.println("-----MONTH VIEW-----");
        System.out.println(currentUser.getUsername() + "'s " + c.getCalendarName() + " EVENTS:");
        int numEvents = c.getNumEvents();
        if (numEvents == 0) {
            System.out.println("-----NONE-----");
        }
        c.setMonthEventMap();
        for (Map.Entry<String, ArrayList<Event>> entry: c.getMonthEventMap().entrySet())
        {
            System.out.println(entry.getKey() + ":\n-----------");
            for (int i = 0; i < entry.getValue().size(); i++)
            {
                System.out.println("\t" + getEventDetailsStr(entry.getValue().get(i)));
            }
            System.out.println();
        }
    }

    private void printEventsByYear(Calendar c)
    {
        System.out.println("-----YEAR VIEW-----");
        System.out.println(currentUser.getUsername() + "'s " + c.getCalendarName() + " EVENTS:");
        int numEvents = c.getNumEvents();
        if (numEvents == 0) {
            System.out.println("-----NONE-----");
        }
        c.setYearEventMap();
        for (Map.Entry<String, ArrayList<Event>> entry: c.getYearEventMap().entrySet())
        {
            System.out.println(entry.getKey() + ":\n--------");
            for (int i = 0; i < entry.getValue().size(); i++)
            {
                System.out.println("\t" + getEventDetailsStr(entry.getValue().get(i)));
            }
            System.out.println();
        }
    }
}