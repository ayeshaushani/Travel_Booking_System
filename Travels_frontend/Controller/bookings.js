/*
$(document).ready(function () {
    const apiBaseUrl = 'http://localhost:8080';
    const token = localStorage.getItem('jwtToken');
    const userId = localStorage.getItem('userId');
    let selectedPackage = null;

    // Initialize steps
    function showStep(stepId) {
        $('.step').removeClass('active').hide();
        $(`#${stepId}`).addClass('active').show();
    }

    // Load packages with better error handling
    function loadPackages() {
        $('#packageContainer').html('<div class="col-12 text-center py-4"><div class="spinner-border text-primary" role="status"></div><p>Loading packages...</p></div>');

        $.ajax({
            url: `${apiBaseUrl}/bookings/packages`,
            method: 'GET',
            headers: {
                'Authorization': 'Bearer ' + localStorage.getItem('jwtToken')
            },
            success: function (packages) {
                const packageContainer = $('#packageContainer');
                packageContainer.empty();

                if (!packages || packages.length === 0) {
                    packageContainer.html('<div class="col-12 text-center py-4"><p>No packages available at the moment.</p></div>');
                    return;
                }

                packages.forEach(pkg => {
                    // Use backticks (`) instead of quotes for template literals
                    const packageCard = $(`
        <div class="col-md-6 mb-4">
            <div class="card package-card h-100" data-package-id="${pkg.tPackageId}">
             
                <div class="card-body">
                    <h5 class="card-title">${pkg.name}</h5>
                    <p class="card-text text-muted">${pkg.description || 'Explore this amazing destination'}</p>
                    <div class="d-flex justify-content-between align-items-center">
                        <span class="h5 text-primary">Rs. ${pkg.price.toFixed(2)}</span>
                        <button class="btn btn-sm btn-outline-primary select-package">Select</button>
                    </div>
                </div>
            </div>
        </div>
    `);
                    packageContainer.append(packageCard);
                });

                // Add click event to package cards
                $('.package-card').on('click', function () {
                    $('.package-card').removeClass('border-primary');
                    $(this).addClass('border-primary');
                    selectedPackage = packages.find(p => p.tPackageId == $(this).data('package-id'));
                    $('#nextBtn1').prop('disabled', false).removeClass('btn-secondary').addClass('btn-primary');
                });
            },
            error: function (xhr) {
                let errorMsg = 'Error loading packages';
                if (xhr.status === 401) {
                    errorMsg = 'Session expired. Please login again.';
                    setTimeout(() => window.location.href = 'login.html', 2000);
                } else if (xhr.responseJSON?.message) {
                    errorMsg = xhr.responseJSON.message;
                }

                $('#packageContainer').html(`
                    <div class="col-12 text-center py-4">
                        <div class="alert alert-danger">${errorMsg}</div>
                        <button class="btn btn-primary" onclick="location.reload()">
                            <i class="bi bi-arrow-repeat"></i> Try Again
                        </button>
                    </div>
                `);
            }
        });
    }

    // Initialize date picker with restrictions
    function initDatePicker() {
        const today = new Date();
        const minDate = today.toISOString().split('T')[0];

        // Set max date to 1 year from now
        const maxDate = new Date();
        maxDate.setFullYear(today.getFullYear() + 1);

        $('#travelDate').attr({
            'min': minDate,
            'max': maxDate.toISOString().split('T')[0]
        }).val(minDate);
    }

    // Step navigation
    $('#nextBtn1').on('click', function () {
        if (!selectedPackage) {
            showAlert('Please select a package first', 'warning');
            return;
        }
        $('#selectedPackageName').text(selectedPackage.name);
        $('#selectedPackagePrice').text('Rs. ' + selectedPackage.price.toFixed(2));
        initDatePicker();
        showStep('step2');
    });

    $('#backBtn2').on('click', function () {
        showStep('step1');
    });

    // Show alert message
    function showAlert(message, type = 'danger') {
        const alert = $(`
            <div class="alert alert-${type} alert-dismissible fade show" role="alert">
                ${message}
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
        `);
        $('#alertsContainer').empty().append(alert);
        setTimeout(() => alert.alert('close'), 5000);
    }

    // Form submission with validation
    $('#bookingForm').on('submit', function (e) {
        e.preventDefault();

        // Validate travelers count
        const travelers = parseInt($('#travelers').val());
        if (isNaN(travelers)){
            showAlert('Please enter a valid number of travelers', 'warning');
            return;
        }

        const submitBtn = $('#submitBtn');
        submitBtn.prop('disabled', true)
            .html('<span class="spinner-border spinner-border-sm" role="status" aria-hidden="true"></span> Processing...');

        const bookingData = {
            bookingDate: $('#travelDate').val(),
            status: "PENDING",
            userId: parseInt(userId),
            tPackageId: parseInt(selectedPackage.tPackageId),
            specialRequests: $('#specialRequests').val().trim(),
            numberOfTravelers: travelers
        };

        $.ajax({
            url: `${apiBaseUrl}/bookings`,
            method: 'POST',
            headers: {
                'Authorization': 'Bearer ' + localStorage.getItem('jwtToken')
            },
            data: JSON.stringify(bookingData),
            success: function (response) {
                // Display confirmation details
                $('#confirmationPackage').text(selectedPackage.name);
                $('#confirmationDate').text(new Date(bookingData.bookingDate).toLocaleDateString());
                $('#confirmationTravelers').text(bookingData.numberOfTravelers);
                $('#confirmationRequests').text(bookingData.specialRequests || 'None');
                $('#confirmationId').text(response.bookingId || 'N/A');

                // Show confirmation step
                showStep('step3');

                // Scroll to top
                window.scrollTo(0, 0);
            },
            error: function (xhr) {
                let errorMsg = 'Booking failed. Please try again.';
                if (xhr.status === 401) {
                    errorMsg = 'Session expired. Redirecting to login...';
                    setTimeout(() => window.location.href = 'login.html', 2000);
                } else if (xhr.responseJSON?.message) {
                    errorMsg = xhr.responseJSON.message;
                }

                showAlert(errorMsg);
                submitBtn.prop('disabled', false).html('Confirm Booking <i class="bi bi-check-lg"></i>');
            }
        });
    });

    // Print confirmation
    $('#printConfirmation').on('click', function() {
        window.print();
    });

    // Make another booking
    $('#newBooking').on('click', function() {
        selectedPackage = null;
        $('#bookingForm')[0].reset();
        $('#nextBtn1').prop('disabled', true).removeClass('btn-primary').addClass('btn-secondary');
        loadPackages();
        showStep('step1');
    });

    // Initial load
    if (!token || !userId) {
        window.location.href = 'login.html';
    } else {
        loadPackages();
        showStep('step1');
        $('#nextBtn1').prop('disabled', true);
    }
});*/
