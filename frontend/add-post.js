document.addEventListener('DOMContentLoaded', function() {
    const form = document.getElementById('addPostForm');

    form.addEventListener('submit', async function(event) {
        event.preventDefault();

        const formData = new FormData();
        formData.append('text', document.getElementById('text').value);
        formData.append('authorId', document.getElementById('authorId').value);
        formData.append('image', document.getElementById('image').files[0]);

        try {
            const response = await fetch('http://localhost:8080/blog/posts/test', {
                method: 'POST',
                body: formData
            });

            if (!response.ok) {
                throw new Error('Ошибка при добавлении поста');
            }

            alert('Пост успешно добавлен!');
            window.location.href = '/'; // Перенаправление на главную страницу
        } catch (error) {
            console.error('Ошибка:', error);
            alert('Произошла ошибка при добавлении поста');
        }
    });
});