package web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.URI;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

@Controller
public class WebController {

    @RequestMapping(value = "/start", method = RequestMethod.GET)
    public
    @ResponseBody
    String start(@RequestParam(value = "bname", required = false) String bname) {
        System.out.println("Start");
        Connection connect = mySqlConnection();
        try {
            if(connect == null) {
                System.out.println("Null connect");
            }
            if(connect.isClosed()) {
                System.out.println("Connection closed");
                return null;
            }

            ResultSet resultSet = connect.prepareStatement("SELECT * FROM beerinfo;").executeQuery();

            BeerService bs = new BeerService();

            ArrayList<BeerInfo> listBeers = new ArrayList<BeerInfo>();

            while(resultSet.next()){
                listBeers.add(bs.convertResultSetToBeerInfo(resultSet));
            }
            connect.close();
            return "";

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Connection mySqlConnection() {
        Connection mySql = null;
        System.out.println(System.getenv("CLEARDB_DATABASE_URL"));
        try {
            URI dbUri = new URI(System.getenv("CLEARDB_DATABASE_URL"));
            String username = dbUri.getUserInfo().split(":")[0];
            String password = dbUri.getUserInfo().split(":")[1];
            String dbUrl = "jdbc:mysql://" + dbUri.getHost() + dbUri.getPath();
            mySql = DriverManager.getConnection(dbUrl, username, password);
        } catch (Exception e) {
            System.out.println("Issue");
            e.printStackTrace();
        }
        return mySql;
    }