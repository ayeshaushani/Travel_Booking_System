$('#signInForm').on('submit', function (event) {
    event.preventDefault(); // Prevent the form from submitting traditionally

    const email = $('#email').val().trim();
    const password = $('#password').val().trim();

    // Validation
    let validationErrors = [];

    // Email validation
    const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!email) {
        validationErrors.push("Email is required.");
    } else if (!emailPattern.test(email)) {
        validationErrors.push("Invalid email format.");
    }

    // Password validation
    if (!password) {
        validationErrors.push("Password is required.");
    } else if (password.length < 6) {
        validationErrors.push("Password must be at least 6 characters.");
    }

    // Show validation errors if any
    if (validationErrors.length > 0) {
        let errorMessage = validationErrors.join(" ");

        let $errorBox = $('#loginError');
        if ($errorBox.length === 0) {
            $errorBox = $(`
                <div id="loginError" class="alert alert-danger alert-dismissible fade show" role="alert"
                     style="position: fixed; top: 20px; right: 20px; z-index: 9999; min-width: 300px;">
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                    <div class="d-flex align-items-center">
                        <i class="bi bi-exclamation-triangle-fill me-2"></i>
                        <div>
                            <strong>Validation Error</strong>
                            <div class="error-message">${errorMessage}</div>
                        </div>
                    </div>
                </div>
            `);
            $('body').append($errorBox);
        } else {
            $errorBox.find('.error-message').text(errorMessage);
            $errorBox.removeClass('fade').show();
        }

        setTimeout(() => {
            $errorBox.alert('close');
        }, 5000);

        return; // Stop if validation fails
    }

    // If validation passes, proceed to send AJAX request
    const signInDTO = {
        email: email,
        password: password
    }

    $.ajax({
        url: 'http://localhost:8080/auth/signIn',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(signInDTO),
        success: function (response) {
            console.log(response);
            localStorage.setItem('jwtToken', response.tokens);
            localStorage.setItem('userId', response.userId);
            localStorage.setItem('userRole', response.userRole);

            switch (response.userRole) {
                case 'ADMIN':
                    window.location.href = '../admin/AdminDashboard.html';
                    break;
                case 'USER':
                    window.location.href = '../index.html';
                    break;
                default:
                    window.location.href = '../index.html';
            }
        },
        error: function (xhr) {
            let errorMessage;
            if (xhr.status === 0) {
                errorMessage = "Network error - please check your connection";
            } else if (xhr.status === 401) {
                errorMessage = "Invalid email or password";
            } else if (xhr.status === 403) {
                errorMessage = "Account not authorized";
            } else if (xhr.responseJSON && xhr.responseJSON.message) {
                errorMessage = xhr.responseJSON.message;
            } else {
                errorMessage = "Login failed. Please try again later.";
            }

            let $errorBox = $('#loginError');
            if ($errorBox.length === 0) {
                $errorBox = $(`
                    <div id="loginError" class="alert alert-danger alert-dismissible fade show" role="alert"
                         style="position: fixed; top: 20px; right: 20px; z-index: 9999; min-width: 300px;">
                        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                        <div class="d-flex align-items-center">
                            <i class="bi bi-exclamation-triangle-fill me-2"></i>
                            <div>
                                <strong>Login Failed</strong>
                                <div class="error-message">${errorMessage}</div>
                            </div>
                        </div>
                    </div>
                `);
                $('body').append($errorBox);
            } else {
                $errorBox.find('.error-message').text(errorMessage);
                $errorBox.removeClass('fade').show();
            }

            setTimeout(() => {
                $errorBox.alert('close');
            }, 5000);
        }
    });
});



/*

$('#signInForm').on('submit', function (event) {
    event.preventDefault(); // Prevent the form from submitting traditionally

    const email = $('#email').val();
    const password = $('#password').val();

    const signInDTO = {
        email: email,
        password: password
    }

    $.ajax({
        url: 'http://localhost:8080/auth/signIn',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(signInDTO),
        success: function (response) {
            console.log(response);
            localStorage.setItem('jwtToken', response.tokens);
            localStorage.setItem('userId', response.userId);
            localStorage.setItem('userRole', response.userRole);
           /!* window.location.href = 'AdminDashBoard.html';*!/

            switch(response.userRole) {
                case 'ADMIN':
                    window.location.href = '../admin/AdminDashboard.html';
                    break;
                case 'GUIDE':
                    window.location.href = '../guide/guideManagment.html';
                    break;
                default:
                    window.location.href = '../index.html';
            }
        },

        error: function(xhr) {
            // Determine error message
            let errorMessage;
            if (xhr.status === 0) {
                errorMessage = "Network error - please check your connection";
            } else if (xhr.status === 401) {
                errorMessage = "Invalid email or password";
            } else if (xhr.status === 403) {
                errorMessage = "Account not authorized";
            } else if (xhr.responseJSON && xhr.responseJSON.message) {
                errorMessage = xhr.responseJSON.message;
            } else {
                errorMessage = "Login failed. Please try again later.";
            }

            // Create or update error message display
            let $errorBox = $('#loginError');
            if ($errorBox.length === 0) {
                $errorBox = $(`
            <div id="loginError" class="alert alert-danger alert-dismissible fade show" role="alert"
                 style="position: fixed; top: 20px; right: 20px; z-index: 9999; min-width: 300px;">
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                <div class="d-flex align-items-center">
                    <i class="bi bi-exclamation-triangle-fill me-2"></i>
                    <div>
                        <strong>Login Failed</strong>
                        <div class="error-message">${errorMessage}</div>
                    </div>
                </div>
            </div>
        `);
                $('body').append($errorBox);
            } else {
                $errorBox.find('.error-message').text(errorMessage);
                $errorBox.removeClass('fade').show();
            }

            // Auto-dismiss after 5 seconds
            setTimeout(() => {
                $errorBox.alert('close');
            }, 5000);
        }
    });

   /!* document.getElementById('forgotPasswordButton').addEventListener("click", function () {
        window.location.href = "../otp.html";
    });*!/



});
*/
