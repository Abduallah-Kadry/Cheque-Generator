package com.tajmisr;


import com.tajmisr.DAO.ChequesRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class DAOTest {

    @Autowired
    private ChequesRepository chequeDAO;

    @Test
    public void testAddNew(){
        Cheque cheque = new Cheque();
        cheque.setChequeName("TestName");
        cheque.setChequeNumber("0985991942013945");
        cheque.setId(Integer.MAX_VALUE);
        cheque.setBank("TestBank");
        cheque.setNotes("TestNote");
        cheque.setUnit("TestUnit");
        cheque.setCorporation("TestCorporation");
        cheque.setPaymentType("TestPaymentType");
        cheque.setProjectName("TestProjectName");
        cheque.setChequeDate(LocalDate.of(2023,10,20));
        cheque.setArchivedDate(LocalDate.of(2023,5,20));

        Cheque savedCheque = chequeDAO.save(cheque);

        assertThat(savedCheque).isNotNull();
        assertThat(savedCheque.getId()).isGreaterThan(0);
        chequeDAO.delete(savedCheque);
    }
}
