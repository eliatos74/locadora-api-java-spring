package locadora_api_java.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true, length = 50)
    private String name;

    @Column(name = "author", nullable = false, length = 50)
    private String author;

    @Column(name = "totalQuantity", nullable = false)
    private Long totalQuantity;

    @Column(name = "availableQuantity", nullable = false)
    private Long availableQuantity;

    @Column(name = "inUseQuantity", nullable = false)
    private Long inUseQuantity;

    @Column(name = "lauchDate", nullable = false)
    private LocalDate lauchDate;


    @Column(name = "publisherId", nullable = false)
    private Long publisherId;

    public Book() {
    }

    public Book(String name, String author, Long totalQuantity, Long availableQuantity, Long inUseQuantity, LocalDate lauchDate, Long publisherId) {
        this.name = name;
        this.author = author;
        this.totalQuantity = totalQuantity;
        this.availableQuantity = availableQuantity;
        this.inUseQuantity = inUseQuantity;
        this.lauchDate = lauchDate;
        this.publisherId = publisherId;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Long getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(Long totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public Long getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(Long availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    public Long getInUseQuantity() {
        return inUseQuantity;
    }

    public void setInUseQuantity(Long inUseQuantity) {
        this.inUseQuantity = inUseQuantity;
    }

    public LocalDate getLauchDate() {
        return lauchDate;
    }

    public void setLauchDate(LocalDate lauchDate) {
        this.lauchDate = lauchDate;
    }

    public Long getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(Long publisherId) {
        this.publisherId = publisherId;
    }
}
