// Load packages and display as cards
function loadPackages() {
    $.ajax({
        url: "http://localhost:8080/travel-packages/getAll",
        type: "GET",
        headers: {
            'Authorization': 'Bearer ' + localStorage.getItem('jwtToken')
        },
        dataType: "json",
        success: function(data) {
            const packageContainer = $("#packageContainer");
            packageContainer.empty(); // Clear previous data

            $.each(data, function(index, package) {
                const imageUrl = `data:image/jpeg;base64,${package.image}`; // Image from base64
                const packageCard = `
                    <div class="col-lg-4 col-md-6 wow fadeInUp" data-wow-delay="0.${index + 1}s">
                        <div class="package-item">
                            <div class="overflow-hidden">
                                <img class="img-fluid" src="${imageUrl}" alt="${package.name}">
                            </div>
                            <div class="d-flex border-bottom">
                                <small class="flex-fill text-center border-end py-2">
                                    <i class="fa fa-map-marker-alt text-primary me-2"></i>${package.destination}
                                </small>
                                <small class="flex-fill text-center border-end py-2">
                                    <i class="fa fa-calendar-alt text-primary me-2"></i>${package.duration} days
                                </small>
                                <small class="flex-fill text-center py-2">
                                    <i class="fa fa-user text-primary me-2"></i>${package.guide}
                                </small>
                            </div>
                            <div class="text-center p-4">
                                <h3 class="mb-0">${package.price}.00</h3>
                                <p>${package.description}</p>
                                <div class="d-flex justify-content-center mb-2">
                                    <a href="bookings.html" class="btn btn-sm btn-primary px-3 border-end" style="border-radius: 30px 0 0 30px;">Read More</a>
                                    <a href="bookings.html" class="btn btn-sm btn-primary px-3" style="border-radius: 0 30px 30px 0;">Book Now</a>
                                </div>
                            </div>
                        </div>
                    </div>`;
                packageContainer.append(packageCard);
            });
        },
        error: function(xhr, status, error) {
            console.error("Error loading packages:", xhr.status, error);

            if (xhr.status === 403) {
                alert("Access Denied! Please log in again.");
                localStorage.removeItem("jwtToken"); // Clear invalid token
                window.location.href = "admin/login.html"; // Redirect to login page
            } else {
                alert("Failed to load packages. Please try again.");
            }
        }
    });
}

// Call function on page load
$(document).ready(function () {
    loadPackages();
});
