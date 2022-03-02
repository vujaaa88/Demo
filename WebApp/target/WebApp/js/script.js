const table = document.querySelector('.table');
const modal = document.querySelector(".modal");
const modalContent = document.querySelector(".modal-content");
const message = document.querySelector(".msg");
const span = document.getElementsByClassName("close")[0];

getUsers();

function createTable(response) {
    let emails = [];
    if (Object.keys(response).length != 0) {
        let htmlOutput = `<tr><th>Full Name</th><th>Address</th><th>City</th><th>Country</th><th>Email</th><th>Action</th></tr>`;
        response.map(continent => {
            continent.countries.map(country => {
                country.users.map(user => {
                    emails.push(user.email);
                    htmlOutput += `<tr>`;
                    htmlOutput += `<td>${user.firstName} ${user.lastName}</td>`;
                    htmlOutput += `<td>${user.address}</td>`;
                    htmlOutput += `<td>${user.city}</td>`;
                    htmlOutput += `<td>${user.country}</td>`;
                    htmlOutput += `<td>${user.email}</td>`;
                    htmlOutput += `<td><form action=\"/WebApp/delete?email=${user.email}\" method=\"post\"><button type=\"submit\" class=\"btn-del\">Delete</button></form></td></tr>`;
                });
            });
        });
        localStorage.setItem("emails", emails);
        table.innerHTML = htmlOutput;
        tableCreated = true;
    }
}

async function getUsers() {
    const response = await fetch('http://localhost:8080/WebApp/data', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    }).then(res => res.json())
    createTable(response);
}

document.querySelector(".btn-reg").addEventListener("click", function() {
    let elements = document.querySelectorAll('input');
    let filtered = [...elements].filter(e => e.value != "");
    let userEmail = document.querySelector('input[name="email"]').value;
    let emails = localStorage.getItem("emails").split(",");

    if (filtered.length == 6) {
        let hasMatch = emails.filter(e => e === userEmail)
        if (hasMatch.length > 0) {
            localStorage.setItem("error", userEmail);
        } else {
            localStorage.setItem("success", userEmail);
        }
    }
});

span.onclick = function() {
    modal.style.display = "none";
    modalContent.className = "modal-content";
}

window.onclick = function(event) {
    if (event.target == modal) {
        modal.style.display = "none";
        modalContent.className = "modal-content";
    }
}

window.onload = function() {
    const error = localStorage.getItem("error");
    const success = localStorage.getItem("success");
    const deleteUser = localStorage.getItem("delete");

    if (error) {
        modalContent.classList.add("error");
        message.textContent = "User with email " + error + " already exist.";
        localStorage.removeItem("error");
    } else if (success) {
        message.textContent = "User with email " + success + " added.";
        modalContent.classList.add("success");
        localStorage.removeItem("success");
    } else if (deleteUser) {
        message.textContent = "User with email " + deleteUser + " deleted.";
        modalContent.classList.add("success");
        localStorage.removeItem("delete");
    }
    if (error || success || deleteUser) {
        modal.style.display = "block";
    }
}