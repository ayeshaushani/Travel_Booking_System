document.addEventListener("DOMContentLoaded", () => {
    const signupForm = document.getElementById("signupForm");
    const signupBtn = document.getElementById("signupBtn");
    const btnText = document.getElementById("btnText");
    const spinner = document.getElementById("spinner");
    const passwordInput = document.getElementById("password");
    const strengthMeterFill = document.getElementById("strengthMeterFill");
    const strengthText = document.getElementById("strengthText");
    const togglePassword = document.getElementById("togglePassword");

    // Password visibility toggle
    togglePassword.addEventListener("click", () => {
        const type = passwordInput.getAttribute("type") === "password" ? "text" : "password";
        passwordInput.setAttribute("type", type);
        togglePassword.classList.toggle("fa-eye-slash");
    });

    // Password strength check
    passwordInput.addEventListener("input", () => {
        const strength = checkPasswordStrength(passwordInput.value);
        strengthMeterFill.style.width = strength.percent + "%";
        strengthMeterFill.style.backgroundColor = strength.color;
        strengthText.textContent = strength.label;
    });

    function checkPasswordStrength(password) {
        let strength = 0;
        if (password.length > 5) strength += 1;
        if (/[A-Z]/.test(password)) strength += 1;
        if (/[0-9]/.test(password)) strength += 1;
        if (/[^A-Za-z0-9]/.test(password)) strength += 1;

        const strengthLevels = [
            { percent: 25, color: "#ef233c", label: "Weak" },
            { percent: 50, color: "#f9c74f", label: "Fair" },
            { percent: 75, color: "#43aa8b", label: "Good" },
            { percent: 100, color: "#4cc9f0", label: "Strong" }
        ];

        return strengthLevels[strength - 1] || strengthLevels[0];
    }

    // Form submission
    signupForm.addEventListener("submit", async (e) => {
        e.preventDefault();

        // Clear error messages
        document.getElementById("email-error").textContent = "";
        document.getElementById("password-error").textContent = "";
        document.getElementById("role-error").textContent = "";

        const email = document.getElementById("email").value.trim();
        const password = passwordInput.value.trim();
        const role = document.getElementById("role").value;

        let isValid = true;

        if (!validateEmail(email)) {
            document.getElementById("email-error").textContent = "Invalid email address";
            isValid = false;
        }

        if (password.length < 6) {
            document.getElementById("password-error").textContent = "Password must be at least 6 characters";
            isValid = false;
        }

        if (!role) {
            document.getElementById("role-error").textContent = "Please select a role";
            isValid = false;
        }

        if (!isValid) return;

        const formData = { email, password, role };

        // Show spinner
        btnText.textContent = "Signing Up...";
        spinner.classList.remove("hidden");
        signupBtn.disabled = true;

        try {
            const response = await fetch("http://localhost:8080/auth/signUp", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(formData),
            });

            const data = await response.json();

            if (!response.ok) {
                throw new Error(data.message || "Signup failed");
            }

            showToast("Signup successful! Redirecting...", "success");

            setTimeout(() => {
                if (role === "ADMIN") {
                    window.location.href = "../admin/AdminDashBoard.html";
                } else {
                    window.location.href = "../index.html";
                }
            }, 1500);

        } catch (error) {
            showToast(error.message || "Signup failed. Please try again.", "error");
            console.error("Signup error:", error);
        } finally {
            // Hide spinner
            btnText.textContent = "Sign Up";
            spinner.classList.add("hidden");
            signupBtn.disabled = false;
        }
    });

    function validateEmail(email) {
        const re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        return re.test(email);
    }

    function showToast(message, type = "success") {
        const toast = document.createElement("div");
        toast.className = `toast ${type}`;
        toast.textContent = message;
        document.body.appendChild(toast);

        // Show toast
        setTimeout(() => {
            toast.classList.add("show");
        }, 100);

        // Remove toast
        setTimeout(() => {
            toast.classList.remove("show");
            setTimeout(() => toast.remove(), 300);
        }, 3000);
    }
});
