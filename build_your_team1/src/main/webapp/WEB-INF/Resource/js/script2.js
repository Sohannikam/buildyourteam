/**
 * 
 */

 
document.addEventListener("DOMContentLoaded", function () {
    console.log("Script loaded successfully!"); // Check if the script is loaded

    const tabs = document.querySelectorAll("#network-tabs .nav-link");
    const contentSections = document.querySelectorAll(".tab-content");

    console.log("Checking if document is fully loaded...");
    console.log("network-tabs:", document.getElementById("network-tabs")); // Should not be null
    console.log("All nav links:", document.querySelectorAll("#network-tabs .nav-link").length); // Should be > 0

    console.log("inside the Tab Switching content")
    console.log("Found tabs:", tabs.length); // Check if tabs are found

    tabs.forEach(tab => {
        console.log("inside of tab switching JavaScript");
        tab.addEventListener("click", function (e) {
            e.preventDefault();
            console.log("Tab clicked:", this.textContent);
            console.log("data-section attribute:", this.getAttribute("data-section"));

            // Remove active class from all tabs
            tabs.forEach(t => t.classList.remove("active"));
            this.classList.add("active");

            // Hide all content sections
            contentSections.forEach(section => section.style.display = "none");

            // Show the selected section
            const sectionId = this.getAttribute("data-section");
            document.getElementById(sectionId).style.display = "block";
        });
    });
});