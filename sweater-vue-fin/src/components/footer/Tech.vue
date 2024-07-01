<template>
    <div>
        <h1>기술 스택</h1>
        <div class="tech-stack">
            <div class="tech-category">
                <h2>언어</h2>
                <ul>
                    <li>
                        <img
                            src="https://seeklogo.com/images/J/java-logo-7F8B35BAB3-seeklogo.com.png"
                            alt="Java"
                        />
                        <span>Java</span>
                    </li>
                    <li>
                        <img
                            src="https://seeklogo.com/images/H/html5-logo-EF92D240D7-seeklogo.com.png"
                            alt="HTML"
                        />
                        <span>HTML</span>
                    </li>
                    <li>
                        <img
                            src="https://seeklogo.com/images/C/css3-logo-8724075274-seeklogo.com.png"
                            alt="CSS"
                        />
                        <span>CSS</span>
                    </li>
                    <li>
                        <img
                            src="https://seeklogo.com/images/J/javascript-js-logo-2949701702-seeklogo.com.png"
                            alt="JavaScript"
                        />
                        <span>JavaScript</span>
                    </li>
                </ul>
            </div>
            <div class="tech-category">
                <h2>백엔드</h2>
                <ul>
                    <li>
                        <img
                            src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRqyq-9lQQNZE9gQKIw_Vgl3_tBEmypLFSGiZQFCxO97g&s"
                            alt="SpringBoot"
                        />
                        <span>SpringBoot</span>
                    </li>
                    <li>
                        <img
                            src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRkz8QzHmxtoi_49Jv4ss0-CLW5KHNFlOyyfTcpJT03Sg&s"
                            alt="MyBatis"
                        />
                        <span>MyBatis</span>
                    </li>
                    <li>
                        <img
                            src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT7yrlIM74QI5xUYOyABn3jZF6NAYjAxqh-p6yQ2l5wtg&s"
                            alt="Swagger"
                        />
                        <span>Swagger</span>
                    </li>
                </ul>
            </div>
            <div class="tech-category">
                <h2>프론트엔드</h2>
                <ul>
                    <li>
                        <img
                            src="https://seeklogo.com/images/V/vuejs-logo-17D586B587-seeklogo.com.png"
                            alt="Vue.js"
                        />
                        <span>Vue.js</span>
                    </li>
                </ul>
            </div>
            <div class="tech-category">
                <h2>DB</h2>
                <ul>
                    <li>
                        <img
                            src="https://blog.kakaocdn.net/dn/eMg94U/btrjqnbEQqL/iklYjnSGKl7J1m3jhhznD0/img.png"
                            alt="MySQL"
                        />
                        <span>MySQL</span>
                    </li>
                </ul>
            </div>
            <div class="tech-category">
                <h2>OPEN API</h2>
                <ul>
                    <li>
                        <img
                            src="https://cs.kakao.com/images/icon/img_kakaocs.png"
                            alt="Kakao Login"
                        />
                        <span>Kakao Login</span>
                    </li>
                    <li>
                        <img
                            src="https://cs.kakao.com/images/icon/img_kakaocs.png"
                            alt="Kakao Map"
                        />
                        <span>Kakao Map</span>
                    </li>
                    <li>
                        <img
                            src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTwBl5CgMuFP51OU_N49Ukp20IR7RayImCwgttcpQwhNg&s"
                            alt="OpenAI"
                        />
                        <span>OpenAI</span>
                    </li>
                </ul>
            </div>
        </div>
    </div>
</template>

<script setup></script>

<style scoped>
.tech-stack {
    display: flex;
    flex-direction: column;
    gap: 20px;
}

.tech-category {
    border: 1px solid #ddd;
    border-radius: 8px;
    padding: 20px;
}

.tech-category h2 {
    margin-bottom: 10px;
}

.tech-category ul {
    list-style: none;
    padding: 0;
    display: flex;
    flex-wrap: wrap;
    gap: 10px;
}

.tech-category li {
    display: flex;
    align-items: center;
    gap: 10px;
}

.tech-category img {
    width: 40px;
    height: 40px;
}
</style>
