package by.chmut.hotel.controller.validation;

import by.chmut.hotel.bean.User;
import by.chmut.hotel.controller.validation.encoder.Encoder;


public class Validator {

    public static boolean isPasswordValid(User user, String password) {

        if (user != null && user.getPassword().equals(Encoder.encode(password))) {

            return true;

        }

        return false;
    }
}