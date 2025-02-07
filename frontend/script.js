document.addEventListener('DOMContentLoaded', function() {
    const postsContainer = document.getElementById('posts');

    const addPostButton = document.getElementById('addPostButton');

    // Функция для получения данных с бэкенда через прокси
    async function fetchPosts() {
        try {
            const response = await fetch('http://localhost:8080/blog/posts'); // Прокси-адрес
            if (!response.ok) {
                throw new Error('Ошибка при загрузке данных');
            }
            const posts = await response.json();
            displayPosts(posts);
        } catch (error) {
            console.error('Ошибка:', error);
        }
    }

    // Обработка нажатия кнопки
    addPostButton.addEventListener('click', function() {
        window.location.href = 'add-post.html'; // Перенаправление на страницу добавления поста
    });

    // Функция для отображения постов
    function displayPosts(posts) {
        postsContainer.innerHTML = ''; // Очистка контейнера
        posts.forEach(post => {
            const postElement = document.createElement('div');
            postElement.classList.add('post');

            const title = document.createElement('h2');
            title.textContent = post.title;

            const text = document.createElement('p');
            text.textContent = post.text;


            const image = document.createElement('img');
            image.src = 'http://localhost:8080/blog/posts/image?postId=' + post.id;
            image.alt = post.text;

            postElement.appendChild(title);
            postElement.appendChild(text);
            postElement.appendChild(image);

            postsContainer.appendChild(postElement);
        });
    }

    // Загрузка постов при загрузке страницы
    fetchPosts();
});