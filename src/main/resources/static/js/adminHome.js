
    // Ваш код для добавления нового пользователя
    document.getElementById('newUserForm').addEventListener('submit', function (event) {
    event.preventDefault();
    const formData = new FormData(this);

    const rolesSelected = Array.from(document.getElementById('roles').selectedOptions).map(option => parseInt(option.value, 10));

    const user = {
    name: formData.get('name'),
    surname: formData.get('surname'),
    age: parseInt(formData.get('age'), 10),
    email: formData.get('email'),
    password: formData.get('password'),
    roles: rolesSelected
};

        console.log('Creating user:', user);

        fetch('/admin/create', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(user)
        })
            .then(response => {
                console.log('Response status:', response.status);
                if (response.status === 200 || response.status === 201) {
                    loadUsers();  // Используем loadUsers() для обновления таблицы после добавления нового пользователя
                    alert('Пользователь успешно создан!');
                    form.reset();  // Сбрасываем форму (используем сохранённую ссылку)
                    const modal = bootstrap.Modal.getInstance(document.getElementById('newUserModal'));
                    modal.hide();  // Закрываем модальное окно
                } else {
                    return response.json().then(data => {
                        throw new Error(data.message || 'Не удалось создать пользователя');
                    });
                }
            })
            .catch(error => {
                console.error('Error creating user:', error);
                alert('Ошибка при создании пользователя: ' + error.message);
            });
    });

    document.addEventListener("DOMContentLoaded", function () {
        // Функция для загрузки пользователей через API
        function loadUsers() {
            fetch('/admin/users') // обращаемся к новому API для получения JSON
                .then(response => response.json()) // получаем данные в формате JSON
                .then(data => {
                    const tableBody = document.getElementById('users-table-body');
                    tableBody.innerHTML = ''; // очищаем таблицу

                    data.forEach(user => {
                        // Создаем строку таблицы для каждого пользователя
                        const row = document.createElement('tr');

                        row.innerHTML = `
                        <td>${user.id}</td>
                        <td>${user.name}</td>
                        <td>${user.surname}</td>
                        <td>${user.age}</td>
                        <td>${user.email}</td>
                        <td>${user.roles.map(role => role.name).join(', ')}</td>
                        <td><a href="/admin/edit/${user.id}" class="btn btn-info">Edit</a></td>
                        <td><button class="btn btn-danger" onclick="deleteUser(${user.id})">Delete</button></td>
                    `;
                        tableBody.appendChild(row);
                    });
                })
                .catch(error => console.error('Ошибка при загрузке пользователей:', error));
        }
    // Функция для удаления пользователя
    function deleteUser(userId) {
    fetch(`/admin/users/${userId}`, {
    method: 'DELETE'
})
    .then(response => {
    if (response.ok) {
    loadUsers(); // Перезагружаем список пользователей после удаления
} else {
    console.error('Ошибка при удалении пользователя');
}
});
}

    // Загружаем пользователей при загрузке страницы
    loadUsers();
});



