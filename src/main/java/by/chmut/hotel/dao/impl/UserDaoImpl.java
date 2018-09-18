package by.chmut.hotel.dao.impl;

import by.chmut.hotel.dao.AbstractDao;
import by.chmut.hotel.dao.DAOException;
import by.chmut.hotel.dao.UserDao;
import by.chmut.hotel.bean.User;
import by.chmut.hotel.dao.rememberme.CookieUtils;
import lombok.Data;

import javax.servlet.http.Cookie;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static by.chmut.hotel.controller.command.impl.constant.Constants.REMEMBER_ME_COOKIE;

@Data

public class UserDaoImpl extends AbstractDao implements UserDao {

    private CookieUtils cookieUtils;// = CookieUtils.getInstance();

    private static final String selectUserByLogin = "SELECT Users.id,Users.login,Users.password,Users.name," +
            "Users.lastname,Users.role FROM Users WHERE Users.login = ?";
    private static final String selectUserById = "SELECT Users.id,Users.login,Users.password,Users.name,Users.lastname," +
            "Users.role,Contacts.email,Contacts.telephone,Contacts.country,Contacts.city,Contacts.address," +
            "Contacts.zip FROM Users,Contacts WHERE Users.contact_id = Contacts.id HAVING Users.id = ?";
    private static final String addContact = "INSERT INTO Contacts (email, telephone, country, city, address, zip) VALUES (?,?,?,?,?,?)";
    private static final String addUser = "INSERT INTO Users (login, password, name, lastname,role,contact_id) VALUES (?,?,?,?,?,?)";
    private static final String getContactID = "SELECT contact_id FROM Users WHERE id=?";
    private static final String updateUser = "UPDATE Contacts SET email=?, telephone=?, country=?, city=?, address=?, zip=? WHERE id=?";
    private static final String deleteUser = "DELETE FROM Users WHERE id=?";
    private static final String getLoginAndToken = "SELECT username, token FROM persistent_logins WHERE series = ?";
    private static final String addLoginAndToken = "INSERT INTO persistent_logins (username, series, token, last_used) VALUES (?, ?, ?, now())";


    public User getUser(String login) throws DAOException {
        try {
            PreparedStatement ps = prepareStatement(selectUserByLogin);
            ps.setString(1, login);
            ResultSet rs = ps.executeQuery();
            User user = new User();
            while (rs.next()) {
                user.setId(rs.getInt(1));
                user.setLogin(rs.getString(2));
                user.setPassword(rs.getString(3));
                user.setName(rs.getString(4));
                user.setLastName(rs.getString(5));
                user.setRole(rs.getString(6));
            }
            return user;
        } catch (SQLException e) {
            throw new DAOException("Error with get User by login", e);
        }
    }

    @Override
    public User getUser(Cookie cookie) throws DAOException {
        User result = null;
        String[] cookieTokens = cookieUtils.decodeCookie(cookie.getValue());
        String presentedSeries = cookieTokens[0];
        String presentedToken = cookieTokens[1];
        if (cookieTokens.length != 2 || presentedSeries == null) {
            return null;
        }
        String login = null;
        String token = "";
        try {
            PreparedStatement ps = prepareStatement(getLoginAndToken);
            ps.setString(1, presentedSeries);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                login = rs.getString(1);
                token = rs.getString(2);
            }
            if ((token.equals(presentedToken)) & login != null) {
                result = getUser(login);
            }
        } catch (SQLException e) {
            throw new DAOException("Error with get Token", e);
        }
        return result;
    }

    @Override
    public Cookie addToken(User user) throws DAOException {
        String login = user.getLogin();
        String series = String.valueOf(generateRandom());
        String token = String.valueOf(generateRandom());
        try {
            PreparedStatement ps = prepareStatement(addLoginAndToken);
            ps.setString(1, login);
            ps.setString(2, series);
            ps.setString(3, token);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String[] cookieTokens = {series,token};
        String cookieValue = cookieUtils.encodeCookie(cookieTokens);
        Cookie result = new Cookie(REMEMBER_ME_COOKIE,cookieValue);
        return result;
    }

    private int generateRandom() {
        return (int) (Math.random() * 1000000) + (int) (Math.random() * 1000);
    }

    @Override
    public User save(User user) throws DAOException {
        try {
            PreparedStatement psContact = prepareStatement(addContact, Statement.RETURN_GENERATED_KEYS);
            PreparedStatement psMainPart = prepareStatement(addUser, Statement.RETURN_GENERATED_KEYS);
            psContact.setString(1, user.getEmail());
            psContact.setString(2, user.getTelephone());
            psContact.setString(3, user.getCountry());
            psContact.setString(4, user.getCity());
            psContact.setString(5, user.getAddress());
            psContact.setString(6, user.getZip());
            psContact.executeUpdate();
            ResultSet rs = psContact.getGeneratedKeys();
            Integer contactId = 0;
            if (rs.next()) {
                contactId = (rs.getInt(1));
            }
            close(rs);
            psMainPart.setString(1, user.getLogin());
            psMainPart.setString(2, user.getPassword());
            psMainPart.setString(3, user.getName());
            psMainPart.setString(4, user.getLastName());
            psMainPart.setString(5, user.getRole());
            psMainPart.setInt(6, contactId);
            psMainPart.executeUpdate();
            rs = psMainPart.getGeneratedKeys();
            if (rs.next()) {
                user.setId(rs.getInt(1));
            }
            close(rs);
            return user;

        } catch (SQLException e) {
            throw new DAOException("Error with save User", e);
        }
    }

    @Override
    public User get(Serializable id) throws DAOException {
        try {
            PreparedStatement psGet = prepareStatement(selectUserById);
            psGet.setInt(1, (int) id);
            ResultSet rs = psGet.executeQuery();
            User user = new User();
            while (rs.next()) {
                user.setId(rs.getInt(1));
                user.setLogin(rs.getString(2));
                user.setPassword(rs.getString(3));
                user.setName(rs.getString(4));
                user.setLastName(rs.getString(5));
                user.setRole(rs.getString(6));
                user.setEmail(rs.getString(7));
                user.setTelephone(rs.getString(8));
                user.setCountry(rs.getString(9));
                user.setCity(rs.getString(10));
                user.setAddress(rs.getString(11));
                user.setZip(rs.getString(12));
            }
            return user;
        } catch (SQLException e) {
            throw new DAOException("Error with get User", e);
        }
    }

    @Override
    public void update(User user) throws DAOException {
        try {
            PreparedStatement psUpdate1 = prepareStatement(getContactID);
            PreparedStatement psUpdate2 = prepareStatement(updateUser);
            psUpdate1.setInt(1, user.getId());
            ResultSet rs = psUpdate1.executeQuery();
            Integer contactId = 0;
            if (rs.next()) {
                contactId = (rs.getInt(1));
            }
            close(rs);
            psUpdate2.setInt(7, contactId);
            psUpdate2.setString(1, user.getEmail());
            psUpdate2.setString(2, user.getTelephone());
            psUpdate2.setString(3, user.getCountry());
            psUpdate2.setString(4, user.getCity());
            psUpdate2.setString(5, user.getAddress());
            psUpdate2.setString(6, user.getZip());
            psUpdate2.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error with update User", e);
        }
    }

    @Override
    public int delete(Serializable id) throws DAOException {
        try {
            PreparedStatement psDelete = prepareStatement(deleteUser);
            psDelete.setInt(1, (int) id);
            return psDelete.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error with delete User", e);
        }
    }


}
