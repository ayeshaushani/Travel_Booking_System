$(document).ready(function() {
    // DOM Elements
    const guideForm = $('#guideForm');
    const guideTableBody = $('#guideTableBody');
    const searchInput = $('#searchInput');
    const saveBtn = $('#saveBtn');
    const resetBtn = $('#resetBtn');
    const formTitle = $('#formTitle');
    const confirmModal = new bootstrap.Modal('#confirmModal');
    const successToast = new bootstrap.Toast('#successToast');
    const errorToast = new bootstrap.Toast('#errorToast');

    let currentPage = 1;
    let guides = [];
    let guideToDelete = null;

    // Initialize the page
    loadGuides();

    // Form submission
    guideForm.on('submit', function(e) {
        e.preventDefault();

        if (validateForm()) {
            const guideData = {
                guideId: $('#guideId').val() || null,
                name: $('#name').val(),
                contact: $('#contact').val(),
                email: $('#email').val(),
                experience: $('#experience').val()
            };

            const isUpdate = guideData.guideId !== null;

            $.ajax({
                url: isUpdate ? `http://localhost:8080/guides/${guideData.guideId}` : 'http://localhost:8080/guides',
                type: isUpdate ? 'PUT' : 'POST',
                headers: {
                    'Authorization': 'Bearer ' + localStorage.getItem('jwtToken')
                },
                contentType: 'application/json',
                data: JSON.stringify(guideData),
                success: function(response) {
                    showSuccessToast(isUpdate ? 'Guide updated successfully!' : 'Guide added successfully!');
                    resetForm();
                    loadGuides();
                },
                error: function(xhr) {
                    showErrorToast(xhr.responseJSON?.message || 'An error occurred. Please try again.');
                }
            });
        }
    });

    // Reset form
    resetBtn.on('click', resetForm);

    // Search functionality
    $('#searchBtn').on('click', function() {
        currentPage = 1;
        loadGuides();
    });

    searchInput.on('keyup', function(e) {
        if (e.key === 'Enter') {
            currentPage = 1;
            loadGuides();
        }
    });

    // Confirm delete
    $('#confirmDeleteBtn').on('click', function() {
        if (guideToDelete) {
            $.ajax({
                url: `http://localhost:8080/guides/${guideToDelete}`,
                type: 'DELETE',
                headers: {
                    'Authorization': 'Bearer ' + localStorage.getItem('jwtToken')
                },
                success: function() {
                    showSuccessToast('Guide deleted successfully!');
                    confirmModal.hide();
                    loadGuides();
                },
                error: function(xhr) {
                    showErrorToast(xhr.responseJSON?.message || 'Failed to delete guide.');
                    confirmModal.hide();
                }
            });
        }
    });

    // Load guides from API
    function loadGuides() {
        const searchTerm = searchInput.val().trim();
        let url = 'http://localhost:8080/guides';

        if (searchTerm) {
            url += `?search=${encodeURIComponent(searchTerm)}`;
        }

        $.ajax({
            url: url,
            type: 'GET',
            headers: {
                'Authorization': 'Bearer ' + localStorage.getItem('jwtToken')
            },
            success: function(data) {
                guides = Array.isArray(data) ? data : [];
                renderGuideTable();
            },
            error: function(xhr) {
                showErrorToast('Failed to load guides. Please try again.');
                console.error('Error loading guides:', xhr);
            }
        });
    }

    // Render guide table
    function renderGuideTable() {
        guideTableBody.empty();

        if (guides.length === 0) {
            guideTableBody.append(`
                <tr>
                    <td colspan="5" class="text-center py-4">No guides found</td>
                </tr>
            `);
            return;
        }

        guides.forEach(guide => {
            const row = `
                <tr data-id="${guide.guideId}">
                    <td>${guide.name}</td>
                    <td>${guide.contact}</td>
                    <td>${guide.email}</td>
                    <td>
                        <span class="badge bg-primary experience-badge">
                            ${guide.experience} ${guide.experience === 1 ? 'year' : 'years'}
                        </span>
                    </td>
                    <td>
                        <button class="btn btn-sm btn-warning me-2 edit-btn action-btn">
                            <i class="bi bi-pencil"></i> Edit
                        </button>
                        <button class="btn btn-sm btn-danger delete-btn action-btn">
                            <i class="bi bi-trash"></i> Delete
                        </button>
                    </td>
                </tr>
            `;
            guideTableBody.append(row);
        });

        // Add event listeners to dynamically created buttons
        $('.edit-btn').on('click', function() {
            const guideId = $(this).closest('tr').data('id');
            editGuide(guideId);
        });

        $('.delete-btn').on('click', function() {
            guideToDelete = $(this).closest('tr').data('id');
            confirmModal.show();
        });
    }

    // Edit guide
    function editGuide(guideId) {
        const guide = guides.find(g => g.guideId === guideId);

        if (guide) {
            $('#guideId').val(guide.guideId);
            $('#name').val(guide.name);
            $('#contact').val(guide.contact);
            $('#email').val(guide.email);
            $('#experience').val(guide.experience);

            formTitle.text('Edit Guide');
            saveBtn.html('<i class="bi bi-save"></i> Update Guide');
            $('html, body').animate({
                scrollTop: guideForm.offset().top
            }, 500);
        }
    }

    // Reset form
    function resetForm() {
        guideForm.trigger('reset');
        $('#guideId').val('');
        formTitle.text('Add New Guide');
        saveBtn.html('<i class="bi bi-save"></i> Save Guide');
        $('.is-invalid').removeClass('is-invalid');
    }

    // Form validation
    function validateForm() {
        let isValid = true;
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        const contactRegex = /^\+?[0-9]{10,15}$/;

        // Validate name
        if ($('#name').val().trim() === '') {
            $('#name').addClass('is-invalid');
            isValid = false;
        } else {
            $('#name').removeClass('is-invalid');
        }

        // Validate contact
        if (!contactRegex.test($('#contact').val())) {
            $('#contact').addClass('is-invalid');
            isValid = false;
        } else {
            $('#contact').removeClass('is-invalid');
        }

        // Validate email
        if (!emailRegex.test($('#email').val())) {
            $('#email').addClass('is-invalid');
            isValid = false;
        } else {
            $('#email').removeClass('is-invalid');
        }

        // Validate experience
        if ($('#experience').val() === '' || parseInt($('#experience').val()) < 0) {
            $('#experience').addClass('is-invalid');
            isValid = false;
        } else {
            $('#experience').removeClass('is-invalid');
        }

        return isValid;
    }

    // Show success toast
    function showSuccessToast(message) {
        $('#successToastBody').text(message);
        successToast.show();
    }

    // Show error toast
    function showErrorToast(message) {
        $('#errorToastBody').text(message);
        errorToast.show();
    }
});