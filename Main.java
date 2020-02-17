import java.util.*;

public class Main {

    public static void main(String args[])
    {
        Scanner input = new Scanner(System.in);
        CalendarsApp app = CalendarsApp.getInstance(input);

        System.out.println("Welcome to the Calendars App\n    (Enter 'Q' to quit) \n" +
                "----------------------------\nEnter a username:");
        String username = input.nextLine();
        if (username.equals("Q"))
        {
            System.exit(0);
        }
        System.out.println("Hello, " + username + "!");

        User newUser = new User(username);
        app.addUser(newUser);
        app.setCurrentUser(newUser);

        String command = "";
        while (!command.equals("Q"))
        {
            System.out.println("[HOME] Select a CALENDAR action " +
                    "(add/delete/update/view/share) or a USER action (create/switch):");
            command = input.nextLine();

            if (command.equalsIgnoreCase("add"))
            {
                app.addCalendar();
            }

            else if (command.equalsIgnoreCase("delete"))
            {
                app.deleteCalendar();
            }

            else if (command.equalsIgnoreCase("update"))
            {
                System.out.println("[CALENDAR] Select an update category (settings/events):");
                String updateOption = input.nextLine();
                if (updateOption.equalsIgnoreCase("settings"))
                {
                    app.updateCalendarSettings();
                }
                else if (updateOption.equalsIgnoreCase("events"))
                {
                    try {
                        app.updateCalendarEvents();
                    } catch (java.text.ParseException e) {
                        e.printStackTrace();
                    }
                }
            }

            else if (command.equalsIgnoreCase("view"))
            {
                System.out.println("[VIEW] Select an view option (day/month/year):");
                String viewOption = input.nextLine();
                app.displayView(viewOption);
            }

            else if (command.equalsIgnoreCase("share"))
            {
                app.shareCalendar();
            }

            else if (command.equalsIgnoreCase("create"))
            {
                app.createNewUser();
            }

            else if (command.equalsIgnoreCase("switch"))
            {
                app.switchCurrentUser();
            }
        }
    }
}