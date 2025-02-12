import { makeEditable } from "./edit-post.js";

const commentsContainer = document.getElementById("comments");

export function initComments(comments, postId) {
    if (comments && comments.length > 0) {
        commentsContainer.innerHTML = "<h3>Comments:</h3>";
        comments.forEach((comment) => {
            const commentElement = createCommentElement(comment, postId);
            commentsContainer.appendChild(commentElement);
        });
    } else {
        commentsContainer.innerHTML = "<p>No comments yet.</p>";
    }
}

function createCommentElement(comment, postId) {
    const commentElement = document.createElement("div");
    commentElement.classList.add("comment");
    commentElement.innerHTML = `
        <p><strong>${comment.author}</strong>: <span class="comment-text">${comment.text}</span></p>
        <button class="button deleteCommentButton" data-comment-id="${comment.id}">Delete</button>
    `;

    commentElement.querySelector(".comment-text").addEventListener("click", function () {
        makeCommentEditable(this, comment.id, postId);
    });

    commentElement.querySelector(".deleteCommentButton").addEventListener("click", function () {
        const commentId = this.getAttribute("data-comment-id");
        deleteComment(commentId, postId);
    });

    return commentElement;
}


function makeCommentEditable(element, commentId, postId) {
    const currentText = element.textContent;
    const input = document.createElement("input");
    input.type = "text";
    input.value = currentText;
    element.replaceWith(input);
    input.focus();

    input.addEventListener("keydown", async function (event) {
        if (event.ctrlKey && event.key === "Enter") {
            const newText = input.value.trim();
            if (newText && newText !== currentText) {
                await editComment(commentId, newText, postId);
            } else {
                input.replaceWith(document.createTextNode(currentText));
            }
        }
    });

    input.addEventListener("blur", function () {
        input.replaceWith(document.createTextNode(currentText));
    });
}

async function editComment(commentId, newText, postId) {
    try {
        const response = await fetch(`http://localhost:8080/blog/posts/comments/${commentId}`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                text: newText,
            }),
        });
        if (!response.ok) {
            throw new Error("Ошибка при редактировании комментария");
        }
        location.reload();
    } catch (error) {
        console.error("Ошибка:", error);
        alert("Failed to edit the comment.");
    }
}

async function deleteComment(commentId, postId) {
    try {
        const response = await fetch(`http://localhost:8080/blog/posts/comments/${commentId}`, {
            method: "DELETE",
        });
        if (!response.ok) {
            throw new Error("Ошибка при удалении комментария");
        }
        location.reload();
    } catch (error) {
        console.error("Ошибка:", error);
        alert("Failed to delete the comment.");
    }
}

export async function addComment(postId, text) {
    try {
        const response = await fetch("http://localhost:8080/blog/posts/comments", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                text: text,
                authorId: 2,
                postId: postId,
            }),
        });
        if (!response.ok) {
            throw new Error("Ошибка при добавлении комментария");
        }
        location.reload();
    } catch (error) {
        console.error("Ошибка:", error);
        alert("Failed to add the comment.");
    }
}