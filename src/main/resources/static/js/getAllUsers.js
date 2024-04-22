mainMethod()

function mainMethod() {
    nameAdnRoleOnTop();
    navBarAdminOrUser();
    showUserTable();
}

function nameAdnRoleOnTop() {
    document.getElementById('nameAdnRoleOnTop').innerHTML = ''
    fetch('http://localhost:8080/api/user/getUser', {
        credentials: 'include',
    })
        .then((response) => {
            if (!response.ok) {
                throw new Error('Error occurred!')
            }
            return response.json()
        }).then(user => {
            let html = document.getElementById('nameAdnRoleOnTop');
            let htmlString = user.name + " with roles:"
            html.innerHTML = "<span> " + htmlString + roleToString(user.roles) + "</span>";
    })
}

function navBarAdminOrUser() {
    document.getElementById('navBarAdminOrUser').innerHTML = ''
    fetch('http://localhost:8080/api/user/getUser', {
        credentials: 'include',
    })
        .then((response) => {
            if (!response.ok) {
                throw new Error('Error occurred!')
            }
            return response.json()
        }).then(user => {
        let navBarAdminOrUser = document.getElementById('navBarAdminOrUser')
        let roles = user.roles;
        let countRole = 0
        let arr = ''
        for (let i = 0; i < roles.length; i++) {
            countRole += 1
            let arr1 = roles[i].name.split('_');
            arr += arr1[1]
        }
        if (countRole >= 2) {
            document.getElementById('navBarAdminOrUser').innerHTML = '<li class="nav-item">\n' +
                '                               <a class="nav-link  active" id="navAdmin" onclick="clickNavBarAdmin()">Admin</a>\n' +
                '                           </li>\n' +
                '                           <li class="nav-item">\n' +
                '                                <a class="nav-link"  id="navUser" onclick="clickNavBarUser()">User</a>\n' +
                '                           </li>\n'
            updateTableAllUsers()
        } else if (arr === "ADMIN") {
            navBarAdminOrUser.innerHTML = '<li class="nav-item">\n' +
                '                               <a class="nav-link  active" id="navAdmin" onclick="updateTableAllUsers()">Admin</a>\n' +
                '                           </li>\n'
            updateTableAllUsers();
        } else {
            navBarAdminOrUser.innerHTML = '<li class="nav-item">\n' +
                '                               <a class="nav-link  active" id="navUser" onclick="updateUserTable()">User</a>\n' +
                '                           </li>\n'
            updateUserTable();
        }
    })
}

function headerTableAdmin() {
    document.getElementById('head1').innerHTML = '<h1>Admin page</h1>\n' +
        '            <ul class="nav nav-tabs">\n' +
        '                <li class="nav-item">\n' +
        '                    <a class="nav-link active" id="adminClickUsersTable" onclick="adminClickUsersTable()">Users table</a>\n' +
        '                </li>\n' +
        '                <li class="nav-item">\n' +
        '                    <a class="nav-link" id="adminClickNewUser" onclick="adminClickNewUser()">New User</a>\n' +
        '                </li>\n' +
        '            </ul>'
    document.getElementById('head3').innerText = 'All users'
}

function showUserTable() {
    document.getElementById("table").hidden = false
    document.getElementById("formAddUser").hidden = true
}

function showFormAddUser() {
    document.getElementById("table").hidden = true
    document.getElementById("formAddUser").hidden = false
}

function headerTableUser() {
    document.getElementById('head1').innerHTML = '<h1>User information-page</h1>'
    document.getElementById('head3').innerText = 'Info user'
}

function adminClickUsersTable() {
    document.getElementById('adminClickUsersTable').classList.add('active')
    document.getElementById('adminClickNewUser').classList.remove("active")
    showUserTable()
    document.getElementById('head3').innerText = 'All users'
}

function adminClickNewUser() {
    document.getElementById('adminClickUsersTable').classList.remove('active')
    document.getElementById('adminClickNewUser').classList.add("active")
    showFormAddUser()
    fillRoleToAddNewUser()
    document.getElementById('head3').innerText = 'New User'
}

function clickNavBarAdmin() {
    document.getElementById('navAdmin').classList.add('active')
    document.getElementById('navUser').classList.remove('active')
    updateTableAllUsers()
}

function clickNavBarUser() {
    document.getElementById('navAdmin').classList.remove('active')
    document.getElementById('navUser').classList.add('active')
    updateUserTable()
}

function fillRoleToAddNewUser() {
    document.getElementById('checkboxNewUser').innerHTML =""
    fetch('http://localhost:8080/api/roles', {
        credentials: 'include',
    })
        .then((response) => {
            if (!response.ok) {
                throw new Error('Error occurred!')
            }
            return response.json()
        })
        .then(role =>{
            let roles = document.getElementById('checkboxNewUser');
            for (let i = 0; i < role.length; i++) {
                if (i !== 0) {
                    roles.innerHTML += "<br/>"
                }
                roles.innerHTML += "<input type=\"checkbox\" name=\"roles\" class=\"form-check-input\" id=\"roleId"+ role[i].id + "\" value=\"" + role[i].id + "\"> " + role[i].name
            }
        })
}

function clickCreateNewUser() {
    let form = document.getElementById('formAddUser')
    let params = new FormData(form)
    console.log(params)
    fetch('http://localhost:8080/api/new', {
        method: 'POST',
        body: params
    })
        .then((response) =>{
            return response.json()
        })
        .then((data) =>{
            console.log("success edit")
            console.log(data)
            mainMethod()
        })
}

function updateUserTable() {
    headerTableUser();
    document.getElementById('data-output').innerHTML = ""
    fetch('http://localhost:8080/api/user/getUser', {
        credentials: 'include',
    })
        .then((response) => {
            if (!response.ok) {
                throw new Error('Error occurred!')
            }
            return response.json()
        }).then(user => {
            document.getElementById('tableHead').innerHTML = '<tr>\n' +
                '                            <th>\n' +
                '                                ID\n' +
                '                            </th>\n' +
                '                            <th>\n' +
                '                                First name\n' +
                '                            </th>\n' +
                '                            <th>\n' +
                '                                Login\n' +
                '                            </th>\n' +
                '                            <th>\n' +
                '                                Age\n' +
                '                            </th>\n' +
                '                            <th>\n' +
                '                                Email\n' +
                '                            </th>\n' +
                '                            <th>\n' +
                '                                Role\n' +
                '                            </th>\n' +
                '                        </tr>'
            let table = document.getElementById("data-output")
            let row = table.insertRow();
            let cellId = row.insertCell(0);
            let cellName = row.insertCell(1);
            let cellLogin = row.insertCell(2);
            let cellAge = row.insertCell(3);
            let cellEmail = row.insertCell(4);
            let cellRole = row.insertCell(5);

            cellId.innerHTML = user.id
            cellName.innerHTML = user.name
            cellLogin.innerHTML = user.login
            cellAge.innerHTML = user.age
            cellEmail.innerHTML = user.email
            cellRole.innerHTML = roleToString(user.roles)
    })

}

function updateTableAllUsers() {
    headerTableAdmin()
    document.getElementById('data-output').innerHTML = ""
    fetch('http://localhost:8080/api/users', {
        credentials: 'include',
    })
        .then((response) => {
            if (!response.ok) {
                throw new Error('Error occurred!')
            }
            return response.json()
        }).then(user => {
            document.getElementById("tableHead").innerHTML = '<tr>\n' +
                '                            <th>\n' +
                '                                ID\n' +
                '                            </th>\n' +
                '                            <th>\n' +
                '                                First name\n' +
                '                            </th>\n' +
                '                            <th>\n' +
                '                                Login\n' +
                '                            </th>\n' +
                '                            <th>\n' +
                '                                Age\n' +
                '                            </th>\n' +
                '                            <th>\n' +
                '                                Email\n' +
                '                            </th>\n' +
                '                            <th>\n' +
                '                                Role\n' +
                '                            </th>\n' +
                '                            <th>\n' +
                '                                Edit\n' +
                '                            </th>\n' +
                '                            <th>\n' +
                '                                Delete\n' +
                '                            </th>\n' +
                '                        </tr>'
            let table = document.getElementById("data-output")
            for (let i = 0; i < user.length; i++) {
                let row = table.insertRow();
                let cellId = row.insertCell(0);
                let cellName = row.insertCell(1);
                let cellLogin = row.insertCell(2);
                let cellAge = row.insertCell(3);
                let cellEmail = row.insertCell(4);
                let cellRole = row.insertCell(5);
                let cellButtonEdit = row.insertCell(6);
                let cellButtonDelete = row.insertCell(7);


                cellId.innerHTML = user[i].id
                cellName.innerHTML = user[i].name
                cellLogin.innerHTML = user[i].login
                cellAge.innerHTML = user[i].age
                cellEmail.innerHTML = user[i].email
                cellRole.innerHTML = roleToString(user[i].roles)

                cellButtonEdit.innerHTML = "<button type=\"button\" class=\"btn btn-primary\" id='userEditBytton' onclick='openEditModal(" + user[i].id + ")'>" +
                    "Edit</button>"
                cellButtonDelete.innerHTML = "<button type=\"button\" class=\"btn btn-danger\" onclick='openDeleteModal(" + user[i].id + ")'>" +
                    "Delete</button>"
        }
    })
        .catch((err) => {
            console.log(err)
        })
}

function roleToString(userRoles) {
    let htmlString = ''
    for (let i = 0; i < userRoles.length; i++) {
        let arr = userRoles[i].name.split('_');
        if (i > 0) {
            htmlString += " " + arr[1]
        } else {
            htmlString += arr[1]
        }
    }
    return htmlString
}


function openEditModal(userId) {
    const modal = document.getElementById('ModalEditUser')
    if (modal) {
        modal.classList.add('show');
        modal.style.display = 'block';
        modal.setAttribute('aria-hidden', 'false');
        document.body.classList.add('modal-open');
        infoUserToModalEdit(userId);
    }
}

function infoUserToModalEdit(userId) {
    fetch('http://localhost:8080/api/users/' + userId, {
        credentials: 'include',
    })
        .then((response) => {
            if (!response.ok) {
                throw new Error('Error occurred!')
            }
            return response.json()
        }).then(user => {
            document.getElementById('userIdEdit').value = user.id;
            document.getElementById('userNameEdit').value = user.name;
            document.getElementById('userLoginEdit').value = user.login;
            document.getElementById('userAgeEdit').value = user.age;
            document.getElementById('userEmailEdit').value = user.email;
            fillRolesToEditModal(user.roles)
    })
}

function fillRolesToEditModal(userRole)  {
    document.getElementById('checkboxEditUser').innerHTML =""
    fetch('http://localhost:8080/api/roles', {
        credentials: 'include',
    })
        .then((response) => {
            if (!response.ok) {
                throw new Error('Error occurred!')
            }
            return response.json()
        })
        .then(role =>{
            let roles = document.getElementById('checkboxEditUser');
            let hasRole = false
            for (let i = 0; i < role.length; i++) {
                hasRole = false
                if (i !== 0) {
                    roles.innerHTML += "<br/>"
                }
                for (let j = 0; j < userRole.length; j++) {
                    if (role[i].id === userRole[j].id) {
                       hasRole = true
                    }
                }
                if (hasRole === true) {
                    roles.innerHTML += "<input type=\"checkbox\" name=\"roles\" class=\"form-check-input\" checked=\"on\" id=\"roleId"+ role[i].id + "\" value=\"" + role[i].id + "\"> " + role[i].name
                } else {
                    roles.innerHTML += "<input type=\"checkbox\" name=\"roles\" class=\"form-check-input\" id=\"roleId"+ role[i].id + "\" value=\"" + role[i].id + "\"> " + role[i].name
                }
            }
        })
}

function clickEditUserButton() {
    let form = document.getElementById("modalEditFormId")
    let params = new FormData(form)
    fetch("http://localhost:8080/api/users", {
        method: 'PATCH',
        body: params
    }).then((response) => {
        return response.json();
    })
        .then((data) => {
            console.log("success edit:")
            console.log(data)
            closeEditModal();
            navBarAdminOrUser();
            nameAdnRoleOnTop();
        });
}

function closeEditModal() {
    const modal = document.getElementById('ModalEditUser')
    if (modal) {
        modal.style.display = 'none';
    }
}


function openDeleteModal(userId) {
    const modal = document.getElementById('ModalDeleteUser')
    if (modal) {
        modal.classList.add('show');
        modal.style.display = 'block';
        modal.setAttribute('aria-hidden', 'false');
        document.body.classList.add('modal-open');
    }
    infoUserToDeleteModal(userId);
}

function infoUserToDeleteModal(userId) {
    fetch('http://localhost:8080/api/users/' + userId, {
        credentials: 'include',
    })
        .then((response) => {
            if (!response.ok) {
                throw new Error('Error occurred!')
            }
            return response.json()
        }).then(user => {
            document.getElementById('userIdDelete').value = user.id
            document.getElementById('userNameDelete').value = user.name;
            document.getElementById('userAgeDelete').value = user.age;
            document.getElementById('userEmailDelete').value = user.email;
            fillRolesToDeleteModal(user.roles)
    })
}

function fillRolesToDeleteModal(userRole)  {
    document.getElementById('checkboxDeleteUser').innerHTML =""
    fetch('http://localhost:8080/api/roles', {
        credentials: 'include',
    })
        .then((response) => {
            if (!response.ok) {
                throw new Error('Error occurred!')
            }
            return response.json()
        })
        .then(role =>{
            let roles = document.getElementById('checkboxDeleteUser');
            for (let i = 0; i < role.length; i++) {
                for (let j = 0; j < userRole.length; j++) {
                    if (i !== 0) {
                        roles.innerHTML += "<br/>"
                    }
                    if (role[i].id === userRole[j].id) {
                        roles.innerHTML += "<input type=\"checkbox\" disabled name=\"roles\" class=\"form-check-input\" checked=\"on\" id=\"roleId"+ role[i].id + "\" value=\"" + role[i].id + "\"> " + role[i].name
                    } else {
                        roles.innerHTML += "<input type=\"checkbox\" disabled name=\"roles\" class=\"form-check-input\" id=\"roleId"+ role[i].id + "\" value=\"" + role[i].id + "\"> " + role[i].name
                    }
                }
            }
        })
}

function clickDeleteUserButton() {
    let form = document.getElementById("modalDeleteFormId")
    let params = new FormData(form)
    fetch("http://localhost:8080/api/users/" + params.get("id"), {
        method: 'DELETE'
    }).then(() => {
            closeDeleteModal();
            navBarAdminOrUser();
            nameAdnRoleOnTop();
        });
}

function closeDeleteModal() {
    const modal = document.getElementById('ModalDeleteUser')
    if (modal) {
        modal.style.display = 'none';
    }
}