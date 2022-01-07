package Users;

import Repositories.CinemaRepository;
import Services.CinemaService;
import Services.PromoService;
import Services.TicketService;
import org.cinemaSystem.CinemaApp;
import org.cinemaSystem.Ticket;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.time.Month;
import java.time.Year;

public class Cinema extends User {
// hassan mohseni
    String cinema_name;
    boolean confirmation;
    TicketService ticketService = new TicketService();

    public Cinema(String username) throws SQLException {
        CinemaRepository cr = new CinemaRepository(username);
        super.username = username;
        super.id = cr.getId();
        this.cinema_name = cr.getCinema_name();
        this.confirmation = cr.isConfirmation();
    }

    public void entry() throws SQLException, ClassNotFoundException, InterruptedException {
        label:
        while (true) {
            System.out.print("""
                    1-Create Ticket
                    2-Create Promo Code
                    3-Exit
                    Option: \040""");
            String ticketOrExit = scanner.nextLine();
            switch (ticketOrExit) {
                case "1":
                    ticketCreation();
                    break;
                case "2":
                    promoCreation();
                    break;
                case "3":
                    break label;
                default:
                    System.out.println("Wrong entry.");
                    break;
            }
        }
    }

    public void ticketCreation() throws SQLException, ClassNotFoundException, InterruptedException {
        utils.clear();
        if (this.confirmation) {
            System.out.println("Welcome to Ticket Making Section. Please fill the questions with proper entry:");
            System.out.print("Movie Title: ");
            String movieTitle = scanner.nextLine();
            System.out.print("Ticket Price: ");
            int price = utils.intReceiver();
            int month = utils.monthReceiver() - 1;
            int day = utils.dayReceiver(month + 1);
            int hour = utils.hourReceiver();
            int minute = utils.minuteReceiver();
            Time start_at = new Time(hour,minute,0);
            System.out.print("How Many tickets can be sold?: ");
            int quantity = utils.intReceiver();
            Ticket ticket = new Ticket(movieTitle,new Date(2022-1900,month,day),price,start_at,quantity);
            ticketService.insertTicket(movieTitle, new Date(2022-1900,month,day), quantity, this.id,price,start_at);
            Thread.sleep(1000);
            utils.printGreen("Ticket added.");
        } else {
            System.out.println("\u001B[31m" + "Your Account has not been confirmed yet." + "\u001B[0m");
            Thread.sleep(1000);
        }
    }
    private void promoCreation() throws InterruptedException, SQLException, ClassNotFoundException {
        utils.clear();
        if(this.confirmation){
            PromoService ps = new PromoService();
            System.out.println("Welcome to Promo Code Creation Section.Enter a promo code and then choose what it will do...");
            Thread.sleep(1000);
            System.out.print("Promo Code: ");
            String promoCode = scanner.nextLine();
            System.out.println("""
                    What will the promo do:
                    1-Discount by percent
                    2-Discount by cost
                    3-Add to cost(For Charity Reasons)
                    Option: \040""");
            String option = scanner.nextLine();
            while (true) {
                if (option.equals("1")) {
                    double amount = utils.percentageReceiver();
                    ps.insert(promoCode,this.id,"PERCENTAGE",amount);
                    break;
                } else if (option.equals("2")) {
                    System.out.print("Cost: ");
                    double amount = utils.doubleReceiver();
                    ps.insert(promoCode,this.id,"SUBTRACT",amount);
                    break;
                } else if (option.equals("3")) {
                    System.out.print("Cost: ");
                    double amount = utils.doubleReceiver();
                    ps.insert(promoCode,this.id,"CHARITYADD",amount);
                    break;
                } else {
                    System.out.println("Wrong number!");
                }
            }
        }
        else{
            System.out.println("\u001B[31m" + "Your Account has not been confirmed yet." + "\u001B[0m");
            Thread.sleep(1000);
        }
    }

}
