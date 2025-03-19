package locadora_api_java.entity;

import jakarta.persistence.*;
import locadora_api_java.enums.RentStatus;

import java.time.LocalDate;

@Entity
@Table(name = "rents")
public class Rent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "renterId", nullable = false)
    private Long renterId;

    @Column(name = "bookId", nullable = false)
    private Long bookId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private RentStatus status;

    @Column(name = "devolutionDate")
    private LocalDate devolutionDate;

    @Column(name = "deadLineDate")
    private LocalDate deadLineDate;

    @Column(name = "rentDate")
    private LocalDate rentDate;

    public Rent() {
    }

    public Rent(Long renterId, Long bookId, RentStatus status, LocalDate devolutionDate, LocalDate deadLineDate, LocalDate rentDate) {
        this.renterId = renterId;
        this.bookId = bookId;
        this.status = status;
        this.devolutionDate = devolutionDate;
        this.deadLineDate = deadLineDate;
        this.rentDate = rentDate;
    }

    public Long getId() {
        return id;
    }

    public Long getRenterId() {
        return renterId;
    }

    public void setRenterId(Long renterId) {
        this.renterId = renterId;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public RentStatus getStatus() {
        return status;
    }

    public void setStatus(RentStatus status) {
        this.status = status;
    }

    public LocalDate getDevolutionDate() {
        return devolutionDate;
    }

    public void setDevolutionDate(LocalDate devolutionDate) {
        this.devolutionDate = devolutionDate;
    }

    public LocalDate getDeadLineDate() {
        return deadLineDate;
    }

    public void setDeadLineDate(LocalDate deadLineDate) {
        this.deadLineDate = deadLineDate;
    }

    public LocalDate getRentDate() {
        return rentDate;
    }

    public void setRentDate(LocalDate rentDate) {
        this.rentDate = rentDate;
    }

    @Override
    public String toString() {
        return "Rent{" +
                "id=" + id +
                ", renterId=" + renterId +
                ", bookId=" + bookId +
                ", status=" + status +
                ", devolutionDate=" + devolutionDate +
                ", deadLineDate=" + deadLineDate +
                ", rentDate=" + rentDate +
                '}';
    }
}
