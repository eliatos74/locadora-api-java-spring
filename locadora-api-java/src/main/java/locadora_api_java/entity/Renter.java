package locadora_api_java.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "renter")
public class Renter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "telephone", nullable = false)
    private String telephone;

    @Column(name = "address")
    private String address;

    @Column(name = "cpf", nullable = false, unique = true)
    private String cpf;

    public Renter() {
    }

    public Renter(String name, String email, String telephone, String address, String cpf) {
        this.name = name;
        this.email = email;
        this.telephone = telephone;
        this.address = address;
        this.cpf = cpf;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}
