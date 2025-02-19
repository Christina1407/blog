document.addEventListener('DOMContentLoaded', function() {
    const form = document.getElementById('addPostForm');

    form.addEventListener('submit', async function(event) {
        event.preventDefault();

        const formData = new FormData();
        formData.append('title', document.getElementById('title').value);
        formData.append('text', document.getElementById('text').value);
        formData.append('authorId', document.getElementById('authorId').value);
        formData.append('image', document.getElementById('image').files[0]);
        
        const tagsInput = document.getElementById('tags').value;
        if (tagsInput) {
            const tagsArray = tagsInput.split(',').map(tag => tag.trim()).filter(tag => tag !== '');
            formData.delete('tags');
            tagsArray.forEach(tag => formData.append('tags', tag));
        }
        
        try {
            const response = await fetch('http://localhost:8080/blog/posts', {
                method: 'POST',
                body: formData
            });

            if (!response.ok) {
                throw new Error('Ошибка при добавлении поста');
            }
            window.location.href = 'index.html'; // Перенаправление на главную страницу
        } catch (error) {
            console.error('Ошибка:', error);
            alert('Произошла ошибка при добавлении поста');
        }
    });
});