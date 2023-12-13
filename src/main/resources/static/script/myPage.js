function handleRowClick(row) {
    let id = row.getAttribute('data-id');
    let flexible = findFlexibleById(flexibles, id);

    console.log(flexible);
    document.getElementById('reservationId').value = flexible.id;
    document.getElementById('space').innerText = flexible.space;
    document.getElementById('rentalFee').innerText = flexible.rentalFee;
    document.getElementById('rentalDeposit').innerText = flexible.rentalDeposit;
    document.getElementById('rentalDuration').innerText = flexible.rentalStartDate + '~' + flexible.rentalEndDate;
}

function findFlexibleById(flexibles, id) {
    return flexibles.find(function(flexible) {
        return flexible.id == id;
    });
}