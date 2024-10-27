
document.getElementById("newUserForm").addEventListener("submit", async function (event) {
    event.preventDefault();
    const formData = new FormData(this);
    const rolesSelected = Array.from(document.getElementById("roles").selectedOptions).map(option => parseInt(option.value, 10));

    const user = {
        name: formData.get("name"),
        surname: formData.get("surname"),
        age: parseInt(formData.get("age"), 10),
        email: formData.get("email"),
        password: formData.get("password"),
        roles: rolesSelected,
    };

    console.log("Creating user:", user);

    try {
        const response = await fetch("/admin/create", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(user),
        });

        if (response.ok) {
            await loadUsers(); // Wait for users to load before resetting form
            alert("Пользователь успешно создан!");
            const form = this;
            form.reset();
            const modal = bootstrap.Modal.getInstance(document.getElementById("newUserModal"));
            modal.hide();
        } else {
            const errorData = await response.json();
            throw new Error(errorData.message || "Не удалось создать пользователя");
        }
    } catch (error) {
        console.error("Error creating user:", error);
        alert("Ошибка при создании пользователя: " + error.message);
    }
});

document.addEventListener("DOMContentLoaded", function () {
    // Function to load users from the API
    async function loadUsers() {
        try {
            const response = await fetch("/admin/users");
            const data = await response.json();

            console.log("User data:", data); // Log user data to verify
            const tableBody = document.getElementById("users-table-body");
            tableBody.innerHTML = ""; // Clear the table

            data.forEach((user) => {
                // Create a row for each user
                const row = document.createElement("tr");
                row.innerHTML = `
                    <td>${user.id}</td>
                    <td>${user.name}</td>
                    <td>${user.surname}</td>
                    <td>${user.age}</td>
                    <td>${user.email}</td>
                    <td>${user.roles.map((role) => role.name).join(", ")}</td>
                    <td><a href="/admin/edit/${user.id}" class="btn btn-info">Edit</a></td>
                    <td><button class="btn btn-danger" onclick="deleteUser(${user.id})">Delete</button></td>
                `;
                tableBody.appendChild(row);
            });
        } catch (error) {
            console.error("Ошибка при загрузке пользователей:", error);
        }
    }

    // Function to delete a user
    async function deleteUser(userId) {
        try {
            const response = await fetch(`/admin/users/${userId}`, {
                method: "DELETE",
            });

            if (response.ok) {
                loadUsers(); // Reload user list after deletion
            } else {
                console.error("Ошибка при удалении пользователя");
            }
        } catch (error) {
            console.error("Error deleting user:", error);
        }
    }

    // Load users when the page loads
    loadUsers();
});


