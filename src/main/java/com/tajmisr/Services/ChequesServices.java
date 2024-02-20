package com.tajmisr.Services;

import com.tajmisr.Cheque;
import com.tajmisr.DAO.ChequesRepository;
import com.tajmisr.Exceptions.ChequeNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.util.List;

@Service
public class ChequesServices {
    @Autowired
    ChequesRepository chequesRepository;

    // get all data

    public Page<Cheque> getAllCheques(Pageable pageable) {
        return chequesRepository.findAll(pageable);
    }

    // searching
    public Cheque getChequeById(int id) throws ChequeNotFoundException {
        var cheques = chequesRepository.findById(id);
        if (cheques.isPresent()) {
            return cheques.get();
        }
        throw new ChequeNotFoundException("couldn't find Cheque with ID: " + id);
    }

    // ADDING
    public void addCheque(Cheque cheque) {
        try {
            chequesRepository.save(cheque);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // EDITING

    public void deleteCheque(int id) throws ChequeNotFoundException {
        if (chequesRepository.existsById(id)) {
            chequesRepository.deleteById(id);
        } else {
            throw new ChequeNotFoundException("Couldn't find a cheque with id: " + id);
        }
    }

    public Page<Cheque> periodicFilter(List<Integer> chequeIDs,
                                       String chequeNames,
                                       String projectNames,
                                       String units,
                                       String paymentTypes,
                                       String corporations,
                                       String banks,
                                       String chequeDateFrom,
                                       String chequeDateTo,
                                       String chequeNumbers,
                                       String notes,
                                       String archivedDateFrom,
                                       String archivedDateTo,
                                       Pageable pageable,Model model) {

        List<Integer> chequeIDsTemp = (chequeIDs.isEmpty()) ? null : chequeIDs;

        LocalDate chequeDateFromParsed = parseDate(chequeDateFrom);
        LocalDate chequeDateToParsed = parseDate(chequeDateTo);
        LocalDate archivedDateFromParsed = parseDate(archivedDateFrom);
        LocalDate archivedDateToParsed = parseDate(archivedDateTo);

        boolean isEmptyParameters = chequeIDs.isEmpty() && chequeNames.isEmpty() && projectNames.isEmpty()
                && units.isEmpty() && paymentTypes.isEmpty() && corporations.isEmpty() && banks.isEmpty() &&
                chequeDateFrom.isEmpty() && chequeDateTo.isEmpty() && chequeNumbers.isEmpty() &&
                notes.isEmpty() && archivedDateFrom.isEmpty() && archivedDateTo.isEmpty();


        Page<Cheque> filteredCheques = chequesRepository.periodicFilter(
                chequeIDsTemp,
                chequeNames,
                projectNames,
                units,
                paymentTypes,
                corporations,
                banks,
                chequeDateFromParsed,
                chequeDateToParsed,
                chequeNumbers,
                notes,
                archivedDateFromParsed,
                archivedDateToParsed,
                isEmptyParameters ? null : pageable
        );

        System.out.println("isEmptyParameters: " + isEmptyParameters);
        System.out.println("filteredCheques: " + filteredCheques.isEmpty());

        if (isEmptyParameters && filteredCheques.isEmpty()) {
            System.out.println("No parameters so no filters are applied");
            model.addAttribute("listCheques", getAllCheques(pageable));
        } else if (!isEmptyParameters && filteredCheques.isEmpty()) {
            System.out.println("There is a parameter but nothing returned from the filter");
            model.addAttribute("notFound", "No result found");
        } else {
            System.out.println("There is a parameter and there is a respond");
            model.addAttribute("listCheques", filteredCheques.getContent());
        }
        return filteredCheques;
    }
    private LocalDate parseDate(String dateStr) {
        if (dateStr != null && !dateStr.isEmpty()) {
            return LocalDate.parse(dateStr);
        }
        return null;
    }

}
