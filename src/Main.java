import DataSQL.AccountSql;
import Layout.*;

public class Main {


    public static void main(String[] args) {

        AccountSql datasource = new AccountSql();
        if (!datasource.openConnection()) {
            System.out.println("Can't open datasource.");
        }


        MainMenuLayout mainMenuLayout = new MainMenuLayout();

        datasource.closeConnection();
    }
}