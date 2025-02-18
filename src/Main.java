import GUI.HomePage;
import models.Receipt;
import networking.PopulateDashboard;
public class Main {
    public static void main(String[] args) {
        HomePage gui = new HomePage();
        PopulateDashboard populateDashboard = new PopulateDashboard();
        populateDashboard.getData();

    }
}