package repository;

import model.User;

public interface IUser  {
    void add(User user);
    void remove(User user);
    void update(User user);
    void list(User user);
    void search(User user);
}
