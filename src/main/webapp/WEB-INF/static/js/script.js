document.addEventListener("DOMContentLoaded", function () {
  const postsContainer = document.getElementById("posts");
  const addPostButton = document.getElementById("addPostButton");
  const paginationContainer = document.createElement("div");
  paginationContainer.id = "pagination";
  document.querySelector(".container").appendChild(paginationContainer);

  // Добавляем элементы для поиска по тегам
  const searchContainer = document.createElement("div");
  searchContainer.className = "search-container";
  searchContainer.innerHTML = `
    <input type="text" id="tagSearch" placeholder="Поиск по тегу">
    <button id="searchButton">Поиск</button>
  `;
  document.querySelector(".container").insertBefore(searchContainer, postsContainer);

  const tagSearchInput = document.getElementById("tagSearch");
  const searchButton = document.getElementById("searchButton");

  let currentPage = 0;
  let currentTag = '';

  // Функция для получения данных с бэкенда
  async function fetchPosts(page = 0, size = 10, sort = "createdDate,desc", tag = '') {
    try {
      let url = `http://localhost:8080/blog/posts?page=${page}&size=${size}&sort=${sort}`;
      if (tag) {
        url = `http://localhost:8080/blog/posts/tags/${tag}?page=${page}&size=${size}&sort=${sort}`;
      }
      const response = await fetch(url);
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

  // Обработка нажатия кнопки добавления поста
  addPostButton.addEventListener("click", function () {
    window.location.href = "add-post.html";
  });

  // Функция для отображения постов
  function displayPosts(posts) {
    postsContainer.innerHTML = "";
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
    prevButton.addEventListener("click", () => fetchPosts(currentPage - 1, 10, "createdDate,desc", currentTag));
    paginationContainer.appendChild(prevButton);

    for (let i = 0; i < totalPages; i++) {
      const pageButton = document.createElement("button");
      pageButton.textContent = i + 1;
      pageButton.classList.add("page-button");
      if (i === currentPage) {
        pageButton.classList.add("active");
      }
      pageButton.addEventListener("click", () => fetchPosts(i, 10, "createdDate,desc", currentTag));
      paginationContainer.appendChild(pageButton);
    }

    const nextButton = document.createElement("button");
    nextButton.textContent = "Следующая";
    nextButton.classList.add("page-button");
    nextButton.disabled = currentPage === totalPages - 1;
    nextButton.addEventListener("click", () => fetchPosts(currentPage + 1, 10, "createdDate,desc", currentTag));
    paginationContainer.appendChild(nextButton);
  }

  // Обработчик события для кнопки поиска
  searchButton.addEventListener("click", function() {
    currentTag = tagSearchInput.value.trim();
    currentPage = 0;
    fetchPosts(currentPage, 10, "createdDate,desc", currentTag);
  });

  // Обработчик события для поля ввода (поиск при нажатии Enter)
  tagSearchInput.addEventListener("keyup", function(event) {
    if (event.key === "Enter") {
      searchButton.click();
    }
  });

  // Загрузка постов при загрузке страницы
  fetchPosts();
});