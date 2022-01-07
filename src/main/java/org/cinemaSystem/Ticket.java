package org.cinemaSystem;

import java.sql.Date;
import java.sql.Time;

public class Ticket {
    private String movieName;
    private Date date;
    private int price;
    private int quantity;
    private Time start_at;

    public Ticket(String movieName,Date date,int price,Time start_at,int quantity){
     this.movieName = movieName;
     this.date = date;
     this.price = price;
     this.quantity = quantity;
     this.start_at = start_at;
    }
}
