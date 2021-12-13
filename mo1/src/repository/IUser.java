package repository;

import model.User;
import model.UserType;

import java.text.ParseException;

public interface IUser  {
    void add(UserType userType) throws ParseException;
    void remove(User user);
    void update(User user);
    void list(User user);
    void search(User user);
}
