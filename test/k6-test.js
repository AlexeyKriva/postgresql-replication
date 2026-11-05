import http from 'k6/http';
import { check } from 'k6';

const BASE_URL = 'http://localhost:8090';

export const options = {
    scenarios: {
        products: {
            executor: 'constant-arrival-rate',

            rate: 5,
            timeUnit: '1s',

            duration: '60s',

            preAllocatedVUs: 10,

            maxVUs: 20,
        },
    },
};

export default function () {
    const id = Math.floor(Math.random() * 10000) + 1;

    const count = Math.floor(Math.random() * 16) + 5;

    const getResponse = http.get(
        `${BASE_URL}/api/v1/products/${id}`
    );

    check(getResponse, {
        'GET status is 200': (r) => r.status === 200,
    });

    const postResponse = http.post(
        `${BASE_URL}/api/v1/products/generate?count=${count}`
    );

    check(postResponse, {
        'POST status is 200': (r) => r.status === 200,
    });
}