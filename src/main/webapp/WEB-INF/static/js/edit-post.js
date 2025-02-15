export function makeEditable(elementId, postId, field) {
    const element = document.getElementById(elementId);
    let originalContent;

    function addClickListener() {
        element.addEventListener("click", startEditing);
    }

    function startEditing() {
        if (field === "text") {
            originalContent = Array.from(element.children)
                .map(p => p.textContent)
                .join('\n\n');
        } else if (field === "tags") {
            originalContent = element.textContent.replace(/#/g, '').split(', ').join(',');
        } else {
            originalContent = element.textContent;
        }

        const textarea = document.createElement("textarea");
        textarea.value = originalContent;
        textarea.style.width = "100%";
        textarea.style.height = field === "text" ? "200px" : "auto";

        element.replaceWith(textarea);
        textarea.focus();

        textarea.addEventListener("blur", finishEditing);
        textarea.addEventListener("keydown", function(e) {
            if (e.ctrlKey && e.key === "Enter") {
                e.preventDefault();
                textarea.blur();
            }
        });
    }

    function finishEditing() {
        const textarea = document.querySelector(`textarea`);
        const newText = textarea.value.trim();
        if (newText !== originalContent) {
            updatePost(postId, field, newText);
        } else {
            restoreOriginalContent(textarea);
        }
    }

    function restoreOriginalContent(textarea) {
        if (field === "text") {
            const div = document.createElement("div");
            div.id = elementId;
            originalContent.split('\n\n').forEach(paragraph => {
                if (paragraph.trim() !== '') {
                    const p = document.createElement('p');
                    p.textContent = paragraph.trim();
                    div.appendChild(p);
                }
            });
            textarea.replaceWith(div);
        } else if (field === "tags") {
            const div = document.createElement("div");
            div.id = elementId;
            div.textContent = originalContent.split(',').map(tag => `#${tag.trim()}`).join(', ');
            textarea.replaceWith(div);
        } else {
            const h2 = document.createElement("h2");
            h2.id = elementId;
            h2.textContent = originalContent;
            textarea.replaceWith(h2);
        }
        addClickListener();
    }

    addClickListener();
}


// Function to handle image upload
export function updatePostImage(postId) {
    const fileInput = document.getElementById('imageUpload');
    const file = fileInput.files[0];

    if (!file) {
        alert('Please select an image to upload.');
        return;
    }

    const formData = new FormData();
    formData.append('file', file);

    fetch(`http://localhost:8080/blog/posts/${postId}/image`, {
        method: 'PUT',
        body: formData
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Ошибка при обновлении изображения');
        }
        alert('Image updated successfully!');
        location.reload();
    })
    .catch(error => {
        console.error('Ошибка:', error);
        alert('Failed to update the image.');
    });
}

// Attach the function to the window object
window.updatePostImage = updatePostImage;

async function updatePost(postId, field, newText) {
    try {
        let body;
        if (field === "tags") {
            body = JSON.stringify({
                tags: newText.split(',').map(tag => tag.trim()).filter(tag => tag !== '')
            });
        } else {
            body = JSON.stringify({
                [field]: newText,
            });
        }

        const response = await fetch(`http://localhost:8080/blog/posts/${postId}`, {
            method: "PATCH",
            headers: {
                "Content-Type": "application/json",
            },
            body: body,
        });
        if (!response.ok) {
            throw new Error(`Ошибка при редактировании ${field}`);
        }
        location.reload();
    } catch (error) {
        console.error("Ошибка:", error);
        alert(`Не удалось отредактировать ${field}.`);
    }
}

function restoreElement(input, elementId, text) {
    const newElement = document.createElement(elementId === "postTitle" ? "h2" : "p");
    newElement.textContent = text;
    newElement.id = elementId;
    newElement.classList.add("editable");
    input.replaceWith(newElement);
    makeEditable(elementId, postId, elementId === "postTitle" ? "title" : "text");
}