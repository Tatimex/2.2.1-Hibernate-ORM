package hiber.model;


import javax.persistence.*;


@Entity
@Table(name = "cars")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "car", cascade = CascadeType.ALL)
    private User user;

    @Column(name = "model")
    private String model;

    @Column(name = "series")
    private int series;


    public Car() {}

    public Car(String model, int series) {
        this.model = model;
        this.series = series;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getSeries() {
        return series;
    }

    public void setSeries(int series) {
        this.series = series;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Car {" +
               "id=" + id +
               ", model='" + model + '\'' +
               ", series=" + series +
               '}';
    }
}
