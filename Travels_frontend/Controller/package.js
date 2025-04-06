// Display message in message box
console.log("token",localStorage.getItem("jwtToken"))


function showMessage(msg, type) {
    const messageBox = document.getElementById("messageBox");
    messageBox.innerText = msg;
    messageBox.className = `message ${type}`;
    messageBox.style.display = "block";
    setTimeout(() => {
        messageBox.style.display = "none";
    }, 3000);
}

// Handle form submission for creating or editing a package
$(document).ready(function() {
    $("#packageForm").submit(function(event) {

        event.preventDefault();

        let formData = new FormData();
        formData.append("tPackageId",$("#id").val());
        formData.append("name", $("#name").val());
        formData.append("destination", $("#destination").val());
        formData.append("duration", $("#duration").val());
        formData.append("price", $("#price").val());
        formData.append("description", $("#description").val());
        formData.append("guide", $("#guide").val());

        let imageFile = $("#image")[0].files[0]; // Image file ekata reference ekak
        if (imageFile) {
            formData.append("image", imageFile);
        }
        console.log("Form Data: ", formData);
        let actionUrl = "http://localhost:8080/travel-packages";
        let method = "POST";

        // Check if it's an update request (if packageId exists)
        const packageId = $("#packageForm").data("packageId");
        if (packageId) {
            actionUrl = `http://localhost:8080/travel-packages/${packageId}`;
            method = "PUT";
        }

        $.ajax({
            url: actionUrl,
            type: method,
            headers: {
                'Authorization': 'Bearer ' + localStorage.getItem('jwtToken') // ✅ Add JWT token
            },
            data: formData,
            processData: false,
            contentType: false,

            success: function(response) {
                showMessage("Package saved successfully!", "success");
                $("#packageForm")[0].reset();
                $("#imagePreview").hide();
                loadPackages(); // Refresh package list (Use correct function name)
            },
            error: function(xhr, status, error) {
                console.error("Error saving package:", error);

                if (xhr.status === 403) {
                    alert("Access Denied! Please log in again.");
                    localStorage.removeItem("jwtToken"); // Clear invalid token
                    window.location.href = "login.html"; // Redirect to login page
                } else {
                    showMessage("Error saving package! Please try again.", "error");
                }
            }
        });
    });
});

// Load packages and populate the table using AJAX
function loadPackages() {
    $.ajax({
        url: "http://localhost:8080/travel-packages/getAll",
        type: "GET",
        headers: {
            'Authorization': 'Bearer ' + localStorage.getItem('jwtToken')
        },
        dataType: "json",
        success: function(data) {
            const tableBody = $("#packagesTable tbody");
            tableBody.empty(); // Clear existing rows

            $.each(data, function(index, package) {
                const imageUrl = `http://localhost:8080/images/${package.image}`;
                const row = `
                     <tr>
                     <td>${package.tPackageId}</td>
                    <td>${package.name}</td>
                    <td>${package.destination}</td>
                    <td>${package.duration}</td>
                    <td>${package.price}</td>
                    <td>${package.description}</td> 
                    <td>${package.guide}</td> 
                     <td>
                                <img src="data:image/jpeg;base64,${package.image}" 
                                     alt="User Image" class="crop-image" 
                                     style="width: 50px; cursor: pointer;">
                            </td>
                     
                    <td>
                        <button class="button success" onclick="editPackage(${package.tPackageId})">Edit</button>
                        <button class="button error" onclick="deletePackage(${package.tPackageId})">Delete</button>
                    </td>
                </tr>
                `;
                tableBody.append(row);
            });
        },
        error: function(xhr, status, error) {
            console.error("Error loading packages:", xhr.status, error);

            if (xhr.status === 403) {
                alert("Access Denied! Please log in again.");
                localStorage.removeItem("jwtToken"); // Clear invalid token
                window.location.href = "login.html"; // Redirect to login page
            } else {
                alert("Failed to load packages. Please try again.");
            }
        }
    });
}

// Load packages when the page is ready
$(document).ready(function() {
    loadPackages();
});


// edit package details
function editPackage(packageId) {
    // Fetch package details and populate the form
    $.ajax({
        url: `http://localhost:8080/travel-packages/${packageId}`,
        type: "GET",
        headers: {
            "Authorization": `Bearer ${localStorage.getItem("jwtToken")}`
        },
        success: function (package) {
            // Populate form with existing data
            $("#id").val(package.id);
            $("#name").val(package.name);
            $("#destination").val(package.destination);
            $("#duration").val(package.duration);
            $("#price").val(package.price);
            $("#description").val(package.description);
            $("#guide").val(package.guide);

            // Set package ID in form
            $("#packageForm").data("packageId", packageId);

            // Set image preview
            if (package.image) {
                $("#imagePreview").attr("src", `http://localhost:8080/images/${package.image}`).show();
            } else {
                $("#imagePreview").hide();
            }
        },
        error: function (xhr, status, error) {
            console.error("Error fetching package details:", xhr.responseText);
            alert("Failed to load package details. Please try again.");
        }
    });
}

// Update package function
function updatePackage() {
    const packageId = $("#packageForm").data("packageId"); // Get package ID

    if (!packageId) {
        alert("Error: No package selected for update.");
        return;
    }

    const formData = new FormData();
    formData.append("id", $("#id").val());
    formData.append("name", $("#name").val());
    formData.append("destination", $("#destination").val());
    formData.append("duration", $("#duration").val());
    formData.append("price", $("#price").val());
    formData.append("description", $("#description").val());
    formData.append("guide", $("#guide").val());

    // Check if a new image is selected
    const imageFile = $("#image")[0].files[0];
    if (imageFile) {
        formData.append("image", imageFile);
    }

    $.ajax({
        url: `http://localhost:8080/travel-packages/${packageId}`,
        type: "PUT",
        headers: {
            "Authorization": `Bearer ${localStorage.getItem("jwtToken")}`
        },
        data: formData,
        processData: false, // Don't process FormData
        contentType: false, // Don't set content type (browser will do it)
        success: function (response) {
            alert("Package updated successfully!");
            $("#packageForm")[0].reset(); // Clear form after update
            $("#imagePreview").hide(); // Hide image preview if needed
            loadPackages(); // Reload packages list
        },
        error: function (xhr, status, error) {
            console.error("Error updating package:", xhr.responseText);
            alert("Failed to update package. Please try again.");
        }
    });
}



// Delete a package
function deletePackage(id) {
    fetch(`http://localhost:8080/travel-packages/${id}`, {
        method: "DELETE",
        headers: {
            "Authorization": `Bearer ${localStorage.getItem("jwtToken")}`
        },
    })
        .then(response => {
            if (response.ok) {
                showMessage("Package deleted successfully!", "success");
                loadPackages(); // Refresh the package list after deletion
            } else {
                showMessage("Error deleting package!", "error");
            }
        })
        .catch(error => {
            console.error("Error deleting package:", error);
        });
}

// Load all packages when the page loads
window.onload = function() {
    loadPackages();
};