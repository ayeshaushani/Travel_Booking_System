package lk.ijse.travel_booking_system.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class TravelPackage {
    @Id
   // @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tPackageId;
    private String name;
    private String destination;
    private String duration;
    private double price;
    private String description;
    @Column(columnDefinition = "LONGTEXT")
    private String image;
    private String guide;


    @OneToMany(mappedBy = "travelPackage", cascade = CascadeType.ALL)
    private List<Booking> bookings;

    @OneToMany(mappedBy = "travelPackage", cascade = CascadeType.ALL)
    private List<Review> reviews;

    public TravelPackage() {
    }
    public TravelPackage(Long tPackageId, String name, String destination, String duration, double price, String description, String image, List<Booking> bookings, List<Review> reviews, String guide, String guideName) {
        this.tPackageId = tPackageId;
        this.name = name;
        this.destination = destination;
        this.duration = duration;
        this.price = price;
        this.description = description;
        this.image = image;
        this.guide = guide;
        this.bookings = bookings;

        this.reviews = reviews;
    }


    public Long gettPackageId() {
        return tPackageId;
    }

    public void settPackageId(Long tPackageId) {
        this.tPackageId = tPackageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        System.out.println("Setting description: " + description);
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        System.out.println("Setting image: " + image);
        this.image = image;
    }
    public String getGuide() {return guide;}

    public void setGuide(String guide) {this.guide = guide;}

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }


    public List<Review> getReviews() {
        return reviews;
    }



    @Override
    public String toString() {
        return "TravelPackage{" +
                "tPackageId=" + tPackageId +
                ", name='" + name + '\'' +
                ", destination='" + destination + '\'' +
                ", duration='" + duration + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                ", bookings=" + bookings +
                ", reviews=" + reviews +
                '}';
    }

}
