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
}





