package repository;

import model.Role;


import java.text.ParseException;

public interface IUser {
    <T> void add(T newUser);

    <T> void update(T obj, Role role,String username) throws ParseException;

}
