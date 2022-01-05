import views.MainView;

import java.text.ParseException;

public class Run {
    public static void main(String[] args) throws ParseException {
        MainView mainView = new MainView();
        mainView.getGreeting();
        mainView.useMain();

    }
}


