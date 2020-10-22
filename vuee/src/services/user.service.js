import axios from 'axios';
import authHedaer from './auth-header';
import header from "vue-resource/src/http/interceptor/header";

const API_URL = 'http://localhost:8080/api/test/';

class UserService {
    getPublicContext() {
        return axios.get(API_URL + 'all');
    }

    getUserBoard() {
        return axios.get(API_URL + 'user', {headers: authHedaer()});
    }

    getModeratorBoard() {
        return axios.get(API_URL + 'mod', {headers: authHedaer()});
    }

    getAdminBoard() {
        return axios.get(API_URL + 'admin', {headers: authHedaer()});
    }
}

export default new UserService();
