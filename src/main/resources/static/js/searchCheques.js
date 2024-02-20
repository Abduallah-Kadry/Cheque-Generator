function confirmDelete(id) {
    if (confirm('Are you sure you want to delete this cheque buddy?')) {
        window.location.href = '/cheques/deleteCheque/' + id;
    }
}

setTimeout(function () {
    const alertMessage = document.getElementById('alertMessage');
    if (alertMessage) {
        alertMessage.remove();
    }
}, 3000);

let timer;

function handleFilterChanges() {
    clearTimeout(timer);
    timer = setTimeout(sendFilterValues, 500);
}


function sendFilterValues() {
    let chequeIDs = document.getElementById("chequeIDs").value;
    let chequeNames = document.getElementById("chequeNames").value;
    let projectNames = document.getElementById("projectNames").value;
    let units = document.getElementById("units").value;
    let paymentTypes = document.getElementById("paymentTypes").value;
    let corporations = document.getElementById("corporations").value;
    let banks = document.getElementById("banks").value;
    let chequeDateFrom = document.getElementById("chequeDateFrom").value;
    let chequeDateTo = document.getElementById("chequeDateTo").value;
    let chequeNumbers = document.getElementById("chequeNumbers").value;
    let notes = document.getElementById("notes").value;
    let archivedDateFrom = document.getElementById("archivedDateFrom").value;
    let archivedDateTo = document.getElementById("archivedDateTo").value;
    let pageNumber = "0";// document.getElementById("pageNumber").value;
    console.log("this is the ajax")

    // Send the filter values to the server
    let xhr = new XMLHttpRequest();
    xhr.open("GET", "/cheques/search?" +
        "chequeIDs=" + chequeIDs +
        "&chequeNames=" + chequeNames +
        "&projectNames=" + projectNames +
        "&units=" + units +
        "&paymentTypes=" + paymentTypes +
        "&corporations=" + corporations +
        "&banks=" + banks +
        "&chequeDateFrom=" + chequeDateFrom +
        "&chequeDateTo=" + chequeDateTo +
        "&chequeNumbers=" + chequeNumbers +
        "&notes=" + notes +
        "&archivedDateFrom=" + archivedDateFrom +
        "&archivedDateTo=" + archivedDateTo +
        "&page=" + pageNumber +
        "&size=" + "5" + //pageSize +
        "&sort=" + "asc", true);//sort, true);

    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
            // Update the table with the filtered data
            document.getElementById("tableBodyPlaceHolder").innerHTML = xhr.responseText;
        }
    };
    xhr.send();
}

// Add event listeners to filter fields
document.getElementById("chequeIDs").addEventListener("input", handleFilterChanges);
document.getElementById("chequeNames").addEventListener("input", handleFilterChanges);
document.getElementById("projectNames").addEventListener("input", handleFilterChanges);
document.getElementById("units").addEventListener("input", handleFilterChanges);
document.getElementById("paymentTypes").addEventListener("input", handleFilterChanges);
document.getElementById("corporations").addEventListener("input", handleFilterChanges);
document.getElementById("banks").addEventListener("input", handleFilterChanges);
document.getElementById("chequeDateFrom").addEventListener("input", handleFilterChanges);
document.getElementById("chequeDateTo").addEventListener("input", handleFilterChanges);
document.getElementById("chequeNumbers").addEventListener("input", handleFilterChanges);
document.getElementById("notes").addEventListener("input", handleFilterChanges);
document.getElementById("archivedDateFrom").addEventListener("input", handleFilterChanges);
document.getElementById("archivedDateTo").addEventListener("input", handleFilterChanges);
