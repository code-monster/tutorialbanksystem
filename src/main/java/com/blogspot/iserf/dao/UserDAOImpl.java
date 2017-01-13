package com.blogspot.iserf.dao;

import com.blogspot.iserf.model.UserModel;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author alex
 */
public class UserDAOImpl implements UserDAO {

    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void save(UserModel user) {
        String query = "insert into users (id, firstname, lastname, address, dob) values (?,?,?,?,?)";

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        Object[] args = new Object[]{user.getUserId(), user.getFirstname(), user.getLastname(), user.getAddress(), user.getDob()};

        int out = jdbcTemplate.update(query, args);

        if (out != 0) {
            System.out.println("User saved with id=" + user.getUserId());
        } else {
            System.out.println("User save failed with id=" + user.getUserId());
        }
    }

    @Override
    public UserModel getById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(UserModel user) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<UserModel> getAll() {

        String query = "select * from users";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<UserModel> userList = new ArrayList<UserModel>();

        List<Map<String, Object>> userRows = jdbcTemplate.queryForList(query);

        for (Map<String, Object> userRow : userRows) {
            UserModel user = new UserModel();
            user.setUserId(Integer.parseInt(String.valueOf(userRow.get("id"))));
            user.setFirstname(String.valueOf(userRow.get("firstname")));
            user.setLastname(String.valueOf(userRow.get("lastname")));
            user.setAddress(String.valueOf(userRow.get("address")));
            user.setDob(stringToDate(String.valueOf(userRow.get("dob"))));
            userList.add(user);
        }
        return userList;
    }

    private Date stringToDate(String stringDate) {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date date=null;
        try {
            date = formatter.parse(stringDate);

        } catch (ParseException ex) {
            Logger.getLogger(UserDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        return sqlDate;

    }

}
