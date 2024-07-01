<template>
    <footer>
        <div class="footer-section">
            <img class="symbol-footer" src="../assets/symbol.png" />
            <span>Sweater</span>
        </div>
        <div class="footer-section">
            <p>SSAFY Final Project</p>
        </div>
        <div class="footer-section about">
            <RouterLink :to="{ name: 'About' }">About</RouterLink>
            <RouterLink :to="{ name: 'Intro' }">Intro</RouterLink>
            <RouterLink :to="{ name: 'Tech' }">Tech</RouterLink>
        </div>
    </footer>
</template>

<script setup></script>

<style scoped>
footer {
    background-color: #f9f3f3;
    height: 15vh;
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 0 5em 0 5em;
}

.footer-section {
    display: flex;
    align-items: center;
}

.symbol-footer {
    animation: 1s jump ease infinite alternate;
    margin-right: 1em;
}

.about {
    display: flex;
    flex-direction: column;
    align-items: flex-start;
}

.about a {
    text-decoration: none;
    color: black;
    margin: 5px 0;
}

@keyframes jump {
    0% {
        transform: scale(1);
    }
    100% {
        transform: scale(1.15);
    }
}
</style>
