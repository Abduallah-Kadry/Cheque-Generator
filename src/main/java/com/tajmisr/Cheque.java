package com.tajmisr;


import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@Entity
@Table(name ="cheques")
public class Cheque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer id;

    @Column(name = "cheque_name")
    private String chequeName;

    @Column(name = "project_name")
    private String projectName;

    private String unit;

    @Column(name = "payment_type")
    private String paymentType;

    private String corporation;

    private String bank;

    @Column(name = "cheque_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate chequeDate;

    @Column(name = "archived_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate archivedDate;

    @Column(name = "cheque_number")
    private String chequeNumber;

    private String notes;

    public boolean isNulls() {
        return id == null && notes == null && archivedDate == null && chequeNumber == null && chequeDate == null &&
                bank == null && corporation == null && paymentType == null && unit == null && projectName == null && chequeName == null;
    }
}
