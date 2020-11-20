package by.zabavskiy.springcourse.homework.userFriends;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UsersMain {
    public static void main(String[] args) {
        List<User> friendsOfBill = new ArrayList<>();
        friendsOfBill.add(new User(1L, "Poll", "Fisher", Collections.emptyList()));
        friendsOfBill.add(new User(2L, "Tom", "Fatty", Collections.emptyList()));

        List<User> friendsOfPoll = new ArrayList<>();
        friendsOfPoll.add(new User(1L, "Tom", "Fatty", Collections.emptyList()));
        friendsOfPoll.add(new User(2L, "Bill", "Dolby", friendsOfBill));


        List<User> friendsOfTom = new ArrayList<>();
        friendsOfTom.add(new User(1L, "Bill", "Dolby", friendsOfBill));
        friendsOfTom.add(new User(2L, "Poll", "Fisher", friendsOfPoll));


        List<User> userList = new ArrayList<>();
        userList.add(new User(1L, "Bill", "Dolby", friendsOfBill));
        userList.add(new User(2L, "Tom", "Fatty", friendsOfTom));
        userList.add(new User(3L, "Poll", "Fisher", friendsOfPoll));

        User ourUser = userList.get(2);

        getUserFriends(ourUser, 3);
        System.err.println("User in method getUserFriends(): " + ourUser);

    }

// the method with recursion
    private static void getUserFriends(User user, int innerLevel) {
        if (innerLevel > 1) {
            for (User friend : user.getFriends()) {
                getUserFriends(friend, innerLevel - 1);
            }
        }

//        realisation of the method getUserFriends() without realisation of the method getUserData() (only friends)
//        System.out.println(user.getFriends());

        getUserData(user, user.getFriends());
    }

    private static void getUserData(User user, List<User> friendsList) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("User: ");
        stringBuilder.append(user.getName());
        stringBuilder.append(" ");
        stringBuilder.append(user.getSurname());
        stringBuilder.append("\n");
        stringBuilder.append("Friends: ");
        for (User friend : friendsList) {
            stringBuilder.append(friend.getName());
            stringBuilder.append(" ");
            stringBuilder.append(friend.getSurname());
            stringBuilder.append("    ");
        }
        stringBuilder.append("\n");
        System.out.println(stringBuilder);
    }
}



