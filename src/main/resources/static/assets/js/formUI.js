document.addEventListener('DOMContentLoaded', function () {
    const minRatingId = "minRating";
    const maxUsersId = "maxUsers";
    // Toggle functionality
    const toggleInput = document.getElementById('toggleAdvanced');
    const advancedOptions = document.getElementById('advancedOptions');
    const collapseInstance = new bootstrap.Collapse(advancedOptions, {
        toggle: false
    });

    toggleInput.addEventListener('change', function () {
        if (this.checked) {
            collapseInstance.show();
        } else {
            collapseInstance.hide();
        }
    });

    // Range slider updates
    document.getElementById(minRatingId).addEventListener('input', function () {
        document.getElementById('minRatingValue').textContent = this.value;
    });

    document.getElementById(maxUsersId).addEventListener('input', function () {
        document.getElementById('maxUsersValue').textContent = this.value;
    });

    // Preset functions
    window.setPreset = function (preset) {
        const minRating = document.getElementById(minRatingId);
        const maxUsers = document.getElementById(maxUsersId);
        const minRatingValue = document.getElementById('minRatingValue');
        const maxUsersValue = document.getElementById('maxUsersValue');

        switch (preset) {
            case 'fast':
                minRating.value = 9;
                maxUsers.value = 250;
                break;
            case 'balanced':
                minRating.value = 8;
                maxUsers.value = 750;
                break;
            case 'comprehensive':
                minRating.value = 7;
                maxUsers.value = 1500;
                break;
        }

        minRatingValue.textContent = minRating.value;
        maxUsersValue.textContent = maxUsers.value;
    };
});