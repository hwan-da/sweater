import { ref, computed } from 'vue';
import { defineStore } from 'pinia';
import axios from 'axios';

const REST_MOUNTAIN_API = `http://localhost:8080/mountain`;

export const useMountainStore = defineStore('mountain', () => {
    const mountainInformation = ref({});

    const loadMountain = async (mntnCode) => {
        try {
            const response = await axios.get(`${REST_MOUNTAIN_API}/${mntnCode}`);
            mountainInformation.value = response.data;

            response.data.forEach((trail) => {
                let flattenedPaths = [];

                const parsedPaths = JSON.parse(trail.paths);

                if (parsedPaths && parsedPaths.length > 0) {
                    parsedPaths.forEach((path) => {
                        path.forEach(([lat, lng]) => {
                            flattenedPaths.push({ lat, lng });
                        });
                    });
                }

                trail.paths = flattenedPaths;
            });
        } catch (error) {}
    };
    return { mountainInformation, loadMountain };
});
