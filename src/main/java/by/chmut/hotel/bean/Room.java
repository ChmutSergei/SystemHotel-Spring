package by.chmut.hotel.bean;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Rooms")
public class Room implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int roomNumber;
    private String type;
    private int bedType;
    private long price;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private String description;
    private int temporaryNumber;
    @OneToMany(mappedBy = "room",
            cascade = {CascadeType.REFRESH,CascadeType.REMOVE})
    private List<Reservation> reservations;

    public Room(int roomNumber, String type, int bedType, long price, LocalDate checkIn, LocalDate checkOut, String description) {
        this.roomNumber = roomNumber;
        this.type = type;
        this.bedType = bedType;
        this.price = price;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.description = description;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        if (id != room.id) {
            return false;
        }
        if (roomNumber != room.roomNumber) {
            return false;
        }
        if (bedType != room.bedType) {
            return false;
        }
        if (price != room.price) {
            return false;
        }
        if (temporaryNumber != room.temporaryNumber) {
            return false;
        }
        if (type == null) {
            if (room.type != null) {
                return false;
            }
        } else if (!type.equals(room.type)) {
            return false;
        }
        if (checkIn == null) {
            if (room.checkIn != null) {
                return false;
            }
        } else if (!checkIn.equals(room.checkIn)) {
            return false;
        }
        if (checkOut == null) {
            if (room.checkOut != null) {
                return false;
            }
        } else if (!checkOut.equals(room.checkOut)) {
            return false;
        }
        if (description == null) {
            if (room.description != null) {
                return false;
            }
        } else if (!description.equals(room.description)) {
            return false;
        }
        if (reservations == null) {
            if (room.reservations != null) {
                return false;
            }
        } else if (!reservations.equals(room.reservations)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = result * 37 + id;
        result = result * 37 + roomNumber;
        result = result * 37 + bedType;
        result = result * 37 + (int) price;
        result = result * 37 + (type == null ? 0 : type.hashCode()) * result;
        result = result * 37 + (checkIn == null ? 0 : checkIn.hashCode()) * result;
        result = result * 37 + (checkOut == null ? 0 : checkOut.hashCode()) * result;
        result = result * 37 + (description == null ? 0 : description.hashCode()) * result;
        result = result * 37 + (reservations == null ? 0 : reservations.hashCode()) * result;
        return result;
    }

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", roomNumber=" + roomNumber +
                ", type='" + type + '\'' +
                ", bedType=" + bedType +
                ", price=" + price +
                ", checkIn=" + checkIn +
                ", checkOut=" + checkOut +
                ", description='" + description + '\'' +
                ", temporaryNumber=" + temporaryNumber +
                '}';
    }
}