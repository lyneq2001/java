let authHeader = null;

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
            alert('Logged in');
            loadData();
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
}

