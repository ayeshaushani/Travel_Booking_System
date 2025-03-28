document.addEventListener('DOMContentLoaded', function() {
    // Fetch packages from your backend
    fetch('http://localhost:8080/travel-packages')
        .then(response => response.json())
        .then(packages => {
            const packageContainer = document.getElementById('packageContainer');
            packageContainer.innerHTML = '';

            if (packages.length === 0) {
                packageContainer.innerHTML = '<div class="col-12 text-center py-4"><p>No packages available at the moment.</p></div>';
                return;
            }

            packages.forEach(pkg => {
                const packageCol = document.createElement('div');
                packageCol.className = 'col-lg-4 col-md-6 wow fadeInUp';
                packageCol.setAttribute('data-wow-delay', '0.1s');

                packageCol.innerHTML = `
                        <div class="package-item">
                            <div class="overflow-hidden">
                                <img class="img-fluid" src="${pkg.image || 'img/default-package.jpg'}" alt="${pkg.name}">
                            </div>
                            <div class="d-flex border-bottom">
                                <small class="flex-fill text-center border-end py-2">
                                    <i class="fa fa-map-marker-alt text-primary me-2"></i>${pkg.destination}
                                </small>
                                <small class="flex-fill text-center border-end py-2">
                                    <i class="fa fa-calendar-alt text-primary me-2"></i>${pkg.duration}
                                </small>
                                <small class="flex-fill text-center py-2">
                                    <i class="fa fa-user text-primary me-2"></i>2 Person
                                </small>
                            </div>
                            <div class="text-center p-4">
                                <h3 class="mb-0">Rs. ${pkg.price.toFixed(2)}</h3>
                                <div class="mb-3">
                                    <small class="fa fa-star text-primary"></small>
                                    <small class="fa fa-star text-primary"></small>
                                    <small class="fa fa-star text-primary"></small>
                                    <small class="fa fa-star text-primary"></small>
                                    <small class="fa fa-star text-primary"></small>
                                </div>
                                <p>${pkg.description || 'Explore this amazing destination'}</p>
                                <div class="d-flex justify-content-center mb-2">
                                    <a href="#" class="btn btn-sm btn-primary px-3 border-end" 
                                       style="border-radius: 30px 0 0 30px;">Read More</a>
                                    <a href="booking.html?packageId=${pkg.tPackageId}" 
                                       class="btn btn-sm btn-primary px-3" 
                                       style="border-radius: 0 30px 30px 0;">Book Now</a>
                                </div>
                            </div>
                        </div>
                    `;

                packageContainer.appendChild(packageCol);
            });
        })
        .catch(error => {
            console.error('Error loading packages:', error);
            document.getElementById('packageContainer').innerHTML = `
                    <div class="col-12 text-center py-4">
                        <p class="text-danger">Error loading packages. Please try again later.</p>
                    </div>
                `;
        });
});
