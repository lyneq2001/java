let authHeader = null;
let currentUser = null;

function login() {
    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;
    authHeader = 'Basic ' + btoa(username + ':' + password);
    fetch('/auth/login', {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({username, password})
    }).then(r => {
        if (r.ok) {
            fetchUser().then(() => {
                document.getElementById('auth').style.display = 'none';
                document.getElementById('dashboard').style.display = 'block';
                loadData();
            });
        } else {
            alert('Login failed');
        }
    });
}

function register() {
    const username = document.getElementById('regUsername').value;
    const password = document.getElementById('regPassword').value;
    const role = document.getElementById('role').value;
    fetch('/auth/register', {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({username, password, role})
    }).then(r => {
        if (r.ok) {
            alert('Registered');
        } else {
            alert('Registration failed');
        }
    });
}

function headers() {
    return authHeader ? { 'Authorization': authHeader, 'Content-Type': 'application/json' } : { 'Content-Type': 'application/json' };
}

function fetchUser() {
    return fetch('/users/me', { headers: { 'Authorization': authHeader } })
        .then(r => r.json())
        .then(u => {
            currentUser = u;
            document.getElementById('welcome').innerText = `Hello ${u.username} (${u.role})`;
            if (u.role === 'ADMIN') {
                document.getElementById('adminPanel').style.display = 'block';
            }
        });
}

function loadAuthors() {
    fetch('/authors', { headers: { 'Authorization': authHeader } })
        .then(r => r.json())
        .then(data => {
            const list = document.getElementById('authorList');
            list.innerHTML = '';
            data.forEach(a => {
                const li = document.createElement('li');
                li.textContent = `${a.id} - ${a.name}`;
                list.appendChild(li);
            });
        });
}

function addAuthor() {
    const name = document.getElementById('authorName').value;
    fetch('/authors', {
        method: 'POST',
        headers: headers(),
        body: JSON.stringify({ name })
    }).then(r => {
        if (r.ok) {
            loadAuthors();
        } else {
            alert('Failed to add author');
        }
    });
}

function loadBooks() {
    fetch('/books', { headers: { 'Authorization': authHeader } })
        .then(r => r.json())
        .then(data => {
            const list = document.getElementById('bookList');
            list.innerHTML = '';
            data.forEach(b => {
                const li = document.createElement('li');
                li.textContent = `${b.id} - ${b.title} (author ${b.author.id})`;
                list.appendChild(li);
            });
        });
}

function addBook() {
    const title = document.getElementById('bookTitle').value;
    const authorId = document.getElementById('bookAuthorId').value;
    fetch('/books', {
        method: 'POST',
        headers: headers(),
        body: JSON.stringify({ title, author: { id: authorId } })
    }).then(r => {
        if (r.ok) {
            loadBooks();
        } else {
            alert('Failed to add book');
        }
    });
}

function loadData() {
    loadAuthors();
    loadBooks();
    loadLoans();
    if (currentUser.role === 'ADMIN') {
        loadUsers();
        loadAllLoans();
    }
}

function loadLoans() {
    fetch('/loans', { headers: { 'Authorization': authHeader } })
        .then(r => r.json())
        .then(data => {
            const list = document.getElementById('loanList');
            list.innerHTML = '';
            data.forEach(l => {
                const li = document.createElement('li');
                li.textContent = `${l.id} - book ${l.book.id}`;
                const btn = document.createElement('button');
                btn.textContent = 'Return';
                btn.onclick = () => returnLoan(l.id);
                li.appendChild(btn);
                list.appendChild(li);
            });
        });
}

function borrowBook() {
    const bookId = document.getElementById('loanBookId').value;
    fetch('/loans', {
        method: 'POST',
        headers: headers(),
        body: JSON.stringify({ bookId })
    }).then(r => {
        if (r.ok) {
            loadLoans();
        } else {
            alert('Borrow failed');
        }
    });
}

function returnLoan(id) {
    fetch(`/loans/${id}`, { method: 'DELETE', headers: { 'Authorization': authHeader } })
        .then(() => loadLoans());
}

function updateAccount() {
    const username = document.getElementById('newUsername').value;
    const password = document.getElementById('newPassword').value;
    fetch('/users/me', {
        method: 'PUT',
        headers: headers(),
        body: JSON.stringify({ username: username || null, password: password || null })
    }).then(r => {
        if (r.ok) {
            fetchUser();
        } else {
            alert('Update failed');
        }
    });
}

function loadUsers() {
    fetch('/users', { headers: { 'Authorization': authHeader } })
        .then(r => r.json())
        .then(data => {
            const list = document.getElementById('userList');
            list.innerHTML = '';
            data.forEach(u => {
                const li = document.createElement('li');
                li.textContent = `${u.id} - ${u.username} (${u.role})`;
                list.appendChild(li);
            });
        });
}

function loadAllLoans() {
    fetch('/loans', { headers: { 'Authorization': authHeader } })
        .then(r => r.json())
        .then(data => {
            const list = document.getElementById('allLoanList');
            list.innerHTML = '';
            data.forEach(l => {
                const li = document.createElement('li');
                li.textContent = `${l.id} - user ${l.user.id} book ${l.book.id}`;
                list.appendChild(li);
            });
        });
}

