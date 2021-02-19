$(function() { //Выполняем функции, когда загружен DOM
    createUsersTable()
});


function createUsersTable() {
    $("#usersTableBody").empty();

    $.ajax("/showUsers", {
        // dataType: "json",
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
                    "<td><button onclick='getUserForEdit(" + users[i].id + ")' type=\"button\" class=\"btn btn-info text-white\" data-bs-toggle=\"modal\" data-bs-target=\"#editModal\">Edit</button></td>" +
                    "<td><button onclick='getUserForDelete(" + users[i].id + ")' type=\"button\" class=\"btn btn-danger\" data-bs-toggle=\"modal\"  data-bs-target=\"#deleteWindow\">Delete</button></td>"
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
    return userRoles;
}


function getUserForEdit(id) {
    $.ajax({
        type: "GET",
        url: "/user/" + id,
        dataType: 'json',
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

function getUserForDelete(id) {
    $.ajax({
        type: "GET",
        url: "/user/" + id,
        dataType: 'json',
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



function updateUser() {
    let roles = [];
    let userId = $("#idEdit").val();

    $("#rolesEdit option:selected").each(function () {
        roles.push({id: $(this).attr("role-id") ,role: $(this).attr("value")})
    });

    let json = {
        name: $("#nameEdit").val(),
        lastName: $("#lastNameEdit").val(),
        age: $("#ageEdit").val(),
        username: $("#usernameEdit").val(),
        password: $("#passwordEdit").val(),
        roles: JSON.parse(JSON.stringify(roles))
    };

    $.ajax({
        type: "PUT",
        url: "/rest/edit_user/" + userId,
        data: JSON.stringify(json),
        contentType: "application/json",
        success: function () {
            createUsersTable();
            $("#usersTableBody #" + id).update();
        },

    });
}



function deleteUser() {

    let id = $("#idDelete").val()

    $.ajax({
        type: "DELETE",
        url: "/delete/" + id,
        contentType: 'application/json',
        success: function (data) {
            // createUsersTable()

            $("#usersTableBody #" + id).remove();
            console.log(data)


        },
    });
}


