$(document).ready(function() {
    const apiBaseUrl = 'http://localhost:8080';
    const token = localStorage.getItem('jwtToken');
    const bookingId = new URLSearchParams(window.location.search).get('bookingId');

    // Initialize payment page
    function initPaymentPage() {
        if (!token || !bookingId) {
            window.location.href = '../admin/login.html';
            return;
        }

        loadBookingDetails();
        setupEventHandlers();
    }

    // Load booking details from backend
    function loadBookingDetails() {
        $.ajax({
            url: `${apiBaseUrl}/bookings/${bookingId}`,
            method: 'GET',
            headers: {
                'Authorization': 'Bearer ' + localStorage.getItem('jwtToken')
            },
            success: function(booking) {
                $('#summaryPackage').text(booking.packageName);
                $('#summaryAmount').text('Rs. ' + booking.totalAmount.toLocaleString('en-US', {
                    minimumFractionDigits: 2,
                    maximumFractionDigits: 2
                }));
            },
            error: function(xhr) {
                showAlert('Failed to load booking details', 'danger');
                console.error('Error loading booking:', xhr);
            }
        });
    }

    // Setup event handlers
    function setupEventHandlers() {
        // Payment method selection
        $('.payment-method').click(function() {
            $('.payment-method').removeClass('active');
            $(this).addClass('active');

            if ($(this).attr('id') === 'otherMethod') {
                $('#cardDetails').hide();
                $('#payNowBtn').text('Continue to Payment Gateway');
            } else {
                $('#cardDetails').show();
                $('#payNowBtn').text('Pay Now');
            }
        });

        // Pay Now button
        $('#payNowBtn').click(processPayment);

        // Card number formatting
        $('#cardNumber').on('input', function() {
            this.value = this.value.replace(/\D/g, '')
                .replace(/(\d{4})(?=\d)/g, '$1 ');
        });

        // Expiry date formatting
        $('#cardExpiry').on('input', function() {
            this.value = this.value.replace(/\D/g, '')
                .replace(/(\d{2})(?=\d)/g, '$1/')
                .substring(0, 5);
        });
    }

    // Process payment
    function processPayment() {
        const btn = $('#payNowBtn');
        btn.prop('disabled', true);
        $('#payButtonText').addClass('d-none');
        $('#payButtonSpinner').removeClass('d-none');

        if ($('#otherMethod').hasClass('active')) {
            // PayHere payment flow
            initiatePayHerePayment();
        } else {
            // Direct card payment
            processCardPayment();
        }
    }

    // Initiate PayHere payment
    function initiatePayHerePayment() {
        $.ajax({
            url: `${apiBaseUrl}/api/payments/payhere/initiate`,
            method: 'POST',
            headers: {
                'Authorization': 'Bearer ' + localStorage.getItem('jwtToken')
            },
            data: JSON.stringify({
                bookingId: bookingId,
                returnUrl: `${window.location.origin}/payment-success.html?bookingId=${bookingId}`,
                cancelUrl: `${window.location.origin}/payment.html?bookingId=${bookingId}&cancel=true`
            }),
            success: function(response) {
                // Create PayHere payment object
                const payment = {
                    sandbox: response.sandbox,
                    merchant_id: response.merchantId,
                    return_url: response.returnUrl,
                    cancel_url: response.cancelUrl,
                    notify_url: response.notifyUrl,
                    order_id: response.orderId,
                    items: response.items,
                    amount: response.amount,
                    currency: response.currency,
                    first_name: response.customerName.split(' ')[0],
                    last_name: response.customerName.split(' ')[1] || '',
                    email: response.customerEmail,
                    phone: response.customerPhone,
                    address: "No.1, Galle Road",
                    city: "Colombo",
                    country: "Sri Lanka",
                    custom_1: localStorage.getItem('userId'),
                    custom_2: bookingId
                };

                // Load PayHere checkout script
                if (typeof PayHere === 'undefined') {
                    const script = document.createElement('script');
                    script.src = response.sandbox ?
                        'https://sandbox.payhere.lk/payhere-checkout.js' :
                        'https://www.payhere.lk/payhere-checkout.js';
                    script.onload = () => startPayHerePayment(payment);
                    document.body.appendChild(script);
                } else {
                    startPayHerePayment(payment);
                }
            },
            error: function(xhr) {
                resetPaymentButton();
                showAlert('Failed to initialize payment. Please try again.', 'danger');
                console.error('PayHere init error:', xhr);
            }
        });
    }

    // Start PayHere payment
    function startPayHerePayment(payment) {
        PayHere.onCompleted = function(orderId) {
            console.log("Payment completed:", orderId);
            window.location.href = payment.return_url;
        };

        PayHere.onDismissed = function() {
            resetPaymentButton();
            console.log("Payment dismissed");
        };

        PayHere.onError = function(error) {
            resetPaymentButton();
            showAlert("Payment error: " + error, "danger");
            console.error("Payment error:", error);
        };

        PayHere.startPayment(payment);
    }

    // Process direct card payment (mock implementation)
    function processCardPayment() {
        // Validate card details
        if (!validateCardDetails()) {
            resetPaymentButton();
            return;
        }

        // Mock processing - replace with actual API call
        setTimeout(() => {
            $.ajax({
                url: `${apiBaseUrl}/api/payments`,
                method: 'POST',
                headers: {
                    'Authorization': 'Bearer ' + localStorage.getItem('jwtToken')
                },
                data: JSON.stringify({
                    amount: parseFloat($('#summaryAmount').text().replace(/[^0-9.]/g, '')),
                    paymentMethod: 'CARD',
                    status: 'PAID',
                    bookingId: bookingId
                }),
                success: function(response) {
                    window.location.href = `payment-success.html?bookingId=${bookingId}`;
                },
                error: function(xhr) {
                    resetPaymentButton();
                    showAlert('Payment failed. Please try again.', 'danger');
                    console.error('Payment error:', xhr);
                }
            });
        }, 1500);
    }

    // Validate card details
    function validateCardDetails() {
        const cardNumber = $('#cardNumber').val().replace(/\s/g, '');
        const cardExpiry = $('#cardExpiry').val();
        const cardCvv = $('#cardCvv').val();
        const cardName = $('#cardName').val();

        if (!cardNumber || cardNumber.length < 16) {
            showAlert('Please enter a valid card number', 'warning');
            return false;
        }

        if (!cardExpiry || !/^\d{2}\/\d{2}$/.test(cardExpiry)) {
            showAlert('Please enter a valid expiry date (MM/YY)', 'warning');
            return false;
        }

        if (!cardCvv || cardCvv.length < 3) {
            showAlert('Please enter a valid CVV', 'warning');
            return false;
        }

        if (!cardName) {
            showAlert('Please enter cardholder name', 'warning');
            return false;
        }

        return true;
    }

    // Show alert message
    function showAlert(message, type = 'danger') {
        const alert = $(`
            <div class="alert alert-${type} alert-dismissible fade show" role="alert">
                ${message}
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
        `);
        $('#alertsContainer').empty().append(alert);
    }

    // Reset payment button state
    function resetPaymentButton() {
        $('#payNowBtn').prop('disabled', false);
        $('#payButtonText').removeClass('d-none');
        $('#payButtonSpinner').addClass('d-none');
    }

    // Initialize the page
    initPaymentPage();
});