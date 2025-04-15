package lk.ijse.travel_booking_system.dto;

public class TravelPackageDTO {
    private Long tPackageId;
    private String name;
    private String destination;
    private String duration;
    private double price;
    private String description;
    private String image;
    private String guide;


    public TravelPackageDTO() {
    }

    public TravelPackageDTO(Long tPackageId, String name, String destination, String image, String duration, double price, String description, String guide) {
        this.tPackageId = tPackageId;
        this.name = name;
        this.destination = destination;
        this.image = image;
        this.duration = duration;
        this.price = price;
        this.description = description;
        this.guide = guide;

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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
        this.description = description;
    }
    public String getGuide() {
        return guide;
    }

    public void setGuide(String guide) {
        this.guide = guide;
    }

    @Override
    public String toString() {
        return "TravelPackageDTO{" +
                "tPackageId=" + tPackageId +
                ", name='" + name + '\'' +
                ", destination='" + destination + '\'' +
                ", image='" + image + '\'' +
                ", duration='" + duration + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", guide='" + guide + '\'' +
                '}';
    }
}