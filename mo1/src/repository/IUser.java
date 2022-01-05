package repository;

import model.Role;
import model.User;

import java.text.ParseException;

public interface IUser {
    <T> void add(T newUser);

    void remove(String username, Role role) throws ParseException;

    <T> void update(T obj, Role role,String username) throws ParseException;


    void list(User user);

    void search(String options);
}
