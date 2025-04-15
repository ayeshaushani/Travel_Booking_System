document.addEventListener('DOMContentLoaded', function() {
    // Get all DOM elements
    const paymentForm = document.getElementById('payment-form');
    const paymentMethod = document.getElementById('paymentMethod');
    const cardDetails = document.getElementById('card-details');
    const amountInput = document.getElementById('amount');
    const bookingIdInput = document.getElementById('bookingId');
    const bookingIdSpan = document.getElementById('booking-id');
    const bookingAmountSpan = document.getElementById('booking-amount');
    const payButton = document.getElementById('pay-button');
    const paymentStatus = document.getElementById('payment-status');
    const successMessage = document.getElementById('success-message');
    const errorMessage = document.getElementById('error-message');
    const errorText = document.getElementById('error-text');
    const tryAgainBtn = document.getElementById('try-again');
    const paymentDetails = document.getElementById('payment-details');

    // Initialize the payment page
    initPaymentPage();

    function initPaymentPage() {
        // Get booking ID from URL or localStorage
        const urlParams = new URLSearchParams(window.location.search);
        const bookingId = urlParams.get('bookingId') || localStorage.getItem('currentBookingId');

        if (!bookingId) {
            showError('No booking ID provided. Please make a booking first.');
            return;
        }

        // Set booking ID in the form
        bookingIdInput.value = bookingId;
        bookingIdSpan.textContent = bookingId;

        // Fetch booking details
        fetchBookingDetails(bookingId);
    }

    // Fetch booking details from backend
    async function fetchBookingDetails(bookingId) {
        try {
            showLoading(true);

            const response = await fetch(`http://localhost:8080/bookings/${bookingId}`);

            if (!response.ok) {
                throw new Error(`Failed to fetch booking: ${response.status}`);
            }

            const bookingData = await response.json();

            // Update UI with booking details
            bookingAmountSpan.textContent = bookingData.totalPrice.toFixed(2);
            amountInput.value = bookingData.totalPrice.toFixed(2);

        } catch (error) {
            console.error('Error fetching booking:', error);
            showError('Failed to load booking details. Please try again.');
        } finally {
            showLoading(false);
        }
    }

    // Process payment
    async function processPayment(paymentData) {
        try {
            showLoading(true);

            const response = await fetch('http://localhost:8080/payments', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(paymentData)
            });

            if (!response.ok) {
                const errorData = await response.json().catch(() => ({}));
                throw new Error(errorData.message || 'Payment failed');
            }

            const paymentResult = await response.json();
            showSuccess(paymentResult);

            // Clear the booking ID after successful payment
            localStorage.removeItem('currentBookingId');

        } catch (error) {
            console.error('Payment error:', error);
            showError(error.message || 'Payment processing failed. Please try again.');
        } finally {
            showLoading(false);
        }
    }

    // Show loading state
    function showLoading(isLoading) {
        if (isLoading) {
            payButton.disabled = true;
            payButton.textContent = 'Processing...';
        } else {
            payButton.disabled = false;
            payButton.textContent = 'Pay Now';
        }
    }

    // Show success message
    function showSuccess(payment) {
        paymentForm.classList.add('hidden');
        paymentStatus.classList.remove('hidden');
        successMessage.classList.remove('hidden');
        errorMessage.classList.add('hidden');

        // Display payment details
        paymentDetails.innerHTML = `
            <p><strong>Payment ID:</strong> ${payment.paymentId}</p>
            <p><strong>Amount:</strong> $${payment.amount.toFixed(2)}</p>
            <p><strong>Method:</strong> ${payment.paymentMethod}</p>
            <p><strong>Status:</strong> ${payment.status}</p>
            <p><strong>Booking ID:</strong> ${payment.bookingId}</p>
        `;
    }

    // Show error message
    function showError(message) {
        paymentForm.classList.add('hidden');
        paymentStatus.classList.remove('hidden');
        successMessage.classList.add('hidden');
        errorMessage.classList.remove('hidden');
        errorText.textContent = message;
    }

    // Event listeners
    paymentMethod.addEventListener('change', function() {
        cardDetails.classList.toggle('hidden', !['credit', 'debit'].includes(this.value));
    });

    paymentForm.addEventListener('submit', function(e) {
        e.preventDefault();

        // Validate form
        if (!paymentForm.checkValidity()) {
            alert('Please fill all required fields correctly');
            return;
        }

        const paymentData = {
            amount: parseFloat(amountInput.value),
            paymentMethod: paymentMethod.value,
            status: 'Pending',
            bookingId: bookingIdInput.value
        };

        processPayment(paymentData);
    });

    tryAgainBtn.addEventListener('click', function() {
        paymentStatus.classList.add('hidden');
        paymentForm.classList.remove('hidden');
    });

    // Format card inputs
    document.getElementById('cardNumber').addEventListener('input', function(e) {
        this.value = this.value.replace(/\s/g, '').replace(/(\d{4})/g, '$1 ').trim();
    });

    document.getElementById('expiryDate').addEventListener('input', function(e) {
        this.value = this.value.replace(/\D/g, '').replace(/(\d{2})(\d)/, '$1/$2');
    });
});