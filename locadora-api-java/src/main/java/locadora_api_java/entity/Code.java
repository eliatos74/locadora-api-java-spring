package locadora_api_java.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "codes")
public class Code {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code", nullable = false)
    private Long code;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "timeCodeExpired", nullable = false)
    private LocalDateTime timeCodeExpired;

    public Code() {
    }

    public Code(String code, User user, LocalDate timeCodeExpired) {
    }

    public Long getId() {
        return id;
    }

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getTimeCodeExpired() {
        return timeCodeExpired;
    }

    public void setTimeCodeExpired(LocalDateTime timeCodeExpired) {
        this.timeCodeExpired = timeCodeExpired;
    }

    @Override
    public String toString() {
        return "Code{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", user=" + user +
                ", timeCodeExpired=" + timeCodeExpired +
                '}';
    }
}
