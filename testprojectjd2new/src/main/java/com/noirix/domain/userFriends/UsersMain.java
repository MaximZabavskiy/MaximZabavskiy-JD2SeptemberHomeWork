package by.zabavskiy.springcourse.homework.userFriends;

import java.util.ArrayList;
import java.util.List;

public class UsersMain {
    public static void main(String[] args) {


        List<User> friendsOfBill = new ArrayList<>();
        friendsOfBill.add(new User(1L, "Poll", "Fisher", null));
        friendsOfBill.add(new User(2L, "Tom", "Fatty", null));

        List<User> friendsOfTom = new ArrayList<>();
        friendsOfTom.add(new User(1L, "Bill", "Dolby", friendsOfBill));
        friendsOfTom.add(new User(2L, "Poll", "Fisher", null));

        List<User> friendsOfPoll = new ArrayList<>();
        friendsOfBill.add(new User(1L, "Poll", "Fisher", null));
        friendsOfBill.add(new User(2L, "Kim", "Rider", null));


        List<User> userList = new ArrayList<>();
        userList.add(new User (1L,"Tom","Fatty", null));
        userList.add(new User (2L,"Bill","Dolby", friendsOfBill));
        userList.add(new User (3L,"Poll","Fisher", friendsOfPoll));

    }
}
