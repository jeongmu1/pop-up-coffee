function handleRowClick(row) {
    let id = row.getAttribute('data-id');
    let flexible = findFlexibleById(flexibles, id);

    let paymentForm = document.querySelector('.mini-form-container');

    if (flexible.status === '확정') {
        // 상태가 '확정'인 경우, 결제 창을 숨깁니다.
        paymentForm.style.display = 'none';
        return;
    }

    console.log(flexible);
    document.getElementById('reservationId').value = flexible.id;
    document.getElementById('space').innerText = flexible.space;
    document.getElementById('rentalFee').innerText = flexible.rentalFee;
    document.getElementById('rentalDeposit').innerText = flexible.rentalDeposit;
    document.getElementById('rentalDuration').innerText = flexible.rentalStartDate + '~' + flexible.rentalEndDate;

    paymentForm.style.display = 'block';
}

function findFlexibleById(flexibles, id) {
    return flexibles.find(function(flexible) {
        return flexible.id == id;
    });
}