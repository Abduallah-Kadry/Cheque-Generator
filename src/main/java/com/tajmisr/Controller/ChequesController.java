package com.tajmisr.Controller;

import com.tajmisr.Cheque;
import com.tajmisr.Exceptions.ChequeNotFoundException;
import com.tajmisr.Services.ChequesServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

// TODO (Advanced) make Profiles roles i mean
// TODO need to sort somehow (Think of adding button next to the name of the fields) utilize the Sort Package with JPA and
// pass the parameters when clicking
// TODO make a button for the archive and archive using Date.now() or something


@Controller()
public class ChequesController {
    @Autowired
    ChequesServices chequesServices;

    // HOME PAGE
    @GetMapping("/")
    public String showHomePage() {
        return "index";
    }


    //Test for pageable
    @GetMapping("cheques/allCheques")
    public String getAllCheques(
            @RequestParam(value = "pageNumber", defaultValue = "1") int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
            @RequestParam(value = "sortField", defaultValue = "id") String sortField,
            @RequestParam(value = "sortDir", defaultValue = "asc") String sortDir,
            Model model) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, Sort.Direction.fromString(sortDir), sortField);
        Page<Cheque> chequePage = chequesServices.getAllCheques(pageable);

        model.addAttribute("currentPage", pageable.getPageNumber() + 1);
        model.addAttribute("totalPages", chequePage.getTotalPages());
        model.addAttribute("totalItems", chequePage.getTotalElements());
        model.addAttribute("listCheques", chequePage.getContent());
        return "DisplayCheques";
    }

    // CREATE NEW Cheque
    // ---load the page
    @GetMapping("cheques/newCheque")
    public String showNewForm(Model model) {

        model.addAttribute("Cheque", new Cheque());
        model.addAttribute("pageTitle", "Add new Cheque");
        return "ChequeForm";
    }

    // ---accept the inputted form
    @PostMapping("cheques/saveCheque")
    public String addCheque(Cheque cheque, RedirectAttributes ra) {
        chequesServices.addCheque(cheque);
        ra.addFlashAttribute("messages", "The Cheque has been saved Successfully");
        return "redirect:/cheques/allCheques";
    }

    // EDIT CHEQUE
    // editing is about passing the attributes to the creation form
    @GetMapping("cheques/editCheque/{id}")
    public String showEditForm(@PathVariable int id, Model model, RedirectAttributes ra) {
        try {
            cheque = chequesServices.getChequeById(id);
            model.addAttribute("Cheque", cheque);
            model.addAttribute("pageTitle", "Edit Cheque: " + id);
            return "ChequeForm";
        } catch (ChequeNotFoundException e) {
            ra.addFlashAttribute("SavedChequeMessage", "The Cheque has been Edited Successfully");
            return "redirect:/cheques/allCheques";
        }

    }

    // search for cheques using column search
    @GetMapping("/cheques/search")
    public String searchCheques(
            @RequestParam(name = "chequeIDs", required = false) List<Integer> IDs,
            @RequestParam(name = "chequeNames", required = false) String chequeNames,
            @RequestParam(name = "projectNames", required = false) String projectNames,
            @RequestParam(name = "units", required = false) String units,
            @RequestParam(name = "paymentTypes", required = false) String paymentTypes,
            @RequestParam(name = "corporations", required = false) String corporations,
            @RequestParam(name = "banks", required = false) String banks,
            @RequestParam(name = "chequeDateFrom", required = false) String chequeDateFrom,
            @RequestParam(name = "chequeDateTo", required = false) String chequeDateTo,
            @RequestParam(name = "chequeNumbers", required = false) String chequeNumbers,
            @RequestParam(name = "notes", required = false) String notes,
            @RequestParam(name = "archivedDateFrom", required = false) String archivedDateFrom,
            @RequestParam(name = "archivedDateTo", required = false) String archivedDateTo,
            @RequestParam(name = "sortField", required = false, defaultValue = "id") String sortField,
            @RequestParam(name = "sortDir", required = false, defaultValue = "ASC") String sortDir,
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size,
            Model model) {

        Pageable pageable = PageRequest.of(page, size, Sort.Direction.fromString(sortDir), sortField);

        chequesServices.periodicFilter(IDs, chequeNames, projectNames, units,
                paymentTypes, corporations, banks,
                chequeDateFrom, chequeDateTo, chequeNumbers, notes,
                archivedDateFrom, archivedDateTo, pageable, model);
        // this uses chequesTableBody.html (that include a fragment)
        // and then returns it to the browser using fragment tableBody
        return "fragments/chequesTableBody::tableBody";
    }


    //DELETE a CHEQUE
    @GetMapping("cheques/deleteCheque/{id}")
    public String deleteCheque(@PathVariable int id, RedirectAttributes ra) {
        try {
            chequesServices.deleteCheque(id);
            ra.addFlashAttribute("messages", "Cheque with ID: " + id + " has been Deleted");
        } catch (ChequeNotFoundException e) {
            ra.addFlashAttribute("messages", e.getMessage());
        }
        return "redirect:/cheques/allCheques";
    }

}

