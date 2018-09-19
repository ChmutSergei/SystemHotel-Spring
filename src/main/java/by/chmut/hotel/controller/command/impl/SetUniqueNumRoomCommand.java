package by.chmut.hotel.controller.command.impl;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;



@Controller

public class SetUniqueNumRoomCommand {

    @RequestMapping(value = "/setUniqueNumber")

    public void setId(HttpServletRequest req) {

        int tempNumber = Integer.parseInt(req.getParameter("tempNumber"));

        req.getSession().setAttribute("tempNum",tempNumber);

    }
}