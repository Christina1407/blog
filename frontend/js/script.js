document.addEventListener("DOMContentLoaded", function () {
  const postsContainer = document.getElementById("posts");
  const addPostButton = document.getElementById("addPostButton");
  const paginationContainer = document.createElement("div");
  paginationContainer.id = "pagination";
  document.querySelector(".container").appendChild(paginationContainer);

  // Функция для получения данных с бэкенда
  async function fetchPosts(page = 0, size = 10, sort = "createdDate,desc") {
    try {
      const response = await fetch(
        `http://localhost:8080/blog/posts?page=${page}&size=${size}&sort=${sort}`
      );
      if (!response.ok) {
        throw new Error("Ошибка при загрузке данных");
      }
      const data = await response.json();
      displayPosts(data.content);
      displayPagination(data.totalPages, page);
    } catch (error) {
      console.error("Ошибка:", error);
    }
  }

  // Обработка нажатия кнопки
  addPostButton.addEventListener("click", function () {
    window.location.href = "add-post.html"; // Перенаправление на страницу добавления поста
  });

  // Функция для отображения постов
  function displayPosts(posts) {
    postsContainer.innerHTML = ""; // Очистка контейнера
    posts.forEach((post) => {
      const postElement = document.createElement("div");
      postElement.classList.add("block", "post-block");
      postElement.classList.add("post");

      const title = document.createElement("h2");
      title.textContent = post.title;
      title.style.cursor = "pointer";
      title.addEventListener("click", () => {
        window.location.href = `post-details.html?postId=${post.id}`;
      });

      const text = document.createElement("p");
      // Extract only the first paragraph
      const firstParagraph = post.text.split("\n\n")[0];
      text.textContent = firstParagraph;

      const image = document.createElement("img");
      image.src = "http://localhost:8080/blog/posts/image?postId=" + post.id;
      image.alt = post.text;

      const stats = document.createElement("div");
      stats.classList.add("post-stats");
      stats.innerHTML = `
        <span class="likes">&#x2764; ${post.reactionCount}</span>
        <span class="comments">&#x1F4AC; ${post.commentCount}</span>
      `;

      postElement.appendChild(title);
      postElement.appendChild(text);
      postElement.appendChild(image);
      postElement.appendChild(stats);
      // Добавляем отображение тегов
      if (post.tags && post.tags.length > 0) {
        const tagsElement = document.createElement("div");
        tagsElement.className = "post-tags";
        post.tags.forEach((tag) => {
          const tagSpan = document.createElement("span");
          tagSpan.className = "tag";
          tagSpan.textContent = `#${tag}`;
          tagsElement.appendChild(tagSpan);
        });
        postElement.appendChild(tagsElement);
      }

      postsContainer.appendChild(postElement);
    });
  }

  function displayPagination(totalPages, currentPage) {
    paginationContainer.innerHTML = "";

    const prevButton = document.createElement("button");
    prevButton.textContent = "Предыдущая";
    prevButton.classList.add("page-button");
    prevButton.disabled = currentPage === 0;
    prevButton.addEventListener("click", () => fetchPosts(currentPage - 1));
    paginationContainer.appendChild(prevButton);

    for (let i = 0; i < totalPages; i++) {
      const pageButton = document.createElement("button");
      pageButton.textContent = i + 1; // Отображаем номера страниц начиная с 1 для удобства пользователей
      pageButton.classList.add("page-button");
      if (i === currentPage) {
        pageButton.classList.add("active");
      }
      pageButton.addEventListener("click", () => fetchPosts(i));
      paginationContainer.appendChild(pageButton);
    }

    if (post.tags && post.tags.length > 0) {
      const tagsElement = document.createElement("div");
      tagsElement.className = "post-tags";
      post.tags.forEach(tag => {
        const tagSpan = document.createElement("span");
        tagSpan.className = "tag";
        tagSpan.textContent = tag;
        tagsElement.appendChild(tagSpan);
      });
      postElement.appendChild(tagsElement);
    }

    const nextButton = document.createElement("button");
    nextButton.textContent = "Следующая";
    nextButton.classList.add("page-button");
    nextButton.disabled = currentPage === totalPages - 1;
    nextButton.addEventListener("click", () => fetchPosts(currentPage + 1));
    paginationContainer.appendChild(nextButton);
  }

  // Загрузка постов при загрузке страницы
  fetchPosts();
});
