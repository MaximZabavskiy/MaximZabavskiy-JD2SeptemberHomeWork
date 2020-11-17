package by.zabavskiy.springcourse.homework.userFriends;

import java.util.List;

public class User {
    private Long id;

    private String name;

    private String surname;

    private List<User> friends;

    public List<User> getFriends() {
        return friends;
    }

    public User(Long id, String name, String surname, List<User> friends) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.friends = friends;
    }

    public User(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                '}';
    }

    // The beginning from the max level
    public List<User> getUserFriends(User user, int innerLevel) {
        if (innerLevel == 1) {
            for (int i = 0; i < friends.size(); i++) {
                for (User friend : friends) {
                    return friend.getFriends();
                }
            }
        }

        List<User> userFriends = user.getFriends().stream().map(User::getUserFriends(User user, innerLevel - 1));

        return userFriends;
    }
}




