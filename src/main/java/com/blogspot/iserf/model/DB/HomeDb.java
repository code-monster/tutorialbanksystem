package com.blogspot.iserf.model.DB;

import com.blogspot.iserf.model.User;
import com.blogspot.iserf.utility.DB;
import com.mysql.jdbc.Statement;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by alex on 10/4/15.
 */
public class HomeDb {


    public static ArrayList<User> getUserList() {

        ClassPathXmlApplicationContext contextBean = new ClassPathXmlApplicationContext("app-beans.xml");
        DB connect = (DB) contextBean.getBean("DB");

        ArrayList<User> userList = new ArrayList<User>();

        try {

            Statement stmt = null;
            Connection connection = connect.getMysqlConnections();

            // Execute SQL query
            stmt = (Statement) connection.createStatement();
            String sql;
            sql = "SELECT * FROM `users`";


            sql = "SELECT users.*, SUM(transactions.money) AS total "
                    + "FROM users "
                    + "LEFT JOIN  users_accounts "
                    + "ON (users_accounts.user_id = users.id) "
                    + "LEFT JOIN transactions "
                    + "ON (transactions.account_id = users_accounts.account_id) "
                    + "GROUP BY users.firstname;";

            ResultSet rs = stmt.executeQuery(sql);

            // Extract data from result set
            while (rs.next()) {

                User user = new User();

                user.setUserId(rs.getInt("id"));
                user.setFirstname(rs.getString("firstname"));
                user.setLastname(rs.getString("lastname"));
                user.setAddress(rs.getString("address"));
                user.setDob(rs.getDate("dob").toString());
                user.setTotalMoney(rs.getDouble("total"));

                userList.add(user);
            }

            // Clean-up environment
            rs.close();
            stmt.close();

            connection.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return userList;
    }
}
