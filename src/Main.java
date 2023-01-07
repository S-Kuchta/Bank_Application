import DataSQL.AccountSql;
import Layout.*;

public class Main {


    public static void main(String[] args) {

        AccountSql datasource = new AccountSql();
        if (!datasource.openConnection()) {
            System.out.println("Can't open datasource or sqlite-jdbc-3.34.0.jar not found. You can add jdbc from SQL folder in project folder.");
        }


        MainMenuLayout mainMenuLayout = new MainMenuLayout();

        datasource.closeConnection();
    }
}