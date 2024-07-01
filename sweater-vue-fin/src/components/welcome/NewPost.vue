<template>
    <div class="container">
        <div class="view-all">
            <a href="http://localhost:5173/board" class="view-all-link">View All Posts →</a>
        </div>
        <div class="posts">
            <div v-for="(post, index) in posts" :key="index" class="post">
                <RouterLink :to="{ name: 'BoardList' }">
                    <img :src="post.image" class="post-image" />
                    <h3 class="post-title">{{ post.title }}</h3>
                    <p class="post-description">{{ post.description }}</p>
                </RouterLink>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref } from 'vue';

const posts = ref([
    {
        image: 'https://img1.daumcdn.net/thumb/R1280x0/?fname=http://t1.daumcdn.net/brunch/service/user/8paF/image/q0mLy3W6nPbqCY9JkBogJbR9rMM.png',
        title: '열정열정열정',
        description: '한사랑 산악회와 함께 합니다.',
    },
    {
        image: 'https://ojosblog.files.wordpress.com/2011/05/half_dome.jpg',
        title: '여름의 백두대간, 시원한 산행로',
        description: '여름의 백두대간, 더위를 잊게 해줄 시원한 산행로',
    },
    {
        image: 'https://www.gousa.or.kr/sites/default/files/styles/hero_l/public/images/hero_media_image/2016-11/HERO_Stock%20Photo%20Tunnel%20View.jpg?h=c9af9790&itok=qp8kEuNZ',
        title: '구례의 가을, 황금빛 단풍이 아름다운 갈대길',
        description:
            '가을이면 구례 갈대길이 황금빛 단풍으로 물든 아름다운 풍경을 선사합니다. 가을 여행을 계획 중이라면 꼭 한 번 걸어보세요.',
    },
    {
        image: 'https://image.dongascience.com/Photo/2018/11/384dd6b1706685370d313cb8ebf8164e.jpg',
        title: '용문산의 가을, 황금빛 단풍이 아름다운 풍경으로 변모합니다',
        description:
            '가을이면 용문산은 황금빛 단풍으로 덮여 더욱 아름다운 풍경을 선사합니다. 황금빛 단풍을 감상하러 용문산을 찾아보세요.',
    },
]);
</script>

<style scoped>
.container {
    max-width: 1200px;
    margin: 0 auto;
    padding: 20px;
}

.posts {
    display: flex;
    justify-content: space-between;
    flex-wrap: wrap;
}

.posts a {
    text-decoration: none;
    color: black;
}

.post {
    width: calc(25% - 10px);
    margin-bottom: 20px;
}

.post-image {
    width: 100%;
    height: auto;
}

.post-title {
    font-size: 16px;
    margin: 10px 0 5px;
}

.post-description {
    font-size: 14px;
    color: #666;
}

.view-all {
    text-align: right;
    margin-top: 2em;
    margin-bottom: 5em;
}

.view-all-link {
    color: #000;
    text-decoration: none;
    font-weight: bold;
}
</style>
