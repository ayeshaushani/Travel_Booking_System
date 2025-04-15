document.addEventListener('DOMContentLoaded', function () {
    // DOM Elements
    const manageGuidesLink = document.getElementById('manageGuidesLink');
    const addGuideLink = document.getElementById('addGuideLink');
    const backToGuides = document.getElementById('backToGuides');
    const dashboardOverview = document.getElementById('dashboardOverview');
    const guideManagement = document.getElementById('guideManagement');
    const guideFormContainer = document.getElementById('guideFormContainer');
    const guideForm = document.getElementById('guideForm');
    const guidesTableElement = document.getElementById('guidesTable');
    const guidesTable = guidesTableElement ? guidesTableElement.querySelector('tbody') : null;
    const searchInput = document.getElementById('searchGuides');
    const prevPageBtn = document.getElementById('prevPage');
    const nextPageBtn = document.getElementById('nextPage');
    const pageInfo = document.getElementById('pageInfo');
    const modal = document.getElementById('guideDetailModal');
    const closeModal = document.querySelector('.close-modal');

    // API Base URL
    const API_BASE_URL = 'http://localhost:8080/guides';

    if (!manageGuidesLink || !addGuideLink || !backToGuides || !dashboardOverview ||
        !guideManagement || !guideFormContainer || !guideForm || !guidesTable ||
        !searchInput || !prevPageBtn || !nextPageBtn || !pageInfo || !modal || !closeModal) {
        console.error('Some elements are missing from the DOM. Check your HTML!');
        return;
    }

    let currentPage = 1;
    let totalPages = 1;
    let allGuides = [];
    let filteredGuides = [];

    // Initialize the dashboard
    loadDashboardStats();
    loadAllGuides();

    // Event Listeners
    manageGuidesLink.addEventListener('click', e => {
        e.preventDefault();
        showGuideManagement();
    });

    addGuideLink.addEventListener('click', e => {
        e.preventDefault();
        showAddGuideForm();
    });

    backToGuides.addEventListener('click', e => {
        e.preventDefault();
        showGuideManagement();
    });

    guideForm.addEventListener('submit', e => {
        e.preventDefault();
        saveGuide();
    });

    searchInput.addEventListener('input', () => {
        filterGuides();
    });

    prevPageBtn.addEventListener('click', () => {
        if (currentPage > 1) {
            currentPage--;
            renderGuidesTable();
        }
    });

    nextPageBtn.addEventListener('click', () => {
        if (currentPage < totalPages) {
            currentPage++;
            renderGuidesTable();
        }
    });

    closeModal.addEventListener('click', () => {
        modal.style.display = 'none';
    });

    window.addEventListener('click', event => {
        if (event.target === modal) {
            modal.style.display = 'none';
        }
    });

    // Functions
    function showGuideManagement() {
        dashboardOverview.style.display = 'none';
        guideFormContainer.style.display = 'none';
        guideManagement.style.display = 'block';
    }

    function showAddGuideForm() {
        dashboardOverview.style.display = 'none';
        guideManagement.style.display = 'none';
        guideFormContainer.style.display = 'block';
        document.getElementById('formTitle').textContent = 'Add New Guide';
        guideForm.reset();
        document.getElementById('guideId').value = '';
    }

    function showEditGuideForm(guide) {
        dashboardOverview.style.display = 'none';
        guideManagement.style.display = 'none';
        guideFormContainer.style.display = 'block';
        document.getElementById('formTitle').textContent = 'Edit Guide';

        // Map backend fields to form fields
        document.getElementById('guideId').value = guide.guideId;
        document.getElementById('fullName').value = guide.name;
        document.getElementById('email').value = guide.email;
        document.getElementById('phone').value = guide.contact;
        document.getElementById('experience').value = guide.experience;
    }

    function loadDashboardStats() {
        // Since your backend doesn't have a stats endpoint, we'll calculate from all guides
        fetch(API_BASE_URL)
            .then(response => {

                if (!response.ok) throw new Error('Failed to load guides');
                return response.json();

            })

            .then(guides => {
                const total = guides.length;
                // Default values since status isn't in your DTO
                document.getElementById('totalGuides').textContent = total;
                document.getElementById('activeGuides').textContent = total;
                document.getElementById('onLeaveGuides').textContent = '0';
                document.getElementById('inactiveGuides').textContent = '0';
            })
            .catch(error => {
                console.error('Error loading dashboard stats:', error);
                // Fallback values
                document.getElementById('totalGuides').textContent = '0';
                document.getElementById('activeGuides').textContent = '0';
                document.getElementById('onLeaveGuides').textContent = '0';
                document.getElementById('inactiveGuides').textContent = '0';
            });
    }

    function loadAllGuides() {
        fetch(API_BASE_URL)

            .then(response => {
                if (!response.ok) throw new Error('Failed to load guides');
                return response.json();

            })
            .then(guides => {
                allGuides = guides.map(guide => ({
                    guideId: guide.guideId,
                    name: guide.name,
                    email: guide.email,
                    contact: guide.contact,
                    experience: guide.experience,
                    // Default values for fields not in your DTO
                    status: 'active',
                    languages: ['english'],
                    specializations: 'General',
                    certifications: '',
                    bio: `Professional guide with ${guide.experience} years of experience`
                }));

                filteredGuides = [...allGuides];
                currentPage = 1;
                calculateTotalPages();
                renderGuidesTable();
            })
            .catch(error => {
                console.error('Error loading guides:', error);
                showError('Failed to load guides. Please try again later.');
            });
    }

    function filterGuides() {
        const searchTerm = searchInput.value.toLowerCase();
        filteredGuides = allGuides.filter(guide =>
            guide.name.toLowerCase().includes(searchTerm) ||
            guide.email.toLowerCase().includes(searchTerm) ||
            guide.contact.toLowerCase().includes(searchTerm)
        );
        currentPage = 1;
        calculateTotalPages();
        renderGuidesTable();
    }

    function calculateTotalPages() {
        totalPages = Math.ceil(filteredGuides.length / 10) || 1;
        pageInfo.textContent = `Page ${currentPage} of ${totalPages}`;
    }

    function renderGuidesTable() {
        if (!guidesTable) return;

        guidesTable.innerHTML = '';
        const startIndex = (currentPage - 1) * 10;
        const guidesToShow = filteredGuides.slice(startIndex, startIndex + 10);

        if (guidesToShow.length === 0) {
            const row = guidesTable.insertRow();
            const cell = row.insertCell();
            cell.colSpan = 7;
            cell.textContent = 'No guides found';
            cell.style.textAlign = 'center';
            return;
        }

        guidesToShow.forEach(guide => {
            const row = guidesTable.insertRow();

            row.insertCell().textContent = guide.guideId;
            row.insertCell().textContent = guide.name;

            const contactCell = row.insertCell();
            contactCell.innerHTML = `<div>${guide.email}</div><div>${guide.contact}</div>`;

            row.insertCell().textContent = guide.languages.join(', ');
            row.insertCell().textContent = guide.specializations || 'N/A';

            const statusCell = row.insertCell();
            const badge = document.createElement('span');
            badge.className = `status-badge status-${guide.status}`;
            badge.textContent = guide.status.replace('_', ' ');
            statusCell.appendChild(badge);

            const actionCell = row.insertCell();
            actionCell.innerHTML = `
                <div class="action-btns">
                    <button class="btn-view" onclick="viewGuideDetails(${guide.guideId})">View</button>
                    <button class="btn-edit" onclick="editGuide(${guide.guideId})">Edit</button>
                    <button class="btn-delete" onclick="deleteGuide(${guide.guideId})">Delete</button>
                </div>
            `;
        });

        pageInfo.textContent = `Page ${currentPage} of ${totalPages}`;
        prevPageBtn.disabled = currentPage === 1;
        nextPageBtn.disabled = currentPage === totalPages;
    }

    function saveGuide() {
        const guideId = document.getElementById('guideId').value;
        const isNew = guideId === '';

        const formData = {
            name: document.getElementById('fullName').value,
            email: document.getElementById('email').value,
            contact: document.getElementById('phone').value,
            experience: parseInt(document.getElementById('experience').value) || 0
        };

        const url = isNew ? API_BASE_URL : `${API_BASE_URL}/${guideId}`;
        const method = isNew ? 'POST' : 'PUT';

        fetch(url, {
            method: method,
            headers: {
                'Authorization': 'Bearer ' + localStorage.getItem('jwtToken') // ✅ Add JWT token
            },
            body: JSON.stringify(formData)
        })
            .then(response => {
                if (!response.ok) throw new Error(`HTTP error! status: ${response.status}`);
                return response.json();
            })
            .then(data => {
                alert(isNew ? 'Guide added successfully!' : 'Guide updated successfully!');
                loadAllGuides();
                showGuideManagement();
            })
            .catch(error => {
                console.error('Error saving guide:', error);
                alert('Error saving guide: ' + error.message);
            });
    }

    function showError(message) {
        const errorDiv = document.createElement('div');
        errorDiv.className = 'error-message';
        errorDiv.textContent = message;
        document.getElementById('mainContent').prepend(errorDiv);
        setTimeout(() => errorDiv.remove(), 5000);
    }

    // Global functions for inline event handlers
    window.viewGuideDetails = function(guideId) {
        fetch(`${API_BASE_URL}/${guideId}`)
            .then(response => {
                if (!response.ok) throw new Error('Failed to load guide details');
                return response.json();
            })
            .then(guide => {
                const modalContent = document.getElementById('guideDetailContent');
                modalContent.innerHTML = `
                    <div class="guide-detail">
                        <div class="guide-photo">
                            <img src="https://ui-avatars.com/api/?name=${encodeURIComponent(guide.name)}&background=random" alt="${guide.name}">
                        </div>
                        <div class="guide-info">
                            <h3>${guide.name}</h3>
                            <p><span class="detail-label">Email:</span> ${guide.email}</p>
                            <p><span class="detail-label">Phone:</span> ${guide.contact}</p>
                            <p><span class="detail-label">Experience:</span> ${guide.experience} years</p>
                            <p><span class="detail-label">Status:</span> <span class="status-badge status-active">Active</span></p>
                        </div>
                    </div>
                    <div class="modal-actions">
                        <button class="btn-primary" onclick="editGuide(${guide.guideId}); document.getElementById('guideDetailModal').style.display='none'">Edit Guide</button>
                    </div>
                `;
                modal.style.display = 'flex';
            })
            .catch(error => {
                console.error('Error loading guide details:', error);
                alert('Failed to load guide details: ' + error.message);
            });
    };

    window.editGuide = function(guideId) {
        fetch(`${API_BASE_URL}/${guideId}`)
            .then(response => {
                if (!response.ok) throw new Error('Failed to load guide for editing');
                return response.json();
            })
            .then(guide => {
                showEditGuideForm({
                    guideId: guide.guideId,
                    name: guide.name,
                    email: guide.email,
                    contact: guide.contact,
                    experience: guide.experience
                });
            })
            .catch(error => {
                console.error('Error loading guide for editing:', error);
                alert('Failed to load guide for editing: ' + error.message);
            });
    };

    window.deleteGuide = function(guideId) {
        if (!confirm('Are you sure you want to delete this guide?')) return;

        fetch(`${API_BASE_URL}/${guideId}`, {
            method: 'DELETE'
        })
            .then(response => {
                if (!response.ok) throw new Error('Failed to delete guide');
                return response.text();
            })
            .then(message => {
                alert(message);
                loadAllGuides();
            })
            .catch(error => {
                console.error('Error deleting guide:', error);
                alert('Error deleting guide: ' + error.message);
            });
    };
});