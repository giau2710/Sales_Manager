package repository;

public interface IProduct {
    void add();
    void remove();
    void update();
    void list();
    void search(String name);
    void searchSuggestions(String name);

}


