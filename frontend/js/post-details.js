import { initComments, addComment } from './comments.js';
import { makeEditable } from './edit-post.js';

document.addEventListener("DOMContentLoaded", function () {
    const postDetailsContainer = document.getElementById("postDetails");
    const newCommentText = document.getElementById("newCommentText");
    const addCommentButton = document.getElementById("addCommentButton");
    const deletePostButton = document.getElementById("deletePostButton");

    const urlParams = new URLSearchParams(window.location.search);
    const postId = urlParams.get("postId");

    async function fetchPostDetails(postId) {
        try {
            const response = await fetch(`http://localhost:8080/blog/posts/${postId}`);
            if (!response.ok) {
                throw new Error("Ошибка при загрузке данных поста");
            }
            const post = await response.json();
            displayPostDetails(post);
        } catch (error) {
            console.error("Ошибка:", error);
        }
    }

    function displayPostDetails(post) {
        postDetailsContainer.innerHTML = `
            <h2 id="postTitle">${post.title}</h2>
            <p id="postText">${post.text}</p>
            <img src="http://localhost:8080/blog/posts/image?postId=${post.id}" alt="${post.text}">
            <p id="likeCount" style="cursor: pointer;">&#x2764; ${post.reactionCount}</p>
        `;

        makeEditable("postTitle", post.id, "title");
        makeEditable("postText", post.id, "text");

        document.getElementById("likeCount").addEventListener("click", async function () {
            try {
                const response = await fetch("http://localhost:8080/blog/posts/reactions", {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json",
                    },
                    body: JSON.stringify({
                        postId: postId,
                        reactionType: "LIKE",
                    }),
                });
                if (!response.ok) {
                    throw new Error("Ошибка при добавлении лайка");
                }
                alert("Liked the post!");
                fetchPostDetails(postId);
            } catch (error) {
                console.error("Ошибка:", error);
                alert("Failed to like the post.");
            }
        });

        initComments(post.comments, postId);
    }

    async function deletePost(postId) {
        try {
            const response = await fetch(`http://localhost:8080/blog/posts/${postId}`, {
                method: "DELETE",
            });
            if (!response.ok) {
                throw new Error("Ошибка при удалении поста");
            }
            alert("Post deleted successfully!");
            window.location.href = "index.html";
        } catch (error) {
            console.error("Ошибка:", error);
            alert("Failed to delete the post.");
        }
    }

    addCommentButton.addEventListener("click", function () {
        const text = newCommentText.value.trim();
        if (text) {
            addComment(postId, text);
        } else {
            alert("Comment text cannot be empty.");
        }
    });

    deletePostButton.addEventListener("click", function () {
        if (confirm("Are you sure you want to delete this post?")) {
            deletePost(postId);
        }
    });

    fetchPostDetails(postId);
});