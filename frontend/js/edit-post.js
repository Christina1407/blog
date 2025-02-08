export function makeEditable(elementId, postId, field) {
    const element = document.getElementById(elementId);
    if (!element) {
        console.error(`Element with id "${elementId}" not found`);
        return;
    }
    element.addEventListener("click", function () {
        const currentText = this.textContent;
        const input = document.createElement("textarea");
        input.value = currentText;
        this.replaceWith(input);
        input.focus();

        input.addEventListener("keydown", async function (event) {
            if (event.ctrlKey && event.key === "Enter") {
                const newText = input.value.trim();
                if (newText && newText !== currentText) {
                    await updatePost(postId, field, newText);
                } else {
                    restoreElement(input, elementId, currentText);
                }
            }
        });

        input.addEventListener("blur", function () {
            restoreElement(input, elementId, currentText);
        });
    });
}

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