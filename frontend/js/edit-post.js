export function makeEditable(elementId, postId, field) {
    const element = document.getElementById(elementId);
    let originalContent;

    element.addEventListener("click", function() {
        if (field === "text") {
            originalContent = Array.from(element.children)
                .map(p => p.textContent)
                .join('\n\n');
        } else {
            originalContent = element.textContent;
        }

        const textarea = document.createElement("textarea");
        textarea.value = originalContent;
        textarea.style.width = "100%";
        textarea.style.height = field === "text" ? "200px" : "auto";

        element.replaceWith(textarea);
        textarea.focus();

        textarea.addEventListener("blur", function() {
            const newText = textarea.value.trim();
            if (newText !== originalContent) {
                updatePost(postId, field, newText);
            } else {
                restoreOriginalContent();
            }
        });

        textarea.addEventListener("keydown", function(e) {
            if (e.ctrlKey && e.key === "Enter") {
                e.preventDefault();
                textarea.blur();
            }
        });
    });

    function restoreOriginalContent() {
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
        } else {
            const h2 = document.createElement("h2");
            h2.id = elementId;
            h2.textContent = originalContent;
            textarea.replaceWith(h2);
        }
    }
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
        const response = await fetch(`http://localhost:8080/blog/posts/${postId}`, {
            method: "PATCH",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                [field]: newText,
            }),
        });
        if (!response.ok) {
            throw new Error(`Ошибка при редактировании ${field}`);
        }
        alert(`${field.charAt(0).toUpperCase() + field.slice(1)} edited successfully!`);
        location.reload();
    } catch (error) {
        console.error("Ошибка:", error);
        alert(`Failed to edit the ${field}.`);
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