<template>
    <div class="container">
        <h1>Update</h1>
        <div>
            <p>제목을 입력하세요</p>
            <label for="input-title">
                <input type="text" name="input-title" v-model="store.selectedThunder.title" />
            </label>
        </div>
        <div>
            <p>머리글을 입력하세요</p>
            <label for="input-header">
                <input type="textarea" name="input-header" v-model="store.selectedThunder.header" />
            </label>
        </div>
        <div>
            <p>출발 날짜를 입력하세요</p>
            <label for="input-start-date">
                <input
                    type="date"
                    name="input-start-date"
                    min="2024-04-01"
                    max="2024-12-31"
                    v-model="formattedStartDate"
                />
            </label>
        </div>
        <div>
            <p>도착 날짜를 입력하세요</p>
            <label for="input-end-date">
                <input
                    type="date"
                    name="input-end-date"
                    :min="store.selectedThunder.startDate"
                    max="2024-12-31"
                    v-model="formattedEndDate"
                />
            </label>
        </div>
        <div v-if="editor">
            <bubble-menu class="bubble-menu" :tippy-options="{ duration: 100 }" :editor="editor">
                <button
                    @click="editor.chain().focus().toggleBold().run()"
                    :class="{ 'is-active': editor.isActive('bold') }"
                >
                    Bold
                </button>
            </bubble-menu>
            <div>
                <button
                    @click="editor.chain().focus().toggleHeading({ level: 1 }).run()"
                    :class="{ 'is-active': editor.isActive('heading', { level: 1 }) }"
                >
                    h1
                </button>
                <button
                    @click="editor.chain().focus().toggleHeading({ level: 2 }).run()"
                    :class="{ 'is-active': editor.isActive('heading', { level: 2 }) }"
                >
                    h2
                </button>
                <button
                    @click="editor.chain().focus().toggleHeading({ level: 3 }).run()"
                    :class="{ 'is-active': editor.isActive('heading', { level: 3 }) }"
                >
                    h3
                </button>
                <button
                    @click="editor.chain().focus().setParagraph().run()"
                    :class="{ 'is-active': editor.isActive('paragraph') }"
                >
                    p
                </button>
                <button
                    @click="editor.chain().focus().toggleBold().run()"
                    :disabled="!editor.can().chain().focus().toggleBold().run()"
                    :class="{ 'is-active': editor.isActive('bold') }"
                >
                    b
                </button>
                <button
                    @click="editor.chain().focus().toggleBulletList().run()"
                    :class="{ 'is-active': editor.isActive('bulletList') }"
                >
                    ul
                </button>
                <button
                    @click="editor.chain().focus().toggleOrderedList().run()"
                    :class="{ 'is-active': editor.isActive('orderedList') }"
                >
                    ol
                </button>
                <button @click="editor.chain().focus().setHorizontalRule().run()">hr</button>
                <button @click="editor.chain().focus().setHardBreak().run()">br</button>
            </div>
            <div>
                <button
                    @click="editor.chain().focus().undo().run()"
                    :disabled="!editor.can().chain().focus().undo().run()"
                >
                    undo
                </button>
                <button
                    @click="editor.chain().focus().redo().run()"
                    :disabled="!editor.can().chain().focus().redo().run()"
                >
                    redo
                </button>
            </div>
        </div>

        <editor-content
            :editor="editor"
            v-model="store.selectedThunder.content"
            class="editor-content"
        />
        <button @click="updateThunder">Update</button>
    </div>
</template>

<script setup>
import { computed } from 'vue';
import { useEditor, EditorContent, BubbleMenu } from '@tiptap/vue-3';
import StarterKit from '@tiptap/starter-kit';
import { useThunderStore } from '@/stores/thunder';
import { useRouter } from 'vue-router';

const store = useThunderStore();
const router = useRouter();

const editor = useEditor({
    content: store.selectedThunder.content,
    extensions: [StarterKit],
});

const formatDateString = (datetime) => {
    return datetime ? datetime.substring(0, 10) : '';
};

const formattedStartDate = computed({
    get: () => formatDateString(store.selectedThunder.startDate),
    set: (value) => {
        store.selectedThunder.startDate = value;
    },
});

const formattedEndDate = computed({
    get: () => formatDateString(store.selectedThunder.endDate),
    set: (value) => {
        store.selectedThunder.endDate = value;
    },
});

const updateThunder = () => {
    store.selectedThunder.content = editor.value.getHTML();
    store.modifyThunder();
    router.push({ name: 'ThunderList' });
};
</script>

<style scoped>
.bubble-menu {
    display: flex;
    background-color: #0d0d0d;
    padding: 0.5rem;
    border-radius: 0.5rem;
}

.bubble-menu button {
    border: none;
    background: none;
    color: #fff;
    font-size: 0.85rem;
    font-weight: 500;
    padding: 0 0.5rem;
    opacity: 0.6;
    transition: opacity 0.3s ease;
}

.bubble-menu button:hover,
.bubble-menu button.is-active {
    opacity: 1;
}

.container {
    margin: 0 auto;
    padding: 0 1rem;
    max-width: 800px;
}

select,
input[type='text'],
input[type='textarea'],
input[type='file'] {
    width: 100%;
    padding: 0.5rem;
    border: 1px solid #ccc;
    border-radius: 4px;
    box-sizing: border-box;
    font-size: 1rem;
    margin-top: 0.5rem;
}

select {
    width: auto;
    min-width: 200px;
}

label {
    display: block;
    margin-bottom: 0.5rem;
}

h1 {
    font-size: 1.5rem;
    margin-bottom: 1rem;
}

p {
    font-size: 1rem;
    margin-bottom: 0.5rem;
}

button {
    display: inline-flex;
    align-content: space-between;
    padding: 0.45rem 0.85rem;
    font-size: 1rem;
    color: #fff;
    background-color: #007bff;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    transition: background-color 0.3s ease;
    margin: 0.2em 0.2em 0.2em 0.2em;
}

button:hover {
    background-color: #0056b3;
}

button:disabled {
    background-color: #6c757d;
    cursor: not-allowed;
}

h2 {
    font-size: 1.2rem;
    margin-top: 1rem;
}

.editor-content {
    min-height: 300px;
    border: 1px solid #ccc;
    border-radius: 4px;
    padding: 1rem;
    box-sizing: border-box;
}
</style>
