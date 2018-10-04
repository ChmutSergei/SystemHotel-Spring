package by.chmut.hotel.controller;

import by.chmut.hotel.bean.Room;
import by.chmut.hotel.bean.User;
import by.chmut.hotel.service.ReservationService;
import by.chmut.hotel.service.validation.PaymentSender;
import by.chmut.hotel.service.ServiceException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class PaymentController {

    @Autowired
    private ReservationService reservationService;

    private static final Logger logger = Logger.getLogger(PaymentController.class);

    @RequestMapping(value = "/payment")
    public String payment(HttpServletRequest req, Model model) {

        List<Room> temporaryRooms = (List<Room>) req.getSession().getAttribute("tempRooms");

        if (makePaymentWithParams(req)) {

            setPaidStatusForReservation(req, temporaryRooms);

            removeAttributes(req.getSession());

            model.addAttribute("success", "ok");

        }

        return "/payment";
    }


    private boolean makePaymentWithParams(HttpServletRequest req) {

        if (req.getSession().getAttribute("totalSum") != null) {

            PaymentSender paymentSender = new PaymentSender(req.getParameter("numCard"), req.getParameter("nameCard"),
                    req.getParameter("cvc2"));

            return paymentSender.payment().equals("true");
        }
        return false;
    }

    private void removeAttributes(HttpSession session) {
        session.removeAttribute("tempRooms");
        session.removeAttribute("checkIn");
        session.removeAttribute("checkOut");
        session.removeAttribute("totalSum");
    }

    private void setPaidStatusForReservation(HttpServletRequest req, List<Room> rooms) {

        HttpSession session = req.getSession();

        User user = (User) session.getAttribute("user");

        for (Room room : rooms) {
            int count = 0;
            int maxTries = 3;

            while (true) {

                try {
                    if (count == maxTries) {
                        break;
                    }
                    reservationService.setPaidStatus(user.getId(), room);

                    break;

                } catch (ServiceException e) {

                    logger.error(e);

                    count++;
                }
            }
        }
    }
}
