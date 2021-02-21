function createUsersTable() {
    $("#usersTableBody").empty()

    $.ajax("/show", { //Ассинхронный запрос
        success: function (data) {
            const users = JSON.parse(JSON.stringify(data)); //преобразуем переданные строки с контроллера в объект
            for (let i = 0; i < users.length; i++) {
                let tr = $("<tr>").attr("id", users[i].id)
                tr.append("" +
                    "<td>" + users[i].id + "</td>" +
                    "<td>" + users[i].name + "</td>" +
                    "<td>" + users[i].lastName + "</td>" +
                    "<td>" + users[i].age + "</td>" +
                    "<td>" + users[i].username + "</td>" +
                    "<td>" + getUserRoles(users[i].authorities) + "</td>" +
                    "<td><button onclick='getUserForEdit(" + users[i].id + ")' type=\"button\" " +
                    "class=\"btn btn-info text-white\" data-bs-toggle=\"modal\" data-bs-target=\"#editModal\">Edit" +
                    "</button></td>" +
                    "<td><button onclick='getUserForDelete(" + users[i].id + ")' type=\"button\" " +
                    "class=\"btn btn-danger\" data-bs-toggle=\"modal\"  data-bs-target=\"#deleteWindow\">Delete" +
                    "</button></td>"
                );
                $("#usersTableBody").append(tr)
            }

        },

    });
}

function getUserRoles(authorities) {
    let userRoles = []
    for (let i in authorities) {
        userRoles[i] = " " + authorities[i].authority
    }
    return userRoles
}

function saveUser() {
    let user = {
        name: $("#nameCreate").val(),
        lastName: $("#lastNameCreate").val(),
        age: $("#ageCreate").val(),
        username: $("#usernameCreate").val(),
        password: $("#passwordCreate").val(),
        roles: $("#rolesCreate").val()
    }

    $.ajax({
        type: "PUT",
        url: "/save/",
        data: JSON.stringify(user), //преобразует значение JavaScript в строку JSON
        contentType: "application/json", //тип для текста JSON:
        success: function () {
            createUsersTable()
        },
    })
}

function getUserForEdit(id) {
    $.ajax({
        type: "GET",
        url: "/user/" + id,
        success: function (user) { //заполнение таблицы данными
            $("#idEdit").val(user.id)
            $("#nameEdit").val(user.name)
            $("#lastNameEdit").val(user.lastName)
            $("#ageEdit").val(user.age)
            $("#usernameEdit").val(user.username)
            $("#passwordEdit").val(user.password)
            $("#rolesDelete").val(user.roles)
        }
    });
}

function editUser() {
    let userId = $("#idEdit").val();
    let user = {
        name: $("#nameEdit").val(),
        lastName: $("#lastNameEdit").val(),
        age: $("#ageEdit").val(),
        username: $("#usernameEdit").val(),
        password: $("#passwordEdit").val(),
        roles: $("#rolesEdit").val()
    };

    $.ajax({
        type: "PUT",
        url: "/edit/" + userId,
        data: JSON.stringify(user),
        contentType: "application/json",
        success: function () {
            createUsersTable()
        },
    })
}

function getUserForDelete(id) {

    $.ajax({
        type: "GET",
        url: "/user/" + id,
        success: function (user) { //заполнение таблицы данными
            $("#idDelete").val(user.id)
            $("#nameDelete").val(user.name)
            $("#lastNameDelete").val(user.lastName)
            $("#ageDelete").val(user.age)
            $("#usernameDelete").val(user.username)
            $("#rolesDelete").val(user.roles)
        }
    });
}

function deleteUser() {
    let id = $("#idDelete").val()

    $.ajax({
        type: "DELETE",
        url: "/delete/" + id,
        success: function () {
            createUsersTable()
        },
    })
}

$(function () { //выполняем функцию, когда загружен DOM
    createUsersTable()
})
