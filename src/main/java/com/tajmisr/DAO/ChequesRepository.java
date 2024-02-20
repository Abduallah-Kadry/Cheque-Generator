package com.tajmisr.DAO;


import com.tajmisr.Cheque;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface ChequesRepository extends JpaRepository<Cheque, Integer> {

    @Query("SELECT c FROM Cheque c WHERE " +
            "( :chequeIDs IS NULL OR c.id IN :chequeIDs) AND " +
            "(:chequeNames = '' OR c.chequeName LIKE %:chequeNames%) AND " +
            "(:projectNames = '' OR c.projectName LIKE %:projectNames%) AND " +
            "(:units = '' OR c.unit LIKE %:units%) AND " +
            "(:paymentTypes= '' OR c.paymentType LIKE %:paymentTypes%) AND " +
            "(:corporations= '' OR c.corporation LIKE %:corporations%) AND " +
            "(:banks= '' OR c.bank LIKE %:banks%) AND " +
            "(:chequeNumbers = '' OR c.chequeNumber LIKE %:chequeNumbers%) AND " +
            "(:notes= '' OR c.notes LIKE %:notes%) AND" +
            "((:chequeDateFrom IS NULL AND :chequeDateTo IS NULL) OR " +
            "(:chequeDateFrom IS NOT NULL AND c.chequeDate = :chequeDateFrom) OR " +
            "(:chequeDateTo IS NOT NULL AND c.chequeDate = :chequeDateTo) OR " +
            "(:chequeDateFrom IS NOT NULL AND :chequeDateTo IS NOT NULL AND c.chequeDate BETWEEN :chequeDateFrom AND :chequeDateTo)) AND " +
            "((:archivedDateFrom IS NULL AND :archivedDateTo IS NULL) OR " +
            "(:archivedDateFrom IS NOT NULL AND c.archivedDate = :archivedDateFrom) OR " +
            "(:archivedDateTo IS NOT NULL AND c.archivedDate = :archivedDateTo) OR " +
            "(:archivedDateFrom IS NOT NULL AND :archivedDateTo IS NOT NULL AND c.archivedDate BETWEEN :archivedDateFrom AND :archivedDateTo))")
    Page<Cheque> periodicFilter(@Param("chequeIDs") List<Integer> chequeIDs,
                                @Param("chequeNames") String chequeNames,
                                @Param("projectNames") String projectNames,
                                @Param("units") String units,
                                @Param("paymentTypes") String paymentTypes,
                                @Param("corporations") String corporations,
                                @Param("banks") String banks,
                                @Param("chequeDateFrom") LocalDate chequeDateFrom,
                                @Param("chequeDateTo") LocalDate chequeDateTo,
                                @Param("chequeNumbers") String chequeNumbers,
                                @Param("notes") String notes,
                                @Param("archivedDateFrom") LocalDate archivedDateFrom,
                                @Param("archivedDateTo") LocalDate archivedDateTo,
                                Pageable pageable);


}



